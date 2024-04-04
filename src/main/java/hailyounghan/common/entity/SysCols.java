package hailyounghan.common.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;

public abstract class SysCols extends SysTimeCols {
    @CreatedBy
    @Column(updatable = false)
    private String reg_id;

    @LastModifiedBy
    private String mod_id;
}
