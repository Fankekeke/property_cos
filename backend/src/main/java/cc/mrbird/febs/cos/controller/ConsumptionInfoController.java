package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ConsumptionInfo;
import cc.mrbird.febs.cos.entity.PaymentManage;
import cc.mrbird.febs.cos.entity.dto.ConsumptionInfoList;
import cc.mrbird.febs.cos.service.IConsumptionInfoService;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/consumption-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConsumptionInfoController {

    private final IConsumptionInfoService consumptionInfoService;

    /**
     * 添加消耗记录
     * @param consumptionInfoStr
     * @return
     */
    @PostMapping("/addConsumption")
        public R addConsumption(String consumptionInfoStr) {
        JSONArray array = JSONUtil.parseArray(consumptionInfoStr);
        List<ConsumptionInfo> consumptionInfoList = JSONUtil.toList(array, ConsumptionInfo.class);
        return R.ok(consumptionInfoService.saveBatch(consumptionInfoList));
    }

}
