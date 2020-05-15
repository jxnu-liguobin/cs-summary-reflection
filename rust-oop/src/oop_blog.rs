use crate::oop_blog::blog_lib::Post;

pub fn blog() {
    let mut post = Post::new();
    post.add_text("I ate a salad for lunch today");
    assert_eq!("", post.content());
    post.request_review();
    assert_eq!("", post.content());
    post.approve();
    assert_eq!("I ate a salad for lunch today", post.content());
}

///使用状态模式（状态耦合）
//通过完全按照面向对象语言所定义的方式来实现状态模式，我们无法充分利用Rust的优势
pub mod blog_lib {
    //1.定义结构体描述博客
    pub struct Post {
        //使用特质对象类型定义state
        state: Option<Box<dyn State>>,
        //内容
        content: String,
    }

    //2.实现博客
    impl Post {
        //定义博客的统一构造方法
        pub fn new() -> Post {
            Post {
                state: Some(Box::new(Draft {})),
                content: String::new(),
            }
        }
        //存储内容的方法
        pub fn add_text(&mut self, text: &str) {
            self.content.push_str(text);
        }

        //委托给State的content方法
        pub fn content(&self) -> &str {
            self.state.as_ref().unwrap().content(self)
        }

        //请求查看内容
        pub fn request_review(&mut self) {
            if let Some(s) = self.state.take() {
                self.state = Some(s.request_review())
            }
        }

        pub fn approve(&mut self) {
            if let Some(s) = self.state.take() {
                self.state = Some(s.approve())
            }
        }
    }

    //3.博客状态
    trait State {
        //该方法仅在持有该类型的Box上调用时才有效
        fn request_review(self: Box<Self>) -> Box<dyn State>;

        //审批博客文章
        fn approve(self: Box<Self>) -> Box<dyn State>;

        fn content<'a>(&self, post: &'a Post) -> &'a str {
            ""
        }
    }

    struct Draft {}

    //4.实现State特质的状态 Draft（草稿状态）
    impl State for Draft {
        fn request_review(self: Box<Self>) -> Box<dyn State> {
            Box::new(PendingReview {})
        }
        fn approve(self: Box<Self>) -> Box<dyn State> {
            self
        }
    }

    //5.实现State特质的状态 PendingReview （审查状态）
    struct PendingReview {}

    impl State for PendingReview {
        fn request_review(self: Box<Self>) -> Box<dyn State> {
            self
        }

        fn approve(self: Box<Self>) -> Box<dyn State> {
            Box::new(Published {})
        }
    }

    //6.实现State特质的状态 Published （已发表状态）
    struct Published {}

    impl State for Published {
        fn request_review(self: Box<Self>) -> Box<dyn State> {
            self
        }

        fn approve(self: Box<Self>) -> Box<dyn State> {
            self
        }

        fn content<'a>(&self, post: &'a Post) -> &'a str {
            &post.content
        }
    }
}
