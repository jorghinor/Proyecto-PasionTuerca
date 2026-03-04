Pasion Futbol - Minimal demo package
This zip contains a minimal runnable skeleton for backend (Spring Boot) and frontend (React + Vite).
Backend quick start:
- Java 17+, Maven
- cd backend
- mvn spring-boot:run
Frontend quick start:
- Node 18+
- cd frontend
- npm install
- npm run dev

Notes:
- Backend uses H2 in-memory DB for quick testing.
- Uploads are stored in backend/uploads and served at /uploads/<filename>.
- Auth is JWT-based via /api/v1/auth/login (demo users admin/admin123, user/user123).


Expanded backend with:
- AppUser, RefreshToken, CalendarItem, Notification, Biography, Competition entities
- Auth with refresh token endpoint (/api/v1/auth/refresh)
- Scheduler that 'sends' notifications every minute (logs)
- Endpoints for calendar, notifications, biography

Docker compose included to run Postgres + backend + frontend.

To run with Docker:
  docker-compose up --build

Seed users: POST /api/v1/auth/seed  (or will run at startup)



=== Advanced configuration added ===

FCM (Firebase Cloud Messaging)
 - To send push notifications, provide a Firebase service account JSON file and set `firebase.service-account` in `application.yml` or as environment variable.
 - The backend will initialize Firebase Admin SDK and use it to send messages via `NotificationService.sendPush`.

S3 Storage
 - To store media in S3, set properties: `s3.bucket`, `s3.region`, `s3.accessKey`, `s3.secretKey`, `s3.endpoint` (optional for S3-compatible).
 - In `MediaController` the `StorageService` bean is injected; by default the `localStorageService` is used. To use S3, change the Qualifier to `s3StorageService` or wire conditionally via property.

BCrypt Passwords
 - Passwords are now encoded with BCrypt via `PasswordEncoder` bean. UserSeeder uses BCrypt to seed demo users.

Capacitor / Mobile builds
 - A `capacitor.config.json` placeholder is included. To build mobile apps:
   1) On your dev machine, install Capacitor CLI: `npm install @capacitor/cli @capacitor/core` in frontend.
   2) Build web assets: `npm run build` (generates `dist/`).
   3) Initialize Capacitor: `npx cap init` (use the same appId/appName as in capacitor.config.json).
   4) Add platforms: `npx cap add android` (or `ios`).
   5) Open native IDE: `npx cap open android` and build from Android Studio (you need Android SDK).
 - Note: This environment cannot produce APK/IPA binaries; follow the above steps locally to generate mobile builds.
