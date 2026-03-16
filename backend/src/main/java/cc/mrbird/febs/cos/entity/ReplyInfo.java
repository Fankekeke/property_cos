package cc.mrbird.febs.cos.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品回复
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReplyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 被回复用户
     */
    private Integer replyUserId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 商品ID
     */
    private Integer commodityId;

    /**
     * 回复用户
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 标题
     */
    @TableField(exist = false)
    private String title;

    /**
     * 用户名称
     */
    @TableField(exist = false)
    private String userName;

}
