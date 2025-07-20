# Schema architecture #

## Architecture summary ##
This Spring Boot application uses both MVC and REST controllers. Thymeleaf templates are used for the Admin and Doctor dashboards, while REST APIs serve all other modules. The application interacts with two databases—MySQL (for patient, doctor, appointment, and admin data) and MongoDB (for prescriptions). All controllers route requests through a common service layer, which in turn delegates to the appropriate repositories. MySQL uses JPA entities while MongoDB uses document models.

## Data flow and control ##
1. User accesses AdminDashboard or Appointment pages.
2. The action is routed to the appropriate Thymeleaf or REST controller.
3. The controller calls the service layer to apply business logic and validate and query data
4. The service layer communicates with repository layer to access data
5. The repository layer interfaces directly with the underlying database engine.
6. The data retrieved from the database, and returned from repositories, is mapped into Java model classes
7. The controller receive the data from the service layer, eventually converts it to DTOs, and  transfers it either to Thymeleaf engine to return a dynamic HTML page, or directly to client in JSON format.

