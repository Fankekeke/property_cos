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
 * 物业项管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PropertyItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 物业项名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 性质 1.统一交付 2.按量计算
     */
    private Integer type;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 创建时间
     */
    private String createDate;


}
