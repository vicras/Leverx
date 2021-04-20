package com.vicras.repository;

import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Long> {
    List<GameObject> findAllByOwner(User owner);
}
