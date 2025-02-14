package com.example.phoneBook.dto;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class MembersContacts {
    private Integer contactId;

    private String name;

    private String phonenumber;

    private String address;

    private String gubunId;

    private String photo;

    private String createDate;

    private String lastUpdate;
}
