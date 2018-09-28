package cn.edu.jxnu.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * 测试
 *
 * @author 梦境迷离
 * @time 2018-08-15
 */
public class TestJava4 {

    private static String RESOURCEURL = "https://cmc.stu.126.net/u/json/cms/moocUrlRoutingConfigurationInfo.json";
    private static String VERSION = "0";
    MobUrlConfigVo mobUrlConfigVo = null;

    @Test
    public void test() {
        mobUrlConfigVo = new MobUrlConfigVo();
        mobUrlConfigVo.setResourceUrl(RESOURCEURL);
        mobUrlConfigVo.setVersion(VERSION);
        String s = JSON.toJSONString(mobUrlConfigVo);
        System.out.println(s);

        //不指定解析的类型会解析失败
        MobUrlConfigVo mobUrlConfigVo = JSONObject.parseObject(s, MobUrlConfigVo.class);
        System.out.println(mobUrlConfigVo.toString());
    }

}
