package com.vicras.repository;

import com.vicras.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author viktar hraskou
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
