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
     * 새로운 회원을 등록합니다.
     *
     * <p>
     * 이 API는 회원의 이름과 비밀번호를 입력받아 새로운 회원을 등록합니다.
     * 성공적으로 회원이 등록되면, HTTP 201 Created 상태와 함께
     * "회원이 성공적으로 생성되었습니다." 메시지를 반환합니다.
     * </p>
     *
     * @param memberRegiDTO 회원 등록에 필요한 데이터를 포함하며, 이름과 비밀번호만 필요합니다.
     * @return ResponseEntity 문자열 메시지와 HTTP 상태 코드를 포함하는 응답을 반환합니다.
     */
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid MemberRegiDTO memberRegiDTO) {
        memberService.registerMember(memberRegiDTO);
        return new ResponseEntity<>("회원이 성공적으로 생성되었습니다.", HttpStatus.CREATED);
    }

    /**
     * 단일 회원 조회
     *
     * @param memberId 멤버 id 만 받아서 조회
     * @return MemberDTO
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> getOneMember(@PathVariable Long memberId) {
        MemberDTO findMember = memberService.getSingleMember(memberId);
        return new ResponseEntity<>(findMember, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> members = memberService.getAllMembers();

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    /**
     * 회원 정보 수정을 위한 API.
     *
     * @param memberUpdateDTO 사용자로부터 받은 회원 정보. (id 기반으로 name, password를 업데이트할 데이터를 포함)
     * @return ResponseEntity<Member> - 수정된 회원 정보와 함께 202 (Accepted) 상태 코드를 반환.
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
