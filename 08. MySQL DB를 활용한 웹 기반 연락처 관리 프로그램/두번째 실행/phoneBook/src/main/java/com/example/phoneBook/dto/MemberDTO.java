package com.example.phoneBook.dto;

import com.example.phoneBook.entity.MemberEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j

public class MemberDTO {
    private Long memberno;

    private String memberid;

    private String username;

    private String password;
    private String email;

    private String createDate;

    private String lastUpdate;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberno(memberEntity.getMemberno());
        memberDTO.setMemberid(memberEntity.getMemberid());
        memberDTO.setUsername(memberEntity.getUsername());
        memberDTO.setPassword(memberEntity.getPassword());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setCreateDate(memberEntity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        memberDTO.setLastUpdate(memberEntity.getLastUpdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        log.debug("memberDTO"+memberDTO.toString());
        return memberDTO;
    }



}
