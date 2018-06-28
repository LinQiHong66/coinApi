import com.warrior.virtualcurrency.JsonUtil;
import jodd.util.StringUtil;
import org.hibernate.validator.internal.util.StringHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lqh
 * @description:
 * @program: ethApi
 * @create: 2018-06-26 15:30
 **/
public class NeoTest {

    String host;
    String port;

    public NeoTest() {
    }

    public NeoTest(String host, String port) {
        this.host = host;
        this.port = port;
    }


    /**
     * 获取当前最高的区块高度
     */
    public String getblockcount() {
        System.out.print("进入获取当前最高的区块高度");
        String result = "";
        String blockcount = "";
        try {
            StringBuffer url = new StringBuffer(this.host).append(":" + this.port);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            List<Object> params = new ArrayList<>();
            map.put("jsonrpc", "2.0");
            map.put("method", "getblockcount");
            map.put("params", params);
            map.put("id", 1);
            result = sendPost(url.toString(), JsonUtil.GsonString(map));
            Map<String, Object> result_map = JsonUtil.GsonToMaps(result.toString());
            String result_map_result = result_map.get("result").toString();
            System.out.print("当前最高的区块高度-------------------------------------------------------------" + result_map_result);
            if (result != null) {
                blockcount = result_map_result;
            }
        } catch (Exception e) {
            System.out.println("提取区块高度失败" + e);
        }
        return blockcount;
    }


    /**
     * 得到高度的hash
     */
    public String getblockhash(String height) {
        System.out.print("进入得到高度的hash");
        String address = "";
        String result = "";
        try {
            StringBuffer url = new StringBuffer(this.host).append(":" + this.port);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            List<Object> params = new ArrayList<Object>();
            params.add(height);
            map.put("jsonrpc", "2.0");
            map.put("method", "getblockhash");
            map.put("params", params);
            map.put("id", 1);
            result = sendPost(url.toString(), JsonUtil.GsonString(map));
            Map<String, Object> result_map = JsonUtil.GsonToMaps(result.toString());
            System.out.print("高度的hash" + result_map);
            if (result_map != null) {
                String result_map_result = result_map.get("result").toString();
                getblock(result_map_result);
            }
        } catch (Exception e) {
            System.out.println("提取区块高度失败" + e);
        }
        return address;
    }


    /**
     * 监听区块的节点信息
     */
    public String getblock(Object identi) {
        System.out.print("进入监听区块的节点信息");
        String address = "";
        String result = "";
        try {
            StringBuffer url = new StringBuffer(this.host).append(":" + this.port);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            List<Object> params = new ArrayList<Object>();
            params.add(identi);
            params.add("1");
            map.put("jsonrpc", "2.0");
            map.put("method", "getblock");
            map.put("params", params);
            map.put("id", 1);
            result = sendPost(url.toString(), JsonUtil.GsonString(map));
            Map<String, Object> result_map = JsonUtil.GsonToMaps(result.toString());

            if (result_map != null) {
                result = result_map.get("result").toString();
            }
        } catch (Exception e) {
            System.out.println("提取区块高度失败" + e);
        }
        return result;
    }


    /**
     * 查询每笔交易的详情
     */
    public String getApplicationLog(String txid) {
        System.out.print("进入监听区块的节点信息");
        String address = "";
        String result = "";
        try {
            StringBuffer url = new StringBuffer(this.host).append(":" + this.port);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            List<Object> params = new ArrayList<Object>();
            params.add(txid);
            params.add("1");
            map.put("jsonrpc", "2.0");
            map.put("method", "getapplicationlog");
            map.put("params", params);
            map.put("id", 1);
            result = sendPost(url.toString(), JsonUtil.GsonString(map));
            Map<String, Object> result_map = JsonUtil.GsonToMaps(result.toString());

            if (result_map != null) {
                String result_map_result = result_map.get("result").toString();
                System.out.print("++++++++++++++++++" + result_map_result);
            }
        } catch (Exception e) {
            System.out.println("提取区块高度失败" + e);
        }
        return address;
    }


    /**
     * newaccount
     */
    public String getnewAddress() {
        String address = "";
        String result = "";
        try {
            StringBuffer url = new StringBuffer(this.host).append(":" + this.port);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            List<Object> params = new ArrayList<>();
            map.put("jsonrpc", "2.0");
            map.put("method", "getnewaddress");
            map.put("params", params);
            map.put("id", 1);
            result = sendPost(url.toString(), JsonUtil.GsonString(map));
            Map<String, Object> result_map = JsonUtil.GsonToMaps(result.toString());
            if (result_map != null) {
                String result_map_result = result_map.get("result").toString();
                if (!StringUtil.isEmpty(result_map_result)) {
                    address = result_map_result;
                }
            }
        } catch (Exception e) {
            System.out.println("创建地址失败:" + e);
        }
        return address;
    }

    /**
     * 获取余额
     */
    public BigDecimal getBalance(String asset_id) {
        BigDecimal balance = new BigDecimal(0);
        try {
            StringBuffer url = new StringBuffer(this.host).append(":" + this.port);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            List<Object> params = new ArrayList<>();
            params.add(asset_id);
            map.put("jsonrpc", "2.0");
            map.put("method", "getbalance");
            map.put("params", params);
            map.put("id", 1);
            String result = sendPost(url.toString(), JsonUtil.GsonString(map));
            System.out.println("result:" + result);
            Map<String, Object> result_map = JsonUtil.GsonToMaps(result.toString());
            if (result_map != null) {
                Map<String, Object> result_map_result = JsonUtil.GsonToMaps(result_map.get("result").toString());
                if (result_map_result != null) {
                    String b = result_map_result.get("confirmed").toString();
                    if (!StringUtil.isEmpty(b)) {
                        balance = new BigDecimal(b);
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("获取余额失败:" + e);
        }
        return balance;
    }

    /**
     * 交易
     */
    public String sendtoaddress(String asset_id, String address, String value, String userName,
                                String pass) {
        String res = "";
        try {
            StringBuffer url = new StringBuffer(this.host).append(":" + this.port);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            List<Object> params = new ArrayList<>();
            params.add(asset_id);
            params.add(address);
            params.add(value);
            params.add("0");
            map.put("jsonrpc", "2.0");
            map.put("method", "sendtoaddress");
            map.put("params", params);
            map.put("id", 1);
            String result = sendPost(url.toString(), JsonUtil.GsonString(map));
            Map<String, Object> result_map = JsonUtil.GsonToMaps(result.toString());
            if (result_map != null) {
                Map<String, Object> result_map_result = JsonUtil.GsonToMaps(result_map.get("result").toString());
                if (result_map_result != null) {
                    String txid = result_map_result.get("txid").toString();
                    if (!StringUtil.isEmpty(txid)) {
                        res = txid;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("交易失败:" + e);
        }
        return res;
    }

    /**
     * 验证地址是否是正确的 NEO地址。
     */
    public boolean validateaddress(String address, String userName, String pass) {
        boolean res = false;
        try {
            StringBuffer url = new StringBuffer(this.host).append(":" + this.port);
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            List<Object> params = new ArrayList<>();
            params.add(address);
            map.put("jsonrpc", "2.0");
            map.put("method", "validateaddress");
            map.put("params", params);
            map.put("id", 1);
            String result = sendPost(url.toString(), JsonUtil.GsonString(map));
            Map<String, Object> result_map = JsonUtil.GsonToMaps(result.toString());
            if (result_map != null) {
                Map<String, Object> result_map_result = JsonUtil.GsonToMaps(result_map.get("result").toString());
                if (result_map_result != null) {
                    String isvalid = result_map_result.get("isvalid").toString();
                    if (!StringUtil.isEmpty(isvalid) && "true".equals(isvalid)) {
                        res = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("校验地址错误:" + e);
        }
        return res;
    }

    public static String sendPost(String... data) throws Exception {
        String result = "";
        try {
            URL postUrl = new URL(data[0]);
            URLConnection connection = postUrl.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setRequestProperty("accept", "*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("charset", "UTF-8");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            try (OutputStreamWriter out = new OutputStreamWriter(httpURLConnection.getOutputStream())) {
                out.write(data[1]);
                out.flush();
            }
            try (BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                String line = "";
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
            if (httpURLConnection.getResponseCode() != 200) {
                throw new Exception("数据接口请求失败");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
        return result;
    }

    public static void main(String args[]) {

        NeoTest neo = new NeoTest("http://192.168.10.183", "20332");

        //1.---------------创建address
/*        String address = neo.getnewAddress();
        System.out.println("address:" + address);*/

        //2.---------------blockAccount
/*        String blockAccount = neo.getblockcount();
        System.out.println("blockAccount:" + blockAccount);*/


        //2.---------------getblock
/*        String block = neo.getblock(4587);
        System.out.println("block:" + block);*/

/*        BigDecimal balance = neo.getBalance("ecc6b20d3ccac1ee9ef109af5a7cdb85706b1df9");
        System.out.println("balance:" + balance);*/

        String log = neo.getApplicationLog("0x78cbf120658c63da2e609e693384a8df7eb63f2afe88b6edfef9a3e4ff14a0b0");
        System.out.println(log);
    }


}
