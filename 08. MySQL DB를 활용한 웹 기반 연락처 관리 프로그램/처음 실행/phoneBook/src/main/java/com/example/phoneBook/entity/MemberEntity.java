package com.example.phoneBook.entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.example.phoneBook.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
@Table(name = "members")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberno")
    private Long memberno;

    @Column(name = "memberid")
    private String memberid;

    @Column(name = "username")
    private String username;

    @Column(name="password", length = 13)
    private String password;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;



    @Builder
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberid(memberDTO.getMemberid());
        memberEntity.setPassword(memberDTO.getPassword());
        memberEntity.setUsername(memberDTO.getUsername());

        LocalDateTime currentTime = LocalDateTime.now();
        memberEntity.setCreateDate(currentTime);
        memberEntity.setLastUpdate(currentTime);

        log.debug("memberEntity"+memberEntity.toString());
        return memberEntity;
    }




}