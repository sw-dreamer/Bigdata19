package com.example.phoneBook.repository;

import com.example.phoneBook.dto.MembersContacts;
import com.example.phoneBook.entity.MembersContactsEntity;
import com.example.phoneBook.projection.MembersContactsMoimProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends CrudRepository<MembersContactsEntity, Integer> {

    @Query(value =
            "SELECT mc.contactId, mc.name, mc.phonenumber, mc.address, mc.photo, mi.gubunName       " +
                    "FROM memberscontacts mc                                                                " +
                    "INNER JOIN moim mi                                                                     " +
                    "ON mi.gubunId = mc.gubunId                                                             ;"
            , nativeQuery = true)
    List<MembersContactsMoimProjection> findMembersContactsMoim();
}
