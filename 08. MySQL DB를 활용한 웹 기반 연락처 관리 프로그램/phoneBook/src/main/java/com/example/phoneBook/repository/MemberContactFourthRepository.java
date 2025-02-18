package com.example.phoneBook.repository;

import com.example.phoneBook.entity.MembersContactsEntity;
import com.example.phoneBook.projection.MemberContactFourthProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberContactFourthRepository extends JpaRepository<MembersContactsEntity, Integer> {
    @Query(value = "SELECT mm.memberid, mc.contactId, mc.name, mc.phonenumber, mc.address, TO_BASE64(mc.photo) as bhotobase, mi.gubunName   " +
            "FROM memberscontacts mc                                                                                                        " +
            "INNER JOIN moim mi ON mi.gubun_id = mc.gubun_id                                                                                " +
            "INNER JOIN membercontactmap mcm ON mc.contactid = mcm.contactid                                                                " +
            "INNER JOIN members mm ON mcm.memberid = mm.memberid                                                                            " +
            "WHERE mm.memberid = :memberId AND mc.name LIKE %:query%", nativeQuery = true)
    List<MemberContactFourthProjection> searchContactsByNameAndMemberId(@Param("memberId") String memberId, @Param("query") String query);
}
