package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.dao.PaymentManageMapper;
import cc.mrbird.febs.cos.entity.EpidemicRegister;
import cc.mrbird.febs.cos.entity.HousesInfo;
import cc.mrbird.febs.cos.dao.HousesInfoMapper;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HousesInfoServiceImpl extends ServiceImpl<HousesInfoMapper, HousesInfo> implements IHousesInfoService {

    private final IOwnerInfoService ownerInfoService;

    private final PaymentManageMapper paymentManageMapper;

    private final HousesInfoMapper housesInfoMapper;

    private final IPaymentManageService paymentManageService;

    private final IPaymentRecordService paymentRecordService;

    private final IBulletinInfoService bulletinInfoService;

    private final IEpidemicRegisterService epidemicRegisterService;

    @Override
    public IPage<LinkedHashMap<String, Object>> housesInfoByPage(Page page, HousesInfo housesInfo) {
        return baseMapper.housesInfoByPage(page, housesInfo);
    }

    @Override
    public List<LinkedHashMap<String, Object>> housesInfoByOwnerId(Integer ownerId) {
        return baseMapper.housesInfoByOwnerId(ownerId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> housesConsumption(Integer housesId) {
        return baseMapper.housesConsumption(housesId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> housesConsumptionMonth(Integer housesId) {
        return baseMapper.housesConsumptionMonth(housesId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> housesConsumptionCheck(Integer housesId, String date) {
        return baseMapper.housesConsumptionCheck(housesId, date);
    }

    @Override
    public LinkedHashMap<String, Object> home(Integer type) {
        if (type == null) {
            LinkedHashMap<String, Object> result= new LinkedHashMap<>();
            result.put("ownerNum", ownerInfoService.count());
            result.put("housesNum", this.count());
            LinkedHashMap<String, Object> collectionInfo = paymentManageMapper.collectionInfo();
            result.put("received", collectionInfo.get("received").toString());
            result.put("advance", collectionInfo.get("advance").toString());
            result.put("bulletinInfo", bulletinInfoService.list());
            result.put("propertyItemYear", paymentManageMapper.propertyItemYear());
            result.put("housesTypeRate", housesInfoMapper.housesTypeRate());
            return result;
        } else {
            OwnerInfo ownerInfo = ownerInfoService.getOne(Wrappers.<OwnerInfo>lambdaQuery().eq(OwnerInfo::getUserId, type));
            LinkedHashMap<String, Object> result= new LinkedHashMap<>();
            result.put("housesNum", this.count(Wrappers.<HousesInfo>lambdaQuery().eq(HousesInfo::getOwnerId, ownerInfo.getId())));
            LinkedHashMap<String, Object> payableRate = paymentManageMapper.payableRate(ownerInfo.getId());
            result.put("unpaid", payableRate.get("unpaid") != null ? payableRate.get("unpaid").toString() : 0);
            result.put("electricity", payableRate.get("electricity") != null ? payableRate.get("electricity").toString() : 0);
            result.put("water", payableRate.get("water") != null ? payableRate.get("water").toString() : 0);
            result.put("bulletinInfo", bulletinInfoService.list());
            result.put("epidemicRate", paymentManageMapper.epidemicRate());
            return result;
        }
    }

    @Override
    public List<LinkedHashMap<String, Object>> housesListByOwnerId(Integer userId) {
        return baseMapper.housesListByOwnerId(userId);
    }
}
