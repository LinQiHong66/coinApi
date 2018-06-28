package com.warrior.virtualcurrency;

import com.alibaba.fastjson.JSON;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lqh
 * @description: 定时任务处理充值
 * @program: ETH
 * @create: 2018-06-23 09:30
 * 实现思路： 通过遍历平台内的所有address账户，去区块浏览器查询to（也就是接收方地址）等于当前address的记录，那么这条记录即为该地址的充值记录，
 * 我们只要处理这条记录，将交易金额增加到对应address账户的资产即可。
 **/
@Component
public class RechargeTask {

    @Scheduled(initialDelay = 1 * 10 * 1000, fixedDelay = 1 * 6 * 1000)
    public void recharge() {
        System.out.println("定时器处理充值开始...................");
        //查询平台内的所有address账户
        List<String> addressList = new ArrayList();
        addressList.add("0xcabe812337277aabed79705ea1a28851beede56f");
        try {
            for (String address : addressList) {
                List<Map<String, String>> trans = RechargeTask.getEthTrans(address); // 区块浏览器获取交易数据
                if (trans == null || trans.size() == 0) {
                    continue;
                }
                for (Map<String, String> map : trans) {
                    String from_address = map.get("from");
                    String to_address = map.get("to"); // 接收方地址
                    String txid = map.get("hash");
                    Integer confirmations = new Integer(map.get("confirmations"));
                    String value = map.get("value");
                    BigDecimal valueMaxUnit = new BigDecimal(value).divide(EthcoinAPI.WEI).setScale(8);

                    if (!to_address.equals(address)) { // 只处理充值方
                        continue;
                    }
                    // 充值
                    //TODO:这里需要操作数据库，根据txid查询充值记录
                    Object rechargeInfo = null;
                    if (rechargeInfo == null && confirmations > 6) {
                        //TODO:这里需要操作数据库，插入一条充值记录，并且增加address账户的资产
                        System.out.println("充值成功," + from_address + "成功向" + to_address + "充值" + valueMaxUnit + "个ETH");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("recharge exception:" + e.getMessage());
        }
    }


    /**
     * ETH交易数据
     *
     * @param address
     * @return
     */
    public static List<Map<String, String>> getEthTrans(String address) {
        String responbody = HttpUtil.sendGet(
                "http://api.etherscan.io/api?module=account&action=txlist&address=" + address + "&sort=asc", null);
        Map<String, String> resultMap = (Map<String, String>) JSON.parseObject(responbody, Map.class);
        Object result = resultMap.get("result");
        if (result == null) {
            return null;
        }
        List<Map<String, String>> trans = JSON.parseObject(result.toString(), List.class);
        return trans;
    }

}
