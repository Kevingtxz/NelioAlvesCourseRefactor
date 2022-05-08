package com.kevin.courserefactor.base.repository;

import com.kevin.courserefactor.base.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {}
