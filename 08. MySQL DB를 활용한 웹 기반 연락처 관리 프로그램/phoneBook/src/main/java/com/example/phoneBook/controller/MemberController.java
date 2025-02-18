package com.example.phoneBook.controller;

import com.example.phoneBook.dto.MemberDTO;
import com.example.phoneBook.dto.MembersContactsDTO;
import com.example.phoneBook.entity.MemberContactMapEntity;
import com.example.phoneBook.entity.MemberEntity;
import com.example.phoneBook.entity.MembersContactsEntity;
import com.example.phoneBook.repository.MemberContactMapRepository;
import com.example.phoneBook.repository.MemberRepository;
import com.example.phoneBook.repository.MembersContactsRepository;
import com.example.phoneBook.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


@Slf4j
@Controller
@RequiredArgsConstructor
@SessionAttributes("memberId")
public class MemberController {
    @Autowired
    private final MemberService memberService;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final MembersContactsRepository membersContactsRepository;

    @Autowired
    private final MemberContactMapRepository memberContactMapRepository;


    @GetMapping("/member/signup")
    public String saveForm() {
        log.info("회원가입 화면");
        return "/member/signup";
    }

    @PostMapping("/member/signup")
    public String signUp(@RequestParam String memberId, @RequestParam String password, @RequestParam String username, @RequestParam String email, @RequestParam String emailDomain, @RequestParam(required = false) String customEmail,
                         Model model, RedirectAttributes attr) {
        log.info("==============회원가입 시도==============");
        log.info("memberId = {}", memberId);
        log.info("username = {}", username);
        log.info("password = {}", password);
        log.info("email = {}", email);
        log.info("emailDomain = {}", emailDomain);
        log.info("customEmail = {}", customEmail);

        if (!memberService.isMemberIdDuplicate(memberId).isEmpty()) {
            log.info("이미 사용 중인 아이디입니다.");
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            attr.addFlashAttribute("msg", "이미 사용 중인 아이디입니다. 회원가입에 실패했습니다.");
            return "redirect:/member/signup";
        }

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberid(memberId);
        memberDTO.setPassword(password);
        memberDTO.setUsername(username);
        memberDTO.setEmail(email);

        try {
            memberService.save(memberDTO);
            log.info("회원가입 완료: {}", memberDTO);
            attr.addFlashAttribute("msg", memberId + " 회원가입이 완료되었습니다.");
            return "redirect:/member/login";
        } catch (Exception e) {
            log.error("회원가입 실패: ", e);
            attr.addFlashAttribute("msg", "회원가입에 실패했습니다. 다시 시도해 주세요.");
            return "redirect:/member/signup";
        }
    }

    @GetMapping("/member/login")
    public String showLoginPage(Model model) {
        log.info("로그인 페이지");
        if (model.containsAttribute("msg")) {
            model.addAttribute("msg", model.getAttribute("msg"));
            log.info("msg from FlashAttribute: {}", model.getAttribute("msg"));
        }
        return "/member/login";
    }

    @PostMapping("/member/login")
    public String login(@RequestParam String memberId, @RequestParam String password, Model model,
                        RedirectAttributes attr, HttpSession session) {
        log.info("==============로그인 시도==============");
        log.info("memberId = {}", memberId);
        log.info("password = {}", password);

        boolean loginSuccessful = memberService.login(memberId, password);
        if (loginSuccessful) {
            log.info("로그인 성공: {}", memberId);
            model.addAttribute("memberId", memberId);
            session.setAttribute("memberId", memberId);
            attr.addFlashAttribute("msg", "로그인 성공!");

            log.info("세션에 저장된 memberId: {}", session.getAttribute("memberId"));

            return "redirect:/member/index/" + memberId;
        } else {
            log.info("로그인 실패");
            attr.addFlashAttribute("msg", "로그인 실패! 아이디와 비밀번호를 확인해주세요.");
            return "redirect:/member/login";
        }
    }

    @GetMapping("/member/index/{memberId}")
    public String memberIndex(@PathVariable String memberId, Model model, HttpSession session) {
        log.info("회원 인덱스 페이지 표시: {}", memberId);

        String sessionMemberId = (String) session.getAttribute("memberId");
        log.info("세션에서 확인한 memberId: {}", sessionMemberId);

        if (sessionMemberId != null && sessionMemberId.equals(memberId)) {
            model.addAttribute("memberId", memberId);
            return "/member/index";
        } else {
            return "redirect:/member/login";
        }
    }

    @GetMapping("/member/forgotId")
    public String showForgotIdPage() {
        return "/member/forgotId";
    }


    @GetMapping("/member/forgotPassword")
    public String showForgotPasswordPage() {
        return "/member/forgotPassword";
    }

    @PostMapping("/member/forgotPassword")
    public String changePasswordByEmailAndId(@RequestParam String memberId, @RequestParam String email, @RequestParam String newPassword, Model model, RedirectAttributes attr) {
        log.info("아이디와 이메일로 비밀번호 재설정 시도: memberId={}, email={}", memberId, email);

        try {
            boolean passwordChanged = memberService.changePasswordByEmailAndId(memberId, email, newPassword);
            if (passwordChanged) {
//                model.addAttribute("msg", "비밀번호가 성공적으로 변경되었습니다.");
                attr.addFlashAttribute("msg", "비밀번호가 성공적으로 변경되었습니다.");
                log.info("비밀번호가 성공적으로 변경되었습니다.");
            } else {
//                model.addAttribute("msg", "아이디나 이메일이 일치하지 않습니다.");
                attr.addFlashAttribute("msg", "아이디나 이메일이 일치하지 않습니다.");
                log.info("아이디나 이메일이 일치하지 않습니다.");

            }
        } catch (NoSuchAlgorithmException e) {
            log.error("비밀번호 변경 오류: {}", e.getMessage());
            attr.addFlashAttribute("msg",  "비밀번호 변경 중 오류가 발생했습니다.");
//            model.addAttribute("msg", "비밀번호 변경 중 오류가 발생했습니다.");
            log.info("비밀번호 변경 중 오류가 발생했습니다.");
        }

        return "redirect:/member/forgotPassword";
    }




    @PostMapping("/member/forgotId")
    public String findMemberIdByEmail(@RequestParam String email, Model model,RedirectAttributes attr) {
        log.info("이메일로 아이디 찾기 시도: {}", email);

        String memberId = memberService.findMemberIdByEmail(email);

        if (!memberId.isEmpty()) {
            model.addAttribute("msg", "회원님의 아이디는 " + memberId + "입니다.");
            attr.addFlashAttribute("msg", "회원님의 아이디는 " + memberId + "입니다.");
            log.info("memberId : "+ memberId);
        } else {
            model.addAttribute("msg", "해당 이메일에 등록된 아이디가 없습니다.");
            attr.addFlashAttribute("msg", "해당 이메일에 등록된 아이디가 없습니다.");
        }

        return "redirect:/member/forgotId";
    }

    @Transactional
    public void addContact(String memberId, MembersContactsDTO contactsDTO) throws IOException {
        log.info("연락처 추가 시작 - MemberID: {}", memberId);

        MembersContactsEntity contactEntity = new MembersContactsEntity();
        MultipartFile photo = contactsDTO.getPhoto();
        byte[] photoBytes = photo != null ? photo.getBytes() : null;
        log.info("-----------------------------------------------------------------------------------");
        contactEntity.setName(contactsDTO.getName());
        log.info("Name : "+ contactsDTO.getName());
        contactEntity.setPhonenumber(contactsDTO.getPhonenumber());
        log.info("Phonenumber : "+ contactsDTO.getPhonenumber());
        contactEntity.setAddress(contactsDTO.getAddress());
        log.info("Address : "+ contactsDTO.getAddress());
        contactEntity.setGubunId(contactsDTO.getGubunId());
        log.info("GubunId : "+ contactsDTO.getGubunId());
        contactEntity.setPhoto(photoBytes);
        log.info("photoBytes : "+ photoBytes);
        log.info("-----------------------------------------------------------------------------------");
        membersContactsRepository.save(contactEntity);
        log.info("Saved Contact ID: {}", contactEntity.getContactid());

        MemberEntity member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    log.error("Member not found for memberId: {}", memberId);
                    return new RuntimeException("Member not found");
                });

        MemberContactMapEntity memberContactMap = new MemberContactMapEntity();
        memberContactMap.setMember(member);
        log.info(memberContactMap.toString());
        memberContactMap.setMembersContact(contactEntity);

        MemberContactMapEntity savedMap = memberContactMapRepository.save(memberContactMap);

        log.info("Saved MemberContactMap : {}", savedMap.toString());
    }




}
