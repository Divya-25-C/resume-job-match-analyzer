# Resume Job Match Analyzer

A Java Spring Boot + React mini project that compares resume text with a job description using a transparent, rule-based skill dictionary. It is a learning aid, not an automated hiring decision.

## Requirements
- Java 21 and Maven 3.9+
- Node.js 20+
- Docker Desktop (optional, for the full stack)

## Run locally
1. Start PostgreSQL (or use Docker Compose below).
2. From `backend`, run `mvn spring-boot:run`.
3. From `frontend`, run `npm install` then `npm run dev`.
4. Open http://localhost:5173. Backend health: http://localhost:8080/api/health.

### Run locally without PostgreSQL or Docker
Install Java 21, Maven, and Node.js. In one terminal, from `backend`, run `mvn spring-boot:run -Dspring-boot.run.profiles=local`; this uses the included in-memory H2 database. In a second terminal, from `frontend`, run `npm install` and then `npm run dev`. Open http://localhost:5173. H2 data is temporary and disappears when the backend stops.

Default local database settings are in `backend/src/main/resources/application.yml`. Override them with `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, and `DB_PASSWORD`. Set `APP_CORS_ALLOWED_ORIGINS` to the frontend origin when it differs from localhost.

## Docker
At the project root, run `docker compose up --build`. Open http://localhost:8081. The database data is stored in a named volume. For a public deployment, configure secrets in the hosting provider and use managed PostgreSQL; do not publish the sample credentials or rely on `ddl-auto: update` for production.

## Deploy
Use a hosting service that can run Docker containers and provide managed PostgreSQL. Create the database first, then deploy `backend` from its Dockerfile with `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, and `DB_PASSWORD` set to the database connection values. Set `APP_CORS_ALLOWED_ORIGINS` to the exact public frontend origin. Deploy `frontend` from its Dockerfile and set build argument `VITE_API_BASE_URL` to the public backend origin (for example `https://your-api.example.com`, without a trailing slash). Expose the frontend on port 80 and backend on port 8080, enable HTTPS at the host, then check `/api/health` and complete an analysis. Set `JPA_DDL_AUTO=validate` after creating a database schema through migrations; this starter does not include a migration tool, so production database migration setup is a required follow-up.

## API
- `POST /api/analyze` JSON: `{ "title": "Backend role", "resumeText": "...", "jobDescriptionText": "..." }`
- `GET /api/analyses` history summaries
- `GET /api/analyses/{id}` saved result
- `DELETE /api/analyses/{id}` delete saved result
- `GET /api/health`

## Publish
Create a repository on GitHub, then at this folder run `git init`, `git add .`, `git commit -m "Build resume job match analyzer"`, `git branch -M main`, `git remote add origin <your-repository-url>`, and `git push -u origin main`. In GitHub Desktop, choose **File → Add local repository** and select this folder. Add the project to LinkedIn only after deploying it; use your live URL and describe the matching score as rule-based.

Suggested LinkedIn project summary: “Built a full-stack Resume Job Match Analyzer with Java, Spring Boot, React, TypeScript, and PostgreSQL. It compares resume text against job descriptions, highlights recognized and missing skills, saves analysis history, and runs in Docker. Matching uses a transparent, rule-based skill dictionary.” Add the deployed demo and GitHub repository links after publishing them.

## Limits
The analyzer recognizes skills in its built-in dictionary and computes a percentage from recognized job-description skills. It does not parse uploaded files, infer equivalent skills, authenticate users, or provide AI-generated advice. Resume text is stored in the database; use sample data while learning and add authentication, retention controls, and privacy protections before real personal data.
