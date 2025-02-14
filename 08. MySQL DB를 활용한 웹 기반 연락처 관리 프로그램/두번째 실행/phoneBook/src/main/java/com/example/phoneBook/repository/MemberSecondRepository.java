package com.example.phoneBook.repository;

import com.example.phoneBook.dto.MemberDTO;
import com.example.phoneBook.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberSecondRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByMemberid(String memberId);
    Optional<MemberEntity> findByEmail(String email);

    Optional<MemberEntity> findByMemberidAndEmail(String memberId, String email);
}
