# Sport Clubbing

## Installation

# Branch Usage

## 🚀 Branch Sequence with TDD Granularity

## 00-setup-project
`00-setup-project` → Initial Spring Boot project, test framework set up.

## 01-db-config
 → Implement DB config

## 02-entities-models

### Club

* `02a-club-impl` → Implement `Club` entity

### Team

* `02b-team-impl` → Implement `Team` entity with `@ManyToOne` to `Club`. Update `Club` `@OneToMany`.

### Player

* `02c-player-impl` → Implement `Player` entity with `@ManyToMany` to `Team`. Update `Team` `@ManyToMany`.

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

## Notes

# Spring Web & Testing Reference

## ✨ Spring Web vs WebFlux

### 🌐 `spring-boot-starter-web`

* **Model:** Traditional Spring MVC
* **Execution:** Blocking
* **Threads:** One per request
* **Use for:**

    * CRUD APIs
    * Simpler applications
    * JDBC-based data access
* **Tech Stack:** Servlet API (Tomcat/Jetty)
* **Annotations:** `@RestController`, `@RequestMapping`

---

### ⚡ `spring-boot-starter-webflux`

* **Model:** Reactive with Project Reactor
* **Execution:** Non-blocking, event-driven
* **Threads:** Small number serving many requests
* **Use for:**

    * High-throughput apps
    * Streaming APIs
    * Reactive DBs (R2DBC, MongoDB)
* **Tech Stack:** Netty/reactive servlet containers
* **Annotations:** Same as MVC, but with `Mono<T>` / `Flux<T>`

---

### 🧠 Summary Table

| Feature            | Web (MVC)        | WebFlux (Reactive)  |
| ------------------ | ---------------- | ------------------- |
| Model              | Imperative       | Reactive            |
| Blocking           | Yes              | No                  |
| Throughput         | Medium           | High                |
| Complexity         | Lower            | Higher              |
| JDBC Compatibility | ✅ Yes            | ⚠️ Risky (blocking) |
| Server             | Tomcat (default) | Netty (default)     |

---

## 📃 MockMvc vs WebTestClient

### ✅ `MockMvc`

* For testing MVC (`spring-boot-starter-web`)
* Fast and familiar
* Does not start a real server
* Great for unit-style controller tests

**Example:**

```java
mockMvc.perform(get("/api/items"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.name").value("Spanner"));
```

---

### ✅ `WebTestClient`

* Designed for `spring-boot-starter-webflux`
* Also works for traditional apps
* Functional, declarative
* Good for integration tests

**Example:**

```java
webTestClient.get().uri("/api/items")
             .exchange()
             .expectStatus().isOk()
             .expectBody()
             .jsonPath("$.name").isEqualTo("Spanner");
```

---

### 💡 When to Use Which

| Context                   | Recommended Tool |
| ------------------------- | ---------------- |
| Spring MVC (`web`)        | `MockMvc`        |
| Spring WebFlux            | `WebTestClient`  |
| Declarative testing style | `WebTestClient`  |
| Unit-style tests          | `MockMvc`        |

---

## ⚖️ Test Strategy (TDD-Inspired)

### ✅ 1. Start with Domain & Data Access

* Test entities, DTOs, repositories
* Use H2/in-memory DB

### ✅ 2. Test Service Layer

* Unit test logic with mocks
* Handle validation, business rules

### ✅ 3. Test Controllers

* Use `@WebMvcTest` with `MockMvc`
* Mock service layer

### ✅ 4. Integration Tests

* Use `@SpringBootTest` with full context
* Test end-to-end paths

---

### 🌟 Why Prefer Integration Tests Early

| Benefit              | Reason                                      |
| -------------------- | ------------------------------------------- |
| Repos are interfaces | No gain mocking generated code              |
| Test real behavior   | Catch mapping, cascade, lazy-loading issues |
| H2 is fast           | Great for early-stage apps                  |
| Avoid mocking chains | Cleaner, real-world test                    |

---

## 🧣 Suggested Test Layers

| Layer      | Type        | Annotation                  | Notes                    |
| ---------- | ----------- | --------------------------- | ------------------------ |
| Repository | Integration | `@DataJpaTest`              | Fast with in-memory DB   |
| Service    | Integration | `@SpringBootTest`           | Test business logic + DB |
| Controller | Unit-style  | `@WebMvcTest + MockMvc`     | Fast API checks          |
| Controller | Integration | `@SpringBootTest + MockMvc` | End-to-end HTTP + DB     |

---

## 🏋️ Domain: Sports

### 🗋 Core Aggregates & Entities

#### 🟦 `Club`

* Represents an organization
* Owns teams

**Fields:** id, name, location, foundedYear
**Relationships:** `Set<Team> teams`

---

#### 🟩 `Team`

* Belongs to one club
* Has many players

**Fields:** id, name, teamType (enum)
**Relationships:** `Club club`, `Set<Player> players`

---

#### 🟨 `Player`

* Plays for multiple teams

**Fields:** id, firstName, lastName, position, dateOfBirth
**Relationships:** `Set<Team> teams`

---

#### 🔴 `Rule`

* Represents a game rule
* Can apply globally or to a team

**Fields:** id, description, appliesTo (enum)
**Relationships:** (Optional) `Team team`

---

### 📦 Modules (Contexts)

* `club`
* `team`
* `player`
* `rules`

---

### ⚖️ Key Relationships

| Relationship   | Type         |
| -------------- | ------------ |
| Club → Teams   | One-to-Many  |
| Team ↔ Players | Many-to-Many |
| Team → Club    | Many-to-One  |

Absolutely — great point. Here’s the updated **Validation Strategy** section, now including the important distinction between **validation annotations** and **schema-level constraints** like `@Column(length = 50, nullable = false)`, and how they relate to each other in the bigger picture:

---

## ✅ Validation Strategy

Validation happens at **multiple layers**, each with a different focus:
Some protect the **user experience**, others protect **business logic**, and some ensure **database integrity**.

---

### 📥 1. DTO Layer — Input Validation

Use **Bean Validation** annotations (Jakarta/Javax) to validate input **before it enters your domain**:

Examples:

* `@NotBlank`
* `@Size(max = 50)`
* `@Email`
* `@Past`

In controllers, enable this by annotating parameters with `@Valid`:

```java
@PostMapping
public ResponseEntity<?> createPlayer(@RequestBody @Valid PlayerDTO dto) {
    ...
}
```

✅ Best for: **User input validation**, frontend-facing constraints
🚫 Avoid complex domain rules here — keep it focused on field-level validation.

---

### 🧱 2. Entity Layer — Schema Constraints (Not Validation)

In JPA entities, annotations like:

* `@Column(length = 50, nullable = false)`
* `@Enumerated(EnumType.STRING)`
* `@ManyToOne(optional = false)`
* `@JoinColumn(nullable = false)`

**do not perform validation** — they are used to **generate and enforce the schema**.

They ensure:

* Field size limits at the DB level
* Required fields (`NOT NULL`)
* Correct data types (e.g., enum storage)

🔒 This protects **data integrity at the DB layer**, not the user/API layer.

👉 You should **mirror these constraints** in your DTOs using Bean Validation, otherwise:

* Your API might accept bad data
* The app might crash on persist due to DB errors

✅ Rule of thumb:
**Every hard DB constraint should be reflected by an equivalent DTO validation.**

---

### 🧠 3. Service Layer — Business Rule Validation

Use **plain Java** here to enforce complex business rules that don’t fit into annotations.

Examples:

* A team can have **at most 23 players**
* A player can’t be on **two teams from the same club**
* A senior team must only contain players **over 18**

These are implemented directly in service logic:

```java
if (team.getPlayers().size() >= 23) {
    throw new IllegalStateException("Team is full");
}
```

You *can* extract this logic into validation helpers or domain services, but **just writing it cleanly in your service often works best**.

---

### 🔄 Mapping & Validation Flow

| Layer      | Handles         | Validates                         |
| ---------- | --------------- | --------------------------------- |
| Controller | DTOs            | Input format, required fields     |
| Mapper     | DTO → Entity    | Converts data, no logic           |
| Entity     | JPA Annotations | Schema generation, not validation |
| Service    | Entities        | Business rules                    |

📌 You can map DTOs to entities **in the controller** or **inside the service** — both are valid, just stay consistent.

---

### 🧪 TL;DR: Where to Put What

| What kind of rule?                   | Where to put it      | How?                                    |
| ------------------------------------ | -------------------- | --------------------------------------- |
| Field required, length, email, etc   | DTO                  | `@NotNull`, `@Size`, `@Pattern`, etc.   |
| DB field length or nullability       | Entity               | `@Column(length = X, nullable = false)` |
| Enum values or references            | Entity               | `@Enumerated`, `@JoinColumn`, etc.      |
| Business rule across fields/entities | Service layer        | Plain Java or helper methods            |
| Shared validation logic              | Custom helper / bean | Static methods, utility classes, etc.   |

## 🧪 Initial Development & Testing Strategy (Entities + Repositories + Seeding)

### 🧱 Step 1: Define Entities

Start by implementing the core entities with proper JPA mappings:

* Relationships (`@OneToMany`, `@ManyToMany`, etc.)
* Field constraints via `@Column(nullable = false, length = 50)`, etc.
* `@EqualsAndHashCode` / `@ToString` exclusion for collections if needed (to avoid circular issues)

👉 No service layer yet — we’re just wiring up the domain and DB.

---

### 🗃️ Step 2: Set Up Repositories

Create standard Spring Data interfaces:

```java
public interface PlayerRepository extends JpaRepository<Player, Long> {}
public interface TeamRepository extends JpaRepository<Team, Long> {}
```

No need for custom queries yet. Stick to `save`, `findAll`, etc.

---

### 🧪 Step 3: Configure In-Memory H2 for Testing

#### ✅ `application.properties` (default = dev config):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sportsdb
spring.datasource.username=postgres
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
```

#### ✅ `application-test.properties` (used only during tests):

```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.h2.console.enabled=true

# Optional: Only run seed files in test
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data-test.sql
```

---

### 🧪 Step 4: Create Seed Data for Tests

Create a file called `src/test/resources/data-test.sql`:

```sql
INSERT INTO club (id, name) VALUES (1, 'Stormers');
INSERT INTO team (id, name, club_id) VALUES (1, 'Senior Team', 1);
INSERT INTO player (id, first_name, last_name) VALUES (1, 'Siya', 'Kolisi');
INSERT INTO team_players (team_id, player_id) VALUES (1, 1);
```

> 💡 Keep test data minimal, meaningful, and only loaded in the `test` profile.

---

### ⚙️ Step 5: Write Repository Integration Tests

Use `@DataJpaTest` to spin up just the DB + repo layer (fast and focused).

```java
@DataJpaTest
@ActiveProfiles("test")
class PlayerRepositoryTest {

    @Autowired PlayerRepository repo;

    @Test
    void canReadSeededPlayer() {
        Player p = repo.findById(1L).orElseThrow();
        assertThat(p.getFirstName()).isEqualTo("Siya");
    }

    @Test
    void canPersistNewPlayer() {
        Player p = new Player();
        p.setFirstName("Cheslin");
        p.setLastName("Kolbe");
        repo.save(p);
        assertThat(repo.findAll()).extracting(Player::getLastName).contains("Kolbe");
    }
}
```

> ✅ These are true **integration tests** — they verify your mappings, seeding, and DB interaction.

---

### 🔄 Switching to Test Profile

Use:

```java
@ActiveProfiles("test")
```

Or set it via:

* IntelliJ → Run Config → VM Options:

  ```
  -Dspring.profiles.active=test
  ```
* Maven/Gradle:

  ```bash
  ./mvnw test -Dspring.profiles.active=test
  ```

---

### 🧼 Summary of Flow

| Phase          | Action                                     |
| -------------- | ------------------------------------------ |
| 1️⃣ Entities   | Map your domain models and relationships   |
| 2️⃣ Repos      | Create standard Spring Data JPA repos      |
| 3️⃣ H2 Config  | Set up `application-test.properties`       |
| 4️⃣ Seeding    | Add `data-test.sql` for test-specific data |
| 5️⃣ Repo Tests | Use `@DataJpaTest` to validate persistence |

### Injecting different implementations based on profile


#### 🧪 Using a Test-Specific Service with `@Profile`

* Define your **test-specific implementation**:

  ```java
  @Service
  @Profile("test")
  class ClubServiceTestImpl implements ClubService { ... }
  ```

* Ensure your **real implementation** excludes test profile:

  ```java
  @Service
  @Profile("!test")
  class ClubServiceImpl implements ClubService { ... }
  ```

* In your test class, activate the `test` profile:

  ```java
  @ActiveProfiles("test")
  ```

* Spring will inject the correct implementation based on the active profile.

✅ Keeps test logic clean and production logic untouched.

## Mapstruct @Data vs entity @Getter @Setter

Great questions. Here's a clean breakdown to clarify your observations:

---

### 🔹 Why use `@Data` on DTOs but not Entities?

* `@Data` (from Lombok) includes:

    * `@Getter`, `@Setter`
    * `@ToString`
    * `@EqualsAndHashCode`
    * `@RequiredArgsConstructor`

For **DTOs**, this is usually fine and convenient because:

* They're **simple data carriers** with no business logic.
* You often compare DTOs in tests or logs, so `equals`, `hashCode`, and `toString` are useful.
* They're **not managed by JPA**, so extra generated methods won’t break anything.

For **Entities**, we avoid `@Data` because:

* `equals` / `hashCode` on entities (especially with IDs) can lead to **subtle bugs** in JPA (e.g., if IDs are generated late, equality behaves weirdly).
* `toString` may trigger lazy-loaded relations (causing `LazyInitializationException`).
* Separation gives you **more control** (which is best practice with JPA).

✅ So:

* `@Data` is perfect for DTOs.
* `@Getter` / `@Setter` is safer and more precise for Entities.

---

### 🔹 Does MapStruct use constructor or setters?

By default:

* **MapStruct uses setters** to populate DTOs/entities.
* If your class is immutable or has a constructor with all arguments, you can configure MapStruct to use constructors.

But in your case — using `@Data` with default constructors — MapStruct will:

* Create an empty instance
* Set fields via **setters**

✅ So you’re fine with mutable DTOs and default `@Data`.

---

### 🔹 Is `equals()` okay on DTOs?

Yes:

* DTOs are typically **compared by value**, so `equals` and `hashCode` are helpful.
* `@Data` gives you value-based equality by default (field-by-field).

✅ Just be mindful not to use `@Data` on JPA entities for the reasons mentioned above.



## Contributing

## License

[MIT](https://choosealicense.com/licenses/mit/)
