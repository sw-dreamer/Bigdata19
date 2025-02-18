package com.example.phoneBook.repository;

import com.example.phoneBook.entity.MemberContactMapEntity;
import com.example.phoneBook.entity.MembersContactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberContactsSecondRepository  extends JpaRepository<MembersContactsEntity, Integer> {
    @Modifying
    @Query(value = "DELETE FROM memberscontacts mcm WHERE mcm.contactid = :id",nativeQuery = true)
    void deleteContact(@Param("id") Long id);

}
