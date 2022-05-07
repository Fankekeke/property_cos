package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.PropertyItem;
import cc.mrbird.febs.cos.dao.PropertyItemMapper;
import cc.mrbird.febs.cos.service.IPropertyItemService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class PropertyItemServiceImpl extends ServiceImpl<PropertyItemMapper, PropertyItem> implements IPropertyItemService {

    @Override
    public IPage<LinkedHashMap<String, Object>> propertyItemByPage(Page page, PropertyItem propertyItem) {
        return baseMapper.propertyItemByPage(page, propertyItem);
    }
}
