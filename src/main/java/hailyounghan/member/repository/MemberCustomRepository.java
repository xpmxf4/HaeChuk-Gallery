package hailyounghan.member.repository;


import hailyounghan.member.dto.query.MemberDbDTO;

import java.util.List;

public interface MemberCustomRepository {

    MemberDbDTO getSingleMember(Long id);

    List<MemberDbDTO> getAllMembers();

    MemberDbDTO getMemberByEmail(String email);
}
