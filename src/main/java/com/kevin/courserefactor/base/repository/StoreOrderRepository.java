package com.kevin.courserefactor.base.repository;

import com.kevin.courserefactor.base.domain.StoreOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreOrderRepository extends JpaRepository<StoreOrder, Integer> {}
