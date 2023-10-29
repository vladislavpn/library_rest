# REST API Library Management system
The Library Management System is a RESTful Spring Boot application that provides API endpoints for managing books, clients, and their interactions in a library. The application utilizes Spring Security for authentication and authorization, Spring AOP for logging, Spring Data JPA for database operations, and JWT for user authentication. Users with different roles (ADMIN, STAFF, USER) can interact with the system to perform various tasks related to library management.

## Structure
```
├── main
│   ├── java          
│   │   ├── com.vlad.libraryjparest    // Common response functions
│   │   │    ├── aspect                // Logging with use of Spring AOP framework 
│   │   │    ├── controller            // Controller classes for the entities
│   │   │    ├── dto                   // Data transfer objects
│   │   │    ├── entity                // Client and Book entities, Roles and Users entities for security
│   │   │    ├── exception_handling    // Custom exceptions for operations with databases for Clients, Books and Users services 
│   │   │    ├── repository            // Repository layer implemented with Spring JPA framework
│   │   │    ├── security              // Spring Security configuration files
│   │   │    ├── service               // Service layer
│   │   │    ├── util                  // Utilities like expiration calculator for books                        
│   │   └── LibraryJpaRestApplication  // The entry point
│   └── rescources
│   └── application.properties         // Configuration
└── test                               // Tests for service, repositiory layer and util
```

## API Endpoints
## Authentication

* #### /auth/register
* `POST` : Register a new user with roles like ADMIN, STAFF, or USER.

* #### /auth/authenticate
* `POST` : Authenticate an existing user and obtain a JWT for subsequent API requests.

## Clients

#### /clients
* `GET` : Retrieve a list of all clients in the library.
* `POST` : Add a new client to the library.

#### /clients/{id}
* `GET` : Retrieve information about a specific client.
* `PATCH` : Update the information of a client.
* `DELETE` : Remove a client from the library.

## Books

#### /books
* `GET` : Retrieve a list of all books available in the library.
* `POST` : Add a new book to the library's collection.

#### /books/{id}
* `GET` : Retrieve information about a specific book.
* `PATCH` : Update the information of a book.
* `DELETE` : Remove a book from the library's collection.

#### /books/{bookId}/assign/{clientId}
* `PATCH` : Assign a book to a client

#### /books/{id}/return
* `PATCH` : Return a book to the library

## Expiration Calculator
The system calculates whether a client has exceeded the maximum time allowed for a book to be assigned. This logic is envoked everytime when info of a client with one or more acquired books is requested.

## Authentication and Authorization
The application uses JWT (JSON Web Tokens) for user authentication. Users are assigned roles (ADMIN, STAFF, USER) and have access to different endpoints based on their roles.

ADMIN role has access to all endpoints.

STAFF role has access to all endpoints but /auth/register endpoint.

USER role has access only to GET /books endpoint.

## Database
Spring Data JPA is used for interacting with the database, making it easy to manage clients, books, and their associations.

## Getting Started
Clone this repository to your local machine.
Configure the application's database properties in the application.properties file.
Build and run the application.

## Getting Started
For questions or issues, please contact Vlad at vladpiven.mbox@gmail.com.
Thank you for checking out the Library Management System!
