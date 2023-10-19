package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.member.MemberDTO;
import HailYoungHan.Board.dto.member.MemberRegiDTO;
import HailYoungHan.Board.dto.member.MemberUpdateDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.exception.member.MemberNotFoundException;
import HailYoungHan.Board.exception.member.NameAlreadyExistsException;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원 등록
    @Transactional
    public void registerMember(MemberRegiDTO memberRegiDTO) {
        String name = memberRegiDTO.getName();
        String password = memberRegiDTO.getPassword();
        String email = memberRegiDTO.getEmail();

        if (memberRepository.existsByName(name)) {
            throw new NameAlreadyExistsException(name);
        }

        Member member = new Member(name, passwordEncoder.encode(password), email);
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
    public Member updateMember(MemberUpdateDTO memberUpdateDTO) {
        Member member = memberRepository
                .findById(memberUpdateDTO.getId())
                .orElseThrow(() -> new MemberNotFoundException(memberUpdateDTO.getId()));

        Optional.ofNullable(memberUpdateDTO.getName())
                .ifPresent(member::setName);
        Optional.ofNullable(memberUpdateDTO.getPassword())
                .ifPresent(rawPwd -> member.setPassword(passwordEncoder.encode(rawPwd)));

        return memberRepository.save(member);
    }

    @Transactional
    public void deleteMemberById(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public void deleteMembers(List<Long> ids) {
        memberRepository.deleteAllByIdInBatch(ids);
    }

    public MemberDTO getMemberByEmail(String email) {
        return memberRepository.getMemberByEmail(email);
    }
}
