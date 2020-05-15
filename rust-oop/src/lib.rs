/// RUST OOP
pub mod gui {
    pub struct AveragedCollection {
        list: Vec<i32>,
        average: f64,
    }

    impl AveragedCollection {
        pub fn add(&mut self, value: i32) {
            self.list.push(value);
            self.update_average();
        }

        pub fn remove(&mut self) -> Option<i32> {
            let result = self.list.pop();
            match result {
                Some(value) => {
                    self.update_average();
                    Some(value)
                }
                None => None,
            }
        }

        pub fn average(&self) -> f64 {
            self.average
        }

        fn update_average(&mut self) {
            let total: i32 = self.list.iter().sum();
            self.average = total as f64 / self.list.len() as f64;
        }
    }

    //Button，Image，和SelectBox，将从继承Component并且因此继承draw方法。
    //他们每个人都可以重写该draw方法来定义其自定义行为
    //rust没有继承，需要使用其他方法实现这一功能
    pub trait Draw {
        fn draw(&self);
    }

    //使用Screen<T: Draw>将限制为一个Screen实例，该实例具有一个全部为Button类型或全部TextField类型的组件的列表。
    //如果您只拥有同类集合，则最好使用泛型和特质范围，因为定义会在编译时被单一化以使用具体类型。
    pub struct Screen {
        pub components: Vec<Box<dyn Draw>>, //Box<dyn Draw>   实现了Draw特质的任何类型组件
    }

    impl Screen {
        pub fn run(&self) {
            for component in self.components.iter() {
                component.draw();
            }
        }
    }

    ///实现特质
    pub struct Button {
        pub width: u32,
        pub height: u32,
        pub label: String,
    }

    impl Draw for Button {
        fn draw(&self) {
            //自定义实现
        }
    }

    pub struct SelectBox {
        pub width: u32,
        pub height: u32,
        pub options: Vec<String>,
    }

    impl Draw for SelectBox {
        fn draw(&self) {
            //自定义实现
        }
    }
}

//安全的特质对象
//返回类型不是Self。
//没有通用类型参数。
