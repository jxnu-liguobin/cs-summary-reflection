package io.github.dreamylost.practice;

/**
 * @description 现有一个消息体，里面 String ， int 和 List 型的数据，数据结构定义如下：
	public class MyObject implements Serializable {
	    public String a;
	    publicint b;
	    public List c;
	}
	有一个发送端接口方法，负责将消息体转化成一个 byte 数组 return 出去以便发送：
	public interface Sender {
	    public byte[] send(MyObject myObject);
	}
	有一个接收端接口方法，负责接受一个 byte 数组并将 byte 数组还原成相应的消息体对象，最终 return 出去（里面的成员变量值要相同）
	public interface Receiver {
	    public MyObject receive(byte[] bytes);
	}
	1) 试写出 Sender 和 Receiver 的一个具体代码实现
	2) 请针对以下方面，试描述一些你认为可行的协议设计优化方案（即二进制数据和结构体对象的相互转换的设计优化方案）；
	或试描述你在1 ）小题中实现或使用的协议在设计上如何符合如下特征 ：
	a) 相同的结构体对象转换成更小的二进制数据，方便传输
	b) 二进制数据和结构体对象如何能更快地进行相互转换
	c) 如何使协议具有可扩展性，可以自动兼容不同结构的结构体
 */
public class Main10 {

	public static void main(String[] args) {
		//答案
//		可参考的序列化方式包括：
//		1) Java 标准序列化，如使用 ByteArrayStream ， ObjectStream 等对象
//		2) 使用 XML 或 JSON ，字符串操作
//		3) 使用二进制传输
//		4) 第三方开源的序列化方式
//		第 2 ）小题属于开放性题目，能描述出如下几点的，每点加 2 分，最高 8 分，其他描述点如果合理，也可以酌情给分：
//		1) 采用压缩算法，如：对基本类型进行变长编码等
//		2) 直接使用二进制数据传输而非字符串等其他形式，提高序列化效率
//		3) 基于 key-value 的设计方案，保证可扩展性
//		4) 对于部分变长类型的基本数据结构（如 String ， List 等），需要记录长度
	}

}
