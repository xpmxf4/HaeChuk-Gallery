package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.MemberDTO;
import HailYoungHan.Board.dto.MemberRegiDTO;
import HailYoungHan.Board.dto.MemberUpdateDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.repository.MemberRepository;
import HailYoungHan.Board.util.PasswordEncoder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        System.out.println("memberUpdateDTO = " + memberUpdateDTO);
        Member member = memberRepository
                .findById(memberUpdateDTO.getId())
                .orElseThrow(() -> new IllegalStateException("이런 회원은 없습니다."));
        System.out.println("bef member = " + member);

        if (memberUpdateDTO.getName() != null) {
            member.setName(memberUpdateDTO.getName());
        }

        if (memberUpdateDTO.getPassword() != null) {
            member.setPassword((passwordEncoder.encode(memberUpdateDTO.getPassword())));
        }
        System.out.println("aft member = " + member);

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
