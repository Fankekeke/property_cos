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
 * 活动记录
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConferenceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建人
     */
    private Integer staffId;

    /**
     * 活动主题
     */
    private String title;

    /**
     * 活动内容
     */
    private String content;

    /**
     * 活动地址
     */
    private String address;

    /**
     * 图片
     */
    private String images;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 活动邀请人
     */
    private String staffIds;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 所属物业
     */
    private Integer enterpriseId;
    private String organizer;
    private String status;

    @TableField(exist = false)
    private String staffName;
}
