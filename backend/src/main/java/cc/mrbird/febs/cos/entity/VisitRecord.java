package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 来访记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VisitRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 来访人员姓名
     */
    private String name;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 访问目的
     */
    private String purposeVisit;

    /**
     * 访问时间
     */
    private String visitTime;

    /**
     * 状态（0.未审批 1.已审批 2.驳回）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 所属用户
     */
    private Integer userId;


}
