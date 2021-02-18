//! An example of querying the starwars API using surf via the cynic
//! integration.
//!
//! This example requires the `surf` feature to be active.

mod query_dsl {
    cynic::query_dsl!("./schemas/starwars.schema.graphql");
}

#[derive(cynic::QueryFragment, Debug)]
#[cynic(
    schema_path = "./schemas/starwars.schema.graphql",
    query_module = "query_dsl",
    graphql_type = "Film"
)]
struct Film {
    title: Option<String>,
    director: Option<String>,
}

#[derive(cynic::FragmentArguments)]
struct FilmArguments {
    id: Option<cynic::Id>,
}

#[derive(cynic::QueryFragment, Debug)]
#[cynic(
    schema_path = "./schemas/starwars.schema.graphql",
    query_module = "query_dsl",
    graphql_type = "Root",
    argument_struct = "FilmArguments"
)]
struct FilmDirectorQuery {
    #[arguments(id = & args.id)]
    film: Option<Film>,
}

pub fn cynic_cynic_starwars_schema() {
    use crate::test_cynic::format_code;
    use cynic_codegen::query_dsl;
    use std::fs::File;
    use std::io::Write;
    use std::path::PathBuf;

    let schema_path = PathBuf::from("./schemas/").join("starwars.schema.graphql");

    let tokens = query_dsl::query_dsl_from_schema(query_dsl::QueryDslParams {
        schema_filename: schema_path.to_str().unwrap().to_string(),
    })
    .unwrap();

    let codes = format_code(format!("{}", tokens));
    let path = "./files/starwars.rs";
    let mut f = File::create(path).expect("unable to create file");
    write!(f, "{}", codes).expect("unable to write");
}

pub fn cynic_starwars1() {
    async fn run_query() -> cynic::GraphQLResponse<FilmDirectorQuery> {
        use cynic::http::SurfExt;

        let operation = build_query();

        surf::post("http://swapi-graphql.netlify.app/.netlify/functions/index")
            .run_graphql(operation)
            .await
            .unwrap()
    }

    fn build_query() -> cynic::Operation<'static, FilmDirectorQuery> {
        use cynic::QueryBuilder;

        FilmDirectorQuery::build(&FilmArguments {
            id: Some("ZmlsbXM6MQ==".into()),
        })
    }
    async_std::task::block_on(async {
        let result = run_query().await;
        println!("{:?}", result.data);
    })
}

pub fn cynic_starwars2() {
    fn run_query() -> cynic::GraphQLResponse<FilmDirectorQuery> {
        use cynic::http::ReqwestBlockingExt;

        let query = build_query();

        reqwest::blocking::Client::new()
            .post("https://swapi-graphql.netlify.app/.netlify/functions/index")
            .run_graphql(query)
            .unwrap()
    }

    fn build_query() -> cynic::Operation<'static, FilmDirectorQuery> {
        use cynic::QueryBuilder;

        FilmDirectorQuery::build(&FilmArguments {
            id: Some("ZmlsbXM6MQ==".into()),
        })
    }

    let result = run_query();
    println!("{:?}", result);
}
