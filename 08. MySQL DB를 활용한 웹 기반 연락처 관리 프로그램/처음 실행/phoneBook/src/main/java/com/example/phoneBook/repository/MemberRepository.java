package com.example.phoneBook.repository;

import com.example.phoneBook.entity.MemberEntity;
import com.example.phoneBook.projection.MemberProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends CrudRepository<MemberEntity, Integer> {
    @Query(value =
            "select memberId,password,username from members where memberId =:id"
            ,nativeQuery = true)
    List<MemberProjection> findMemberData(@Param("id") String id);


}
