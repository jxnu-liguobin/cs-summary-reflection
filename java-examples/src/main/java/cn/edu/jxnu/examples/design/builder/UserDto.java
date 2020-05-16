package cn.edu.jxnu.examples.design.builder;

/**
 * builder模式
 *
 * <p>Scala的case的copy方法也是
 *
 * @author 梦境迷离
 * @version 1.0 2018-08-27
 */
public class UserDto {

    private final String firstName; // 必传参数
    private final String lastName; // 必传参数
    private final int age; // 可选参数
    private final String phone; // 可选参数
    private final String address; // 可选参数

    private UserDto(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    // 因为有两个必填参数
    public static UserBuilder newUserBuilder(String fn, String ln) {
        return new UserBuilder(fn, ln);
    }

    // 委托UserBuilder来构造User对象
    public static class UserBuilder {
        private final String firstName;
        private final String lastName;
        private int age;
        private String phone;
        private String address;

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public UserDto builder() {
            return new UserDto(this);
        }
    }

    public static void main(String[] args) {
        UserDto userDto =
                UserDto.newUserBuilder("hello", "world")
                        .setAge(12)
                        .setPhone("110")
                        .setAddress("beijing")
                        .builder();
        System.out.println(userDto);
    }
}
