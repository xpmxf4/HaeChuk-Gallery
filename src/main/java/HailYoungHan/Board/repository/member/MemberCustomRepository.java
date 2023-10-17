package HailYoungHan.Board.repository.member;

import HailYoungHan.Board.dto.member.MemberDTO;
import HailYoungHan.Board.exception.member.MemberNotFoundException;

import java.util.List;

public interface MemberCustomRepository {

    MemberDTO getSingleMember(Long id) throws MemberNotFoundException;
    List<MemberDTO> getAllMembers();
}
