package com.example.phoneBook.controller;

import com.example.phoneBook.dto.MemberDTO;
import com.example.phoneBook.repository.MemberRepository;
import com.example.phoneBook.repository.MemberSecondRepository;
import com.example.phoneBook.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j

@Controller
@RequiredArgsConstructor
public class MemberController {
    @Autowired
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberSecondRepository memberSecondRepository;

    @GetMapping("/member/login")
    public String login() {
        log.info("로그인 화면");
        return "/member/login";
    }

    @GetMapping("/member/signup")
    public String saveForm() {
        log.info("회원가입 화면");
        return "/member/signup";
    }

    @PostMapping("/member/signup")
    public String signUp(@RequestParam String memberId, @RequestParam String password, @RequestParam String username,
                         Model model, RedirectAttributes attr) {
        log.info("==============회원가입 시도==============");
        log.info("memberId = {}", memberId);
        log.info("username = {}", username);

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

    @PostMapping("/member/login")
    public String login(@RequestParam String memberId, @RequestParam String password, Model model,
                        HttpSession session, RedirectAttributes attr) {
        log.info("==============로그인 시도==============");
        log.info("memberId = {}", memberId);
        log.info("password = {}", password);

        boolean loginSuccessful = memberService.login(memberId, password);
        if (loginSuccessful) {
            log.info("로그인 성공: {}", memberId);
            session.setAttribute("memberId", memberId);
            attr.addFlashAttribute("msg", "로그인 성공!");
            return "redirect:/member/index/" + memberId;
        } else {
            log.info("로그인 실패");
            attr.addFlashAttribute("msg", "로그인 실패! 아이디와 비밀번호를 확인해주세요.");
            return "redirect:/member/login";
        }
    }

    @GetMapping("/member/index/{memberId}")
    public String memberIndex(@PathVariable String memberId, Model model) {
        log.info("회원 인덱스 페이지 표시: {}", memberId);
        model.addAttribute("memberId", memberId);
        return "member/index";
    }

    @GetMapping("/member/index")
    public String indexForm(HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");

        if (memberId != null) {
            log.info("사용자 {} 로그인", memberId);
            return "member/index";
        } else {
            return "redirect:/member/login";
        }
    }
}

