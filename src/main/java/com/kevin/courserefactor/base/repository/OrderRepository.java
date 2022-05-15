package com.kevin.courserefactor.base.repository;

import ch.qos.logback.core.net.server.Client;
import com.kevin.courserefactor.base.domain.ClientEntity;
import com.kevin.courserefactor.base.domain.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Transactional(readOnly = true)
    Page<OrderEntity> findByClient(ClientEntity client, Pageable pageable);
}
