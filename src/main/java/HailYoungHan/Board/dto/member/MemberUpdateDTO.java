package HailYoungHan.Board.dto.member;

import HailYoungHan.Board.validation.AtLeastOneNotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@AtLeastOneNotNull(fields = {"name", "password"})
public class MemberUpdateDTO {

    @NotNull(message = "회원 업데이트시 id 값은 필수입니다")
    private Long id;
    @Nullable
    private String name;
    @Nullable
    private String password;
}
