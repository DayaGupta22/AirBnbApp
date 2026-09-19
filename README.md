# 🏠 Airbnb Backend

A backend application inspired by Airbnb, built using **Java and Spring Boot** to provide a robust foundation for a property rental and hotel booking platform.

The application provides RESTful APIs for managing users, properties, rooms, inventory, bookings, authentication, payments, and other core rental-platform functionality.

---

## 🚀 Tech Stack

| Technology | Purpose |
|------------|---------|
| ☕ Java | Backend development |
| 🌱 Spring Boot | Application framework |
| 🔐 Spring Security | Authentication & authorization |
| 🪪 JWT | Stateless authentication |
| 🗄️ Spring Data JPA | Database operations |
| ⚙️ Hibernate | ORM |
| 🐘 PostgreSQL | Relational database |
| 💳 Stripe | Payment processing |
| 🔄 REST APIs | Client-server communication |
| 🔀 ModelMapper | DTO ↔ Entity mapping |
| 📦 Maven | Dependency management |
| 🧪 Postman | API testing |

---

## ✨ Key Features

| Feature | Description |
|---------|-------------|
| 🔐 Authentication | User registration and login using JWT |
| 👤 Authorization | Role-based access control using Spring Security |
| 🏠 Property Management | Create, update, delete and manage properties |
| 🛏️ Room Management | Manage rooms and room details |
| 📦 Inventory Management | Track room availability and inventory |
| 🔎 Search | Search and filter properties |
| 📅 Booking | Create and manage room reservations |
| 💳 Payments | Online payment integration using Stripe |
| 🗄️ Database | PostgreSQL with JPA/Hibernate |
| ⚠️ Exception Handling | Centralized global exception handling |
| ✅ Validation | Request and data validation |
| 📄 Pagination | Pagination and sorting |
| 🔄 DTO Mapping | ModelMapper for Entity/DTO conversion |

---

# 🏗️ Application Architecture

The project follows a **layered architecture** to separate responsibilities and maintain clean, scalable code.

```text
                    ┌─────────────────┐
                    │     Client      │
                    │  Web / Mobile   │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   Controllers   │
                    │    REST APIs    │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │    Services     │
                    │ Business Logic  │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   Repository    │
                    │  Spring Data   │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   PostgreSQL    │
                    │    Database     │
                    └─────────────────┘
```

### Main Layers

- **Controller** → Handles HTTP requests and responses
- **Service** → Contains application and business logic
- **Repository** → Handles database operations
- **Entity** → Represents database tables
- **DTO** → Handles API request and response data
- **Mapper** → Converts Entity ↔ DTO
- **Security** → Handles authentication and authorization
- **Exception Handler** → Provides centralized error handling

---

# 📊 Data Flow Diagram (DFD)

The following DFD represents the major data flow between users, the Spring Boot backend, PostgreSQL database, and Stripe payment gateway.

```text
                         ┌──────────────────┐
                         │       USER       │
                         └────────┬─────────┘
                                  │
                    Register / Login / Search
                                  │
                                  ▼
                     ┌────────────────────────┐
                     │    Spring Boot API     │
                     │      Controllers       │
                     └────────────┬───────────┘
                                  │
                                  ▼
                     ┌────────────────────────┐
                     │     Business Logic     │
                     │        Services        │
                     └────────────┬───────────┘
                                  │
             ┌────────────────────┼────────────────────┐
             │                    │                    │
             ▼                    ▼                    ▼
       ┌────────────┐      ┌─────────────┐      ┌────────────┐
       │    User    │      │   Booking   │      │   Hotel    │
       │   Service  │      │   Service   │      │   Service  │
       └──────┬─────┘      └──────┬──────┘      └──────┬─────┘
              │                   │                    │
              └───────────────────┼────────────────────┘
                                  │
                                  ▼
                       ┌────────────────────┐
                       │     PostgreSQL     │
                       │      Database      │
                       └──────────┬─────────┘
                                  │
                           Booking Data
                                  │
                                  ▼
                       ┌────────────────────┐
                       │  Stripe Payment   │
                       │      Gateway      │
                       └──────────┬─────────┘
                                  │
                           Payment Status
                                  │
                                  ▼
                              ┌───────┐
                              │ USER  │
                              └───────┘
```

---

## 🔄 Main Data Flow

### 1. User → API

The user can:

- Register
- Login
- Search properties
- View rooms
- Check availability
- Create bookings
- Make payments

### 2. API → Services

Controllers receive HTTP requests and forward them to the appropriate service layer.

```text
HTTP Request
     ↓
Controller
     ↓
Service
```

### 3. Services → PostgreSQL

Services interact with repositories to store and retrieve:

- Users
- Hotels
- Rooms
- Inventory
- Bookings
- Payments

```text
Service
   ↓
Repository
   ↓
PostgreSQL
```

### 4. Booking → Stripe

When a user makes a booking requiring payment:

```text
User
 ↓
Booking Request
 ↓
Booking Service
 ↓
Payment Service
 ↓
Stripe
```

### 5. Stripe → Backend

Stripe processes the payment and returns the payment status.

```text
Stripe
   ↓
Payment Status
   ↓
Backend
```

### 6. Backend → User

The backend returns the booking/payment result through the REST API.

```text
Backend
   ↓
REST Response
   ↓
User
```

---

# 🔐 Authentication Workflow

The application uses **Spring Security and JWT** for secure authentication.

```text
                    User
                     │
                     ▼
              Register / Login
                     │
                     ▼
             Authentication API
                     │
                     ▼
              Spring Security
                     │
                     ▼
             Validate Credentials
                     │
                     ▼
                 JWT Token
                     │
                     ▼
                   Client
                     │
                     │ Authorization:
                     │ Bearer <JWT>
                     ▼
                JWT Filter
                     │
                     ▼
              Validate Token
                     │
                     ▼
             Authorize Request
                     │
                     ▼
                Controller
```

### JWT Request Example

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 🏠 Property & Room Workflow

Hosts/Admins can manage properties and rooms.

```text
Admin / Host
      │
      ▼
Create Property
      │
      ▼
Add Rooms
      │
      ▼
Configure Room Details
      │
      ▼
Initialize Inventory
      │
      ▼
Property Available
      │
      ▼
User Search
```

---

# 🔎 Hotel Search Workflow

```text
User
 │
 ▼
Search Request
 │
 ▼
Search Controller
 │
 ▼
Search Service
 │
 ▼
Repository
 │
 ▼
JPA Query
 │
 ▼
PostgreSQL
 │
 ▼
Available Properties
 │
 ▼
DTO Mapping
 │
 ▼
API Response
 │
 ▼
User
```

The search functionality can use filters such as:

- Location
- Date
- Room availability
- Price
- Property information

---

# 📦 Inventory & Availability Workflow

Inventory is used to determine whether rooms are available for a particular period.

```text
User Search
     │
     ▼
Check Dates
     │
     ▼
Inventory Service
     │
     ▼
Check Room Availability
     │
     ▼
PostgreSQL
     │
     ▼
Available Rooms
     │
     ▼
Booking
```

---

# 📅 Booking Workflow

```text
User
 │
 ▼
Search Property
 │
 ▼
Select Room
 │
 ▼
Check Availability
 │
 ▼
Create Booking
 │
 ▼
Calculate Amount
 │
 ▼
Payment
 │
 ▼
Payment Successful
 │
 ▼
Confirm Booking
 │
 ▼
Update Inventory
 │
 ▼
Booking Confirmation
```

---

# 💳 Stripe Payment Workflow

The project integrates **Stripe** for online payment processing.

```text
                     USER
                       │
                       ▼
                  Select Room
                       │
                       ▼
                 Create Booking
                       │
                       ▼
                Calculate Amount
                       │
                       ▼
                  Payment API
                       │
                       ▼
                Stripe Gateway
                       │
                ┌──────┴──────┐
                │             │
                ▼             ▼
             SUCCESS        FAILURE
                │             │
                ▼             ▼
        Confirm Booking   Payment Failed
                │
                ▼
        Update Booking Status
                │
                ▼
                USER
```

### Payment Flow

1. User selects a room.
2. Backend calculates the booking amount.
3. Payment request is created.
4. Stripe processes the payment.
5. Payment status is received.
6. Successful payment leads to booking confirmation.
7. Booking/inventory status is updated.

---

# 🗃️ Core Domain Model

The application contains several interconnected entities.

```text
                         ┌──────────────┐
                         │     User     │
                         └──────┬───────┘
                                │
                                │
                         ┌──────▼───────┐
                         │   Booking    │
                         └──────┬───────┘
                                │
                         ┌──────▼───────┐
                         │   Payment    │
                         └──────────────┘


                         ┌──────────────┐
                         │    Hotel     │
                         └──────┬───────┘
                                │
                         ┌──────▼───────┐
                         │     Room     │
                         └──────┬───────┘
                                │
                         ┌──────▼───────┐
                         │  Inventory   │
                         └──────────────┘
```

### Core Entities

| Entity | Responsibility |
|--------|----------------|
| 👤 User | User account and authentication |
| 🏠 Hotel | Property/listing information |
| 🛏️ Room | Room details and pricing |
| 📦 Inventory | Room availability |
| 📅 Booking | Reservation information |
| 💳 Payment | Payment information |
| 🔑 Role | User authorization |

---

# 🔄 Entity Relationships

The major relationships can be represented as:

```text
User
 │
 ├──────────────► Booking
 │
 └──────────────► Role

Hotel
 │
 ├──────────────► Room
 │                    │
 │                    └──────────────► Inventory
 │
 └──────────────► Booking

Booking
 │
 └──────────────► Payment
```

---

# 📂 Project Structure

```text
src/
└── main/
    ├── java/
    │   └── com.example.airbnb/
    │       │
    │       ├── controllers/
    │       │
    │       ├── services/
    │       │
    │       ├── repositories/
    │       │
    │       ├── entities/
    │       │
    │       ├── dto/
    │       │
    │       ├── security/
    │       │
    │       ├── exceptions/
    │       │
    │       ├── mappers/
    │       │
    │       └── config/
    │       │
    │       └── AirbnbApplication.java
    │
    └── resources/
        │
        ├── application.properties
        └── ...
```

---

# 🔒 Security

The application uses **Spring Security + JWT** to secure REST APIs.

### Security Features

- User authentication
- JWT token generation
- JWT token validation
- Password protection
- Role-based authorization
- Protected REST endpoints
- Stateless authentication

---

# 🗄️ Database

The application uses **PostgreSQL** with **Spring Data JPA and Hibernate**.

### Database Responsibilities

- Store users and roles
- Store property information
- Store room information
- Manage inventory
- Store bookings
- Store payment information

JPA/Hibernate is used to manage entity relationships and database operations.

---

# 🔄 DTO & ModelMapper

The project uses **DTOs (Data Transfer Objects)** to separate API models from database entities.

```text
Client
  │
  ▼
Request DTO
  │
  ▼
ModelMapper
  │
  ▼
Entity
  │
  ▼
Repository
  │
  ▼
PostgreSQL
```

For responses:

```text
PostgreSQL
    │
    ▼
 Entity
    │
    ▼
ModelMapper
    │
    ▼
Response DTO
    │
    ▼
 Client
```

This helps keep the API layer independent from the persistence layer.

---

# ⚠️ Exception Handling

The project uses centralized exception handling to provide consistent API responses.

```text
Controller
    │
    ▼
Service
    │
    ▼
Exception
    │
    ▼
Global Exception Handler
    │
    ▼
Standard API Error Response
```

Example response:

```json
{
  "status": 404,
  "message": "Hotel not found",
  "timestamp": "2026-09-19T10:30:00"
}
```

---

# ✅ Validation

Request validation is applied to API requests to prevent invalid data from entering the application.

Examples include:

- Required fields
- Valid email addresses
- Valid booking dates
- Valid room information
- Valid user information

---

# 📄 Pagination & Sorting

Pagination and sorting are used for APIs that may return large amounts of data.

Example:

```http
GET /hotels?page=0&size=10&sort=name,asc
```

This helps reduce unnecessary database and network load.

---

# 🧪 API Testing

REST APIs are tested using **Postman**.

### Main API Modules

```text
/auth
/users
/hotels
/rooms
/inventory
/bookings
/payments
```

Example:

```http
POST /auth/login
GET  /hotels
GET  /hotels/{id}
POST /bookings
GET  /bookings/{id}
POST /payments
```

---

# 🎯 Project Goal

The goal of this project is to design and develop a **real-world, scalable backend** similar to an Airbnb-style property rental and hotel booking platform.

The project focuses on:

- Clean layered architecture
- Secure REST API development
- JWT authentication
- Role-based authorization
- Relational database design
- Hotel and room management
- Inventory and availability management
- Booking and reservation workflows
- Stripe payment integration
- DTO-based API architecture
- Exception handling
- Request validation
- Pagination and sorting

---

# 🚧 Future Improvements

Possible future enhancements include:

- ⭐ Review and rating system
- ❤️ Wishlist functionality
- 📧 Email notifications
- 🔔 Booking notifications
- 📍 Location-based search
- 🤖 AI-based property recommendations
- 📊 Admin dashboard and analytics
- ⚡ Redis caching
- 🐳 Docker containerization
- ☁️ Cloud deployment
- 📈 Advanced monitoring using Spring Boot Actuator

---

# 📌 Project Status

🚧 **Under Development**

New features, optimizations, and improvements are being added continuously.

---

# 👨‍💻 Author

**Dayanand Kumar Gupta**

Java | Spring Boot | Backend Development | Full Stack Development

---
