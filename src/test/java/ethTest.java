import com.warrior.virtualcurrency.EthcoinAPI;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author: lqh
 * @description:
 * @program: ETH
 * @create: 2018-06-22 17:54
 **/
public class ethTest {


    final static EthcoinAPI eth = new EthcoinAPI("", "", "localhost", "8545", "");


    //1.创建钱包账户
    @Test
    public void newAccount() {
        String account = eth.newAccount("123456789"); //密码很重要，这里的密码在转账的时候需要作为参数传入
        System.out.println("account:" + account);
    }

    //2.查询账户余额
    @Test
    public void getBalance() {
        BigDecimal balance = eth.getBalance("0x3721d738e6cb2436d0758961c3d0a5ed625bbfa3"); //这里获取到的余额是最小单位的(1ETH= 1000000000000000000 WEI),这里需要进行转换
        System.out.println("balance:" + balance.divide(EthcoinAPI.WEI) + " ETH");
    }

    //转账
    @Test
    public void transfer() {
        String from = eth.getMainaccount();
        String to = "0x3721d738e6cb2436d0758961c3d0a5ed625bbfa3";
        BigDecimal value = new BigDecimal(1);
        String from_pwd = "123456";
        if (eth.unlockAccount(from, from_pwd)) {
            String txid = eth.sendTransaction(from, to, EthcoinAPI.unit10To16(value.multiply(EthcoinAPI.WEI)));
            if (!txid.equals("")) {
                System.out.println("转账成功...");
            } else {
                System.out.println("转账失败...");
            }
        } else {
            System.out.println("账户解锁失败，无法转账....");
        }
    }

    //预估矿工费
    @Test
    public void getFee() {
        BigDecimal fee = eth.getGasAndGasPrice();
        System.out.println("预估矿工费:"+fee.divide(EthcoinAPI.WEI));
    }
}
