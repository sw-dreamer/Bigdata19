package com.example.phoneBook.dto;

import com.example.phoneBook.entity.MemberEntity;
import com.example.phoneBook.entity.MembersContactsEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class MembersContactsDTO {
    private Integer contactId;
    private String name;
    private String phonenumber;
    private String address;
    private String gubunId;
    private byte[] photo;
    private String createDate;
    private String lastUpdate;

    public static MembersContactsDTO toMembersContactsDTO(MembersContactsEntity memberEntity) {
        MembersContactsDTO membersContactsDTO = new MembersContactsDTO();

        membersContactsDTO.setContactId(memberEntity.getContactId());
        membersContactsDTO.setName(memberEntity.getName());
        membersContactsDTO.setPhonenumber(memberEntity.getPhonenumber());
        membersContactsDTO.setAddress(memberEntity.getAddress());
        membersContactsDTO.setGubunId(memberEntity.getGubunId());
        membersContactsDTO.setPhoto(memberEntity.getPhoto());

        membersContactsDTO.setCreateDate(memberEntity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        membersContactsDTO.setLastUpdate(memberEntity.getLastUpdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        log.info("membersContactsDTO: " + membersContactsDTO.toString());
        return membersContactsDTO;
    }
}
