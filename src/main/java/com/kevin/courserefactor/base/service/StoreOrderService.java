package com.kevin.courserefactor.base.service;

import com.kevin.courserefactor.base.domain.Category;
import com.kevin.courserefactor.base.domain.PaymentWithTicket;
import com.kevin.courserefactor.base.domain.StoreOrder;
import com.kevin.courserefactor.base.domain.StoreOrderItem;
import com.kevin.courserefactor.base.domain.enums.PaymentState;
import com.kevin.courserefactor.base.repository.PaymentRepository;
import com.kevin.courserefactor.base.repository.ProductRepository;
import com.kevin.courserefactor.base.repository.StoreOrderItemRepository;
import com.kevin.courserefactor.base.repository.StoreOrderRepository;
import com.kevin.courserefactor.base.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class StoreOrderService {

    @Autowired
    private StoreOrderRepository repo;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StoreOrderItemRepository storeOrderItemRepository;

    @Autowired
    private ProductService productService;


    public StoreOrder find(Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Object not found. ID: " + id
                        + ", Type: " + Category.class.getName()));
    }

    @Transactional
    public StoreOrder insert(StoreOrder obj) {
        obj.setId(null);
        obj.setInstant(new Date());
        obj.getPayment().setPaymentState(PaymentState.PENDING);
        obj.getPayment().setStoreOrder(obj);

        // no mundo real é feito com webservice
        if (obj.getPayment() instanceof PaymentWithTicket) {
            PaymentWithTicket payBank = (PaymentWithTicket) obj.getPayment();
            ticketService.completePaymentWithTicket(payBank, obj.getInstant());
        }
        obj = repo.save(obj);
        for (StoreOrderItem soi : obj.getItems()) {
            soi.setDiscount(0d);
            soi.setPrice(productService.find(soi.getProduct().getId()).getPrice());
            soi.setStoreOrder(obj);
        }
        storeOrderItemRepository.saveAll(obj.getItems());
        return obj;
    }
}
