package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 日常管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DailyManage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型 1.投诉 2.建议
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 创建人
     */
    private Integer userId;

    /**
     * 发布时间
     */
    private String createDate;

    /**
     * 状态 0.未处理 1.派出工作人员 2.已完成
     */
    private Integer readStatus;

    /**
     * 工作人员ID
     */
    private Integer worker;


}
