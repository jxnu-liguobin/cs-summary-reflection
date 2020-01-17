//导入子模块的东东
pub mod hosting;

//Rust会查找 src/hosting.rs（同名文件） 或者 src/hosting/mod.rs，mod不再包含子模块则使用src/hosting.rs，否则使用src/hosting/mod.rs
pub fn add_to_waitlist() {
    hosting::add_to_waitlist_host();
}