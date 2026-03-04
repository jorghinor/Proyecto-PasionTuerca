Pasion Futbol - Backend (minimal skeleton)

Quick start:
- Requires Java 17 and Maven.
- From backend/ run:
    mvn spring-boot:run
- The app runs on http://localhost:8080

Default demo users:
- admin / admin123
- user / user123

Auth:
- POST /api/v1/auth/login with JSON {"username":"admin","password":"admin123"}
  -> returns {"accessToken":"..."}

Media:
- GET /api/v1/media/carousel
- POST /api/v1/media (multipart/form-data) fields: file, title, type, whatsapp
  -> requires Authorization: Bearer <token>
