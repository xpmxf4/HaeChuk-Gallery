package HailYoungHan.Board.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class MemberUpdateDTO {

    @NotBlank(message = "회원 업데이트시 id 값은 필수입니다")
    private Long id;
    @Nullable
    private String name;
    @Nullable
    private String password;
}
