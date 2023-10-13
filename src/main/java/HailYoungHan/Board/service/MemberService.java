package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.member.MemberDTO;
import HailYoungHan.Board.dto.member.MemberRegiDTO;
import HailYoungHan.Board.dto.member.MemberUpdateDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.exception.member.MemberNotFoundException;
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
    public Member registerMember(MemberRegiDTO memberRegiDTO) throws IllegalStateException {
        String name = memberRegiDTO.getName();
        String password = memberRegiDTO.getPassword();

        if (memberRepository.existsByName(name)) {
            throw new IllegalStateException("해당 이름은 이미 있는 이름입니다.");
        }
        Member member = new Member(name, passwordEncoder.encode(password));
        return memberRepository.save(member);
    }

    public MemberDTO getSingleMember(Long id) {
        if (!memberRepository.existsById(id))
            throw new IllegalArgumentException("없는 회원입니다.");

        return memberRepository.getSingleMember(id);
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

}
