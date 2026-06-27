# Schedule Arrangement — Review & Suggestions

Overview
- CLI Java app that manages students, subjects, and sections.
- AppConfig initializes repos and JSON storage (data/*.json), registers checkers, and exposes a RegistrationService.
- App provides a simple text menu for listing and (un)registering students to sections.
- Registration flow: locate Student/Section/Subject → run checkers (capacity, duplicate section/subject, schedule conflict) → update in-memory repos → save.

Findings
- Clear, modular structure: models, repositories, storage, checkers, and services are well separated.
- JSON and TEXT storage implementations coexist and are easy to extend.
- Concurrency-conscious repos (ConcurrentHashMap + AtomicBoolean).

Suggestions
1. Fix App entrypoint: App.main signature is `static void main()` — change to `public static void main(String[] args)` to run normally.
2. Add README with run/build instructions (mvn package; java -cp target/... App) and sample commands.
3. Add unit tests for checkers, repos, and storage; add CI (GitHub Actions) for builds/tests.
4. Improve error handling/logging (use a logger instead of System.err; propagate stack traces where helpful).
5. Add CLI arguments or an exec plugin in pom.xml to run without modifying main signature repeatedly.
6. Validate JSON structure on load and provide clearer warnings when files are missing or malformed.

Next steps (optional)
- Implement unit tests and add a small CI workflow.
- Optionally correct App.main and add an exec-maven-plugin entry to simplify running.

Reviewer: AI assistant using Copilot CLI runtime in VS Code
Date: 2026-06-24
