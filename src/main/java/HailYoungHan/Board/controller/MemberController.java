package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.MemberRegistrationDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> register(@RequestBody MemberRegistrationDTO memberRegistrationDTO) {
        Member member = memberService.registerMember(memberRegistrationDTO.getName(), memberRegistrationDTO.getPassword());
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }
}
