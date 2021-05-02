package com.vicras.repository;

import com.vicras.entity.ApprovedStatus;
import com.vicras.entity.Comment;
import com.vicras.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDestinationUserAndApprovedStatusIn(User destinationUser, Collection<ApprovedStatus> approvedStatus);

    List<Comment> findAllByApprovedStatusIn(List<ApprovedStatus> statuses);

    Optional<Comment> findByMessage(String message);

    Optional<Comment> findByIdAndApprovedStatus(Long id, ApprovedStatus approvedStatus);

    @Modifying
    @Query("update Comment c set c.approvedStatus =?2 where c.id in ?1 and c.approvedStatus in ?3")
    void updateObjectStatusWithIdIn(List<Long> idsToApprove, ApprovedStatus approved, Collection<ApprovedStatus> oldStatus);
}
