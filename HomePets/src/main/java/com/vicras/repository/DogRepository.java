package com.vicras.repository;

import com.vicras.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author viktar hraskou
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}
