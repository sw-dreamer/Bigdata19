package com.example.phoneBook.repository;

import com.example.phoneBook.entity.MembersContactsEntity;
import org.springframework.data.repository.CrudRepository;

public interface PhoneThirdRepository extends CrudRepository<MembersContactsEntity, Long> {
}
