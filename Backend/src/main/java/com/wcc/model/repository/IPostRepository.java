package com.wcc.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcc.model.entity.Postcodelatlng;

@Repository
public interface IPostRepository extends JpaRepository<Postcodelatlng, Integer> {

	Postcodelatlng findByPostcode(String postcodePrefix);
}