package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 水电耗量
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConsumptionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    /**
     * 房屋ID
     */
    private Integer housesId;
    /**
     * 类型 1..电 2.水
     */
    private Integer type;

    /**
     * 用量
     */
    private BigDecimal dose;

    /**
     * 年
     */
    private Integer year;

    /**
     * 月
     */
    private Integer month;


}
