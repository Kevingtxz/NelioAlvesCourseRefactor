package com.kevin.courserefactor.base.repository;

import com.kevin.courserefactor.base.domain.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

    @Transactional(readOnly = true)
    ClientEntity findByEmail(String email);
}
