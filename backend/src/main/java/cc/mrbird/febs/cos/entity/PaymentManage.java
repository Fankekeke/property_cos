package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 缴纳费用管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PaymentManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 房屋ID
     */
    private Integer housesId;

    /**
     * 年
     */
    private Integer year;

    /**
     * 月
     */
    private Integer month;

    /**
     * 缴纳类型 0.物业费
     */
    private Integer type;

    /**
     * 本月用量
     */
    private BigDecimal dosage;

    /**
     * 应收金额
     */
    private BigDecimal price;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String address;
    @TableField(exist = false)
    private Integer userId;
    @TableField(exist = false)
    private String payStatus;

}
