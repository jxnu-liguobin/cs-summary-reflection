use std::{io::Write, process::Stdio};

use cynic_codegen::query_dsl;
use insta::assert_snapshot;
use rstest::rstest;
use std::path::PathBuf;

#[rstest]
fn snapshot_query_dsl() {
    let schema_path = PathBuf::from("./schemas/").join("book.graphql");

    let tokens = query_dsl::query_dsl_from_schema(query_dsl::QueryDslParams {
        schema_filename: schema_path.to_str().unwrap().to_string(),
    })
    .unwrap();

    assert_snapshot!(format_code(format!("{}", tokens)));
}

fn format_code(input: String) -> String {
    let mut cmd = std::process::Command::new("rustfmt")
        .stdin(Stdio::piped())
        .stdout(Stdio::piped())
        .stderr(Stdio::inherit())
        .spawn()
        .expect("failed to execute rustfmt");

    write!(cmd.stdin.as_mut().unwrap(), "{}", input).unwrap();

    std::str::from_utf8(&cmd.wait_with_output().unwrap().stdout)
        .unwrap()
        .to_owned()
}
