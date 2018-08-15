package cn.edu.jxnu.other;

/**
 * @date: 2018-07-20
 * @author: liguobin
 * @description: MobUrlConfigVo
 */
public class MobUrlConfigVo {

	/**
	 * 允许被拓展
	 */
	private String resourceUrl;
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getResourceUrl() {

		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

}
