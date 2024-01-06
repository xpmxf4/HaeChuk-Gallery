package hailyounghan.board.repository.member;

import hailyounghan.board.dto.member.query.MemberDbDTO;

import java.util.List;

public interface MemberCustomRepository {

    MemberDbDTO getSingleMember(Long id);

    List<MemberDbDTO> getAllMembers();

    MemberDbDTO getMemberByEmail(String email);
}
