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
 * 房屋管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HousesInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 房屋地址
     */
    private String address;

    /**
     * 所属楼宇
     */
    private Integer buildingId;

    /**
     * 号
     */
    private String number;

    /**
     * 层
     */
    private Integer floor;

    /**
     * 使用面积(m²)
     */
    private BigDecimal usageArea;

    /**
     * 建筑面积(m²)
     */
    private BigDecimal surfaceArea;

    /**
     * 性质 1.住宅楼房 2.社区用处
     */
    private Integer nature;

    /**
     * 间数
     */
    private Integer rooms;

    /**
     * 购房人姓名
     */
    private String buyer;

    /**
     * 物业工作人员
     */
    private Integer staffId;

    /**
     * 业主ID
     */
    private Integer ownerId;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String community;


}
