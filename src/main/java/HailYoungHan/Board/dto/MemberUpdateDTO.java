package HailYoungHan.Board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateDTO {

    private Long id;
    private String newName;
    private String newPassword;

}
