package com.example.phoneBook.repository;

import com.example.phoneBook.entity.MembersContactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberContactsThirdRepository extends JpaRepository<MembersContactsEntity, Long> {
    Optional<MembersContactsEntity> findById(Long contactid);

    List<MembersContactsEntity> findByNameContainingIgnoreCase(String name);
}
