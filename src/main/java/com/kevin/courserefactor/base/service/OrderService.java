package com.kevin.courserefactor.base.service;

import com.kevin.courserefactor.base.domain.*;
import com.kevin.courserefactor.base.domain.enums.PaymentState;
import com.kevin.courserefactor.base.domain.enums.ProfileRole;
import com.kevin.courserefactor.base.repository.PaymentRepository;
import com.kevin.courserefactor.base.repository.OrderItemRepository;
import com.kevin.courserefactor.base.repository.OrderRepository;
import com.kevin.courserefactor.base.service.email.EmailService;
import com.kevin.courserefactor.base.service.exceptions.AuthorizationException;
import com.kevin.courserefactor.base.service.exceptions.ObjectNotFoundException;
import com.kevin.courserefactor.base.service.ticket.TicketService;
import com.kevin.courserefactor.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderRepository repo;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderItemRepository storeOrderItemRepository;
    @Autowired
    private EmailService emailService;


    public Page<OrderEntity> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        UserSS user = UserService.authenticated();
        if (user == null)
            throw new AuthorizationException("Access denied");
        ClientEntity client = clientService.find(user.getId());
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findByClient(client, pageRequest);
    }

    public OrderEntity find(Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Object not found. ID: " + id
                        + ", Type: " + Category.class.getName()));
    }

    @Transactional
    public OrderEntity insert(OrderEntity obj) {
        obj.setId(null);
        obj.setInstant(new Date());
        obj.setClient(clientService.find(obj.getClient().getId()));
        obj.getPayment().setPaymentState(PaymentState.PENDING);
        obj.getPayment().setStoreOrder(obj);

        // no mundo real é feito com webservice
        if (obj.getPayment() instanceof PaymentWithTicketEntity) {
            PaymentWithTicketEntity payBank = (PaymentWithTicketEntity) obj.getPayment();
            ticketService.completePaymentWithTicket(payBank, obj.getInstant());
        }
        obj = repo.save(obj);
        for (OrderItemEntity soi : obj.getItems()) {
            soi.setDiscount(0d);
            soi.setProduct(productService.find(soi.getProduct().getId()));
            soi.setPrice(soi.getProduct().getPrice());
            soi.setStoreOrder(obj);
        }
        storeOrderItemRepository.saveAll(obj.getItems());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }
}
