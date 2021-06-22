package com.vicras.repository;

import com.vicras.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author viktar hraskou
 */
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}
