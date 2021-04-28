package com.vicras.repository;

import com.vicras.entity.ApprovedStatus;
import com.vicras.entity.EntityStatus;
import com.vicras.entity.GameObject;
import com.vicras.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameObjectRepository extends JpaRepository<GameObject, Long> {
    List<GameObject> findAllByOwner(User owner);

    List<GameObject> findAllByApprovedStatusIn(List<ApprovedStatus> approvedStatus);

    List<GameObject> findAllByEntityStatusIsAndApprovedStatusIs(EntityStatus entityStatus, ApprovedStatus approvedStatus);

    Optional<GameObject> findByTitle(String title);

    @Modifying
    @Query("update GameObject g set g.entityStatus =?2 where g.id = ?1")
    void updateEntityStatusById(Long objectId, EntityStatus status);

    @Modifying
    @Query("update GameObject g set g.approvedStatus =?2 where g.id in ?1 and g.approvedStatus in ?3")
    void updateObjectStatusWithIdIn(Collection<Long> gameObjectsIds, ApprovedStatus status, Collection<ApprovedStatus> oldStatus);
}
