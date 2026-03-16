package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 充电桩管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChargeStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 编号
     */
    private String code;

    /**
     * 充电桩名称
     */
    private String name;

    /**
     * 所在区域
     */
    private String address;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（1.空闲、2.出售、3.出租、4.已预定、5.维修）
     */
    private String status;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 类型（1.快充 2.慢充）
     */
    private String type;

    /**
     *
     */
    private Integer ownerId;


}
