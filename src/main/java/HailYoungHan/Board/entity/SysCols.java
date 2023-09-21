package HailYoungHan.Board.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class SysCols {

    private LocalDateTime reg_date;
    private String reg_id;
    private LocalDateTime mod_date;
    private String mod_id;
}
