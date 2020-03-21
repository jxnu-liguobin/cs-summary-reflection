package cn.edu.jxnu.examples.reflect;

/**
 * @author 梦境迷离.
 * @time 2018年6月12日
 * @version v1.0
 */
public class User {

	private String userId;
	private String userName;

	public User() {
		super();
	}

	public User(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + "]";
	}

}
