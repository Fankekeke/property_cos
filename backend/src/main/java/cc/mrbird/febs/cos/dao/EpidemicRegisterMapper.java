package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.EpidemicRegister;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface EpidemicRegisterMapper extends BaseMapper<EpidemicRegister> {

    // 分页获取防疫登记信息
    IPage<LinkedHashMap<String, Object>> epidemicByPage(Page page, @Param("epidemicRegister") EpidemicRegister epidemicRegister);
}
