package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.MemberRegiDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.repository.MemberRepository;
import HailYoungHan.Board.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member registerMember(MemberRegiDTO memberRegiDTO) throws IllegalStateException {
        String name = memberRegiDTO.getName();
        String password = memberRegiDTO.getPassword();

        if (memberRepository.existsByName(name)) {
            throw new IllegalStateException("해당 이름은 이미 있는 이름입니다.");
        }
        Member member = new Member(name, passwordEncoder.encode(password));
        return memberRepository.save(member);
    }

}
