# User System Demo

This is a simple **User Management System** built with **Spring Boot + JPA + MySQL + MapStruct**.  
It demonstrates basic **CRUD**, pagination, validation, DTO mapping, and exception handling.

---

## Tech Stack

- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **MySQL** (recommended to run with Docker)
- **MapStruct** (for DTO ↔ Entity mapping)
- **Lombok**
- **Jakarta Bean Validation**

---

##  How to Run Locally

### Clone the Repository

```bash
git clone https://github.com/lujx3419/user-system-demo.git
cd user-system-demo
```

---

### Start MySQL with Docker

```bash
docker run --name mysql8-demo \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=usersystem \
  -p 3306:3306 \
  -d mysql:8.0
```

> ⏳ Wait until the MySQL container is ready!

---

### Configure Database Connection

Check your \`src/main/resources/application.properties\`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/usersystem
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

---

### Compile and Generate MapStruct Implementation

Run this to generate \`UserMapperImpl\` automatically:

```bash
./mvnw clean compile
```

---

### Start the Application

```bash
./mvnw spring-boot:run
```

Or run \`UsersystemApplication\` directly in your IDE.

---

## 📌 API Endpoints

| Method | Endpoint                      | Description               |
|--------|-------------------------------|---------------------------|
| POST   | `/users`                    | Create a new user         |
| PUT    | `/users/{id}`               | Update an existing user   |
| GET    | `/users/{id}`               | Get user by ID            |
| GET    | `/users`                    | Get all users             |
| GET    | `/users/page?page=0&size=5` | Get users with pagination |
| DELETE | `/users/{id}`               | Delete user by ID         |

All request/response DTOs are defined under the \`dto\` package.

---

## 🗂️ Project Structure

```
com.lujx3419.usersystem
 ├── common/           # Common utils, exceptions, base classes
 │   ├── ApiResponse.java
 │   ├── BusinessException.java
 │   └── GlobalExceptionHandler.java
 │
 ├── config/           # Configuration classes (if needed)
 │   └── MapStructConfig.java (optional)
 │
 ├── controller/       # REST controllers
 │   └── UserController.java
 │
 ├── dto/              # DTOs
 │   ├── UserRequest.java  
 │   └── UserResponse.java
 │
 ├── mapper/           # MapStruct mappers
 │   └── UserMapper.java
 │
 ├── model/            # JPA entities (domain models)
 │   └── User.java
 │
 ├── repository/       # JPA repositories
 │   └── UserRepository.java
 │
 ├── service/          # Service interfaces & implementations
 │   ├── UserService.java
 │   └── UserServiceImpl.java
 │       
 │
 └── UsersystemApplication.java   # Spring Boot main entry point

```

---

## ✅ TODO

- [ ] Add user registration & login logic
- [ ] Integrate Swagger for API docs
- [ ] Add unit & integration tests

---

## 📃 License

This project is for learning and demonstration purposes only.  
Feel free to fork, improve, and adapt!

---

⭐️ If you find this helpful, please star the repo!
