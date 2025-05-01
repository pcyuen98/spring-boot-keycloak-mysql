package com.wcc;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.wcc.model.entity.Postcodelatlng;
import com.wcc.model.repository.IPostRepository;

/**
 * This class contains integration tests for the {@link IPostRepository}
 * focusing on demonstrating the behavior of lazy and eager loading of
 * entity relationships within the Spring Data JPA context.
 */
@SpringBootTest
public class PostRepositoryEagerTest {

	/**
	 * The repository interface for accessing {@link Postcodelatlng} entities
	 * from the database. Spring will automatically inject an instance of this
	 * repository.
	 */
	@Autowired
	private IPostRepository postRepository;

	/**
	 * This test method demonstrates the difference between lazy and eager loading
	 * of related entities when fetching {@link Postcodelatlng} objects.
	 * By using {@link Transactional}, we ensure that the session remains open
	 * for the duration of the test, allowing us to observe lazy loading behavior
	 * if the relationships in {@link Postcodelatlng} are configured as such.
	 */
	@Test
	@Transactional
	public void demoLazyAndEager() {
		// Fetch all Postcodelatlng entities from the database.
		List<Postcodelatlng> p = postRepository.findAll();

		// This print statement serves as a marker to indicate where a new SQL
		// query might be executed if a related entity is lazily loaded and hasn't
		// been fetched yet. Debugging the application at this point will reveal
		// whether a new query is triggered when accessing a related property
		// of the Postcodelatlng entity for the first time.
		System.out.println("Expecting new SQL query if lazy type. Need a debugging to see the result-------------");

		// Accessing the first Postcodelatlng entity in the list and printing its
		// details. If any related entities within Postcodelatlng are configured
		// for lazy loading, accessing them here (if the toString() method or
		// other logic in Postcodelatlng attempts to access them) will trigger
		// a separate database query within the transactional context. If they
		// are eagerly loaded, the related data would have been fetched in the
		// initial `findAll()` query.
		System.out.println("Details------------->  " + p.get(0));
	}
}