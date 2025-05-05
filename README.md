Spring Students

Spring Students is a backend application on Spring Boot for managing student data. The project implements basic CRUD operations, JWT authentication, OAuth2 login via Google, and integration with MySQL databases.

Main features

Registration and login via JWT

Login via Google OAuth2

Student management (add, update, delete, view)

Working with User, Student entities.

Technologies

Java 17

Spring Boot

Spring Security (JWT and OAuth2)

Spring Data JPA

MY SQL

Lombok

How to run Cloud

1. Clone the repository

2. Specify your Google OAuth2 data in application.properties

How to run Locally

1. create your secret key and insert it into application.properties

2. register in postman, use this data for authorization

3. get access to your data

Notes

After logging in via Google, the user will be automatically created in the database.

The JWT token is returned upon successful authorization and is used to access protected resources.
