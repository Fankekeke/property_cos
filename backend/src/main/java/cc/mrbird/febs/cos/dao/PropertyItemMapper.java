package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.PropertyItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface PropertyItemMapper extends BaseMapper<PropertyItem> {

    // 分页查询物业管理项
    IPage<LinkedHashMap<String,Object>> propertyItemByPage(Page page, @Param("propertyItem") PropertyItem propertyItem);
}
