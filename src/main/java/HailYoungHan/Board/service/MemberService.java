package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.member.MemberDTO;
import HailYoungHan.Board.dto.member.MemberRegiDTO;
import HailYoungHan.Board.dto.member.MemberUpdateDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.exception.member.MemberNotFoundException;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.util.PasswordEncoder;
import HailYoungHan.Board.exception.member.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            throw new EmailAlreadyExistsException(email);
        }

        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(PasswordEncoder.encode(password))
                .build();

        memberRepository.save(member);
    }

    public MemberDTO getSingleMember(Long id) {
        MemberDTO memberDTO = memberRepository.getSingleMember(id);

        if (memberDTO == null)
            throw new MemberNotFoundException(id);

        return memberDTO;
    }

    public List<MemberDTO> getAllMembers() {
        return memberRepository.getAllMembers();
    }

    //특정 회원 정보 수정
    @Transactional
    public void updateMember(Long userId, MemberUpdateDTO memberUpdateDTO) {
        // DB 에 memberUpdateDTO 의 id 에 해당하는 유저 존재 여부 확인
        Member member = memberRepository
                .findById(userId)
                .orElseThrow(() -> new MemberNotFoundException(userId));

        // memberUpdateDTO ---(map)---> Member(Entity) 로 map
        member.mapFromUpdateDto(memberUpdateDTO);

        // DB 업데이트 실행
        memberRepository.save(member);
    }

    @Transactional
    public void deleteMembers(List<Long> ids) {
        memberRepository.deleteMembers(ids);
    }
}
