package com.example.phoneBook.dto;

import com.example.phoneBook.entity.MembersContactsEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
@AllArgsConstructor
public class MembersContactsDTO {
    private Integer contactid;
    private String name;
    private String phonenumber;
    private String address;
    private String gubunId;
    private MultipartFile photo;
    private String createDate;
    private String lastUpdate;
    private String encodedPhoto;

    public static MembersContactsDTO toMembersContactsDTO(MembersContactsEntity memberEntity) {
        MembersContactsDTO membersContactsDTO = new MembersContactsDTO();

        membersContactsDTO.setContactid(memberEntity.getContactid());
        membersContactsDTO.setName(memberEntity.getName());
        membersContactsDTO.setPhonenumber(memberEntity.getPhonenumber());
        membersContactsDTO.setAddress(memberEntity.getAddress());
        membersContactsDTO.setGubunId(memberEntity.getGubunId());

        if (memberEntity.getPhoto() != null && memberEntity.getPhoto().length > 0) {
            membersContactsDTO.setEncodedPhoto("C:/big19/spring_dev/phoneBook/uploadfile" + "/your-file-name");
        }

        membersContactsDTO.setCreateDate(memberEntity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        membersContactsDTO.setLastUpdate(memberEntity.getLastUpdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        log.info("membersContactsDTO: " + membersContactsDTO.toString());
        return membersContactsDTO;
    }

}
