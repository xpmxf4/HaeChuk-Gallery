package hailyounghan.member.controller;

import hailyounghan.member.dto.query.MemberDbDTO;
import hailyounghan.member.dto.request.MemberRegiDTO;
import hailyounghan.member.dto.request.MemberUpdateDTO;
import hailyounghan.member.dto.response.MemberResponseDTO;
import hailyounghan.member.service.MemberService;
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
     * <p>회원 등록 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>memberRegiDTO</b> – 회원 생성에 필요한 데이터:</li>
     *     <ul>
     *         <li><b>name:</b> 회원 이름. Not blank, 2-50 characters.</li>
     *         <li><b>email:</b> 회원 이메일. Not blank, valid email format.</li>
     *         <li><b>password:</b> 회원 비밀번호. Not blank, minimum 8 characters.</li>
     *     </ul>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>"회원이 성공적으로 생성되었습니다." 메시지와 HTTP 상태 코드 201 (생성됨)</p>
     */
    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid MemberRegiDTO memberRegiDTO) {
        memberService.registerMember(memberRegiDTO);
        return new ResponseEntity<>("회원이 성공적으로 생성되었습니다.", HttpStatus.CREATED);
    }

    /**
     * <p>단일 회원 조회 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>memberId:</b> 조회하고자 하는 회원의 ID.</li>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>조회된 회원 정보와 HTTP 상태 코드 200 (OK)</p>
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDbDTO> getOneMember(@PathVariable Long memberId) {
        MemberDbDTO findMember = memberService.getSingleMember(memberId);
        return new ResponseEntity<>(findMember, HttpStatus.OK);
    }

    /**
     * <p>모든 회원 조회 API</p>
     *
     * <p><b>Returns:</b></p>
     * <p>조회된 모든 회원 정보의 리스트와 HTTP 상태 코드 200 (OK)</p>
     */
    @GetMapping
    public ResponseEntity<MemberResponseDTO> getAllMembers() {
        MemberResponseDTO membersResponse = memberService.getAllMembers();
        return new ResponseEntity<>(membersResponse, HttpStatus.OK);
    }

    /**
     * <p>회원 정보 수정 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>memberId:</b> 수정하고자 하는 회원의 ID.</li>
     *     <li><b>memberUpdateDTO</b> – 회원 정보 수정에 필요한 데이터:</li>
     *     <ul>
     *         <li><b>name:</b> 회원 이름. NotBlank, 2-50 characters.</li>
     *         <li><b>email:</b> 회원 이메일. NotBlank, valid email format.</li>
     *         <li><b>password:</b> 회원 비밀번호. NotBlank, minimum 8 characters.</li>
     *     </ul>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>HTTP 상태 코드 202 (Accepted)</p>
     */
    @PutMapping("/{memberId}")
    public ResponseEntity<Void> update(@PathVariable Long memberId, @RequestBody @Valid MemberUpdateDTO memberUpdateDTO) {
        memberService.updateMember(memberId, memberUpdateDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    /**
     * <p>여러 회원 삭제 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>ids:</b> 삭제하고자 하는 회원들의 ID 리스트.</li>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>HTTP 상태 코드 200 (OK)</p>
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteMembers(@RequestBody List<Long> ids) {
        memberService.deleteMembers(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
