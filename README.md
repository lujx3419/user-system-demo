# User Management System

A comprehensive **User Management System** built with **Spring Boot + JPA + MySQL + JWT + Spring Security**.  
It demonstrates advanced features including **JWT authentication**, **role-based authorization**, **CRUD operations**, **pagination**, **validation**, **DTO mapping**, and **exception handling**.

---

## 🚀 Features

### ✅ Authentication & Authorization
- **JWT Token Authentication** with 7-day validity
- **Role-based Access Control** (USER/ADMIN roles)
- **Secure Password Encryption** using BCrypt
- **Token Refresh** functionality
- **Permission-based API Access**

### ✅ User Management
- **User Registration** (regular users)
- **Admin Registration** (with admin code validation)
- **User Login** with JWT token response
- **Password Change** functionality
- **User Profile Management**

### ✅ API Features
- **RESTful API Design**
- **Request/Response Validation**
- **Global Exception Handling**
- **Swagger/OpenAPI Documentation**
- **Pagination Support**

---

## 🛠️ Tech Stack

- **Spring Boot 3.5.3**
- **Spring Security** (JWT authentication)
- **Spring Data JPA**
- **MySQL** (recommended to run with Docker)
- **JWT** (JSON Web Tokens)
- **MapStruct** (for DTO ↔ Entity mapping)
- **Lombok**
- **Jakarta Bean Validation**
- **Swagger/OpenAPI**

---

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/lujx3419/user-system-demo.git
cd user-system-demo
```

### 2. Start MySQL with Docker

```bash
docker run --name mysql8-demo \
  -e MYSQL_ROOT_PASSWORD=123456 \
  -e MYSQL_DATABASE=user_system_demo \
  -p 3306:3306 \
  -d mysql:8.0
```

> ⏳ Wait until the MySQL container is ready!

### 3. Configure Database Connection

Check your `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_system_demo
spring.datasource.username=root
spring.datasource.password=123456
```

### 4. Compile and Start

```bash
./mvnw clean compile
./mvnw spring-boot:run
```

Or run `UsersystemApplication` directly in your IDE.

### 5. Access API Documentation

Open your browser and visit: `http://localhost:8080/swagger-ui.html`

---

## 📌 API Endpoints

### 🔓 Public Endpoints (No Authentication Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/users/register` | Register a new user |
| POST | `/users/register/admin` | Register an admin (requires admin code) |
| POST | `/users/login` | User login |

### 🔒 Protected Endpoints (Require JWT Authentication)

| Method | Endpoint | Description | Permission |
|--------|----------|-------------|------------|
| GET | `/users/me` | Get current user info | All users |
| GET | `/users/{id}` | Get user by ID | Own data or ADMIN |
| PUT | `/users/{id}` | Update user | Own data or ADMIN |
| DELETE | `/users/{id}` | Delete user | Own data or ADMIN |
| PUT | `/users/{id}/password` | Change password | Own data or ADMIN |
| POST | `/users/refresh-token` | Refresh JWT token | All users |
| GET | `/users` | Get all users | ADMIN only |
| GET | `/users/page` | Get users with pagination | ADMIN only |

### 🔑 Authentication

All protected endpoints require JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

---

## 👥 User Roles & Permissions

### Regular User (USER)
- ✅ View own profile
- ✅ Update own information
- ✅ Change own password
- ✅ Delete own account
- ❌ View other users' data
- ❌ View all users list

### Administrator (ADMIN)
- ✅ All regular user permissions
- ✅ View all users' data
- ✅ Update any user's information
- ✅ Delete any user account
- ✅ View users list with pagination

---

## 🗂️ Project Structure

```
com.lujx3419.usersystem
 ├── common/                    # Common utilities and exceptions
 │   ├── ApiResponse.java      # Standard API response wrapper
 │   ├── BusinessException.java # Custom business exceptions
 │   ├── GlobalExceptionHandler.java # Global exception handler
 │   ├── JwtUtil.java          # JWT token utilities
 │   └── SecurityUtil.java     # Security and permission utilities
 │
 ├── config/                   # Configuration classes
 │   ├── SecurityConfig.java   # BCrypt password encoder
 │   ├── WebSecurityConfig.java # Spring Security configuration
 │   ├── JwtAuthenticationFilter.java # JWT authentication filter
 │   └── OpenApiConfig.java    # Swagger/OpenAPI configuration
 │
 ├── controller/               # REST controllers
 │   └── UserController.java   # User management endpoints
 │
 ├── dto/                      # Data Transfer Objects
 │   ├── request/              # Request DTOs
 │   │   ├── UserRequest.java
 │   │   ├── UserLoginRequest.java
 │   │   ├── UserRegisterRequest.java
 │   │   ├── AdminRegisterRequest.java
 │   │   └── ChangePasswordRequest.java
 │   └── response/             # Response DTOs
 │       ├── UserResponse.java
 │       └── LoginResponse.java
 │
 ├── mapper/                   # MapStruct mappers
 │   └── UserMapper.java       # DTO ↔ Entity mapping
 │
 ├── model/                    # JPA entities
 │   └── User.java             # User entity with role field
 │
 ├── repository/               # Data access layer
 │   └── UserRepository.java   # User repository
 │
 ├── service/                  # Business logic layer
 │   ├── UserService.java      # Service interface
 │   ├── UserServiceImpl.java  # Service implementation
 │   └── CustomUserDetailsService.java # Spring Security user details
 │
 └── UsersystemApplication.java # Spring Boot main class
```

---

## 🔧 Configuration

### JWT Configuration
- **Token Validity**: 7 days
- **Algorithm**: HS256
- **Secret Key**: Configured in `JwtUtil.java`

### Admin Registration
- **Admin Code**: `ADMIN123` (configurable in `UserServiceImpl.java`)

### Database Configuration
- **Auto DDL**: `update` (tables created automatically)
- **SQL Logging**: Enabled for development

---

## 🧪 Testing

### Using Postman

1. **Register a user**:
   ```json
   POST /users/register
   {
     "name": "testuser",
     "password": "123456"
   }
   ```

2. **Login to get JWT token**:
   ```json
   POST /users/login
   {
     "name": "testuser",
     "password": "123456"
   }
   ```

3. **Use token for protected endpoints**:
   ```
   Authorization: Bearer <token-from-login>
   ```

### Using Swagger UI
Visit `http://localhost:8080/swagger-ui.html` for interactive API documentation.

---

## 🔒 Security Features

- **JWT Token Authentication**
- **BCrypt Password Encryption**
- **Role-based Access Control**
- **CSRF Protection** (disabled for API testing)
- **Input Validation**
- **Global Exception Handling**

---

## 🚧 Future Enhancements

- [ ] Add user email and phone number fields
- [ ] Implement password strength validation
- [ ] Add user avatar upload functionality
- [ ] Implement user status management (active/inactive)
- [ ] Add audit logging
- [ ] Implement rate limiting
- [ ] Add unit and integration tests
- [ ] Implement Redis caching
- [ ] Add user activity tracking

---

## 📃 License

This project is for learning and demonstration purposes only.  
Feel free to fork, improve, and adapt!

---

⭐️ If you find this helpful, please star the repo!
