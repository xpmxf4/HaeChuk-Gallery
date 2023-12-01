package HailYoungHan.Board.dto.member.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
public class MemberDbDTO {

    @NotNull
    private Long id;

    private String name;

    private String email;

    @QueryProjection
    public MemberDbDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @QueryProjection
    public MemberDbDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
