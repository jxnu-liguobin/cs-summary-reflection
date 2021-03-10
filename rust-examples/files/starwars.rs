#[allow(dead_code)]
pub struct Node {}
#[allow(dead_code)]
pub struct Film;
#[allow(dead_code)]
impl Film {
    pub fn title() -> film::TitleSelectionBuilder {
        film::TitleSelectionBuilder::new(vec![])
    }
    pub fn episode_id() -> film::EpisodeIDSelectionBuilder {
        film::EpisodeIDSelectionBuilder::new(vec![])
    }
    pub fn opening_crawl() -> film::OpeningCrawlSelectionBuilder {
        film::OpeningCrawlSelectionBuilder::new(vec![])
    }
    pub fn director() -> film::DirectorSelectionBuilder {
        film::DirectorSelectionBuilder::new(vec![])
    }
    pub fn producers() -> film::ProducersSelectionBuilder {
        film::ProducersSelectionBuilder::new(vec![])
    }
    pub fn release_date() -> film::ReleaseDateSelectionBuilder {
        film::ReleaseDateSelectionBuilder::new(vec![])
    }
    pub fn species_connection() -> film::SpeciesConnectionSelectionBuilder {
        film::SpeciesConnectionSelectionBuilder::new(vec![])
    }
    pub fn starship_connection() -> film::StarshipConnectionSelectionBuilder {
        film::StarshipConnectionSelectionBuilder::new(vec![])
    }
    pub fn vehicle_connection() -> film::VehicleConnectionSelectionBuilder {
        film::VehicleConnectionSelectionBuilder::new(vec![])
    }
    pub fn character_connection() -> film::CharacterConnectionSelectionBuilder {
        film::CharacterConnectionSelectionBuilder::new(vec![])
    }
    pub fn planet_connection() -> film::PlanetConnectionSelectionBuilder {
        film::PlanetConnectionSelectionBuilder::new(vec![])
    }
    pub fn created() -> film::CreatedSelectionBuilder {
        film::CreatedSelectionBuilder::new(vec![])
    }
    pub fn edited() -> film::EditedSelectionBuilder {
        film::EditedSelectionBuilder::new(vec![])
    }
    pub fn id() -> film::IdSelectionBuilder {
        film::IdSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmCharactersConnection;
#[allow(dead_code)]
impl FilmCharactersConnection {
    pub fn page_info() -> film_characters_connection::PageInfoSelectionBuilder {
        film_characters_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> film_characters_connection::EdgesSelectionBuilder {
        film_characters_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> film_characters_connection::TotalCountSelectionBuilder {
        film_characters_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn characters() -> film_characters_connection::CharactersSelectionBuilder {
        film_characters_connection::CharactersSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmCharactersEdge;
#[allow(dead_code)]
impl FilmCharactersEdge {
    pub fn node() -> film_characters_edge::NodeSelectionBuilder {
        film_characters_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> film_characters_edge::CursorSelectionBuilder {
        film_characters_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmPlanetsConnection;
#[allow(dead_code)]
impl FilmPlanetsConnection {
    pub fn page_info() -> film_planets_connection::PageInfoSelectionBuilder {
        film_planets_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> film_planets_connection::EdgesSelectionBuilder {
        film_planets_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> film_planets_connection::TotalCountSelectionBuilder {
        film_planets_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn planets() -> film_planets_connection::PlanetsSelectionBuilder {
        film_planets_connection::PlanetsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmPlanetsEdge;
#[allow(dead_code)]
impl FilmPlanetsEdge {
    pub fn node() -> film_planets_edge::NodeSelectionBuilder {
        film_planets_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> film_planets_edge::CursorSelectionBuilder {
        film_planets_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmsConnection;
#[allow(dead_code)]
impl FilmsConnection {
    pub fn page_info() -> films_connection::PageInfoSelectionBuilder {
        films_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> films_connection::EdgesSelectionBuilder {
        films_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> films_connection::TotalCountSelectionBuilder {
        films_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn films() -> films_connection::FilmsSelectionBuilder {
        films_connection::FilmsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmsEdge;
#[allow(dead_code)]
impl FilmsEdge {
    pub fn node() -> films_edge::NodeSelectionBuilder {
        films_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> films_edge::CursorSelectionBuilder {
        films_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmSpeciesConnection;
#[allow(dead_code)]
impl FilmSpeciesConnection {
    pub fn page_info() -> film_species_connection::PageInfoSelectionBuilder {
        film_species_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> film_species_connection::EdgesSelectionBuilder {
        film_species_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> film_species_connection::TotalCountSelectionBuilder {
        film_species_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn species() -> film_species_connection::SpeciesSelectionBuilder {
        film_species_connection::SpeciesSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmSpeciesEdge;
#[allow(dead_code)]
impl FilmSpeciesEdge {
    pub fn node() -> film_species_edge::NodeSelectionBuilder {
        film_species_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> film_species_edge::CursorSelectionBuilder {
        film_species_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmStarshipsConnection;
#[allow(dead_code)]
impl FilmStarshipsConnection {
    pub fn page_info() -> film_starships_connection::PageInfoSelectionBuilder {
        film_starships_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> film_starships_connection::EdgesSelectionBuilder {
        film_starships_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> film_starships_connection::TotalCountSelectionBuilder {
        film_starships_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn starships() -> film_starships_connection::StarshipsSelectionBuilder {
        film_starships_connection::StarshipsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmStarshipsEdge;
#[allow(dead_code)]
impl FilmStarshipsEdge {
    pub fn node() -> film_starships_edge::NodeSelectionBuilder {
        film_starships_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> film_starships_edge::CursorSelectionBuilder {
        film_starships_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmVehiclesConnection;
#[allow(dead_code)]
impl FilmVehiclesConnection {
    pub fn page_info() -> film_vehicles_connection::PageInfoSelectionBuilder {
        film_vehicles_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> film_vehicles_connection::EdgesSelectionBuilder {
        film_vehicles_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> film_vehicles_connection::TotalCountSelectionBuilder {
        film_vehicles_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn vehicles() -> film_vehicles_connection::VehiclesSelectionBuilder {
        film_vehicles_connection::VehiclesSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct FilmVehiclesEdge;
#[allow(dead_code)]
impl FilmVehiclesEdge {
    pub fn node() -> film_vehicles_edge::NodeSelectionBuilder {
        film_vehicles_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> film_vehicles_edge::CursorSelectionBuilder {
        film_vehicles_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PageInfo;
#[allow(dead_code)]
impl PageInfo {
    pub fn has_next_page() -> page_info::HasNextPageSelectionBuilder {
        page_info::HasNextPageSelectionBuilder::new(vec![])
    }
    pub fn has_previous_page() -> page_info::HasPreviousPageSelectionBuilder {
        page_info::HasPreviousPageSelectionBuilder::new(vec![])
    }
    pub fn start_cursor() -> page_info::StartCursorSelectionBuilder {
        page_info::StartCursorSelectionBuilder::new(vec![])
    }
    pub fn end_cursor() -> page_info::EndCursorSelectionBuilder {
        page_info::EndCursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PeopleConnection;
#[allow(dead_code)]
impl PeopleConnection {
    pub fn page_info() -> people_connection::PageInfoSelectionBuilder {
        people_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> people_connection::EdgesSelectionBuilder {
        people_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> people_connection::TotalCountSelectionBuilder {
        people_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn people() -> people_connection::PeopleSelectionBuilder {
        people_connection::PeopleSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PeopleEdge;
#[allow(dead_code)]
impl PeopleEdge {
    pub fn node() -> people_edge::NodeSelectionBuilder {
        people_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> people_edge::CursorSelectionBuilder {
        people_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct Person;
#[allow(dead_code)]
impl Person {
    pub fn name() -> person::NameSelectionBuilder {
        person::NameSelectionBuilder::new(vec![])
    }
    pub fn birth_year() -> person::BirthYearSelectionBuilder {
        person::BirthYearSelectionBuilder::new(vec![])
    }
    pub fn eye_color() -> person::EyeColorSelectionBuilder {
        person::EyeColorSelectionBuilder::new(vec![])
    }
    pub fn gender() -> person::GenderSelectionBuilder {
        person::GenderSelectionBuilder::new(vec![])
    }
    pub fn hair_color() -> person::HairColorSelectionBuilder {
        person::HairColorSelectionBuilder::new(vec![])
    }
    pub fn height() -> person::HeightSelectionBuilder {
        person::HeightSelectionBuilder::new(vec![])
    }
    pub fn mass() -> person::MassSelectionBuilder {
        person::MassSelectionBuilder::new(vec![])
    }
    pub fn skin_color() -> person::SkinColorSelectionBuilder {
        person::SkinColorSelectionBuilder::new(vec![])
    }
    pub fn homeworld() -> person::HomeworldSelectionBuilder {
        person::HomeworldSelectionBuilder::new(vec![])
    }
    pub fn film_connection() -> person::FilmConnectionSelectionBuilder {
        person::FilmConnectionSelectionBuilder::new(vec![])
    }
    pub fn species() -> person::SpeciesSelectionBuilder {
        person::SpeciesSelectionBuilder::new(vec![])
    }
    pub fn starship_connection() -> person::StarshipConnectionSelectionBuilder {
        person::StarshipConnectionSelectionBuilder::new(vec![])
    }
    pub fn vehicle_connection() -> person::VehicleConnectionSelectionBuilder {
        person::VehicleConnectionSelectionBuilder::new(vec![])
    }
    pub fn created() -> person::CreatedSelectionBuilder {
        person::CreatedSelectionBuilder::new(vec![])
    }
    pub fn edited() -> person::EditedSelectionBuilder {
        person::EditedSelectionBuilder::new(vec![])
    }
    pub fn id() -> person::IdSelectionBuilder {
        person::IdSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PersonFilmsConnection;
#[allow(dead_code)]
impl PersonFilmsConnection {
    pub fn page_info() -> person_films_connection::PageInfoSelectionBuilder {
        person_films_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> person_films_connection::EdgesSelectionBuilder {
        person_films_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> person_films_connection::TotalCountSelectionBuilder {
        person_films_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn films() -> person_films_connection::FilmsSelectionBuilder {
        person_films_connection::FilmsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PersonFilmsEdge;
#[allow(dead_code)]
impl PersonFilmsEdge {
    pub fn node() -> person_films_edge::NodeSelectionBuilder {
        person_films_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> person_films_edge::CursorSelectionBuilder {
        person_films_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PersonStarshipsConnection;
#[allow(dead_code)]
impl PersonStarshipsConnection {
    pub fn page_info() -> person_starships_connection::PageInfoSelectionBuilder {
        person_starships_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> person_starships_connection::EdgesSelectionBuilder {
        person_starships_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> person_starships_connection::TotalCountSelectionBuilder {
        person_starships_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn starships() -> person_starships_connection::StarshipsSelectionBuilder {
        person_starships_connection::StarshipsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PersonStarshipsEdge;
#[allow(dead_code)]
impl PersonStarshipsEdge {
    pub fn node() -> person_starships_edge::NodeSelectionBuilder {
        person_starships_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> person_starships_edge::CursorSelectionBuilder {
        person_starships_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PersonVehiclesConnection;
#[allow(dead_code)]
impl PersonVehiclesConnection {
    pub fn page_info() -> person_vehicles_connection::PageInfoSelectionBuilder {
        person_vehicles_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> person_vehicles_connection::EdgesSelectionBuilder {
        person_vehicles_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> person_vehicles_connection::TotalCountSelectionBuilder {
        person_vehicles_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn vehicles() -> person_vehicles_connection::VehiclesSelectionBuilder {
        person_vehicles_connection::VehiclesSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PersonVehiclesEdge;
#[allow(dead_code)]
impl PersonVehiclesEdge {
    pub fn node() -> person_vehicles_edge::NodeSelectionBuilder {
        person_vehicles_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> person_vehicles_edge::CursorSelectionBuilder {
        person_vehicles_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct Planet;
#[allow(dead_code)]
impl Planet {
    pub fn name() -> planet::NameSelectionBuilder {
        planet::NameSelectionBuilder::new(vec![])
    }
    pub fn diameter() -> planet::DiameterSelectionBuilder {
        planet::DiameterSelectionBuilder::new(vec![])
    }
    pub fn rotation_period() -> planet::RotationPeriodSelectionBuilder {
        planet::RotationPeriodSelectionBuilder::new(vec![])
    }
    pub fn orbital_period() -> planet::OrbitalPeriodSelectionBuilder {
        planet::OrbitalPeriodSelectionBuilder::new(vec![])
    }
    pub fn gravity() -> planet::GravitySelectionBuilder {
        planet::GravitySelectionBuilder::new(vec![])
    }
    pub fn population() -> planet::PopulationSelectionBuilder {
        planet::PopulationSelectionBuilder::new(vec![])
    }
    pub fn climates() -> planet::ClimatesSelectionBuilder {
        planet::ClimatesSelectionBuilder::new(vec![])
    }
    pub fn terrains() -> planet::TerrainsSelectionBuilder {
        planet::TerrainsSelectionBuilder::new(vec![])
    }
    pub fn surface_water() -> planet::SurfaceWaterSelectionBuilder {
        planet::SurfaceWaterSelectionBuilder::new(vec![])
    }
    pub fn resident_connection() -> planet::ResidentConnectionSelectionBuilder {
        planet::ResidentConnectionSelectionBuilder::new(vec![])
    }
    pub fn film_connection() -> planet::FilmConnectionSelectionBuilder {
        planet::FilmConnectionSelectionBuilder::new(vec![])
    }
    pub fn created() -> planet::CreatedSelectionBuilder {
        planet::CreatedSelectionBuilder::new(vec![])
    }
    pub fn edited() -> planet::EditedSelectionBuilder {
        planet::EditedSelectionBuilder::new(vec![])
    }
    pub fn id() -> planet::IdSelectionBuilder {
        planet::IdSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PlanetFilmsConnection;
#[allow(dead_code)]
impl PlanetFilmsConnection {
    pub fn page_info() -> planet_films_connection::PageInfoSelectionBuilder {
        planet_films_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> planet_films_connection::EdgesSelectionBuilder {
        planet_films_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> planet_films_connection::TotalCountSelectionBuilder {
        planet_films_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn films() -> planet_films_connection::FilmsSelectionBuilder {
        planet_films_connection::FilmsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PlanetFilmsEdge;
#[allow(dead_code)]
impl PlanetFilmsEdge {
    pub fn node() -> planet_films_edge::NodeSelectionBuilder {
        planet_films_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> planet_films_edge::CursorSelectionBuilder {
        planet_films_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PlanetResidentsConnection;
#[allow(dead_code)]
impl PlanetResidentsConnection {
    pub fn page_info() -> planet_residents_connection::PageInfoSelectionBuilder {
        planet_residents_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> planet_residents_connection::EdgesSelectionBuilder {
        planet_residents_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> planet_residents_connection::TotalCountSelectionBuilder {
        planet_residents_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn residents() -> planet_residents_connection::ResidentsSelectionBuilder {
        planet_residents_connection::ResidentsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PlanetResidentsEdge;
#[allow(dead_code)]
impl PlanetResidentsEdge {
    pub fn node() -> planet_residents_edge::NodeSelectionBuilder {
        planet_residents_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> planet_residents_edge::CursorSelectionBuilder {
        planet_residents_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PlanetsConnection;
#[allow(dead_code)]
impl PlanetsConnection {
    pub fn page_info() -> planets_connection::PageInfoSelectionBuilder {
        planets_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> planets_connection::EdgesSelectionBuilder {
        planets_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> planets_connection::TotalCountSelectionBuilder {
        planets_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn planets() -> planets_connection::PlanetsSelectionBuilder {
        planets_connection::PlanetsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct PlanetsEdge;
#[allow(dead_code)]
impl PlanetsEdge {
    pub fn node() -> planets_edge::NodeSelectionBuilder {
        planets_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> planets_edge::CursorSelectionBuilder {
        planets_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct Root;
#[allow(dead_code)]
impl Root {
    pub fn all_films() -> root::AllFilmsSelectionBuilder {
        root::AllFilmsSelectionBuilder::new(vec![])
    }
    pub fn film() -> root::FilmSelectionBuilder {
        root::FilmSelectionBuilder::new(vec![])
    }
    pub fn all_people() -> root::AllPeopleSelectionBuilder {
        root::AllPeopleSelectionBuilder::new(vec![])
    }
    pub fn person() -> root::PersonSelectionBuilder {
        root::PersonSelectionBuilder::new(vec![])
    }
    pub fn all_planets() -> root::AllPlanetsSelectionBuilder {
        root::AllPlanetsSelectionBuilder::new(vec![])
    }
    pub fn planet() -> root::PlanetSelectionBuilder {
        root::PlanetSelectionBuilder::new(vec![])
    }
    pub fn all_species() -> root::AllSpeciesSelectionBuilder {
        root::AllSpeciesSelectionBuilder::new(vec![])
    }
    pub fn species() -> root::SpeciesSelectionBuilder {
        root::SpeciesSelectionBuilder::new(vec![])
    }
    pub fn all_starships() -> root::AllStarshipsSelectionBuilder {
        root::AllStarshipsSelectionBuilder::new(vec![])
    }
    pub fn starship() -> root::StarshipSelectionBuilder {
        root::StarshipSelectionBuilder::new(vec![])
    }
    pub fn all_vehicles() -> root::AllVehiclesSelectionBuilder {
        root::AllVehiclesSelectionBuilder::new(vec![])
    }
    pub fn vehicle() -> root::VehicleSelectionBuilder {
        root::VehicleSelectionBuilder::new(vec![])
    }
    pub fn node(id: ::cynic::Id) -> root::NodeSelectionBuilder {
        root::NodeSelectionBuilder::new(vec![::cynic::Argument::new("id", "ID!", id)])
    }
}
#[allow(dead_code)]
pub struct Species;
#[allow(dead_code)]
impl Species {
    pub fn name() -> species::NameSelectionBuilder {
        species::NameSelectionBuilder::new(vec![])
    }
    pub fn classification() -> species::ClassificationSelectionBuilder {
        species::ClassificationSelectionBuilder::new(vec![])
    }
    pub fn designation() -> species::DesignationSelectionBuilder {
        species::DesignationSelectionBuilder::new(vec![])
    }
    pub fn average_height() -> species::AverageHeightSelectionBuilder {
        species::AverageHeightSelectionBuilder::new(vec![])
    }
    pub fn average_lifespan() -> species::AverageLifespanSelectionBuilder {
        species::AverageLifespanSelectionBuilder::new(vec![])
    }
    pub fn eye_colors() -> species::EyeColorsSelectionBuilder {
        species::EyeColorsSelectionBuilder::new(vec![])
    }
    pub fn hair_colors() -> species::HairColorsSelectionBuilder {
        species::HairColorsSelectionBuilder::new(vec![])
    }
    pub fn skin_colors() -> species::SkinColorsSelectionBuilder {
        species::SkinColorsSelectionBuilder::new(vec![])
    }
    pub fn language() -> species::LanguageSelectionBuilder {
        species::LanguageSelectionBuilder::new(vec![])
    }
    pub fn homeworld() -> species::HomeworldSelectionBuilder {
        species::HomeworldSelectionBuilder::new(vec![])
    }
    pub fn person_connection() -> species::PersonConnectionSelectionBuilder {
        species::PersonConnectionSelectionBuilder::new(vec![])
    }
    pub fn film_connection() -> species::FilmConnectionSelectionBuilder {
        species::FilmConnectionSelectionBuilder::new(vec![])
    }
    pub fn created() -> species::CreatedSelectionBuilder {
        species::CreatedSelectionBuilder::new(vec![])
    }
    pub fn edited() -> species::EditedSelectionBuilder {
        species::EditedSelectionBuilder::new(vec![])
    }
    pub fn id() -> species::IdSelectionBuilder {
        species::IdSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct SpeciesConnection;
#[allow(dead_code)]
impl SpeciesConnection {
    pub fn page_info() -> species_connection::PageInfoSelectionBuilder {
        species_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> species_connection::EdgesSelectionBuilder {
        species_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> species_connection::TotalCountSelectionBuilder {
        species_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn species() -> species_connection::SpeciesSelectionBuilder {
        species_connection::SpeciesSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct SpeciesEdge;
#[allow(dead_code)]
impl SpeciesEdge {
    pub fn node() -> species_edge::NodeSelectionBuilder {
        species_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> species_edge::CursorSelectionBuilder {
        species_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct SpeciesFilmsConnection;
#[allow(dead_code)]
impl SpeciesFilmsConnection {
    pub fn page_info() -> species_films_connection::PageInfoSelectionBuilder {
        species_films_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> species_films_connection::EdgesSelectionBuilder {
        species_films_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> species_films_connection::TotalCountSelectionBuilder {
        species_films_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn films() -> species_films_connection::FilmsSelectionBuilder {
        species_films_connection::FilmsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct SpeciesFilmsEdge;
#[allow(dead_code)]
impl SpeciesFilmsEdge {
    pub fn node() -> species_films_edge::NodeSelectionBuilder {
        species_films_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> species_films_edge::CursorSelectionBuilder {
        species_films_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct SpeciesPeopleConnection;
#[allow(dead_code)]
impl SpeciesPeopleConnection {
    pub fn page_info() -> species_people_connection::PageInfoSelectionBuilder {
        species_people_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> species_people_connection::EdgesSelectionBuilder {
        species_people_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> species_people_connection::TotalCountSelectionBuilder {
        species_people_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn people() -> species_people_connection::PeopleSelectionBuilder {
        species_people_connection::PeopleSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct SpeciesPeopleEdge;
#[allow(dead_code)]
impl SpeciesPeopleEdge {
    pub fn node() -> species_people_edge::NodeSelectionBuilder {
        species_people_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> species_people_edge::CursorSelectionBuilder {
        species_people_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct Starship;
#[allow(dead_code)]
impl Starship {
    pub fn name() -> starship::NameSelectionBuilder {
        starship::NameSelectionBuilder::new(vec![])
    }
    pub fn model() -> starship::ModelSelectionBuilder {
        starship::ModelSelectionBuilder::new(vec![])
    }
    pub fn starship_class() -> starship::StarshipClassSelectionBuilder {
        starship::StarshipClassSelectionBuilder::new(vec![])
    }
    pub fn manufacturers() -> starship::ManufacturersSelectionBuilder {
        starship::ManufacturersSelectionBuilder::new(vec![])
    }
    pub fn cost_in_credits() -> starship::CostInCreditsSelectionBuilder {
        starship::CostInCreditsSelectionBuilder::new(vec![])
    }
    pub fn length() -> starship::LengthSelectionBuilder {
        starship::LengthSelectionBuilder::new(vec![])
    }
    pub fn crew() -> starship::CrewSelectionBuilder {
        starship::CrewSelectionBuilder::new(vec![])
    }
    pub fn passengers() -> starship::PassengersSelectionBuilder {
        starship::PassengersSelectionBuilder::new(vec![])
    }
    pub fn max_atmosphering_speed() -> starship::MaxAtmospheringSpeedSelectionBuilder {
        starship::MaxAtmospheringSpeedSelectionBuilder::new(vec![])
    }
    pub fn hyperdrive_rating() -> starship::HyperdriveRatingSelectionBuilder {
        starship::HyperdriveRatingSelectionBuilder::new(vec![])
    }
    pub fn mglt() -> starship::MgltselectionBuilder {
        starship::MgltselectionBuilder::new(vec![])
    }
    pub fn cargo_capacity() -> starship::CargoCapacitySelectionBuilder {
        starship::CargoCapacitySelectionBuilder::new(vec![])
    }
    pub fn consumables() -> starship::ConsumablesSelectionBuilder {
        starship::ConsumablesSelectionBuilder::new(vec![])
    }
    pub fn pilot_connection() -> starship::PilotConnectionSelectionBuilder {
        starship::PilotConnectionSelectionBuilder::new(vec![])
    }
    pub fn film_connection() -> starship::FilmConnectionSelectionBuilder {
        starship::FilmConnectionSelectionBuilder::new(vec![])
    }
    pub fn created() -> starship::CreatedSelectionBuilder {
        starship::CreatedSelectionBuilder::new(vec![])
    }
    pub fn edited() -> starship::EditedSelectionBuilder {
        starship::EditedSelectionBuilder::new(vec![])
    }
    pub fn id() -> starship::IdSelectionBuilder {
        starship::IdSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct StarshipFilmsConnection;
#[allow(dead_code)]
impl StarshipFilmsConnection {
    pub fn page_info() -> starship_films_connection::PageInfoSelectionBuilder {
        starship_films_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> starship_films_connection::EdgesSelectionBuilder {
        starship_films_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> starship_films_connection::TotalCountSelectionBuilder {
        starship_films_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn films() -> starship_films_connection::FilmsSelectionBuilder {
        starship_films_connection::FilmsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct StarshipFilmsEdge;
#[allow(dead_code)]
impl StarshipFilmsEdge {
    pub fn node() -> starship_films_edge::NodeSelectionBuilder {
        starship_films_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> starship_films_edge::CursorSelectionBuilder {
        starship_films_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct StarshipPilotsConnection;
#[allow(dead_code)]
impl StarshipPilotsConnection {
    pub fn page_info() -> starship_pilots_connection::PageInfoSelectionBuilder {
        starship_pilots_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> starship_pilots_connection::EdgesSelectionBuilder {
        starship_pilots_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> starship_pilots_connection::TotalCountSelectionBuilder {
        starship_pilots_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn pilots() -> starship_pilots_connection::PilotsSelectionBuilder {
        starship_pilots_connection::PilotsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct StarshipPilotsEdge;
#[allow(dead_code)]
impl StarshipPilotsEdge {
    pub fn node() -> starship_pilots_edge::NodeSelectionBuilder {
        starship_pilots_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> starship_pilots_edge::CursorSelectionBuilder {
        starship_pilots_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct StarshipsConnection;
#[allow(dead_code)]
impl StarshipsConnection {
    pub fn page_info() -> starships_connection::PageInfoSelectionBuilder {
        starships_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> starships_connection::EdgesSelectionBuilder {
        starships_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> starships_connection::TotalCountSelectionBuilder {
        starships_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn starships() -> starships_connection::StarshipsSelectionBuilder {
        starships_connection::StarshipsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct StarshipsEdge;
#[allow(dead_code)]
impl StarshipsEdge {
    pub fn node() -> starships_edge::NodeSelectionBuilder {
        starships_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> starships_edge::CursorSelectionBuilder {
        starships_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct Vehicle;
#[allow(dead_code)]
impl Vehicle {
    pub fn name() -> vehicle::NameSelectionBuilder {
        vehicle::NameSelectionBuilder::new(vec![])
    }
    pub fn model() -> vehicle::ModelSelectionBuilder {
        vehicle::ModelSelectionBuilder::new(vec![])
    }
    pub fn vehicle_class() -> vehicle::VehicleClassSelectionBuilder {
        vehicle::VehicleClassSelectionBuilder::new(vec![])
    }
    pub fn manufacturers() -> vehicle::ManufacturersSelectionBuilder {
        vehicle::ManufacturersSelectionBuilder::new(vec![])
    }
    pub fn cost_in_credits() -> vehicle::CostInCreditsSelectionBuilder {
        vehicle::CostInCreditsSelectionBuilder::new(vec![])
    }
    pub fn length() -> vehicle::LengthSelectionBuilder {
        vehicle::LengthSelectionBuilder::new(vec![])
    }
    pub fn crew() -> vehicle::CrewSelectionBuilder {
        vehicle::CrewSelectionBuilder::new(vec![])
    }
    pub fn passengers() -> vehicle::PassengersSelectionBuilder {
        vehicle::PassengersSelectionBuilder::new(vec![])
    }
    pub fn max_atmosphering_speed() -> vehicle::MaxAtmospheringSpeedSelectionBuilder {
        vehicle::MaxAtmospheringSpeedSelectionBuilder::new(vec![])
    }
    pub fn cargo_capacity() -> vehicle::CargoCapacitySelectionBuilder {
        vehicle::CargoCapacitySelectionBuilder::new(vec![])
    }
    pub fn consumables() -> vehicle::ConsumablesSelectionBuilder {
        vehicle::ConsumablesSelectionBuilder::new(vec![])
    }
    pub fn pilot_connection() -> vehicle::PilotConnectionSelectionBuilder {
        vehicle::PilotConnectionSelectionBuilder::new(vec![])
    }
    pub fn film_connection() -> vehicle::FilmConnectionSelectionBuilder {
        vehicle::FilmConnectionSelectionBuilder::new(vec![])
    }
    pub fn created() -> vehicle::CreatedSelectionBuilder {
        vehicle::CreatedSelectionBuilder::new(vec![])
    }
    pub fn edited() -> vehicle::EditedSelectionBuilder {
        vehicle::EditedSelectionBuilder::new(vec![])
    }
    pub fn id() -> vehicle::IdSelectionBuilder {
        vehicle::IdSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct VehicleFilmsConnection;
#[allow(dead_code)]
impl VehicleFilmsConnection {
    pub fn page_info() -> vehicle_films_connection::PageInfoSelectionBuilder {
        vehicle_films_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> vehicle_films_connection::EdgesSelectionBuilder {
        vehicle_films_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> vehicle_films_connection::TotalCountSelectionBuilder {
        vehicle_films_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn films() -> vehicle_films_connection::FilmsSelectionBuilder {
        vehicle_films_connection::FilmsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct VehicleFilmsEdge;
#[allow(dead_code)]
impl VehicleFilmsEdge {
    pub fn node() -> vehicle_films_edge::NodeSelectionBuilder {
        vehicle_films_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> vehicle_films_edge::CursorSelectionBuilder {
        vehicle_films_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct VehiclePilotsConnection;
#[allow(dead_code)]
impl VehiclePilotsConnection {
    pub fn page_info() -> vehicle_pilots_connection::PageInfoSelectionBuilder {
        vehicle_pilots_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> vehicle_pilots_connection::EdgesSelectionBuilder {
        vehicle_pilots_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> vehicle_pilots_connection::TotalCountSelectionBuilder {
        vehicle_pilots_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn pilots() -> vehicle_pilots_connection::PilotsSelectionBuilder {
        vehicle_pilots_connection::PilotsSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct VehiclePilotsEdge;
#[allow(dead_code)]
impl VehiclePilotsEdge {
    pub fn node() -> vehicle_pilots_edge::NodeSelectionBuilder {
        vehicle_pilots_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> vehicle_pilots_edge::CursorSelectionBuilder {
        vehicle_pilots_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct VehiclesConnection;
#[allow(dead_code)]
impl VehiclesConnection {
    pub fn page_info() -> vehicles_connection::PageInfoSelectionBuilder {
        vehicles_connection::PageInfoSelectionBuilder::new(vec![])
    }
    pub fn edges() -> vehicles_connection::EdgesSelectionBuilder {
        vehicles_connection::EdgesSelectionBuilder::new(vec![])
    }
    pub fn total_count() -> vehicles_connection::TotalCountSelectionBuilder {
        vehicles_connection::TotalCountSelectionBuilder::new(vec![])
    }
    pub fn vehicles() -> vehicles_connection::VehiclesSelectionBuilder {
        vehicles_connection::VehiclesSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub struct VehiclesEdge;
#[allow(dead_code)]
impl VehiclesEdge {
    pub fn node() -> vehicles_edge::NodeSelectionBuilder {
        vehicles_edge::NodeSelectionBuilder::new(vec![])
    }
    pub fn cursor() -> vehicles_edge::CursorSelectionBuilder {
        vehicles_edge::CursorSelectionBuilder::new(vec![])
    }
}
#[allow(dead_code)]
pub mod film {
    pub struct TitleSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TitleSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TitleSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Film> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "title",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EpisodeIDSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EpisodeIDSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EpisodeIDSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::Film> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "episodeID",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct OpeningCrawlSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl OpeningCrawlSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            OpeningCrawlSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Film> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "openingCrawl",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct DirectorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl DirectorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            DirectorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Film> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "director",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ProducersSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ProducersSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ProducersSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<Vec<Option<String>>>, super::Film>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "producers",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(::cynic::selection_set::scalar()),
                )),
            )
        }
    }
    pub struct ReleaseDateSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ReleaseDateSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ReleaseDateSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Film> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "releaseDate",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct SpeciesConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl SpeciesConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            SpeciesConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmSpeciesConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Film> {
            ::cynic::selection_set::field(
                "speciesConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct StarshipConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl StarshipConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            StarshipConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmStarshipsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Film> {
            ::cynic::selection_set::field(
                "starshipConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct VehicleConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl VehicleConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            VehicleConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmVehiclesConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Film> {
            ::cynic::selection_set::field(
                "vehicleConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct CharacterConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CharacterConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CharacterConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmCharactersConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Film> {
            ::cynic::selection_set::field(
                "characterConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct PlanetConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PlanetConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PlanetConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmPlanetsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Film> {
            ::cynic::selection_set::field(
                "planetConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct CreatedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CreatedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CreatedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Film> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "created",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EditedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EditedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EditedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Film> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "edited",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct IdSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl IdSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            IdSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, ::cynic::Id, super::Film> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("id", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod film_characters_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::FilmCharactersConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmCharactersEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmCharactersConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<i32>,
            super::FilmCharactersConnection,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct CharactersSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CharactersSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CharactersSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmCharactersConnection,
        > {
            ::cynic::selection_set::field(
                "characters",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod film_characters_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::FilmCharactersEdge>
        {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::FilmCharactersEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod film_planets_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::FilmPlanetsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmPlanetsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmPlanetsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::FilmPlanetsConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PlanetsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PlanetsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PlanetsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Planet>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmPlanetsConnection,
        > {
            ::cynic::selection_set::field(
                "planets",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod film_planets_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Planet>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::FilmPlanetsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::FilmPlanetsEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod films_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::FilmsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<Vec<Option<T>>>, super::FilmsConnection>
        {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::FilmsConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct FilmsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<Vec<Option<T>>>, super::FilmsConnection>
        {
            ::cynic::selection_set::field(
                "films",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod films_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::FilmsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::FilmsEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod film_species_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::FilmSpeciesConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmSpeciesEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmSpeciesConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::FilmSpeciesConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct SpeciesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl SpeciesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            SpeciesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Species>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmSpeciesConnection,
        > {
            ::cynic::selection_set::field(
                "species",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod film_species_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Species>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::FilmSpeciesEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::FilmSpeciesEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod film_starships_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::FilmStarshipsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmStarshipsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmStarshipsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<i32>,
            super::FilmStarshipsConnection,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct StarshipsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl StarshipsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            StarshipsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Starship>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmStarshipsConnection,
        > {
            ::cynic::selection_set::field(
                "starships",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod film_starships_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Starship>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::FilmStarshipsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::FilmStarshipsEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod film_vehicles_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::FilmVehiclesConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmVehiclesEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmVehiclesConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::FilmVehiclesConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct VehiclesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl VehiclesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            VehiclesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Vehicle>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::FilmVehiclesConnection,
        > {
            ::cynic::selection_set::field(
                "vehicles",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod film_vehicles_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Vehicle>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::FilmVehiclesEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::FilmVehiclesEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod page_info {
    pub struct HasNextPageSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl HasNextPageSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            HasNextPageSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, bool, super::PageInfo> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "hasNextPage",
                self.args,
                ::cynic::selection_set::scalar(),
            )
        }
    }
    pub struct HasPreviousPageSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl HasPreviousPageSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            HasPreviousPageSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, bool, super::PageInfo> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "hasPreviousPage",
                self.args,
                ::cynic::selection_set::scalar(),
            )
        }
    }
    pub struct StartCursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl StartCursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            StartCursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::PageInfo>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "startCursor",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EndCursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EndCursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EndCursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::PageInfo>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "endCursor",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
}
#[allow(dead_code)]
pub mod people_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::PeopleConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PeopleEdge>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<Vec<Option<T>>>, super::PeopleConnection>
        {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::PeopleConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PeopleSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PeopleSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PeopleSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<Vec<Option<T>>>, super::PeopleConnection>
        {
            ::cynic::selection_set::field(
                "people",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod people_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::PeopleEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::PeopleEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod person {
    pub struct NameSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NameSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NameSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "name",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct BirthYearSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl BirthYearSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            BirthYearSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "birthYear",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EyeColorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EyeColorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EyeColorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "eyeColor",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct GenderSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl GenderSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            GenderSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "gender",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct HairColorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl HairColorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            HairColorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "hairColor",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct HeightSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl HeightSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            HeightSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "height",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct MassSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl MassSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            MassSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "mass",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct SkinColorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl SkinColorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            SkinColorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "skinColor",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct HomeworldSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl HomeworldSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            HomeworldSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Planet>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Person> {
            ::cynic::selection_set::field(
                "homeworld",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct FilmConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PersonFilmsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Person> {
            ::cynic::selection_set::field(
                "filmConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct SpeciesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl SpeciesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            SpeciesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Species>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Person> {
            ::cynic::selection_set::field(
                "species",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct StarshipConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl StarshipConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            StarshipConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PersonStarshipsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Person> {
            ::cynic::selection_set::field(
                "starshipConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct VehicleConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl VehicleConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            VehicleConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PersonVehiclesConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Person> {
            ::cynic::selection_set::field(
                "vehicleConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct CreatedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CreatedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CreatedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "created",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EditedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EditedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EditedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "edited",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct IdSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl IdSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            IdSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, ::cynic::Id, super::Person> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("id", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod person_films_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::PersonFilmsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PersonFilmsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PersonFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::PersonFilmsConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct FilmsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PersonFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "films",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod person_films_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::PersonFilmsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::PersonFilmsEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod person_starships_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::PersonStarshipsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PersonStarshipsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PersonStarshipsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<i32>,
            super::PersonStarshipsConnection,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct StarshipsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl StarshipsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            StarshipsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Starship>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PersonStarshipsConnection,
        > {
            ::cynic::selection_set::field(
                "starships",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod person_starships_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Starship>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::PersonStarshipsEdge>
        {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::PersonStarshipsEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod person_vehicles_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::PersonVehiclesConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PersonVehiclesEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PersonVehiclesConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<i32>,
            super::PersonVehiclesConnection,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct VehiclesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl VehiclesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            VehiclesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Vehicle>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PersonVehiclesConnection,
        > {
            ::cynic::selection_set::field(
                "vehicles",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod person_vehicles_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Vehicle>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::PersonVehiclesEdge>
        {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::PersonVehiclesEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod planet {
    pub struct NameSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NameSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NameSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "name",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct DiameterSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl DiameterSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            DiameterSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "diameter",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct RotationPeriodSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl RotationPeriodSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            RotationPeriodSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "rotationPeriod",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct OrbitalPeriodSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl OrbitalPeriodSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            OrbitalPeriodSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "orbitalPeriod",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct GravitySelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl GravitySelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            GravitySelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "gravity",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PopulationSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PopulationSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PopulationSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "population",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ClimatesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ClimatesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ClimatesSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<Vec<Option<String>>>, super::Planet>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "climates",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(::cynic::selection_set::scalar()),
                )),
            )
        }
    }
    pub struct TerrainsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TerrainsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TerrainsSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<Vec<Option<String>>>, super::Planet>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "terrains",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(::cynic::selection_set::scalar()),
                )),
            )
        }
    }
    pub struct SurfaceWaterSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl SurfaceWaterSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            SurfaceWaterSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "surfaceWater",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ResidentConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ResidentConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ResidentConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PlanetResidentsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Planet> {
            ::cynic::selection_set::field(
                "residentConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct FilmConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PlanetFilmsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Planet> {
            ::cynic::selection_set::field(
                "filmConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct CreatedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CreatedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CreatedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "created",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EditedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EditedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EditedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "edited",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct IdSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl IdSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            IdSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, ::cynic::Id, super::Planet> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("id", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod planet_films_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::PlanetFilmsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PlanetFilmsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PlanetFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::PlanetFilmsConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct FilmsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PlanetFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "films",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod planet_films_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::PlanetFilmsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::PlanetFilmsEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod planet_residents_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::PlanetResidentsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PlanetResidentsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PlanetResidentsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<i32>,
            super::PlanetResidentsConnection,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ResidentsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ResidentsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ResidentsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PlanetResidentsConnection,
        > {
            ::cynic::selection_set::field(
                "residents",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod planet_residents_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::PlanetResidentsEdge>
        {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::PlanetResidentsEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod planets_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::PlanetsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PlanetsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PlanetsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::PlanetsConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PlanetsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PlanetsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PlanetsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Planet>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::PlanetsConnection,
        > {
            ::cynic::selection_set::field(
                "planets",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod planets_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Planet>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::PlanetsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::PlanetsEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod root {
    pub struct AllFilmsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl AllFilmsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            AllFilmsSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::FilmsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "allFilms",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct FilmSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmSelectionBuilder { args }
        }
        pub fn id(mut self, id: impl ::cynic::IntoArgument<Option<::cynic::Id>>) -> Self {
            self.args
                .push(::cynic::Argument::new("id", "ID", id.into_argument()));
            self
        }
        pub fn film_id(mut self, film_id: impl ::cynic::IntoArgument<Option<::cynic::Id>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "filmID",
                "ID",
                film_id.into_argument(),
            ));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field("film", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct AllPeopleSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl AllPeopleSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            AllPeopleSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PeopleConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "allPeople",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct PersonSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PersonSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PersonSelectionBuilder { args }
        }
        pub fn id(mut self, id: impl ::cynic::IntoArgument<Option<::cynic::Id>>) -> Self {
            self.args
                .push(::cynic::Argument::new("id", "ID", id.into_argument()));
            self
        }
        pub fn person_id(
            mut self,
            person_id: impl ::cynic::IntoArgument<Option<::cynic::Id>>,
        ) -> Self {
            self.args.push(::cynic::Argument::new(
                "personID",
                "ID",
                person_id.into_argument(),
            ));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "person",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct AllPlanetsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl AllPlanetsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            AllPlanetsSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PlanetsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "allPlanets",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct PlanetSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PlanetSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PlanetSelectionBuilder { args }
        }
        pub fn id(mut self, id: impl ::cynic::IntoArgument<Option<::cynic::Id>>) -> Self {
            self.args
                .push(::cynic::Argument::new("id", "ID", id.into_argument()));
            self
        }
        pub fn planet_id(
            mut self,
            planet_id: impl ::cynic::IntoArgument<Option<::cynic::Id>>,
        ) -> Self {
            self.args.push(::cynic::Argument::new(
                "planetID",
                "ID",
                planet_id.into_argument(),
            ));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Planet>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "planet",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct AllSpeciesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl AllSpeciesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            AllSpeciesSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::SpeciesConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "allSpecies",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct SpeciesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl SpeciesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            SpeciesSelectionBuilder { args }
        }
        pub fn id(mut self, id: impl ::cynic::IntoArgument<Option<::cynic::Id>>) -> Self {
            self.args
                .push(::cynic::Argument::new("id", "ID", id.into_argument()));
            self
        }
        pub fn species_id(
            mut self,
            species_id: impl ::cynic::IntoArgument<Option<::cynic::Id>>,
        ) -> Self {
            self.args.push(::cynic::Argument::new(
                "speciesID",
                "ID",
                species_id.into_argument(),
            ));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Species>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "species",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct AllStarshipsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl AllStarshipsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            AllStarshipsSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::StarshipsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "allStarships",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct StarshipSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl StarshipSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            StarshipSelectionBuilder { args }
        }
        pub fn id(mut self, id: impl ::cynic::IntoArgument<Option<::cynic::Id>>) -> Self {
            self.args
                .push(::cynic::Argument::new("id", "ID", id.into_argument()));
            self
        }
        pub fn starship_id(
            mut self,
            starship_id: impl ::cynic::IntoArgument<Option<::cynic::Id>>,
        ) -> Self {
            self.args.push(::cynic::Argument::new(
                "starshipID",
                "ID",
                starship_id.into_argument(),
            ));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Starship>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "starship",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct AllVehiclesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl AllVehiclesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            AllVehiclesSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::VehiclesConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "allVehicles",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct VehicleSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl VehicleSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            VehicleSelectionBuilder { args }
        }
        pub fn id(mut self, id: impl ::cynic::IntoArgument<Option<::cynic::Id>>) -> Self {
            self.args
                .push(::cynic::Argument::new("id", "ID", id.into_argument()));
            self
        }
        pub fn vehicle_id(
            mut self,
            vehicle_id: impl ::cynic::IntoArgument<Option<::cynic::Id>>,
        ) -> Self {
            self.args.push(::cynic::Argument::new(
                "vehicleID",
                "ID",
                vehicle_id.into_argument(),
            ));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Vehicle>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field(
                "vehicle",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Node>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Root> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
}
#[allow(dead_code)]
pub mod species {
    pub struct NameSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NameSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NameSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Species> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "name",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ClassificationSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ClassificationSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ClassificationSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Species> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "classification",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct DesignationSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl DesignationSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            DesignationSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Species> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "designation",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct AverageHeightSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl AverageHeightSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            AverageHeightSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Species> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "averageHeight",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct AverageLifespanSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl AverageLifespanSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            AverageLifespanSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::Species> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "averageLifespan",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EyeColorsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EyeColorsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EyeColorsSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<Vec<Option<String>>>,
            super::Species,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "eyeColors",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(::cynic::selection_set::scalar()),
                )),
            )
        }
    }
    pub struct HairColorsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl HairColorsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            HairColorsSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<Vec<Option<String>>>,
            super::Species,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "hairColors",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(::cynic::selection_set::scalar()),
                )),
            )
        }
    }
    pub struct SkinColorsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl SkinColorsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            SkinColorsSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<Vec<Option<String>>>,
            super::Species,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "skinColors",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(::cynic::selection_set::scalar()),
                )),
            )
        }
    }
    pub struct LanguageSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl LanguageSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            LanguageSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Species> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "language",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct HomeworldSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl HomeworldSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            HomeworldSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Planet>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Species> {
            ::cynic::selection_set::field(
                "homeworld",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct PersonConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PersonConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PersonConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::SpeciesPeopleConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Species> {
            ::cynic::selection_set::field(
                "personConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct FilmConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::SpeciesFilmsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Species> {
            ::cynic::selection_set::field(
                "filmConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct CreatedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CreatedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CreatedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Species> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "created",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EditedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EditedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EditedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Species> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "edited",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct IdSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl IdSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            IdSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, ::cynic::Id, super::Species> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("id", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod species_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::SpeciesConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::SpeciesEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::SpeciesConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::SpeciesConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct SpeciesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl SpeciesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            SpeciesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Species>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::SpeciesConnection,
        > {
            ::cynic::selection_set::field(
                "species",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod species_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Species>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::SpeciesEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::SpeciesEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod species_films_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::SpeciesFilmsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::SpeciesFilmsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::SpeciesFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::SpeciesFilmsConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct FilmsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::SpeciesFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "films",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod species_films_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::SpeciesFilmsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::SpeciesFilmsEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod species_people_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::SpeciesPeopleConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::SpeciesPeopleEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::SpeciesPeopleConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<i32>,
            super::SpeciesPeopleConnection,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PeopleSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PeopleSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PeopleSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::SpeciesPeopleConnection,
        > {
            ::cynic::selection_set::field(
                "people",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod species_people_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::SpeciesPeopleEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::SpeciesPeopleEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod starship {
    pub struct NameSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NameSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NameSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Starship>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "name",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ModelSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ModelSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ModelSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Starship>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "model",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct StarshipClassSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl StarshipClassSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            StarshipClassSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Starship>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "starshipClass",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ManufacturersSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ManufacturersSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ManufacturersSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<Vec<Option<String>>>,
            super::Starship,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "manufacturers",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(::cynic::selection_set::scalar()),
                )),
            )
        }
    }
    pub struct CostInCreditsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CostInCreditsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CostInCreditsSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Starship> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "costInCredits",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct LengthSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl LengthSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            LengthSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Starship> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "length",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct CrewSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CrewSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CrewSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Starship>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "crew",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PassengersSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PassengersSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PassengersSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Starship>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "passengers",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct MaxAtmospheringSpeedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl MaxAtmospheringSpeedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            MaxAtmospheringSpeedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::Starship> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "maxAtmospheringSpeed",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct HyperdriveRatingSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl HyperdriveRatingSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            HyperdriveRatingSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Starship> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "hyperdriveRating",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct MgltselectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl MgltselectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            MgltselectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::Starship> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "MGLT",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct CargoCapacitySelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CargoCapacitySelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CargoCapacitySelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Starship> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "cargoCapacity",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ConsumablesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ConsumablesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ConsumablesSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Starship>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "consumables",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PilotConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PilotConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PilotConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::StarshipPilotsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Starship> {
            ::cynic::selection_set::field(
                "pilotConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct FilmConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::StarshipFilmsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Starship> {
            ::cynic::selection_set::field(
                "filmConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct CreatedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CreatedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CreatedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Starship>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "created",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EditedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EditedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EditedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Starship>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "edited",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct IdSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl IdSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            IdSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, ::cynic::Id, super::Starship> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("id", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod starship_films_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::StarshipFilmsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::StarshipFilmsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::StarshipFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<i32>,
            super::StarshipFilmsConnection,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct FilmsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::StarshipFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "films",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod starship_films_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::StarshipFilmsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::StarshipFilmsEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod starship_pilots_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::StarshipPilotsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::StarshipPilotsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::StarshipPilotsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<i32>,
            super::StarshipPilotsConnection,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PilotsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PilotsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PilotsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::StarshipPilotsConnection,
        > {
            ::cynic::selection_set::field(
                "pilots",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod starship_pilots_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::StarshipPilotsEdge>
        {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::StarshipPilotsEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod starships_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::StarshipsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::StarshipsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::StarshipsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::StarshipsConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct StarshipsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl StarshipsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            StarshipsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Starship>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::StarshipsConnection,
        > {
            ::cynic::selection_set::field(
                "starships",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod starships_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Starship>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::StarshipsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::StarshipsEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod vehicle {
    pub struct NameSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NameSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NameSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "name",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ModelSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ModelSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ModelSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "model",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct VehicleClassSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl VehicleClassSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            VehicleClassSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "vehicleClass",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ManufacturersSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ManufacturersSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ManufacturersSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<Vec<Option<String>>>,
            super::Vehicle,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "manufacturers",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(::cynic::selection_set::scalar()),
                )),
            )
        }
    }
    pub struct CostInCreditsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CostInCreditsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CostInCreditsSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "costInCredits",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct LengthSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl LengthSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            LengthSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "length",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct CrewSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CrewSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CrewSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "crew",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PassengersSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PassengersSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PassengersSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "passengers",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct MaxAtmospheringSpeedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl MaxAtmospheringSpeedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            MaxAtmospheringSpeedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "maxAtmospheringSpeed",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct CargoCapacitySelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CargoCapacitySelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CargoCapacitySelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<f64>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "cargoCapacity",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct ConsumablesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl ConsumablesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            ConsumablesSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "consumables",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PilotConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PilotConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PilotConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::VehiclePilotsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Vehicle> {
            ::cynic::selection_set::field(
                "pilotConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct FilmConnectionSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmConnectionSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmConnectionSelectionBuilder { args }
        }
        pub fn after(mut self, after: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "after",
                "String",
                after.into_argument(),
            ));
            self
        }
        pub fn first(mut self, first: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "first",
                "Int",
                first.into_argument(),
            ));
            self
        }
        pub fn before(mut self, before: impl ::cynic::IntoArgument<Option<String>>) -> Self {
            self.args.push(::cynic::Argument::new(
                "before",
                "String",
                before.into_argument(),
            ));
            self
        }
        pub fn last(mut self, last: impl ::cynic::IntoArgument<Option<i32>>) -> Self {
            self.args
                .push(::cynic::Argument::new("last", "Int", last.into_argument()));
            self
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::VehicleFilmsConnection>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::Vehicle> {
            ::cynic::selection_set::field(
                "filmConnection",
                self.args,
                ::cynic::selection_set::option(fields),
            )
        }
    }
    pub struct CreatedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CreatedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CreatedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "created",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct EditedSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EditedSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EditedSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<String>, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "edited",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct IdSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl IdSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            IdSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, ::cynic::Id, super::Vehicle> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("id", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod vehicle_films_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::VehicleFilmsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::VehicleFilmsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::VehicleFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::VehicleFilmsConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct FilmsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl FilmsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            FilmsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::VehicleFilmsConnection,
        > {
            ::cynic::selection_set::field(
                "films",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod vehicle_films_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Film>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::VehicleFilmsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::VehicleFilmsEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod vehicle_pilots_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::VehiclePilotsConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::VehiclePilotsEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::VehiclePilotsConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<
            'static,
            Option<i32>,
            super::VehiclePilotsConnection,
        > {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct PilotsSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PilotsSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PilotsSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::VehiclePilotsConnection,
        > {
            ::cynic::selection_set::field(
                "pilots",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod vehicle_pilots_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Person>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::VehiclePilotsEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::VehiclePilotsEdge>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
#[allow(dead_code)]
pub mod vehicles_connection {
    pub struct PageInfoSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl PageInfoSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            PageInfoSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::PageInfo>,
        ) -> ::cynic::selection_set::SelectionSet<'a, T, super::VehiclesConnection> {
            ::cynic::selection_set::field("pageInfo", self.args, fields)
        }
    }
    pub struct EdgesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl EdgesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            EdgesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::VehiclesEdge>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::VehiclesConnection,
        > {
            ::cynic::selection_set::field(
                "edges",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
    pub struct TotalCountSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl TotalCountSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            TotalCountSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, Option<i32>, super::VehiclesConnection>
        {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field(
                "totalCount",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::scalar()),
            )
        }
    }
    pub struct VehiclesSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl VehiclesSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            VehiclesSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Vehicle>,
        ) -> ::cynic::selection_set::SelectionSet<
            'a,
            Option<Vec<Option<T>>>,
            super::VehiclesConnection,
        > {
            ::cynic::selection_set::field(
                "vehicles",
                self.args,
                ::cynic::selection_set::option(::cynic::selection_set::vec(
                    ::cynic::selection_set::option(fields),
                )),
            )
        }
    }
}
#[allow(dead_code)]
pub mod vehicles_edge {
    pub struct NodeSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl NodeSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            NodeSelectionBuilder { args }
        }
        pub fn select<'a, T: 'a + Send + Sync>(
            self,
            fields: ::cynic::selection_set::SelectionSet<'a, T, super::Vehicle>,
        ) -> ::cynic::selection_set::SelectionSet<'a, Option<T>, super::VehiclesEdge> {
            ::cynic::selection_set::field("node", self.args, ::cynic::selection_set::option(fields))
        }
    }
    pub struct CursorSelectionBuilder {
        args: Vec<::cynic::Argument>,
    }
    impl CursorSelectionBuilder {
        pub(super) fn new(args: Vec<::cynic::Argument>) -> Self {
            CursorSelectionBuilder { args }
        }
        pub fn select(
            self,
        ) -> ::cynic::selection_set::SelectionSet<'static, String, super::VehiclesEdge> {
            #[allow(unused_imports)]
            use cynic::selection_set::{boolean, float, integer, string};
            ::cynic::selection_set::field("cursor", self.args, ::cynic::selection_set::scalar())
        }
    }
}
impl ::cynic::QueryRoot for Root {}
impl ::cynic::selection_set::HasSubtype<Film> for Node {}
impl ::cynic::selection_set::HasSubtype<Person> for Node {}
impl ::cynic::selection_set::HasSubtype<Planet> for Node {}
impl ::cynic::selection_set::HasSubtype<Species> for Node {}
impl ::cynic::selection_set::HasSubtype<Starship> for Node {}
impl ::cynic::selection_set::HasSubtype<Vehicle> for Node {}
