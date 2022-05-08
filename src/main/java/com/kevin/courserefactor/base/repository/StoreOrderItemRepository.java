package com.kevin.courserefactor.base.repository;

import com.kevin.courserefactor.base.domain.StoreOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreOrderItemRepository extends JpaRepository<StoreOrderItem, Integer> {}
