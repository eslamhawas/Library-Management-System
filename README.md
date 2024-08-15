# 📚 Library Management System

A comprehensive Library Management System built with Java, Spring Boot, and MySQL to manage a library's catalog of books, patrons, and borrowing records. It offers a RESTful API that allows CRUD operations on the library's resources.

## 🚀 Features

- **Books Management:** including adding, updating, and deleting book records.
- **Patrons Management:** including registering new patrons and updating their information.
- **Handle borrowing records:** including recording the borrowing and returning of books.
- **Transaction Management:** declarative transaction management using Spring's @Transactional annotation to ensure data integrity during critical operations.
- **Validation and Error Handling:** input validation for API requests (e.g., validating required fields, data formats, etc.).
   Handles exceptions gracefully and return appropriate HTTP status codes and error messages.
- **Caching:** Utilize Spring's caching mechanisms to cache frequently accessed data, such as book details or patron information, to improve system performance.

## 🎯 Future Enhancements

- **Batch Insert for Borrow Requests**: Implement batch processing for handling multiple borrow requests efficiently.
- **Utilizing Response Classes**: Transition to using response classes as controller return types. This approach is considered more flexible and maintainable.
- **Using DTO Mapper**: Integrate a DTO mapper (e.g., MapStruct) for better separation between entities and DTOs, improving code maintainability and clarity.
- **Security**: Implement basic authentication or JWT-based authorization to protect the API endpoints.
- **Aspects**: Implement logging using Aspect-Oriented Programming (AOP) to log method calls, exceptions, and performance metrics of certain operations like book additions, updates, and patron transactions.
- ~~**Caching**: Utilize Spring's caching mechanisms to cache frequently accessed data, such as book details or patron information, to improve system performance.~~ **(Done)**



## 🛠️ Technologies Used

- **Backend:**
    - Java
    - Spring Boot
    - Spring Data JPA
    - Spring Caching
    - Spring Validation
- **Database:**
    - MySQL
    - H2
- **Tools:**
    - Maven
    - Postman (for API testing)

## 📖 Project Structure

```plaintext
📁 src
 ┣ 📂 main
 ┃ ┣ 📂 java
 ┃ ┃ ┣ 📂 cc.maid.lms
 ┃ ┃ ┃ ┣ 📂 controller   # REST Controllers
 ┃ ┃ ┃ ┣ 📂 model        # Entities (Book, Patron, Borrowing Record)
 ┃ ┃ ┃ ┣ 📂 repository   # Repositories (JPA Repositories for database operations)
 ┃ ┃ ┃ ┗ 📂 service      # Services (Business Logic)
 ┃ ┣ 📂 resources
 ┃ ┃ ┣ 📜 application.properties  # Configuration files
 ┃ 📁 test  # Unit Tests
 ┃ ┣ 📂 java
 ┃ ┃  ┣ 📂 repository 
 ┗ ┗  ┗ 📂 service    





```
## 📦 Getting Started

### Prerequisites

* Java 22
* Maven
* MySQL (via XAMPP)
* Postman (for testing API)

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/eslamhawas/Library-Management-System
   cd Library_Management_System

2. **Configure the Database:**

    * Start MySQL via XAMPP.
    * Create a new database called library_management_system.
    * Update `application.properties` with your MySQL credentials:

      ```properties
      
      spring.datasource.url=jdbc:mysql://localhost:3306/library_management_system
      spring.datasource.username=root
      spring.datasource.password=your_password
      ```
3. **Run the Application:**
    ```bash
    mvn spring-boot:run
   ```
4. **Test the API Endpoints:**

   Use Postman to test the API. For example, to add a new book:

   ```http
   POST /api/books
   Content-Type: application/json
   {
    "title": "Book1",
    "author": "Author1",
    "publicationYear": 2024,
    "isbn": "ISBN12345"
   }
   ```

## 🔗 API Endpoints

### Books

- **GET** `/api/books` - List all books
- **POST** `/api/books` - Add a new book
- **GET** `/api/books/{id}` - Get book details by ID
- **PUT** `/api/books/{id}` - Update book details by ID
- **DELETE** `/api/books/{id}` - Delete a book by ID

### Patrons

- **GET** `/api/patrons` - List all patrons
- **POST** `/api/patrons` - Add a new patron
- **GET** `/api/patrons/{id}` - Get patron details by ID
- **PUT** `/api/patrons/{id}` - Update patron details by ID
- **DELETE** `/api/patrons/{id}` - Delete a patron by ID

### Borrowing Records

- **POST** `/api/borrow/{bookId}/patron/{patronId}` - Allow a patron to borrow a book.
- **PUT** `/api/return/{bookId}/patron/{patronId}` - Record the return of a borrowed book by a patron.

## 🧪 Testing

Run unit tests with:

```bash
mvn test
```

