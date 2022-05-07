package cc.mrbird.febs.job.domain;


import cc.mrbird.febs.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_job_log")
@Excel("调度日志信息表")
public class JobLog implements Serializable {

    private static final long serialVersionUID = -7114915445674333148L;
    // 任务执行成功
    public static final String JOB_SUCCESS = "0";
    // 任务执行失败
    public static final String JOB_FAIL = "1";

    @TableId(value = "LOG_ID", type = IdType.AUTO)
    private Long logId;

    private Long jobId;

    @ExcelField(value = "Bean名称")
    private String beanName;

    @ExcelField(value = "方法名称")
    private String methodName;

    @ExcelField(value = "方法参数")
    private String params;

    @ExcelField(value = "状态", writeConverterExp = "0=成功,1=失败")
    private String status;

    @ExcelField(value = "异常信息")
    private String error;

    @ExcelField(value = "耗时（毫秒）")
    private Long times;

    @ExcelField(value = "执行时间", writeConverter = TimeConverter.class)
    private Date createTime;

    private transient String createTimeFrom;
    private transient String createTimeTo;

}
