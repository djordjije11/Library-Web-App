# Library-Web-App

The application enables the management of book rentals within the library subsystem. The user (library staff) primarily logs into the application using their credentials (username and password) to gain access to the software's functionalities. Once logged in, the user can create new book entries in the system, search for existing ones, modify their information, and delete them from the system. Each book has its copies located in different library buildings, so records are also kept for them, and all the aforementioned operations apply to them as well. Additionally, library members can be created, searched for, modified, and deleted. By creating lending confirmations, employees can record which book copies were lent by which library member in which library building and when. Modifying lending confirmations allows for recording the return of lent books. Additionally, library staff members have the ability to view the lending history of each library member individually.

## Conceptual model
![Conceptual model](https://github.com/djordjije11/Library-Web-App/blob/main/images/library-uml-model.png?raw=true "Conceptual model")

## Implementation

The developed software represents a web application, where the server-side program is built using the Java programming language with the Spring Boot framework, and the client-side program is built using the JavaScript library ReactJS with the use of the strictly typed programming language TypeScript. The server-side consists of the application's programming interface (API), while the client-side of the application, providing the user interface, is a single-page application (SPA) that runs in a web browser. For persistent data storage, the application relies on the MySQL database management system. For styling HTML elements, Tailwind CSS was used, a framework that simplifies the use of CSS.

## Software Architecture

The software system is built on a three-level architecture, comprising the following components:

- User interface
- Application logic
  - Application logic controller
  - Business logic (structure and behavior)
  - Database broker
- Database

![Software architecture](https://github.com/djordjije11/Library-Web-App/blob/main/images/software%20architecture.png?raw=true "Software architecture")

## User interface

![Login page](https://github.com/djordjije11/Library-Web-App/blob/main/images/login%20ui.png?raw=true "Login page")
![Home page](https://github.com/djordjije11/Library-Web-App/blob/main/images/home%20page%20ui.png?raw=true "Home page")
![Table view](https://github.com/djordjije11/Library-Web-App/blob/main/images/table%20ui.png?raw=true "Table view")

### Detailed documentation in Serbian language
[Link ka dokumentaciji](https://github.com/djordjije11/Library-Web-App/blob/main/dokumentacija/Diplomski%20rad%20%20-%20%C4%90or%C4%91ije%20Radovi%C4%87%202019-0162.pdf)
