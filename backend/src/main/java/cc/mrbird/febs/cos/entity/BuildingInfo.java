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
 * 楼宇管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BuildingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 楼宇名称
     */
    private String name;

    /**
     * 楼宇地址
     */
    private String address;

    /**
     * 街道
     */
    private String street;

    /**
     * 社区
     */
    private String community;

    /**
     * 总使用面积(m²)
     */
    private BigDecimal usageArea;

    /**
     * 总建筑面积(m²)
     */
    private BigDecimal surfaceArea;

    /**
     * 类别 1.平房 2.多层楼 3.高层楼 4.简易楼
     */
    private Integer type;

    /**
     * 间数
     */
    private Integer rooms;

    /**
     * 单元数
     */
    private Integer units;

    /**
     * 层数
     */
    private Integer layers;

    /**
     * 图片
     */
    private String images;

    /**
     * 创建时间
     */
    private String createDate;


}
