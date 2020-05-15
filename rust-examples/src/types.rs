///类型
pub fn types() {
    let decimal = 65.4321_f32;
    //不支持隐式转化
    //let integer: u8 = decimal;
    // FIXME ^ Comment out this line

    //显示转化
    let integer = decimal as u8;
    let character = integer as char;

    println!("Casting: {} -> {} -> {}", decimal, integer, character);

    // 1000 already fits in a u16
    println!("1000 as a u16 is: {}", 1000 as u16);

    // 1000 - 256 - 256 - 256 = 232
    // Under the hood, the first 8 least significant bits (LSB) are kept,
    // while the rest towards the most significant bit (MSB) get truncated.
    //println!("1000 as a u8 is : {}", 1000 as u8);
    // -1 + 256 = 255
    println!("  -1 as a u8 is : {}", (-1i8) as u8);
    println!("1000 mod 256 is : {}", 1000 % 256);

    println!(" 128 as a i16 is: {}", 128 as i16);
    //println!(" 128 as a i8 is : {}", 128 as i8);
    //println!("1000 as a u8 is : {}", 1000 as u8);
    //println!(" 232 as a i8 is : {}", 232 as i8);

    literals();
}

fn literals() {
    //类型作为后缀
    let x = 1u8;
    let y = 2u32;
    let z = 3f32;

    //非固定文字，其类型取决于如何使用
    let i = 1;
    let f = 1.0;

    //Returns the size of the pointed-to value in bytes.
    println!("size of `x` in bytes: {}", std::mem::size_of_val(&x));
    println!("size of `y` in bytes: {}", std::mem::size_of_val(&y));
    println!("size of `z` in bytes: {}", std::mem::size_of_val(&z));
    println!("size of `i` in bytes: {}", std::mem::size_of_val(&i));
    println!("size of `f` in bytes: {}", std::mem::size_of_val(&f));
}
