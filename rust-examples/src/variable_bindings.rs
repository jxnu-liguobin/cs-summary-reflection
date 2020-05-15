///变量绑定
pub fn variable_bindings() {
    let an_integer = 1u32;
    let a_boolean = true;
    let unit = ();

    //拷贝
    let copied_integer = an_integer;

    println!("An integer: {:?}", copied_integer);
    println!("A boolean: {:?}", a_boolean);
    println!("Meet the unit value: {:?}", unit);

    //编译器对未使用的变量绑定发出警告；这些警告可以
    //通过在变量名前面加下划线来消音
    let _unused_variable = 3u32;

    let noisy_unused_variable = 2u32;

    //可变
    let _immutable_binding = 1;
    let mut mutable_binding = 1;

    println!("Before mutation: {}", mutable_binding);

    mutable_binding += 1;

    println!("After mutation: {}", mutable_binding);

    //_immutable_binding += 1;

    scope_shadowing();
    declare_first();
}

fn scope_shadowing() {
    let long_lived_binding = 1;

    {
        //仅在此块中有效
        let short_lived_binding = 2;

        println!("inner short: {}", short_lived_binding);

        let long_lived_binding = 5_f32;

        println!("inner long: {}", long_lived_binding);
    }

    //println!("outer short: {}", short_lived_binding);

    println!("outer long: {}", long_lived_binding);

    let long_lived_binding = 'a';

    println!("outer long: {}", long_lived_binding);
}

fn declare_first() {
    //定义但不初始化
    let a_binding;

    {
        let x = 2;
        //初始化
        a_binding = x * x;
    }

    println!("a binding: {}", a_binding);

    let another_binding;

    //未初始化
    //编译器禁止使用未初始化的变量，因为这将导致未定义的行为
    //println!("another binding: {}", another_binding);

    another_binding = 1;

    println!("another binding: {}", another_binding);
}
