package com.example.phoneBook.service;

import com.example.phoneBook.dto.MembersContactsDTO;
import com.example.phoneBook.entity.MemberContactMapEntity;
import com.example.phoneBook.entity.MembersContactsEntity;
import com.example.phoneBook.entity.MemberEntity;
import com.example.phoneBook.projection.MemberContactFourthProjection;
import com.example.phoneBook.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PhoneService {

    @Autowired
    private MemberContactMapRepository memberContactMapRepository;

    @Autowired
    private MembersContactsRepository membersContactsRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberThirdRepository memberThirdRepository;

    @Autowired
    private MemberContactsThirdRepository memberContactsThirdRepository;

    @Autowired
    private final MemberContactFourthRepository memberContactFourthRepository;

    public MembersContactsEntity save(MembersContactsDTO membersContactsDTO) {
        MembersContactsEntity membersContactsEntity = new MembersContactsEntity();
        log.info("save의 과정");

        membersContactsEntity.setName(membersContactsDTO.getName());
        log.info("name : " + membersContactsDTO.getName());
        membersContactsEntity.setPhonenumber(membersContactsDTO.getPhonenumber());
        log.info("phoneNumber : " + membersContactsDTO.getPhonenumber());
        membersContactsEntity.setAddress(membersContactsDTO.getAddress());
        log.info("address : " + membersContactsDTO.getAddress());
        membersContactsEntity.setGubunId(membersContactsDTO.getGubunId());
        log.info("gubunId : " + membersContactsDTO.getGubunId());

        if (membersContactsDTO.getPhoto() != null) {
            try {
                byte[] photoBytes = membersContactsDTO.getPhoto().getBytes();  // MultipartFile을 byte[]로 변환
                membersContactsEntity.setPhoto(photoBytes);
            } catch (IOException e) {
                log.error("Error while converting photo to byte[]", e);
            }
//            byte[] photoBytes = membersContactsDTO.getPhoto();
        } else {
            membersContactsEntity.setPhoto(new byte[0]);
        }

        log.info("photo : " + membersContactsDTO.getPhoto());

        log.info("save의 membersContactsEntity : " + membersContactsEntity.toString());

        return membersContactsRepository.save(membersContactsEntity);
    }


    public MembersContactsEntity saveMemberContact(MembersContactsEntity membersContacts) {
        return membersContactsRepository.save(membersContacts);
    }

    public void addMemberContact(String memberId, Integer contactId) {
        MemberEntity member = memberRepository.findByMemberId(memberId).orElse(null);
        MembersContactsEntity contact = membersContactsRepository.findById(contactId).orElse(null);

        if (member != null && contact != null) {
            MemberContactMapEntity memberContactMap = MemberContactMapEntity.builder()
                    .member(member)
                    .membersContact(contact)
                    .build();
            memberContactMapRepository.save(memberContactMap);
        }
    }

    public List<MembersContactsEntity> getContactsByMemberId(String memberId) {
        return memberThirdRepository.findByMemberContactMaps_Member_MemberId(memberId);
    }


    public Optional<MembersContactsEntity> findById(Long contactid) {
        return memberContactsThirdRepository.findById(contactid);
    }



    @Autowired
    public PhoneService(MemberContactFourthRepository memberContactFourthRepository) {
        this.memberContactFourthRepository = memberContactFourthRepository;
    }

    public List<MemberContactFourthProjection> searchContactsByNameAndMemberId(String memberId, String query) {
        return memberContactFourthRepository.searchContactsByNameAndMemberId(memberId, query);
    }


}
