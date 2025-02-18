package com.example.phoneBook.repository;

import com.example.phoneBook.entity.MembersContactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembersContactsRepository extends JpaRepository<MembersContactsEntity, Integer> {
    List<MembersContactsEntity> findByMemberContactMaps_Member_MemberId(String memberId);

    Optional<MembersContactsEntity> findById(Integer contactid);
}
