package com.example.phoneBook.repository;


import com.example.phoneBook.entity.MemberContactMapEntity;
import com.example.phoneBook.projection.MemberProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberContactMapRepository extends JpaRepository<MemberContactMapEntity, Integer> {
    @Query(value =
            "SELECT mm.memberid, mc.contactId, mc.name, mc.phonenumber, mc.address, TO_BASE64(mc.photo) as bhotobase, mi.gubunName  " +
                    "FROM memberscontacts mc                                                                                        " +
                    "INNER JOIN moim mi                                                                                             " +
                    "ON mi.gubun_id = mc.gubun_id                                                                                   " +
                    "Inner join membercontactmap mcm                                                                                " +
                    "on mc.contactid = mcm.contactid                                                                                " +
                    "Inner join members mm                                                                                          " +
                    "on mcm.memberid =mm.memberid                                                                                   " +
                    "where mm.memberid=:memid and mc.contactId =:contid"
            ,nativeQuery = true)
    List<MemberProjection> findMemberData(@Param("memid") String memberid,@Param("contid")Long contactid);


}
