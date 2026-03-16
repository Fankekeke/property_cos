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
 * 房屋出租
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 房屋编号
     */
    private Integer houseId;

    /**
     * 业主编号
     */
    private Integer ownerId;

    /**
     * 出租内容
     */
    private String content;

    /**
     * 图册
     */
    private String images;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 类型（1.出租 2.售卖）
     */
    private Integer type;

    /**
     * 价格
     */
    private BigDecimal rentPrice;

    /**
     * 电视（1.有 2.无）
     */
    private Integer hasTelevision;

    /**
     * 空调（1.有 2.无）
     */
    private Integer hasAirConditioner;

    /**
     * 衣柜（1.有 2.无）
     */
    private Integer hasWardrobe;

    /**
     * 床（1.有 2.无）
     */
    private Integer hasBed;

    /**
     * 卫生间（1.有 2.无）
     */
    private Integer hasBathroom;

    /**
     * 智能门锁（1.有 2.无）
     */
    private Integer hasDoorLock;

    /**
     * 阳台（1.有 2.无）
     */
    private Integer hasBalcony;

    /**
     * 暖气（1.有 2.无）
     */
    private Integer hasHeating;

    /**
     * 冰箱（1.有 2.无）
     */
    private Integer hasRefrigerator;

    /**
     * 洗衣机（1.有 2.无）
     */
    private Integer hasWashingMachine;

    /**
     * 热水器（1.有 2.无）
     */
    private Integer hasWaterHeater;

    /**
     * 宽带（1.有 2.无）
     */
    private Integer hasBroadband;

    /**
     * 沙发（1.有 2.无）
     */
    private Integer hasSofa;

    /**
     * 油烟机（1.有 2.无）
     */
    private Integer hasRangeHood;

    /**
     * 燃气灶（1.有 2.无）
     */
    private Integer hasGasStoves;

    /**
     * 做饭（1.有 2.无）
     */
    private Integer hasCook;

    /**
     * 删除标识（1.上架 2.下架）
     */
    private Integer delFlag;

    /**
     * 出租标题
     */
    private String title;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String address;
}
