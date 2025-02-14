package com.example.phoneBook.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.phoneBook.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Table(name = "members")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberno")
    private Long memberno;

    @Column(name = "memberid", nullable = false, unique = true)
    private String memberid;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name="password", length = 255, nullable = false)
    private String password;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @ManyToMany
    @JoinTable(
            name = "membercontactmap",
            joinColumns = @JoinColumn(name = "memberid"),
            inverseJoinColumns = @JoinColumn(name = "contactid")
    )
    private List<MembersContactsEntity> contacts;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberid(memberDTO.getMemberid());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setUsername(memberDTO.getUsername());
        memberEntity.setEmail(memberDTO.getEmail());

        LocalDateTime currentTime = LocalDateTime.now();
        memberEntity.setCreateDate(currentTime);
        memberEntity.setLastUpdate(currentTime);

        log.debug("memberEntity: " + memberEntity.toString());
        return memberEntity;
    }
}
