package com.vicras.repository;

import com.vicras.entity.ApprovedStatus;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Long> {
    List<GameObject> findAllByOwner(User owner);
    List<GameObject> findAllByApprovedStatusIn(List<ApprovedStatus> approvedStatus);

    @Query("update GameObject g set g.approvedStatus =?2 where g.id in ?1")
    void updateObjectStatusWithIdIn(Collection<Long> gameObjectsIds, ApprovedStatus status);
}
