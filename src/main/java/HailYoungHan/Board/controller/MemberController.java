package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.MemberRegiDTO;
import HailYoungHan.Board.dto.MemberUpdateDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> register(@RequestBody MemberRegiDTO memberRegiDTO) {
        Member member = null;
        try {
            member = memberService.registerMember(memberRegiDTO);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Member> update(@RequestBody MemberUpdateDTO memberUpdateDTO) {
        Member member = memberService.updateMember(memberUpdateDTO);
        return new ResponseEntity<>(member, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Member> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMemberById(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMembers(@RequestBody List<Long> ids) {
        memberService.deleteMembers(ids);
        return ResponseEntity.ok().build();
    }
}
