package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.member.query.MemberDbDTO;
import HailYoungHan.Board.dto.member.request.MemberRegiDTO;
import HailYoungHan.Board.dto.member.request.MemberUpdateDTO;
import HailYoungHan.Board.dto.member.response.MemberResponseDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.exception.CustomException;
import HailYoungHan.Board.exception.ErrorCode;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static HailYoungHan.Board.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 등록
    @Transactional
    public void registerMember(MemberRegiDTO memberRegiDTO) {
        String name = memberRegiDTO.getName();
        String password = memberRegiDTO.getPassword();
        String email = memberRegiDTO.getEmail();

        if (memberRepository.existsByEmail(email)) { // email 은 unique 제약 조건이 있음.
            throw new CustomException(EMAIL_ALREADY_EXISTS, email);
        }

        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(PasswordEncoder.encode(password))
                .build();

        memberRepository.save(member);
    }

    public MemberDbDTO getSingleMember(Long memberId) {
        MemberDbDTO memberDbDTO = memberRepository.getSingleMember(memberId);

        return memberDbDTO;
    }

    public MemberResponseDTO getAllMembers() {
        List<MemberDbDTO> allMembers = memberRepository.getAllMembers();
        return new MemberResponseDTO(allMembers);
    }

    //특정 회원 정보 수정
    @Transactional
    public void updateMember(Long userId, MemberUpdateDTO memberUpdateDTO) {
        // DB 에 memberUpdateDTO 의 id 에 해당하는 유저 존재 여부 확인
        Member member = memberRepository
                .findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_BY_ID, userId));

        // memberUpdateDTO ---(map)---> Member(Entity) 로 map
        member.mapFromUpdateDto(memberUpdateDTO);
    }

    @Transactional
    public void deleteMembers(List<Long> ids) {
        memberRepository.deleteMembers(ids);
    }
}
