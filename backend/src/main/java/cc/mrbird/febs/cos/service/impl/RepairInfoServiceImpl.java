package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.HousesInfo;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.service.IHousesInfoService;
import cc.mrbird.febs.cos.service.IOwnerInfoService;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import cc.mrbird.febs.system.service.IMailService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairInfoServiceImpl extends ServiceImpl<RepairInfoMapper, RepairInfo> implements IRepairInfoService {

    private final IHousesInfoService housesInfoService;

    private final IOwnerInfoService ownerInfoService;

    private final TemplateEngine templateEngine;

    private final IMailService mailService;

    @Override
    public IPage<LinkedHashMap<String, Object>> repairInfoByPage(Page page, RepairInfo repairInfo) {
        return baseMapper.repairInfoByPage(page, repairInfo);
    }

    @Override
    public LinkedHashMap<String, Object> repairInfoById(Integer repairId) {
        return baseMapper.repairInfoById(repairId);
    }

    /**
     * 分页查询房屋维修统计
     *
     * @param page       分页对象
     * @param repairInfo 房屋
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectHouseRepairPage(Page<HousesInfo> page, RepairInfo repairInfo) {
        return baseMapper.selectHouseRepairPage(page, repairInfo);
    }

    /**
     * 维修分析
     *
     * @param housesId 房屋ID
     */
    @Override
    @Async
    public void repairAnalyze(Integer housesId) {
        // 获取此房屋业主信息
        HousesInfo housesInfo = housesInfoService.getById(housesId);

        OwnerInfo ownerInfo = ownerInfoService.getById(housesInfo.getOwnerId());
        // 此房屋的维修记录
        List<RepairInfo> repairInfoList = this.list(Wrappers.<RepairInfo>lambdaQuery().eq(RepairInfo::getHousesId, housesId));
        Map<String, List<RepairInfo>> repairMap = repairInfoList.stream().filter(e -> StrUtil.isNotEmpty(e.getRepairType())).collect(Collectors.groupingBy(RepairInfo::getRepairType));
        if (CollectionUtil.isNotEmpty(repairMap)) {
            List<String> repairTypeList = new ArrayList<>();
            repairMap.forEach((key, value) -> {
                if (value.size() > 0) {
                    repairTypeList.add(key);
                }
            });
            Map<String, String> typeMap = new HashMap<String, String>() {
                {
                    put("1", "上下水管道");
                    put("2", "落水管");
                    put("3", "水箱");
                    put("4", "天线");
                    put("5", "供电线路");
                    put("6", "通讯线路");
                    put("7", "照明");
                    put("8", "供气线路");
                    put("9", "消防设施");
                }
            };
            if (CollectionUtil.isNotEmpty(repairTypeList)) {
                List<String> typeNameList = new ArrayList<>();
                for (String type : repairTypeList) {
                    typeNameList.add(typeMap.get(type));
                }
                if (StrUtil.isNotEmpty(ownerInfo.getEmail())) {
                    Context context = new Context();
                    context.setVariable("today", DateUtil.formatDate(new Date()));
                    context.setVariable("custom", ownerInfo.getName() + "，您好。" + StrUtil.join("、", typeNameList) + "已维修多次，请更换设备");
                    String emailContent = templateEngine.process("registerEmail", context);
                    mailService.sendHtmlMail(ownerInfo.getEmail(), DateUtil.formatDate(new Date()) + "设备更换提示", emailContent);
                }
            }
        }
    }
}
