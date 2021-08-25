package problem.jsonTransfer;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @program: javaExercise
 * @description:
 * @author: admin
 * @create: 2019/7/6 14:58
 */
public class Problem1 {
    public ResultBean doService(){
        ResultBean resultBean = new ResultBean();
        String returnString = "{resultData={\"ret\":\"0\",\"requestId\":\"20190625000296189032\",\"data\":{\"failureNumber\":0,\"failureList\":[],\"successfulAccount\":412.0,\"resultCode\":\"001\",\"successfulNumber\":1,\"msg\":\"您勾选的公务交易已成功提交\"},\"msg\":\"\"}}";
        System.out.println("getBusinesConsume.paramMap esg返回数据为(String)："+returnString);
        String returnStringtojson = "{\"ret\":\"0\",\"requestId\":\"20190625000296189032\",\"data\":{\"failureNumber\":0,\"failureList\":[],\"successfulAccount\":412.0,\"resultCode\":\"001\",\"successfulNumber\":1,\"msg\":\"您勾选的公务交易已成功提交\"},\"msg\":\"\"}";
        JSONObject  myJson = JSONObject.parseObject(returnStringtojson);
        Map returnMap = myJson;
        System.out.println("getBusinesConsume.paramMap esg返回数据为(Map)："+returnMap);
        /*JSONObject dataObject = (JSONObject) returnMap.get("resultData");*/
        JSONObject paramObject = myJson.getJSONObject("data");
        System.out.println("getBusinesConsume.data 内容是："+paramObject);

        resultBean.setData(paramObject);
        resultBean.setCode("0");
        resultBean.setMessage("操作成功");
        return resultBean;
    }

    public static void main(String[] args) {
        Problem1  problem1 = new Problem1();
        problem1.doService();
    }
}
