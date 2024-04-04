package hailyounghan.member.dto.response;

import hailyounghan.member.dto.query.MemberDbDTO;
import lombok.Getter;

import java.util.List;

// MemberDTO 를 { members : [ ... ] } 의 형태로 내보내기 위한 ResponseDTO
@Getter
public class MemberResponseDTO {

    private List<MemberDbDTO> members;

    public MemberResponseDTO(List<MemberDbDTO> members) {
        this.members = members;
    }
}
