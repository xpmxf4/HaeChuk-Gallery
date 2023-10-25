package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.member.query.MemberDbDTO;
import HailYoungHan.Board.dto.member.request.MemberRegiDTO;
import HailYoungHan.Board.dto.member.response.MemberResponseDTO;
import HailYoungHan.Board.dto.member.request.MemberUpdateDTO;
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
     * @param memberRegiDTO 회원 등록에 필요한 데이터를 포함하며, 이름과 비밀번호, 이메일을 필요합니다.
     * @return ResponseEntity&lt;String&gt; 문자열 메시지와 HTTP 상태 코드를 포함하는 응답을 반환합니다.
     */
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid MemberRegiDTO memberRegiDTO) {
        memberService.registerMember(memberRegiDTO);
        return new ResponseEntity<>("회원이 성공적으로 생성되었습니다.", HttpStatus.CREATED);
    }

    /**
     * 단일 회원을 조회합니다.
     *
     * <p>
     * 주어진 회원 ID를 기반으로 해당 회원의 정보를 조회합니다.
     * </p>
     *
     * @param memberId 조회하고자 하는 회원의 ID.
     * @return ResponseEntity&lt;MemberDTO&gt; 조회된 회원 정보와 HTTP 상태 코드를 포함하는 응답을 반환합니다.
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDbDTO> getOneMember(@PathVariable Long memberId) {
        MemberDbDTO findMember = memberService.getSingleMember(memberId);
        return new ResponseEntity<>(findMember, HttpStatus.OK);
    }

    /**
     * 등록된 모든 회원을 조회합니다.
     *
     * @return ResponseEntity&lt;List&lt;MemberDTO&gt;&gt; 조회된 모든 회원 정보와 HTTP 상태 코드를 포함하는 응답을 반환합니다.
     */
    @GetMapping
    public ResponseEntity<MemberResponseDTO> getAllMembers() {
        MemberResponseDTO membersResponse = memberService.getAllMembers();
        return new ResponseEntity<>(membersResponse, HttpStatus.OK);
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * <p>
     * 주어진 회원 정보를 기반으로 해당 회원의 정보를 수정합니다.
     * </p>
     *
     * @param memberUpdateDTO 수정하고자 하는 회원의 정보.
     * @return ResponseEntity&lt;Member&gt; 수정된 회원 정보와 HTTP 상태 코드를 포함하는 응답을 반환합니다.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Void> update(@PathVariable Long userId, @RequestBody @Valid MemberUpdateDTO memberUpdateDTO) {
        memberService.updateMember(userId, memberUpdateDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * 여러 회원들을 삭제합니다.
     *
     * <p>
     * 주어진 회원 ID 리스트를 기반으로 해당 회원들을 삭제합니다.
     * </p>
     *
     * @param ids 삭제하고자 하는 회원들의 ID 리스트.
     * @return ResponseEntity&lt;Void&gt; HTTP 상태 코드만을 포함하는 응답을 반환합니다.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteMembers(@RequestBody List<Long> ids) {
        memberService.deleteMembers(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
