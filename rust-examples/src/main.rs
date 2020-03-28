use std::fmt;
use std::fmt::{Display, Error, Formatter};

use crate::closures::*;
use crate::conversion::*;
use crate::custom_types::*;
use crate::flow_control::*;
use crate::formatted_print::*;
use crate::functions::*;
use crate::generics::*;
use crate::modules::*;
use crate::primitives::*;
use crate::types::*;
use crate::variable_bindings::*;

pub mod formatted_print;
pub mod generics;
pub mod modules;
pub mod functions;
pub mod flow_control;
pub mod closures;
pub mod conversion;
pub mod types;
pub mod primitives;
pub mod custom_types;
pub mod variable_bindings;

///为了方便将每个知识点分为单独的源文件，并提供与之相同的公开方法测试内部所有代码
fn main() {
    formatted_print();
    primitives();
    custom_types();
    types();
    conversion();
    flow_control();
    functions();
    closures();
    modules();
    generics();
}
