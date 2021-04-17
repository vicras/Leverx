package com.vicras.repository;

import com.vicras.entity.GameObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Long> {
}
