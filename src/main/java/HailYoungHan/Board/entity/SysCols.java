package HailYoungHan.Board.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class SysCols {

    @CreatedDate
    private LocalDateTime reg_date;
    @CreatedBy
    private String reg_id;
    @LastModifiedDate
    private LocalDateTime mod_date;
    @LastModifiedBy
    private String mod_id;
}