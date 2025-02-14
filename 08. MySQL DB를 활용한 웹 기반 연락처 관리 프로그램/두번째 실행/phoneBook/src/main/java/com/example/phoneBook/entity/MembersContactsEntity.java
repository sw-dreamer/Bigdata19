package com.example.phoneBook.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "memberscontacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
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

    @Column(name = "gubunId", insertable = false, updatable = false)
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
}
