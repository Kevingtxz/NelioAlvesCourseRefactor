package com.kevin.courserefactor.base.repository;

import com.kevin.courserefactor.base.domain.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {}
