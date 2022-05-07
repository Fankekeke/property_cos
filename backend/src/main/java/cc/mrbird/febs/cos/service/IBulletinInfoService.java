package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.BulletinInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IBulletinInfoService extends IService<BulletinInfo> {

    // 分页获取公告信息
    IPage<LinkedHashMap<String, Object>> bulletinInfoByPage(Page page, BulletinInfo bulletinInfo);

}
