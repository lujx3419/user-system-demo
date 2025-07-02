# User System Demo

A simple **Spring Boot** backend demo for user management.  
This project demonstrates a basic **three-layer architecture** (Controller / Service / Repository) with typical CRUD RESTful APIs.

---

## 📌 Features

- Create a new user (`POST /users`)
- Get a single user by ID (`GET /users/{id}`)
- Get all users (`GET /users`)
- Update user information (`PUT /users/{id}`)
- Delete a user (`DELETE /users/{id}`)

---

## 🗂️ Project Structure

```
src/main/java/com/lujx3419/usersystem
 ├── controller  // Handles HTTP requests
 ├── service     // Business logic layer
 ├── repository  // Simulated database (in-memory)
 ├── model       // Entity classes
 ├── dto         // Request DTOs
```

---

## 🚀 How to Run

1. Make sure you have JDK 17+ and Maven installed.
2. Clone the project:
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
   cd your-repo-name
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   The server will start at `http://localhost:8080`.

---

## 🧩 Learning Points

- Three-layer structure (Controller / Service / Repository)
- Spring Boot RESTful API design
- Path variables, query parameters, JSON request body
- Basic DTO usage
- Simulated database using `Map`

---

## 🔒 Notes

- This is a simple learning version without a real database.
- You can upgrade it later to use **Spring Data JPA + MySQL** or another database.

---

## ✨ Author

**LU Jianxu (Riku Kenkaku)**

---

> Feel free to open an issue or submit a pull request if you have any suggestions!
