package com.kevin.courserefactor.base.repository;

import com.kevin.courserefactor.base.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {}
