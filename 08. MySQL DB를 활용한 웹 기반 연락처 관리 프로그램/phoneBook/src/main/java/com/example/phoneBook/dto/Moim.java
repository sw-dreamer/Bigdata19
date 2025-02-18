package com.example.phoneBook.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j

public class Moim {
    @Id
    @Column(name="gubunId")
    private String gubunId;

    @Column(name="gubunName")
    private String gubunName;
}
