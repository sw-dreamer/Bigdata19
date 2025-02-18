package com.example.phoneBook.entity;

import com.example.phoneBook.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberno")
    private Integer memberNo;

    @Column(name = "memberid", unique = true, nullable = false)
    private String memberId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberContactMapEntity> memberContactMaps;

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

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberId(memberDTO.getMemberid());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setUsername(memberDTO.getUsername());
        memberEntity.setEmail(memberDTO.getEmail());

        LocalDateTime currentTime = LocalDateTime.now();
        memberEntity.setCreateDate(currentTime);
        memberEntity.setLastUpdate(currentTime);

        log.info("memberEntity: " + memberEntity.toString());
        return memberEntity;
    }
}
