package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 业主管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OwnerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 业主姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 照片
     */
    private String images;

    /**
     * 所属账户
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 邮箱地址
     */
    private String email;
}
