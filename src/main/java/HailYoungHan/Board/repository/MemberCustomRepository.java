package HailYoungHan.Board.repository;

import HailYoungHan.Board.dto.MemberDTO;

import java.util.List;

public interface MemberCustomRepository {

    MemberDTO getSingleMember(Long id);
    List<MemberDTO> getAllMembers();
}
