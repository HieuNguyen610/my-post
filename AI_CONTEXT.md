AI Context for implementing features in this repository

Purpose
- Provide a compact, machine- and human-readable context file for automated agents (and developers) to consult before implementing features.
- Capture important conventions, running/testing instructions, and places to change behavior in the codebase.

Project at-a-glance
- Language: Java 17
- Framework: Spring Boot (parent version 3.5.4)
- Build: Maven (wrapper included: `mvnw.cmd`)
- In-memory DB for dev: H2 (configured in `src/main/resources/application.properties`)
- OpenAPI: springdoc-openapi starter included (Swagger UI enabled)
- Lombok used for DTOs/entities
- Nullability: project uses Spring `@NonNullApi` conventions in repositories; use `org.springframework.lang.NonNull`/`@Nullable` where applicable

Important files & folders
- `src/main/java/com/example/demo` — main package
  - `controller/` — REST controllers (annotate with `@Tag` and `@Operation` for OpenAPI)
  - `service/impl/` — service implementations (business logic goes here)
  - `repository/` — Spring Data JPA repositories
  - `entity/` — JPA entity classes (note: some entities set `@Entity(name = "staffs")`)
  - `request/` and `response/` — DTO classes for requests and responses
  - `config/` — Spring configuration (OpenAPI config, others)
- `src/main/resources/application.properties` — app configuration (H2, JPA, logging, H2 console path)
- `pom.xml` — Maven dependencies and build configuration

Run & inspect locally
- Run (PowerShell): `.\mvnw.cmd spring-boot:run`
- Build: `.\mvnw.cmd -DskipTests package`
- H2 Console (while app runs): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:TESTDB;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
  - User: `sa` (no password)
- Swagger UI: http://localhost:8080/swagger-ui.html (or `/swagger-ui/index.html`)
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Database notes
- Default is in-memory; it is created and dropped with the application lifecycle.
- To allow external tools to connect or to persist data, change to file-based URL:
  - `spring.datasource.url=jdbc:h2:file:./data/TESTDB;DB_CLOSE_ON_EXIT=FALSE`
- If you need TCP access for external tools, consider running an H2 TCP server bean in the Spring Boot app and using `jdbc:h2:tcp://localhost:9092/mem:TESTDB` (or use file-based DB over TCP).

Coding & design conventions (follow these when adding features)
- Controller layer: keep controllers thin; call into services to execute business logic.
- Service layer: implement business rules and mapping between entities and response DTOs.
- Repository layer: use Spring Data JPA; prefer `@EntityGraph(attributePaths = {...})` for queries that must fetch relationships to avoid N+1.
- DTOs: request objects in `request/`, response objects in `response/`. Do not expose sensitive fields (e.g., password) in response DTOs.
- Mapping: manual mapping is used (builder pattern via Lombok). Keep mapping explicit and avoid leaking entities directly.
- Pagination: use Spring Data `Pageable` and return Page metadata (page, size, totalElements, totalPages) in page response objects.
- OpenAPI: annotate controllers and endpoints with `@Tag` on class and `@Operation(summary=..., description=...)` on endpoints for good documentation.
- Nullability: when overriding repository methods or defining repository signatures, use `@NonNull` and `@Nullable` from `org.springframework.lang` consistently.

Testing
- Unit tests go under `src/test/java/...` and Spring Boot tests already exist.
- Fast checks:
  - `.\mvnw.cmd test` — run tests
  - Use `@DataJpaTest` for repository tests or mock repositories in service unit tests.
- When adding integration tests that rely on H2, use the same JDBC URL or configure `@AutoConfigureTestDatabase(replace = Replace.NONE)` to use the configured DB.

OpenAPI / Swagger guidance
- Add `springdoc-openapi-starter-webmvc-ui` dependency (already present) to enable Swagger UI.
- For request objects bound from query params, annotate with `@ParameterObject` from `org.springdoc.api.annotations` to surface individual fields in the docs.
- If Linux/IDE indicates missing swagger model classes during compile, ensure dependency versions are defined in `pom.xml`. Prefer the springdoc starter which pulls the necessary transitive deps.

Security notes
- `spring-boot-starter-security` is included; if endpoints are not accessible in local environment, check `SecurityConfig` under `config/`.
- For local dev, `spring.security.user.name` and `spring.security.user.password` can be set in `application.properties` to create a default user.
- Make sure to allow `/swagger-ui/**` and `/v3/api-docs/**` if you want open API docs without authentication.

Style & formatting
- Follow existing style: concise methods, meaningful variable names.
- Prefer using `LocalDate` for date-only values in new code; existing DTOs use `java.util.Date`—be mindful when mapping.
- JSON date format: `StaffResponse.dateOfBirth` uses `@JsonFormat(pattern = "yyyy/MM/dd")`.

Where to add tests for new features
- Unit tests for service logic: `src/test/java/com/example/demo/service/...`
- Controller tests: `src/test/java/com/example/demo/controller/...` using `@WebMvcTest` and mocked services.
- Repository tests: `src/test/java/com/example/demo/repository/...` with `@DataJpaTest` or integration style tests with full context.

PR checklist for AI changes
- Code compiles with `mvn -q -DskipTests package`.
- Add/adjust unit tests for new behavior (happy path + 1-2 edge cases).
- Update OpenAPI annotations and ensure endpoints are documented.
- Do not expose sensitive information (passwords/api keys) in responses or logs.
- Keep changes minimal and well-scoped; add a short entry in `README.md` if the UX for running or testing changes.

Useful commands
- Run app: `./mvnw.cmd spring-boot:run`
- Run tests: `./mvnw.cmd test`
- Build jar: `./mvnw.cmd -DskipTests package`

Contact points in codebase
- Common files to update when adding a resource:
  - Controller: `src/main/java/com/example/demo/controller/YourResourceController.java`
  - Service: `src/main/java/com/example/demo/service/impl/YourResourceServiceImpl.java`
  - Repository: `src/main/java/com/example/demo/repository/YourResourceRepository.java`
  - Request/Response DTOs: `src/main/java/com/example/demo/request/` and `.../response/`
  - Tests: `src/test/java/...`

If you (the AI) need to change dependency versions, update `pom.xml` and run `mvnw.cmd -q -DskipTests package` to validate.

Last updated: 2026-01-12

