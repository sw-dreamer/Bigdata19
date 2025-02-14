package com.example.phoneBook.repository;

import com.example.phoneBook.entity.MembersContactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneSecondRepository extends JpaRepository<MembersContactsEntity,Long> {
}
