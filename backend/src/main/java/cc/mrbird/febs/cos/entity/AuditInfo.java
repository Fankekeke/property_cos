package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 活动审核
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuditInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动id
     */
    private Integer conferenceId;

    /**
     * 业主ID
     */
    private Integer ownerId;

    /**
     * 状态（0.未审核 1.通过 2.驳回）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 审核时间
     */
    private String auditDate;

    @TableField(exist = false)
    private String title;
    @TableField(exist = false)
    private String name;


}
