package com.example.phoneBook.service;

import com.example.phoneBook.dto.MemberDTO;
import com.example.phoneBook.entity.MemberEntity;
import com.example.phoneBook.projection.MemberProjection;
import com.example.phoneBook.repository.MemberRepository;
import com.example.phoneBook.repository.MemberSecondRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberSecondRepository memberSecondRepository;


    public void save(MemberDTO memberDTO) throws NoSuchAlgorithmException {
        MemberEntity userEntity = MemberEntity.toMemberEntity(memberDTO);

        String password = memberDTO.getPassword();
        String salt = "bigdata19";
        String passwordWithSalt = salt + password;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(passwordWithSalt.getBytes());
        String hashedPassword = Base64.getEncoder().encodeToString(encodedHash);

        userEntity.setPassword(hashedPassword);

        log.info("userEntity: " + userEntity.toString());
        log.info("SHA-256 password: " + hashedPassword);

        memberSecondRepository.save(userEntity);
    }


    public List<MemberProjection> isMemberIdDuplicate(String memberId) {
        return memberRepository.findMemberData(memberId);

    }


    public boolean login(String memberId, String password) {
        Optional<MemberEntity> memberEntityOptional = memberSecondRepository.findByMemberid(memberId);
        log.info("memberEntityOptional: {}", memberEntityOptional.toString());

        if (memberEntityOptional.isPresent()) {
            MemberEntity memberEntity = memberEntityOptional.get();
            log.info("memberEntity: {}", memberEntity.toString());

            String salt = "bigdata19";
            String passwordWithSalt = salt + password;

            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] encodedHash = digest.digest(passwordWithSalt.getBytes());
                String hashedPassword = Base64.getEncoder().encodeToString(encodedHash);

                return hashedPassword.equals(memberEntity.getPassword());
            } catch (NoSuchAlgorithmException e) {
                log.error("Algorithm not found: {}", e.getMessage());
                return false;
            }
        }

        return false;
    }





}

