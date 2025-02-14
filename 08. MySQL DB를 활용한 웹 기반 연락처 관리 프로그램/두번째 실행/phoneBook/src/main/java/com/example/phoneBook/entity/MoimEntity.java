package com.example.phoneBook.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "moim")
@Data
public class MoimEntity {

    @Id
    @Column(name = "gubunId")
    private String gubunId;

    @Column(name = "gubunName")
    private String gubunName;
}