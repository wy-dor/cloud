package com.learning.cloud.alipay.service;

import com.alipay.api.request.AlipayEcoEduKtBillingSendRequest;
import com.learning.cloud.alipay.entity.BillParam;

/**
 * @Author: yyt
 * @Date: 2020/1/10 2:16 下午
 * @Desc:
 */
public interface AlipaySignService {

    String getSignedOrder(BillParam billParam)throws Exception;
}
