package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.PropertyItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IPropertyItemService extends IService<PropertyItem> {

    // 分页查询物业管理项
    IPage<LinkedHashMap<String,Object>> propertyItemByPage(Page page, PropertyItem propertyItem);

}
