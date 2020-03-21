package cn.edu.jxnu.other;

import java.util.List;

public class TestJava6 {

	public static void main(String[] args) {

	}
	public void inspect(List<?> list) {
		for(Object object : list) {
			System.out.println(object);
		}
	//	list.add(1);
		//向未知集合中添加对象不是类型安全的，这会导致编译错误，唯一例外的是null jdk1.5以后可以使用指定索引的添加 
	}
//	public void test() {
//		List<String> strs = new ArrayList<String>();
//		inspect(strs);//编译错误
//	}

}
