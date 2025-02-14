package com.example.phoneBook.entity;

import com.example.phoneBook.dto.MembersContactsDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "memberscontacts")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class MembersContactsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactId")
    private Integer contactId;

    @Column(name = "name")
    private String name;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "address")
    private String address;

    @Column(name = "gubun_id")
    private String gubunId;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    // MemberEntity와 다대다 관계 설정
    @ManyToMany(mappedBy = "contacts")
    private List<MemberEntity> members;

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

    public static MembersContactsEntity toMemberContactsEntity(MembersContactsDTO membersContacts) {
        MembersContactsEntity membersContactsEntity = new MembersContactsEntity();
        membersContactsEntity.setName(membersContacts.getName());

        membersContactsEntity.setPhonenumber(membersContacts.getPhonenumber());
        membersContactsEntity.setAddress(membersContacts.getAddress());
        membersContactsEntity.setGubunId(membersContacts.getGubunId());
        if (membersContacts.getPhoto() != null) {
            membersContactsEntity.setPhoto(membersContacts.getPhoto());
        } else {
            membersContactsEntity.setPhoto(new byte[0]);
        }

        log.info("name : " + membersContacts.getName());
        log.info("phone : " + membersContacts.getPhonenumber());
        log.info("address : " + membersContacts.getAddress());
        log.info("gubunId : " + membersContacts.getGubunId());
        log.info("Photo : " + membersContacts.getPhoto());
        log.info("membersContactsEntity : "+membersContactsEntity.toString());
        return membersContactsEntity;
    }

}
