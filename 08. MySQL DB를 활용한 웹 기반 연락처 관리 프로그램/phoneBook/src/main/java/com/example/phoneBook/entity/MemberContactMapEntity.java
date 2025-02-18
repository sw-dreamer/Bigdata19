package com.example.phoneBook.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "membercontactmap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberContactMapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberContactNo")
    private Integer memberContactNo;

    @ManyToOne
    @JoinColumn(name = "memberid", referencedColumnName = "memberid", nullable = false)
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "contactid", referencedColumnName = "contactid", nullable = false)
    private MembersContactsEntity membersContact;

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
}