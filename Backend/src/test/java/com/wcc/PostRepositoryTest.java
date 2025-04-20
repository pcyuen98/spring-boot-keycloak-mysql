package com.wcc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.wcc.model.entity.Postcodelatlng;
import com.wcc.model.repository.IPostRepository;

/**
 * Unit tests for {@link IPostRepository} using H2 in-memory database.
 * <p>
 * This test class verifies basic CRUD operations and custom query logic
 * defined in the {@code IPostRepository} interface.
 * It uses the "test" Spring profile and the {@code @DataJpaTest} annotation
 * to auto-configure an embedded database and JPA-related components.
 * </p>
 * 
 * <p>
 * Make sure that the `application-test.properties` is properly configured to use
 * H2 and Hibernate DDL auto settings to enable schema generation at runtime.
 * </p>
 * 
 * @author CY
 */
@DataJpaTest
@ActiveProfiles("test") // Uses application-test.properties for H2 setup
public class PostRepositoryTest {

    @Autowired
    private IPostRepository postRepository;

    /**
     * Tests that a {@link Postcodelatlng} entity can be saved and retrieved
     * correctly by its postcode using {@code findByPostcode}.
     */
    @Test
    @DisplayName("Save and retrieve postcode by value")
    public void testSaveAndFindByPostcode() {
        // Arrange
        Postcodelatlng entity = new Postcodelatlng();
        entity.setPostcode("50088");
        entity.setLatitude(3.1506);
        entity.setLongitude(101.7072);

        // Act
        postRepository.save(entity);
        Postcodelatlng found = postRepository.findByPostcode("50088");

        // Assert
        assertNotNull(found);
        assertEquals("50088", found.getPostcode());
        assertEquals(3.1506, found.getLatitude());
        assertEquals(101.7072, found.getLongitude());
    }

    /**
     * Tests that {@code findByPostcode} returns {@code null}
     * when the given postcode does not exist in the database.
     */
    @Test
    @DisplayName("Return null for non-existing postcode")
    public void testFindByPostcodeNotFound() {
        Postcodelatlng found = postRepository.findByPostcode("99999");
        assertNull(found);
    }
}
