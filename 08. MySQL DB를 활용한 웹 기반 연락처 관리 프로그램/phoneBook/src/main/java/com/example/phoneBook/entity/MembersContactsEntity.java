package com.example.phoneBook.entity;

import com.example.phoneBook.dto.MembersContactsDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "memberscontacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class MembersContactsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactid")
    private Integer contactid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phonenumber", nullable = false)
    private String phonenumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "gubun_id")
    private String gubunId;

    @Column(name = "photo", nullable = false)
    private byte[] photo;

    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @PrePersist
    public void prePersist() {
        LocalDateTime currentTime = LocalDateTime.now();
        this.createDate = currentTime;
        this.lastUpdate = currentTime;
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "membersContact", cascade = CascadeType.ALL)
    private List<MemberContactMapEntity> memberContactMaps;

    public static MembersContactsEntity toMemberContactsEntity(MembersContactsDTO membersContacts) {
        MembersContactsEntity membersContactsEntity = new MembersContactsEntity();
        membersContactsEntity.setName(membersContacts.getName());
        membersContactsEntity.setPhonenumber(membersContacts.getPhonenumber());
        membersContactsEntity.setAddress(membersContacts.getAddress());
        membersContactsEntity.setGubunId(membersContacts.getGubunId());

        if (membersContacts.getPhoto() != null) {
            try {
                byte[] photoBytes = membersContacts.getPhoto().getBytes();
                membersContactsEntity.setPhoto(photoBytes);
            } catch (IOException e) {
                log.error("Error while converting photo to byte[]", e);
            }
        } else {
            membersContactsEntity.setPhoto(null);
        }


        log.info("name : " + membersContacts.getName());
        log.info("phone : " + membersContacts.getPhonenumber());
        log.info("address : " + membersContacts.getAddress());
        log.info("gubunId : " + membersContacts.getGubunId());
        log.info("Photo : " + membersContacts.getPhoto());
        log.info("membersContactsEntity : " + membersContactsEntity.toString());

        return membersContactsEntity;
    }

}
