package com.example.phoneBook.service;

import com.example.phoneBook.dto.MembersContactsDTO;
import com.example.phoneBook.entity.MembersContactsEntity;
import com.example.phoneBook.repository.PhoneSecondRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PhoneService {
    @Autowired
    private final PhoneSecondRepository phoneSecondRepository;

    public PhoneService(PhoneSecondRepository phoneSecondRepository) {
        this.phoneSecondRepository = phoneSecondRepository;
    }

    @Transactional
    public  void save(MembersContactsDTO membersContacts){
        MembersContactsEntity memberContactsEntity = MembersContactsEntity.toMemberContactsEntity(membersContacts);
        log.info("-----------------------------------------------------------------------------------------------------");
        log.info(membersContacts.toString());
        log.info(memberContactsEntity.toString());
        phoneSecondRepository.save(memberContactsEntity);
    }
}
