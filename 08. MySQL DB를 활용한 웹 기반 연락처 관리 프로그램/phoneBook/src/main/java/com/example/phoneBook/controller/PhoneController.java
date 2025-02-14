package com.example.phoneBook.controller;

import com.example.phoneBook.dto.MembersContactsDTO;
import com.example.phoneBook.repository.PhoneRepository;
import com.example.phoneBook.repository.PhoneThirdRepository;
import com.example.phoneBook.service.PhoneService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j

public class PhoneController {
    @Autowired
    private final PhoneService phoneService;
    private final PhoneRepository phoneRepository;
    private final PhoneThirdRepository phoneThirdRepository;
    @GetMapping("/contact/{memberId}/add")
    public String saveFormWithMemberId(@PathVariable String memberId, HttpSession session) {
        String sessionMemberId = (String) session.getAttribute("memberId");

        log.info("세션에 설정된 memberId: {}", session.getAttribute("memberId"));
        log.info("URL에서 전달된 memberId: {}", memberId);


        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {
            log.info("연락처 추가 페이지 (memberId: {})", memberId);
            return "/contact/add";
        } else {
            log.info("세션의 memberId와 URL의 memberId가 일치하지 않습니다.");
            return "redirect:/member/login";
        }
    }


    @PostMapping("/contact/{memberId}/add")
    public String addContact(@PathVariable String memberId, @ModelAttribute MembersContactsDTO membersContacts, HttpSession session, RedirectAttributes attr) {
        String sessionMemberId = (String) session.getAttribute("memberId");

        log.info("세션에서 가져온 memberId: {}", sessionMemberId);
        log.info("요청된 memberId: {}", memberId);

        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {
            log.info("연락처 추가 처리 (memberId: {})", memberId);

            phoneService.save(membersContacts);
            attr.addFlashAttribute("msg", "연락처가 성공적으로 추가되었습니다.");

            return "redirect:/contact/" + memberId + "/list";
        } else {
            log.info("세션의 memberId와 URL의 memberId가 일치하지 않습니다.");
            return "redirect:/member/login";
        }
    }

    @GetMapping("/contact/{memberId}/list")
    public String show(@PathVariable String memberId, Model model,HttpSession session){
        String sessionMemberId = (String) session.getAttribute("memberId");

        log.info("세션에서 가져온 memberId: {}", sessionMemberId);
        log.info("요청된 memberId: {}", memberId);
        List<MembersContactsDTO> membersContactsDTOList = (List<MembersContactsDTO>)phoneThirdRepository.findAll();
        model.addAttribute("phoneList",membersContactsDTOList);
        return "redirect:/contact/" + memberId + "/list";

    }


}
