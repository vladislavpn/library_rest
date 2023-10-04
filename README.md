# REST API library accounting system
A RESTful API application to manage library accounting system with Spring Boot. 
It features managing clients, books, assigning books to clients and implements some logic regarding calculating expiration date for the assigned books.

## Structure
```
├── main
│   ├── java          
│   │   ├── com.vlad.libraryjparest    // Common response functions
│   │   │    ├── aspect                // Logging with a use of Spring AOP framework 
│   │   │    ├── controller            // Controller classes for the entities 
│   │   │    ├── entity                // Client and Book entities
│   │   │    ├── exception_handling    // Custom exceptions for operations with databases for Clients and Books services
│   │   │    ├── repository            // Repository layer implemented with Spring JPA framework
│   │   │    ├── service               // Service layer
│   │   │    ├── util                  // Utilities like expiration calculator for books                       
│   │   └── LibraryJpaRestApplication  // The entry point
│   └── rescources
│   └── application.properties         // Configuration
└── test
```

## API

#### /clients
* `GET` : Get all clients
* `POST` : Add new client

#### /clients/{id}
* `GET` : Get client
* `PATCH` : Update client
* `DELETE` : Delete client

#### /books
* `GET` : Get all books
* `POST` : Add new book

#### /books/{id}
* `GET` : Get book
* `PATCH` : Update book
* `DELETE` : Delete book

#### /books/{bookId}/assign/{clientId}
* `PATCH` : Assign a book to a client

#### /books/{id}/return
* `PATCH` : Return a book to the library
