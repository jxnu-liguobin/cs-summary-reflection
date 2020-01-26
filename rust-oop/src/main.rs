use crate::lib::gui::{Button, Screen, SelectBox};
use crate::oop_blog::blog;
use crate::rust_blog::blog_rust;

pub mod lib;
pub mod oop_blog;
pub mod rust_blog;

fn main() {
    let screen = Screen {
        components: vec![
            Box::new(SelectBox {
                width: 75,
                height: 10,
                options: vec![
                    String::from("Yes"),
                    String::from("Maybe"),
                    String::from("No")
                ],
            }),
            Box::new(Button {
                width: 50,
                height: 10,
                label: String::from("OK"),
            }),
        ],
    };

    screen.run();


    blog();
    blog_rust();
}