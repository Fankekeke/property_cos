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
 * 缴费记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PaymentRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 缴纳人
     */
    private Integer userId;

    /**
     * 账单ID
     */
    private Integer paymentId;

    /**
     * 缴费日期
     */
    private String createDate;

    /**
     * 支付方式 1.支付宝
     */
    private Integer paymentType;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String address;
    @TableField(exist = false)
    private Integer type;

}
