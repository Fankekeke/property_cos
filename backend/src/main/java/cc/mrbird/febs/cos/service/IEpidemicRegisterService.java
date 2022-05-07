package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.EpidemicRegister;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IEpidemicRegisterService extends IService<EpidemicRegister> {

    // 分页获取防疫登记信息
    IPage<LinkedHashMap<String, Object>> epidemicByPage(Page page, EpidemicRegister epidemicRegister);

}
