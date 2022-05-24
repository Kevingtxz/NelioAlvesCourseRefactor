package com.kevin.courserefactor.config.db_simulation.service;

import com.kevin.courserefactor.base.domain.*;
import com.kevin.courserefactor.base.domain.enums.ClientType;
import com.kevin.courserefactor.base.domain.enums.PaymentState;
import com.kevin.courserefactor.base.domain.enums.ProfileRole;
import com.kevin.courserefactor.base.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderRepository storeOrderRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderItemRepository storeOrderItemRepository;


    public void instantiateTestDatabase() {

        try {

            Category cat1 = new Category(null, "Informática");
            Category cat2 = new Category(null, "Escritório");
            Category cat3 = new Category(null, "Cama mesa e banho");
            Category cat4 = new Category(null, "Eletrônicos");
            Category cat5 = new Category(null, "Jardinagem");
            Category cat6 = new Category(null, "Decoração");
            Category cat7 = new Category(null, "Perfumaria");

            ProductEntity p1 = new ProductEntity(null, "Computador", 2000.00);
            ProductEntity p2 = new ProductEntity(null, "Impressora", 800.00);
            ProductEntity p3 = new ProductEntity(null, "Mouse", 80.00);
            ProductEntity p4 = new ProductEntity(null, "Mesa de escritório", 3000.00);
            ProductEntity p5 = new ProductEntity(null, "Toalha", 50.00);
            ProductEntity p6 = new ProductEntity(null, "Colcha", 200.00);
            ProductEntity p7 = new ProductEntity(null, "TV true color", 1200.00);
            ProductEntity p8 = new ProductEntity(null, "Roçadeira", 800.00);
            ProductEntity p9 = new ProductEntity(null, "Abajour", 100.00);
            ProductEntity p10 = new ProductEntity(null, "Pendente", 180.00);
            ProductEntity p11 = new ProductEntity(null, "Shampoo", 90.00);

            cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
            cat2.getProducts().addAll(Arrays.asList(p2, p4));
            cat3.getProducts().addAll(Arrays.asList(p5, p6));
            cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
            cat5.getProducts().addAll(Arrays.asList(p8));
            cat6.getProducts().addAll(Arrays.asList(p9, p10));
            cat7.getProducts().addAll(Arrays.asList(p11));

            p1.getCategories().addAll(Arrays.asList(cat1, cat4));
            p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
            p3.getCategories().addAll(Arrays.asList(cat1, cat4));
            p4.getCategories().addAll(Arrays.asList(cat2));
            p5.getCategories().addAll(Arrays.asList(cat3));
            p6.getCategories().addAll(Arrays.asList(cat3));
            p7.getCategories().addAll(Arrays.asList(cat4));
            p8.getCategories().addAll(Arrays.asList(cat5));
            p9.getCategories().addAll(Arrays.asList(cat6));
            p10.getCategories().addAll(Arrays.asList(cat6));
            p11.getCategories().addAll(Arrays.asList(cat7));

            categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
            productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));


            StateEntity st1 = new StateEntity(null, "Minas Gerais");
            StateEntity st2 = new StateEntity(null, "São Paulo");

            CityEntity c1 = new CityEntity(null, "Uberlândia", st1);
            CityEntity c2 = new CityEntity(null, "São Paulo", st2);
            CityEntity c3 = new CityEntity(null, "Campinas", st2);

            st1.getCities().addAll(Arrays.asList(c1));
            st2.getCities().addAll(Arrays.asList(c2, c3));

            stateRepository.saveAll(Arrays.asList(st1, st2));
            cityRepository.saveAll(Arrays.asList(c1, c2, c3));

            ClientEntity cli1 = new ClientEntity(null, "Maria Silva", "user@email.com", "372498474", ClientType.NATURALPERSON, pe.encode("user"));
            cli1.getPhoneNumbers().addAll(Arrays.asList("27363323", "93838393"));

            ClientEntity cli2 = new ClientEntity(null, "Julio Alvares", "admin@email.com", "57848193055", ClientType.NATURALPERSON, pe.encode("admin"));
            cli2.addProfileRole(ProfileRole.ADMIN);

            AddressEntity a1 = new AddressEntity(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
            AddressEntity a2 = new AddressEntity(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
            AddressEntity a3 = new AddressEntity(null, "Avenida Floriano", "2106", null, "Centro", "28777012", cli2, c2);

            cli1.getAddresses().addAll(Arrays.asList(a1, a2));
            cli1.getAddresses().addAll(Arrays.asList(a1, a2));
            cli2.getAddresses().addAll(Arrays.asList(a3));

            clientRepository.saveAll(Arrays.asList(cli1, cli2));
            addressRepository.saveAll(Arrays.asList(a1, a2, a3));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            OrderEntity so1 = new OrderEntity(null, sdf.parse("30/09/2017 10:32"), cli1, a1);
            OrderEntity so2 = new OrderEntity(null, sdf.parse("10/10/2017 19:35"), cli1, a2);


            PaymentEntity pay1 = new PaymentWithCardEntity(null, PaymentState.PAID, so1, 6);
            so1.setPayment(pay1);

            PaymentEntity pay2 = new PaymentWithTicketEntity(null, PaymentState.PENDING, so2, sdf.parse("20/10/2017 00:00"), null);
            so2.setPayment(pay2);

            cli1.getStoreOrders().addAll(Arrays.asList(so1, so2));

            storeOrderRepository.saveAll(Arrays.asList(so1, so2));
            paymentRepository.saveAll(Arrays.asList(pay1, pay2));

            OrderItemEntity soi1 = new OrderItemEntity(so1, p1, 0.00, 1, 2000.00);
            OrderItemEntity soi2 = new OrderItemEntity(so1, p3, 0.00, 2, 80.00);
            OrderItemEntity soi3 = new OrderItemEntity(so2, p2, 100.00, 1, 800.00);

            so1.getItems().addAll(Arrays.asList(soi1, soi2));
            so2.getItems().addAll(Arrays.asList(soi3));

            p1.getItems().addAll(Arrays.asList(soi1));
            p2.getItems().addAll(Arrays.asList(soi3));
            p3.getItems().addAll(Arrays.asList(soi2));

            storeOrderItemRepository.saveAll(Arrays.asList(soi1, soi2, soi3));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
