package com.example.phoneBook.repository;

import com.example.phoneBook.entity.MembersContactsEntity;
import com.example.phoneBook.projection.PhoneMoimProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneFourthRepository extends CrudRepository<MembersContactsEntity, Integer> {
    @Query(value =  "SELECT mm.memberid, mc.contactId, mc.name, mc.phonenumber, mc.address, TO_BASE64(mc.photo) as bhotobase, mi.gubunName  " +
            "FROM memberscontacts mc                                                                                                        " +
            "INNER JOIN moim mi                                                                                                             " +
            "ON mi.gubun_id = mc.gubun_id                                                                                                   " +
            "Inner join membercontactmap mcm                                                                                                " +
            "on mc.contactid = mcm.contactid                                                                                                " +
            "Inner join members mm                                                                                                          " +
            "on mcm.memberid =mm.memberid                                                                                                   " +
            "where mm.memberId =:id                                                                                                         "
    ,nativeQuery = true)
    List<PhoneMoimProjection> phoneMoimData(@Param("id") String id);

}
