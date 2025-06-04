# Sky Take Out – Restaurant Management System

A comprehensive Spring Boot-based back-end for a restaurant management application (“Sky Take Out”). This multi-module Maven project provides RESTful APIs for managing employees, users, dishes, set meals, orders, shopping carts, and reports, featuring JWT-based authentication, MyBatis integration, Redis caching, WebSocket notifications, and integrations with AliOSS and WeChat Pay.

---

## Table of Contents

1. [Project Structure](#project-structure)
2. [Technologies Used](#technologies-used)
3. [Features](#features)
4. [API Overview](#api-overview)
5. [Directory Structure](#directory-structure)

---

## Project Structure

This is a multi-module Maven project with the following modules:

- **`sky-common/`**
    - Shared utilities, constants, DTOs, and result wrappers.
    - Contains common code for JWT utilities, custom exceptions, page/result wrappers, and auto-fill aspects.

- **`sky-pojo/`**
    - Plain Old Java Objects (entities) and MyBatis mapper interfaces for all database tables (e.g., `Employee`, `User`, `Dish`, `Category`, `Order`, etc.).
    - Entity-to-database mapping definitions.

- **`sky-server/`**
    - The Spring Boot web application.
    - Contains controllers, services, mappers, configuration classes (Redis, WebSocket, upload), and resource files (YAML, SQL mappers, static assets).

---

## Technologies Used

- **Language & Framework**
    - Java 8+
    - Spring Boot 2.7.3
    - Spring MVC (REST)
    - MyBatis (with XML mappers)
    - Spring AOP (for auto-filling fields)
    - WebSocket (real-time order notifications)

- **Build & Dependency Management**
    - Maven (multi-module project)

- **Database & Caching**
    - MySQL (any recent version)
    - Druid (connection pool)
    - Redis (caching and session storage)
    - MyBatis PageHelper (pagination)

- **Security & Auth**
    - JSON Web Tokens (JWT) for authentication & authorization
    - Custom JWT utilities and filters

- **File Storage & Third-Party Integrations**
    - AliOSS (file/image uploads)
    - WeChat Pay (order payments)

- **Utilities & Tools**
    - Lombok (boilerplate code reduction)
    - FastJSON (JSON serialization)
    - Apache Commons Lang
    - Apache POI (Excel exports for reporting)
    - Swagger Annotations (API documentation)

---

## Features

- **User & Employee Management**
    - JWT-based login/logout for employees and users.
    - Role-based access control (admin vs. regular user endpoints).

- **Dish & Category Management** (Admin)
    - CRUD operations on dish categories and individual dishes.
    - Upload dish images to AliOSS.

- **Set Meal Management** (Admin)
    - CRUD operations on “set meals” (combination of multiple dishes).
    - Associate multiple dishes with a set meal via `SetMealDish` mapping.

- **Shopping Cart & Order Processing**
    - Add/remove items to/from shopping cart (for logged-in users).
    - Place orders: generate order records, compute totals, decrease inventory if needed.
    - Order detail viewing (admin and user sides).

- **Address Book** (User)
    - Manage multiple delivery addresses per user.
    - CRUD operations on address entries.

- **Reporting & Analytics**
    - Generate sales reports, popular dishes, revenue by date range (admin).
    - Export report data to Excel (via Apache POI).

- **Real-Time Notifications**
    - WebSocket notifications to the front end when new orders arrive.
    - Order status updates pushed via WebSocket channels.

- **Configuration Profiles**
    - `application.yml` (default settings)
    - `application-dev.yml` (development settings, e.g., local DB/Redis).

---

## API Overview

### Admin API Endpoints

| Method | Path                                 | Description               |
| ------ | ------------------------------------ | ------------------------- |
| GET    | `/admin/order/conditionSearch`       | Search orders             |
| GET    | `/admin/order/details/{id}`          | Get order details         |
| GET    | `/admin/order/statistics`            | Get order statistics      |
| PUT    | `/admin/order/confirm`               | Confirm order             |
| PUT    | `/admin/order/cancel`                | Cancel order              |
| PUT    | `/admin/order/rejection`             | Reject order              |
| PUT    | `/admin/order/delivery/{id}`         | Mark order as delivering  |
| PUT    | `/admin/order/complete/{id}`         | Complete order            |
| GET    | `/admin/order/reminder/{id}`         | Send order reminder       |
| PUT    | `/admin/shop/{status}`               | Set shop open/closed       |
| GET    | `/admin/shop/status`                 | Get current shop status    |
| GET    | `/admin/dish/page`                   | Get paginated list of dishes |
| GET    | `/admin/dish/{id}`                   | Get dish by ID            |
| POST   | `/admin/dish/status/{status}`        | Change dish availability  |
| GET    | `/admin/dish/list`                   | Get dishes by category    |
| POST   | `/admin/common/upload`               | Upload a file             |
| GET    | `/admin/setmeal/page`                | Get paginated list of set meals |
| POST   | `/admin/setmeal/status/{status}`     | Change set meal availability |
| GET    | `/admin/setmeal/{id}`                | Get set meal by ID        |
| GET    | `/admin/category/page`               | Get paginated list of categories |
| POST   | `/admin/category`                    | Create a new category     |
| DELETE | `/admin/category`                    | Delete a category         |
| PUT    | `/admin/category`                    | Update a category         |
| GET    | `/admin/category/list`               | Get all categories        |
| GET    | `/admin/report/dailySales`           | Daily sales report        |
| GET    | `/admin/report/userRegister`         | User registration report  |
| GET    | `/admin/report/salesTop10`           | Top 10 sales report       |
| GET    | `/admin/report/userReport`           | User activity report      |
| GET    | `/admin/employee/page`               | Get paginated list of employees |
| POST   | `/admin/employee`                    | Create a new employee     |
| DELETE | `/admin/employee`                    | Delete an employee        |
| PUT    | `/admin/employee`                    | Update employee info      |
| GET    | `/admin/employee/{id}`               | Get employee by ID        |
| POST   | `/admin/employee/login`              | Employee login            |
| POST   | `/admin/employee/logout`             | Employee logout           |

### User API Endpoints

| Method | Path                                  | Description               |
| ------ | ------------------------------------- | ------------------------- |
| POST   | `/user/user/login`                    | User login                |
| POST   | `/user/user/sendMsg`                  | Send SMS code             |
| POST   | `/user/user/logout`                   | User logout               |
| POST   | `/user/user/add`                      | Create a new user         |
| GET    | `/user/user/info`                     | Get current user info     |
| POST   | `/user/order/submit`                  | Submit an order           |
| PUT    | `/user/order/payment`                 | Pay for an order          |
| GET    | `/user/order/historyOrders`           | Get order history         |
| GET    | `/user/order/orderDetail/{id}`        | Get order details by ID    |
| PUT    | `/user/order/cancel/{id}`             | Cancel an order by ID     |
| POST   | `/user/order/repetition/{id}`         | Reorder by ID             |
| GET    | `/user/shop/status`                   | Get shop status           |
| POST   | `/user/shoppingCart/add`              | Add item to shopping cart |
| GET    | `/user/shoppingCart/list`             | List shopping cart items  |
| DELETE | `/user/shoppingCart/clean`            | Clear shopping cart       |
| POST   | `/user/shoppingCart/sub`              | Remove item from cart     |
| GET    | `/user/dish/list`                     | Get available dishes      |
| GET    | `/user/dish/{id}`                     | Get dish by ID            |
| GET    | `/user/category/list`                 | Get available categories  |
| GET    | `/user/setmeal/list`                  | Get available set meals   |
| GET    | `/user/setmeal/dish/{id}`             | Get dishes in a set meal by ID |
| GET    | `/user/addressBook/list`              | Get address book entries  |
| GET    | `/user/addressBook/{id}`              | Get address by ID         |
| PUT    | `/user/addressBook/default`           | Set default address       |
| GET    | `/user/addressBook/default`           | Get default address       |
| POST   | `/user/addressBook`                   | Create a new address      |
| PUT    | `/user/addressBook`                   | Update an address         |
| DELETE | `/user/addressBook`                   | Delete an address         |

> **Note:**
> - Replace path parameters (e.g., `{id}`, `{status}`) with actual values when making requests.
> - All admin endpoints require a valid JWT token in the `Authorization` header; user endpoints require a valid user session.
> - File upload endpoints (such as `/admin/common/upload`) expect `multipart/form-data` requests.
> - Pagination endpoints accept query parameters like `page`, `pageSize`, and any search-specific filters as defined in their DTOs.
> - For order-related user endpoints, replace `{id}` with the actual order ID.
> - For status-changing endpoints (e.g., `/admin/dish/status/{status}`), use `0` for “off sale” or `1` for “on sale” as appropriate.  

---

## Directory Structure

```text
sky-take-out/
├── pom.xml
├── .gitignore
├── sky-common/
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/
│               └── com/
│                   └── sky/
│                       ├── context/
│                       │   └── BaseContext.java
│                       ├── enumeration/
│                       │   └── OperationType.java
│                       ├── interceptor/
│                       │   ├── JwtTokenAdminInterceptor.java
│                       │   └── JwtTokenUserInterceptor.java
│                       ├── result/
│                       │   ├── PageResult.java
│                       │   └── Result.java
│                       └── utils/
│                           ├── PasswordEncoderUtil.java
│                           ├── RedisTemplateUtil.java
│                           └── ... (other utility classes)
├── sky-pojo/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/
│       │   │       └── sky/
│       │   │           └── pojo/
│       │   │               ├── AddressBook.java
│       │   │               ├── Category.java
│       │   │               ├── Dish.java
│       │   │               ├── Employee.java
│       │   │               ├── Order.java
│       │   │               ├── OrderDetail.java
│       │   │               ├── SetMeal.java
│       │   │               ├── SetMealDish.java
│       │   │               ├── ShoppingCart.java
│       │   │               └── User.java
│       │   └── resources/
│       │       └── application.properties
│       └── test/
│           └── java/
│               └── (unit tests, if any)
├── sky-server/
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/
│           │       └── sky/
│           │           ├── controller/
│           │           │   ├── Admin/
│           │           │   │   ├── CategoryController.java
│           │           │   │   ├── DishController.java
│           │           │   │   ├── EmployeeController.java
│           │           │   │   ├── OrderController.java
│           │           │   │   ├── ReportController.java
│           │           │   │   ├── SetMealController.java
│           │           │   │   └── UserController.java
│           │           │   └── User/
│           │           │       ├── AddressBookController.java
│           │           │       ├── DishController.java
│           │           │       ├── OrderController.java
│           │           │       ├── ShoppingCartController.java
│           │           │       └── UserController.java
│           │           ├── filter/
│           │           │   ├── JwtAuthenticationFilter.java
│           │           │   └── CorsFilter.java
│           │           ├── mapper/
│           │           │   ├── CategoryMapper.java
│           │           │   ├── DishMapper.java
│           │           │   ├── EmployeeMapper.java
│           │           │   ├── OrderDetailMapper.java
│           │           │   ├── OrderMapper.java
│           │           │   ├── SetMealDishMapper.java
│           │           │   ├── SetMealMapper.java
│           │           │   ├── ShoppingCartMapper.java
│           │           │   └── UserMapper.java
│           │           ├── service/
│           │           │   ├── CategoryService.java
│           │           │   ├── DishService.java
│           │           │   ├── EmployeeService.java
│           │           │   ├── OrderService.java
│           │           │   ├── ReportService.java
│           │           │   ├── SetMealService.java
│           │           │   ├── ShoppingCartService.java
│           │           │   └── UserService.java
│           │           ├── service/impl/
│           │           │   ├── CategoryServiceImpl.java
│           │           │   ├── DishServiceImpl.java
│           │           │   ├── EmployeeServiceImpl.java
│           │           │   ├── OrderServiceImpl.java
│           │           │   ├── ReportServiceImpl.java
│           │           │   ├── SetMealServiceImpl.java
│           │           │   ├── ShoppingCartServiceImpl.java
│           │           │   └── UserServiceImpl.java
│           │           ├── utils/
│           │           │   ├── JwtTokenUtil.java
│           │           │   └── MailUtil.java
│           │           └── SkyTakeOutApplication.java
│           └── resources/
│               ├── application-dev.properties
│               ├── application-prod.properties
│               ├── logback.xml
│               ├── mapper/
│               │   ├── CategoryMapper.xml
│               │   ├── DishMapper.xml
│               │   ├── EmployeeMapper.xml
│               │   ├── OrderDetailMapper.xml
│               │   ├── OrderMapper.xml
│               │   ├── SetMealDishMapper.xml
│               │   ├── SetMealMapper.xml
│               │   ├── ShoppingCartMapper.xml
│               │   └── UserMapper.xml
│               └── static/
│                   └── (static resources, if any)
├── .git/
└── .idea/

