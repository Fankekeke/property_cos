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
 * 安全巡检
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SafetyInspection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    private Integer staffId;

    /**
     * 站点编号
     */
    private String stationName;

    /**
     * 检查类型（1.早 2.中 3.晚）
     */
    private String checkType;

    /**
     * 检查时间
     */
    private String checkDate;
    private String content;

    /**
     * 打卡图片
     */
    private String images;


    @TableField(exist = false)
    private String staffName;
}
