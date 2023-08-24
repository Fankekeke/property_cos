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
 * 维修上报
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RepairInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String code;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 房屋
     */
    private Integer housesId;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String images;

    /**
     * 维修状态 0.未派修 1.已派修 2.已完成
     */
    private Integer repairStatus;

    /**
     * 工作人员
     */
    private Integer worker;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 维修类型（1.上下水管道 2.落水管 3.水箱 4.天线 5.供电线路 6.通讯线路 7.照明 8.供气线路 9.消防设施）
     */
    private String repairType;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String phone;
    @TableField(exist = false)
    private String houseAddress;

}
