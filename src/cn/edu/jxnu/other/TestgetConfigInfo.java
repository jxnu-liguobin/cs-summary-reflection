package cn.edu.jxnu.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class TestgetConfigInfo {
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
		MobUrlConfigVo mobUrlConfigVo =JSONObject.parseObject(s,MobUrlConfigVo.class);
		System.out.println(mobUrlConfigVo.toString());
	}

}
