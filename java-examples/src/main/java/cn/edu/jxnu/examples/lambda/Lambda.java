package cn.edu.jxnu.examples.lambda;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description Java 8 学习
 * @author 梦境迷离
 */
public class Lambda {

    /**
     * Lambda可能会返回一个值。返回值的类型也是由编译器推测出来的。如果lambda的函数体只有一行的话， 那么没有必要显式使用return语句
     *
     * @description:
     */
    public static void main(String[] args) {
        int[] iArr = {1, 3, 5, 6, 7, 8, 9, 4, 2};
        Arrays.stream(iArr).forEach(System.out::print);
        System.out.println("\n******************************");
        Arrays.stream(iArr).map((x) -> x + 1).forEach(System.out::print);
        System.out.println("\n***************所有传递的对象都不会被轻易改变***************");
        Arrays.stream(iArr).forEach(System.out::print);
        System.out.println("\n******************************");
        Arrays.stream(iArr).map(x -> x % 2 == 0 ? x : x + 1).forEach(System.out::print);
        System.out.println("\n******************************");
        // 先安长度，再按大小写不敏感的字母顺序排序
        Comparator<String> cmp =
                Comparator.comparingInt(String::length)
                        .thenComparing(String.CASE_INSENSITIVE_ORDER);
        List<String> list = new ArrayList<String>();
        list.add("abc");
        list.add("abcd");
        list.add("qwe");
        list.add("qwz");
        Collections.sort(list, cmp);
        // 遍历集合
        list.stream().forEach(System.out::println);
        System.out.println("\n**********处理后返回********************");
        List<String> list2 = new ArrayList<>();
        list2 =
                list.stream()
                        .map(
                                string -> {
                                    return "stream().map()处理之后：" + string;
                                })
                        .collect(Collectors.toList());
        list2.stream().forEach(System.out::println);
        System.out.println("\n**********对集合进行过滤********************");
        List<String> list3 = new ArrayList<>();
        list3 = list.stream().filter(s -> s != "abc").collect(Collectors.toList());
        list3.stream().forEach(System.out::println);
        System.out.println("\n**********final********************");
        final int num = 4; // 去掉final也不会报错，Java8自动将lambda中的num视为final
        Function<Integer, Integer> strigConverter = (from) -> from * num;
        System.out.println(strigConverter.apply(3));
        System.out.println("\n**********下面去掉final会报错********************");
        // int num2 = 0;
        // Function<Integer, Integer> strigConverter2 = (from)->from * num2;
        // num2++;//不能对num2进行操作，已是final
        // System.out.println(strigConverter.apply(3));
        System.out.println("\n************方法引用1******************");
        // 前半部表示；类名或实例名，后半部表示方法名称，如果是构造函数则使用new
        // 静态方法引用-ClassName::methodName
        // 实例上的实例方法引用-instanceReference::methodName
        // 超类上的实例方法引用-super::methodName
        // 类型上的实例方法引用-ClassName：：methodName
        // 构造方法引用-Class::new
        // 数组构造方法引用-TypeName[]::new
        List<Integer> integers = new ArrayList<>();
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.stream().map(i -> i + 1).forEach(System.out::println);
        System.out.println("\n************方法引用2******************");
        List<User> users = new ArrayList<>();
        for (int j = 1; j < 10; j++) {
            users.add(new User(j, j + "user"));
        }
        // 输出所有name属性，User::getName作为调用目标，System.out::println作为参数传入，由系统自动判断
        users.stream().map(User::getName).forEach(System.out::println);
        System.out.println("\n************方法引用3******************");
        // 如果使用的是静态方法，或调用目标明确，流内的元素会自动作为参数使用
        // 如果函数引用表示实例方法，并且不存在调用目标，流内的元素自动作为调用目标
        // 如果存在同名的实例方法，或静态函数，则编译报错
        // 方法引用可以直接使用构造函数,左边必须为函数接口
        UserTactory<User> u = User::new;
        // 系统根据UserTactory.create的函数签名，选择合适的User构造方法，创建UserTactory实例后，create被委托给User的实际构造函数
        User u1 = u.create(1, "1-name");
        System.out.println(u1.toString());
        User u2 = new User(2, "2-name");
        System.out.println(u2.toString());
        System.out.println("\n************1-----遍历数组并进行处理******************");
        int[] a = {1, 3, 4, 5, 6, 7, 8, 9, 10};
        // Arrays.stream(a)方法返回一个流对象，类似于集合或数组，流对象也是一个对象的集合，赋予我们遍历流内元素的功能
        Arrays.stream(a)
                .forEach(
                        new IntConsumer() { // forEach方法接收IntConsumer接口的实现，因为数组int，IntStream
                            // 还支持，DoubleStream，LongStream，普通对象流，这取决于它的参数
                            @Override
                            public void accept(int value) {
                                System.out.println("处理之后" + value);
                            }
                        });
        System.out.println("\n************2-----遍历数组并进行处理******************");
        // 进一步简化---去掉forEach方法的类型，因为可以从上下文推导出来
        Arrays.stream(a)
                .forEach(
                        (final int x) -> {
                            System.out.println("2-处理之后" + x);
                        });
        System.out.println("\n***************************");
        // 去掉x的参数类型，因为可以从上下文推导出来
        Arrays.stream(a)
                .forEach(
                        (x) -> {
                            System.out.println("3-处理之后" + x);
                        });
        System.out.println("\n***************************");
        // 进一步简化---去掉方括号
        Arrays.stream(a).forEach((x) -> System.out.println("4-处理之后" + x));
        System.out.println("\n***************************");
        // 进一步简化，因为可以通过方法引用推导，可以省略参数申明和传递
        Arrays.stream(a).forEach(System.out::println);
        // Lambda在虚拟机中几乎等同匿名类的实现，但是写法和编程范式上有明显的区别
        System.out.println("\n************多个处理器的整合***************");
        IntConsumer outPrintln = System.out::println;
        IntConsumer errPrintln = System.err::println;
        Arrays.stream(a).forEach(outPrintln.andThen(errPrintln));
        System.out.println("\n************使用并行流过滤数据***************");
        // 使用函数式编程统计给定范围内所有的质数
        // long s = System.currentTimeMillis();
        // long t1 = IntStream.range(1,1000000).filter(Lambda::isPrime).count();
        // 耗费400~500ms
        // System.out.println("包含质数个数：" + t1 + " 花费时间：" +
        // String.valueOf(System.currentTimeMillis() - s));
        // //并行化流
        long s2 = System.currentTimeMillis();
        long t2 = IntStream.range(1, 1000000).parallel().filter(Lambda::isPrime).count();
        // 耗费时间不超过250ms,提升很多
        System.out.println(
                "包含质数个数：" + t2 + " 花费时间：" + String.valueOf(System.currentTimeMillis() - s2));
        // 从集合中得到一个流，或者并行流
        List<Student> uu = new ArrayList<>();
        uu.add(new Student(1, 100d));
        uu.add(new Student(2, 55d));
        uu.add(new Student(3, 65d));
        uu.add(new Student(4, 88d));
        uu.add(new Student(5, 99d));
        uu.add(new Student(6, 85d));
        // 计算平均分
        double avg = uu.stream().mapToDouble(s -> s.score).average().getAsDouble();
        System.out.println("平均分：" + String.valueOf(avg));
        System.out.println("\n************将代码并行化***************");
        // 得到并行流
        double avg2 = uu.parallelStream().mapToDouble(s -> s.score).average().getAsDouble();
        System.out.println(avg2);
        // 构造一个大的数组准备排序
        int[] paeall = {
            1, 2, 3, 4, 6, 7, 8, 9, 0, 11, 22, 44, 66, 77, 44, 55, 66, 88, 99, 0, 1, 1, 2, 3, 4, 6,
            7, 8, 9, 0, 11, 22, 44, 66, 77, 44, 55, 66, 88, 99, 0, 1, 1, 2, 3, 4, 6, 7, 8, 9, 0, 11,
            22, 44, 66, 77, 44, 55, 66, 88, 99, 0, 1, 1, 2, 3, 4, 6, 7, 8, 9, 0, 11, 22, 44, 66, 77,
            44, 55, 66, 88, 99, 0, 1, 1, 2, 3, 4, 6, 7, 8, 9, 0, 11, 22, 44, 66, 77, 44, 55, 66, 88,
            99, 0, 1, 1, 2, 3, 4, 6, 7, 8, 9, 0, 11, 22, 44, 66, 77, 44, 55, 66, 88, 99, 0, 1, 1, 2,
            3, 4, 6, 7, 8, 9, 0, 11, 22, 44, 66, 77, 44, 55, 66, 88, 99, 0, 1, 1, 2, 3, 4, 6, 7, 8,
            9, 0, 11, 22, 44, 66, 77, 44, 55, 66, 88, 99, 0, 1, 1, 2, 3, 4, 6, 7, 8, 9, 0, 11, 22,
            44, 66, 77, 44, 55, 66, 88, 99, 0, 1, 1, 2, 3, 4, 6, 7, 8, 9, 0, 11, 22, 44, 66, 77, 44,
            55, 66, 88, 99, 0, 1
        };
        Arrays.parallelSort(paeall);
        System.out.println("\n************给数组中的每个值都赋值一个随机数***************");
        Random random = new Random();
        System.out.println("\n************串行**************");
        Arrays.setAll(a, i -> random.nextInt(100)); // 串行
        Arrays.stream(a).forEach(System.out::println);
        System.out.println("\n************并行**************");
        Arrays.parallelSetAll(a, i -> random.nextInt(100)); // 并行
        Arrays.stream(a).forEach(System.out::println);
    }

    /** @description:判断一个数是否是质数 */
    public static boolean isPrime(int number) {
        int tmp = number;
        if (tmp < 2) return false;
        for (int i = 2; i <= Math.sqrt(tmp); i++) {
            if (tmp % i == 0) return false;
        }
        return true;
    }

    // 声明一个函数接口，U extends User，上界通配符，只能取
    @FunctionalInterface
    interface UserTactory<U extends User> {
        U create(int id, String name);
    }

    static class User {
        private Integer id;

        private String name;

        public User(Integer id, String name) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "User [id=" + id + ", name=" + name + "]";
        }
    }

    static class Student {
        public Student(Integer idInteger, Double score) {
            super();
            this.idInteger = idInteger;
            this.score = score;
        }

        private Integer idInteger;
        private Double score;

        public Integer getIdInteger() {
            return idInteger;
        }

        public void setIdInteger(Integer idInteger) {
            this.idInteger = idInteger;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Student [idInteger=" + idInteger + ", score=" + score + "]";
        }
    }
}
