# Sport Clubbing

## Installation

# Branch Usage

## 🚀 Branch Sequence with TDD Granularity

## 00-setup-project
`00-setup-project` → Initial Spring Boot project, test framework set up.

## 01-db-config
 → Implement DB config

## 02-entities-models
### MusicLabel
- `02a-musiclabel-test` → Write test: can create and persist MusicLabel.
- `02b-musiclabel-impl` → Implement `MusicLabel` entity (JPA annotations).

### Album
- `02c-album-test` → Write test: can create Album linked to a MusicLabel.
- `02d-album-impl` → Implement `Album` entity with `@ManyToOne`.

### Artist
- `02e-artist-test` → Write test: can create Artist and associate with Albums.
- `02f-artist-impl` → Implement `Artist` entity with `@ManyToMany`.

## 03-repositories
- `03a-repo-tests` → Write tests for repositories (find by labelId, etc).
- `03b-repo-impl` → Implement repositories to pass tests.

## 04-seed-data
- `04a-seed-test` → Write test: DB should have 3 labels, 6 albums, 10 artists after seeding.
- `04b-seed-impl` → Implement seeder with CommandLineRunner.

## 05-dto-mapping
- `05a-dto-test` → Write test: MusicLabel maps to MusicLabelDTO correctly.
- `05b-dto-impl` → Implement DTOs + mapping (MapStruct/ModelMapper).

## 06-services
- `06a-service-tests` → Write unit tests for LabelService, AlbumService, ArtistService.
- `06b-service-impl` → Implement services until tests pass.

## 07-crud-endpoints
- `07a-crud-tests` → Write integration tests for CRUD endpoints (POST/GET/PUT/DELETE).
- `07b-crud-impl` → Implement controllers to pass tests.

## 08-related-updates
- `08a-related-tests` → Write tests for updating related data (albums in label, artists in album).
- `08b-related-impl` → Implement endpoints.

## 09-reports
- `09a-reports-tests` → Write tests for reports (albums by label, artists by album/label).
- `09b-reports-impl` → Implement endpoints.

## 10-swagger-docs
- `10a-swagger-test` → Write test: OpenAPI spec exists at `/v3/api-docs`.
- `10b-swagger-impl` → Add Swagger annotations + config.

## 11-validation-errorhandling
- `11a-validation-tests` → Write tests for invalid DTOs and not found cases.
- `11b-validation-impl` → Implement validation + `@ControllerAdvice` exception handler.

## 12-advanced-features
- `12a-softdelete-test` → Test soft delete for artists/albums.
- `12b-softdelete-impl` → Implement soft delete.
- `12c-conditional-test` → Test conditional include (`?includeArtists=true`).
- `12d-conditional-impl` → Implement conditional inclusion.
- `12e-bulkimport-test` → Test bulk import endpoint.
- `12f-bulkimport-impl` → Implement bulk import.
- `12g-pagination-test` → Test pagination/sorting query params.
- `12h-pagination-impl` → Implement pagination/sorting.

---


## Contributing

## License

[MIT](https://choosealicense.com/licenses/mit/)
