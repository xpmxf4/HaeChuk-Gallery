package HailYoungHan.Board.repository.member;

import HailYoungHan.Board.dto.member.query.MemberDbDTO;
import HailYoungHan.Board.exception.member.MemberNotFoundException;

import java.util.List;

public interface MemberCustomRepository {

    MemberDbDTO getSingleMember(Long id) throws MemberNotFoundException;
    List<MemberDbDTO> getAllMembers();

    MemberDbDTO getMemberByEmail(String email);
}
