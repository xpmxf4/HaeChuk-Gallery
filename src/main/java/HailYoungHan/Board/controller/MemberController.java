package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.member.MemberDTO;
import HailYoungHan.Board.dto.member.MemberRegiDTO;
import HailYoungHan.Board.dto.member.MemberUpdateDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 등록
     * @param memberRegiDTO name, password 만 받음
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid MemberRegiDTO memberRegiDTO) {
        try {
            memberService.registerMember(memberRegiDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 단일 회원 조회
     *
     * @param memberId 멤버 id 만 받아서 조회
     * @return MemberDTO
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> getOne(@PathVariable Long memberId) {
        MemberDTO findMember = memberService.getSingleMember(memberId);

        return new ResponseEntity<>(findMember, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> members = memberService.getAllMembers();

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    /**
     * 회원 수정
     *
     * @param memberUpdateDTO id 값을 받아 존재하는 name, password 로 덮음
     * @return member - 업데이트된 객체
     */
    @PutMapping
    public ResponseEntity<Member> update(@RequestBody @Valid MemberUpdateDTO memberUpdateDTO) {
        Member member = memberService.updateMember(memberUpdateDTO);
        return new ResponseEntity<>(member, HttpStatus.ACCEPTED);
    }


    /**
     * 특정 회원 삭제
     *
     * @param memberId
     * @return
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Member> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMemberById(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 다수 회원 삭제
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public ResponseEntity<?> deleteMembers(@RequestBody List<Long> ids) {
        memberService.deleteMembers(ids);
        return ResponseEntity.ok().build();
    }

}
