package cc.mrbird.febs.cos.entity.dto;

import cc.mrbird.febs.cos.entity.ConsumptionInfo;
import lombok.Data;

import java.util.List;

@Data
public class ConsumptionInfoList {
    private List<ConsumptionInfo> consumptionInfos;
}
