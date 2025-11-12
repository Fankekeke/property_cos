package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 防疫登记
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EpidemicRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 体温
     */
    private BigDecimal temperature;

    /**
     * 健康码情况 0.正常 1.异常 2.感染
     */
    private Integer codeStatus;

    /**
     * 途经城市
     */
    private String throughCity;

    /**
     * 登记时间
     */
    private String createDate;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String phone;

}
