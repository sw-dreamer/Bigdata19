package com.example.phoneBook.controller;

import com.example.phoneBook.dto.MembersContactsDTO;
import com.example.phoneBook.entity.MemberContactMapEntity;
import com.example.phoneBook.entity.MemberEntity;
import com.example.phoneBook.entity.MembersContactsEntity;
import com.example.phoneBook.projection.MemberContactFourthProjection;
import com.example.phoneBook.projection.PhoneMoimProjection;
import com.example.phoneBook.repository.*;
import com.example.phoneBook.service.PhoneService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PhoneController {

    @Autowired
    private final PhoneService phoneService;
    @Autowired
    private final PhoneRepository phoneRepository;
    @Autowired
    private final PhoneThirdRepository phoneThirdRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final MemberContactMapRepository memberContactMapRepository;
    @Autowired
    private final PhoneFourthRepository phoneFourthRepository;
    @Autowired
    private final MemberContactsSecondRepository memberContactsSecondRepository;
    @Autowired
    private final MemberContactFourthRepository memberContactFourthRepository;

    @GetMapping("/contact/{memberId}/add")
    public String saveFormWithMemberId(@PathVariable String memberId, HttpSession session) {
        String sessionMemberId = (String) session.getAttribute("memberId");

        log.info("세션에 설정된 memberId: {}", session.getAttribute("memberId"));
        log.info("URL에서 전달된 memberId: {}", memberId);

        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {
            log.info("연락처 추가 페이지 (memberId: {})", memberId);
            return "contact/add";
        } else {
            log.warn("세션의 memberId와 URL의 memberId가 일치하지 않습니다.");
            session.invalidate();
            return "redirect:/member/login";
        }
    }

    @Transactional
    @PostMapping("/contact/{memberId}/add")
    public String addContact(@PathVariable String memberId,
                             @ModelAttribute MembersContactsDTO membersContacts,
                             @RequestParam("photo") MultipartFile photo,
                             HttpSession session,
                             RedirectAttributes attr) {
        String sessionMemberId = (String) session.getAttribute("memberId");

        log.info("세션에서 가져온 memberId: {}", sessionMemberId);
        log.info("요청된 memberId: {}", memberId);

        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {
            log.info("연락처 추가 처리 (memberId: {})", memberId);

            try {
                String uploadDir = "C:/big19/spring_dev/phoneBook/uploadfile";
                if (!photo.isEmpty()) {
                    String fileName = photo.getOriginalFilename();
                    String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                    Path uploadPath = Paths.get(uploadDir, uniqueFileName);
                    Files.copy(photo.getInputStream(), uploadPath);
                    membersContacts.setEncodedPhoto(uploadPath.toString());
                    log.info("파일 저장 경로: {}", uploadPath);
                    log.info("photo: {}", photo);
                    log.info("membersContacts.getPhoto(): {}", membersContacts.getPhoto());
                    log.info("membersContacts.getEncodedPhoto(): {}", membersContacts.getEncodedPhoto());
                    log.info("membersContacts: {}", membersContacts);
                }
                MembersContactsEntity contactEntity = phoneService.save(membersContacts);
                if (contactEntity == null || contactEntity.getContactid() == null) {
                    log.error("연락처 저장 실패: contactEntity가 null이거나 contactid가 없음.");
                    attr.addFlashAttribute("errorMsg", "연락처 저장에 실패했습니다.");
                    return "redirect:/contact/" + memberId + "/list";
                }
                log.info("저장된 연락처 ID: {}", contactEntity.getContactid());
                MemberEntity member = memberRepository.findByMemberId(memberId)
                        .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다: " + memberId));
                MemberContactMapEntity contactMap = new MemberContactMapEntity();
                contactMap.setMember(member);
                contactMap.setMembersContact(contactEntity);
                memberContactMapRepository.save(contactMap);
                log.info("회원-연락처 매핑 저장 완료: memberId={}, contactid={}", memberId, contactEntity.getContactid());
                attr.addFlashAttribute("msg", "연락처가 성공적으로 추가되었습니다.");
                return "redirect:/contact/" + memberId + "/list";
            } catch (IOException e) {
                log.error("파일 업로드 중 오류 발생: {}", e.getMessage());
                attr.addFlashAttribute("errorMsg", "파일 업로드 중 오류가 발생했습니다.");
                return "redirect:/contact/" + memberId + "/list";
            }
        } else {
            log.warn("세션의 memberId와 URL의 memberId가 일치하지 않습니다.");
            return "redirect:/member/login";
        }
    }



    private boolean isValidBase64(String base64) {
        try {
            Base64.getDecoder().decode(base64);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @GetMapping("/contact/{memberId}/list")
    public String show(@PathVariable String memberId, Model model, HttpSession session) {
        String sessionMemberId = (String) session.getAttribute("memberId");
        log.info("세션에서 가져온 memberId: {}", sessionMemberId);
        log.info("요청된 memberId: {}", memberId);

        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {
            List<PhoneMoimProjection> phoneMoimProjectionList = phoneFourthRepository.phoneMoimData(memberId);
            log.info("phoneMoimProjectionList : {}", phoneMoimProjectionList);

            model.addAttribute("moimList", phoneMoimProjectionList);
            return "contact/list";
        } else {
            log.warn("세션의 memberId와 URL의 memberId가 일치하지 않습니다.");
            return "redirect:/member/login";
        }
    }

    @Transactional
    @GetMapping("/contact/{memberId}/{contactid}/delete")
    public String deleteContactForm(@PathVariable String memberId, @PathVariable Long contactid, HttpSession session, Model model, RedirectAttributes attr) {
        String sessionMemberId = (String) session.getAttribute("memberId");
        log.info("세션에 설정된 memberId: {}", sessionMemberId);
        log.info("URL에서 전달된 memberId: {}", memberId);
        log.info("URL에서 전달된 contactid: {}", contactid);

        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {
            log.info("연락처 삭제 페이지 (memberId: {}, contactid: {})", memberId, contactid);
            try {
                memberContactsSecondRepository.deleteContact(contactid);
//                model.addAttribute("msg", "연락처 삭제에 성공했습니다.");
                attr.addFlashAttribute("msg", "연락처 삭제에 성공했습니다.");
                log.info("연락처가 성공적으로 삭제되었습니다.");
                return "redirect:/contact/" + memberId + "/list";
            } catch (Exception e) {
                log.error("연락처 삭제 중 오류가 발생했습니다: {}", e.getMessage());
//                model.addAttribute("msg", "연락처 삭제에 실패했습니다.");
                attr.addFlashAttribute("msg", "연락처 삭제에 실패했습니다.");
                return "contact/list";
            }
        } else {
            log.warn("세션의 memberId와 URL의 memberId가 일치하지 않습니다.");
            session.invalidate();
            return "redirect:/member/login";
        }
    }

    @GetMapping("/contact/{memberId}/{contactid}/edit")
    public String editContactForm(@PathVariable String memberId, @PathVariable Long contactid, HttpSession session, Model model) {
        String sessionMemberId = (String) session.getAttribute("memberId");

        log.info("세션에 설정된 memberId: {}", session.getAttribute("memberId"));
        log.info("URL에서 전달된 memberId: {}", memberId);
        log.info("URL에서 전달된 contactid: {}", contactid);

        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {


            MembersContactsEntity contactEntity = phoneService.findById(contactid)
                    .orElseThrow(() -> new RuntimeException("연락처를 찾을 수 없습니다: " + contactid));
            log.info("연락처 수정 페이지 (memberId: {}, contactid: {})", memberId, contactid);
            model.addAttribute("contact", contactEntity);
            return "contact/edit";
        } else {
            log.warn("세션의 memberId와 URL의 memberId가 일치하지 않습니다.");
            session.invalidate();
            return "redirect:/member/login";
        }
    }

    @Transactional
    @PostMapping("/contact/{memberId}/{contactid}/edit")
    public String editContact(@PathVariable String memberId,
                              @PathVariable Long contactid,
                              @ModelAttribute MembersContactsDTO membersContacts,
                              @RequestParam("photo") MultipartFile photo,
                              HttpSession session,
                              RedirectAttributes attr) {
        String sessionMemberId = (String) session.getAttribute("memberId");

        log.info("세션에서 가져온 memberId: {}", sessionMemberId);
        log.info("요청된 memberId: {}", memberId);
        log.info("요청된 contactid: {}", contactid);

        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {
            log.info("연락처 수정 처리 (memberId: {}, contactid: {})", memberId, contactid);

            try {
                MembersContactsEntity contactEntity = phoneService.findById(contactid)
                        .orElseThrow(() -> new RuntimeException("연락처를 찾을 수 없습니다: " + contactid));

                contactEntity.setName(membersContacts.getName());
                contactEntity.setPhonenumber(membersContacts.getPhonenumber());
                contactEntity.setAddress(membersContacts.getAddress());
                contactEntity.setGubunId(membersContacts.getGubunId());

                if (!photo.isEmpty()) {
                    String uploadDir = "C:/big19/spring_dev/phoneBook/uploadfile";
                    String fileName = photo.getOriginalFilename();
                    String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                    Path uploadPath = Paths.get(uploadDir, uniqueFileName);
                    Files.copy(photo.getInputStream(), uploadPath);

                    byte[] photoBytes = Files.readAllBytes(uploadPath);
                    contactEntity.setPhoto(photoBytes);
                    log.info("새로운 파일 저장 경로: {}", uploadPath);
                }

                MembersContactsDTO contactDTO = MembersContactsDTO.toMembersContactsDTO(contactEntity);

                phoneService.save(contactDTO);

                log.info("연락처 수정 완료: contactid={}", contactid);
                attr.addFlashAttribute("msg", "연락처가 성공적으로 수정되었습니다.");

                return "redirect:/contact/" + memberId + "/list";
            } catch (IOException e) {
                log.error("파일 업로드 중 오류 발생: {}", e.getMessage());
                attr.addFlashAttribute("errorMsg", "파일 업로드 중 오류가 발생했습니다.");
                return "redirect:/contact/" + memberId + "/list";
            }
        } else {
            log.warn("세션의 memberId와 URL의 memberId가 일치하지 않습니다.");
            return "redirect:/member/login";
        }
    }

    @GetMapping("/contact/{memberId}/search")
    public String searchContacts(
            @PathVariable String memberId,
            @RequestParam String query,
            HttpSession session,
            Model model) {
        String sessionMemberId = (String) session.getAttribute("memberId");

        log.info("세션에서 가져온 memberId: {}", sessionMemberId);
        log.info("요청된 memberId: {}", memberId);
        log.info("검색 쿼리: {}", query);

        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {
            List<MemberContactFourthProjection> searchResults = phoneService.searchContactsByNameAndMemberId(memberId, query);

            log.info("검색된 연락처 개수: {}", searchResults.size());

            for(int i = 0;i<searchResults.size();i++){
                log.info("searchResults.get(i).getContactId() : "+ searchResults.get(i).getContactId());
                log.info("searchResults.get(i).getAddress() : "+searchResults.get(i).getAddress());
                log.info("searchResults.get(i).getBhotobase() : "+searchResults.get(i).getBhotobase());
                log.info("searchResults.get(i).getName() : "+searchResults.get(i).getName());
                log.info("searchResults.get(i).getGubunName() : "+ searchResults.get(i).getGubunName());
                log.info("searchResults.get(i).getMemberId() : "+searchResults.get(i).getMemberId());

            }
            model.addAttribute("searchList", searchResults);
            model.addAttribute("query", query);
            return "contact/search";
        } else {
            log.warn("세션의 memberId와 URL의 memberId가 일치하지 않습니다.");
            session.invalidate();
            return "redirect:/member/login";
        }
    }














}
