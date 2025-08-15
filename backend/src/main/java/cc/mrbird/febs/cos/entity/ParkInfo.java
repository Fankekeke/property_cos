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
 * 停车位管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParkInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 编号
     */
    private String code;

    /**
     * 停车位名称
     */
    private String name;

    /**
     * 所在区域
     */
    private String address;

    /**
     * 所属业主
     */
    private Integer ownerId;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 大小
     */
    private String parkSize;

    /**
     * 状态（1.空闲、2.出售、3.出租、4.已预定、5.维修）
     */
    private String status;

    @TableField(exist = false)
    private String ownerName;


}
