package com.wcc.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcc.model.entity.Postcodelatlng;

/**
 * Repository interface for accessing and managing postcode geolocation data.
 * <p>
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * for the {@link Postcodelatlng} entity. It also includes a custom method to
 * retrieve geolocation data based on a postcode prefix.
 * </p>
 *
 * @author CY 
 */
@Repository
public interface IPostRepository extends JpaRepository<Postcodelatlng, Integer> {

	Postcodelatlng findByPostcode(String postcodePrefix);
}