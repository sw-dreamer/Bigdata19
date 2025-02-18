package com.example.phoneBook.repository;

import com.example.phoneBook.entity.MemberEntity;
import com.example.phoneBook.projection.MemberProjection;
import com.example.phoneBook.projection.MemberSecondProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<MemberEntity, Integer> {
    @Query(value =
            "select memberId,password,username from members where memberId =:id"
            ,nativeQuery = true)
    List<MemberProjection> findMemberData(@Param("id") String id);

    @Query(value = "select memberid from members where email =:email",nativeQuery = true)
    List<MemberSecondProjection> findMemberByemail(@Param("email") String email);

    Optional<MemberEntity> findByMemberId(String memberId);


}
