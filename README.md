# Library Management System

A Spring Boot application designed to manage library operations, including managing books, patrons, and borrowing records.

## Features

- Manage books, including adding, updating, and deleting book records.
- Manage patrons, including registering new patrons and updating their information.
- Handle borrowing records, including recording the borrowing and returning of books.
- Centralized exception handling and transactional operations.
- RESTful API endpoints for interacting with the library management system.

## Future Work

- **Batch Insert for Borrow Requests**: Implement batch processing for handling multiple borrow requests efficiently.
- **Utilizing Response Classes**: Transition to using response classes instead of `@JsonIgnore` for excluding certain fields in API responses. This approach is considered more flexible and maintainable.
- **Using DTO Mapper**: Integrate a DTO mapper (e.g., MapStruct) for better separation between entities and DTOs, improving code maintainability and clarity.
- **Security**: Implement basic authentication or JWT-based authorization to protect the API endpoints.
- **Aspects**: Implement logging using Aspect-Oriented Programming (AOP) to log method calls, exceptions, and performance metrics of certain operations like book additions, updates, and patron transactions.
- **Caching**: Utilize Spring's caching mechanisms to cache frequently accessed data, such as book details or patron information, to improve system performance.


## Technologies Used

- **Java 22**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database (for testing)**
- **MySQL (for production)**
- **Maven**
- **JUnit 5** (for unit and integration tests)
- **Lombok** (to reduce boilerplate code)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 22.
- Maven 3.6.3 or later.
- MySQL Server (for production environment).
- H2 Database (for testing environment).

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/eslamhawas/Library-Management-System.git
cd Library-Management-System
```

### Build the Project

```bash
mvn clean install
```

### Setting Up the Database

- **For Development/Production**: Update the application.properties file to configure your MySQL database.
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
- **For Testing**: H2 is already configured.

### Running the Application 

```bash
mvn spring-boot:run
```
The application will start on http://localhost:8080.

## API Endpoints

### Book Management Endpoints

- **GET /api/books**: Retrieve a list of all books.
- **GET /api/books/{id}**: Retrieve details of a specific book by ID.
- **POST /api/books**: Add a new book to the library.
- **PUT /api/books/{id}**: Update an existing book's information.
- **DELETE /api/books/{id}**: Remove a book from the library.

### Patron Management Endpoints

- **GET /api/patrons**: Retrieve a list of all patrons.
- **GET /api/patrons/{id}**: Retrieve details of a specific patron by ID.
- **POST /api/patrons**: Add a new patron to the system.
- **PUT /api/patrons/{id}**: Update an existing patron's information.
- **DELETE /api/patrons/{id}**: Remove a patron from the system.

### Borrowing Endpoints

- **POST /api/borrow/{bookId}/patron/{patronId}**: Allow a patron to borrow a book.
- **PUT /api/return/{bookId}/patron/{patronId}**: Record the return of a borrowed book by a patron.

## Testing

Unit and integration tests are provided using JUnit 5. To run the tests, use the following command:

```bash
mvn test
```


  

