package hailyounghan.member.service;

import hailyounghan.common.exception.CustomException;
import hailyounghan.common.util.PasswordEncoder;
import hailyounghan.member.dto.query.MemberDbDTO;
import hailyounghan.member.dto.request.MemberRegiDTO;
import hailyounghan.member.dto.request.MemberUpdateDTO;
import hailyounghan.member.dto.response.MemberResponseDTO;
import hailyounghan.member.entity.Member;
import hailyounghan.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hailyounghan.common.exception.ErrorCode.*;


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
        if (!memberRepository.existsById(memberId))
            throw new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId);

        return memberRepository.getSingleMember(memberId);
    }

    public MemberResponseDTO getAllMembers() {
        List<MemberDbDTO> allMembers = memberRepository.getAllMembers();
        return new MemberResponseDTO(allMembers);
    }

    //특정 회원 정보 수정
    @Transactional
    public void updateMember(Long memberId, MemberUpdateDTO memberUpdateDTO) {
        // DB 에 memberUpdateDTO 의 id 에 해당하는 유저 존재 여부 확인
        Member member = memberRepository
                .findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId));

        // memberUpdateDTO ---(map)---> Member(Entity) 로 map
        member.mapFromUpdateDto(memberUpdateDTO);
    }

    @Transactional
    public void deleteMembers(List<Long> memberIds) {
        validateMemberIds(memberIds);
        memberRepository.deleteMembers(memberIds);
    }

    private void validateMemberIds(List<Long> memberIds) {
        if (memberIds == null || memberIds.isEmpty())
            throw new CustomException(MEMBER_IDS_IS_EMPTY_OR_NULL,
                    memberIds == null ? null : memberIds.toString());

        if (memberRepository.countByIds(memberIds) != memberIds.size())
            throw new CustomException(INVALID_MEMBER_ID_IS_INCLUDED);
    }
}
