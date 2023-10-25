package HailYoungHan.Board.dto.member.response;

import HailYoungHan.Board.dto.member.query.MemberDbDTO;

import java.util.List;

// MemberDTO 를 { members : [ ... ] } 의 형태로 내보내기 위한 ResponseDTO
public class MemberResponseDTO {

    private List<MemberDbDTO> members;

    public MemberResponseDTO(List<MemberDbDTO> members) {
        this.members = members;
    }
}
