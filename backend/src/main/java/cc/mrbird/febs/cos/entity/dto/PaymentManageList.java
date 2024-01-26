package cc.mrbird.febs.cos.entity.dto;

import cc.mrbird.febs.cos.entity.PaymentManage;
import lombok.Data;

import java.util.List;

@Data
public class PaymentManageList {
    private List<PaymentManage> paymentManages;
}
