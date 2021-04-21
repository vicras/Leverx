package com.vicras.repository;

import com.vicras.entity.EntityStatus;
import com.vicras.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.entityStatus = ?1")
    List<User> findAllWithStatus(EntityStatus entityStatus);

    Optional<User> findByEmail(String email);

}
