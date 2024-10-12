# MyTravel API

>This is the backend for a travel diary application, where users can post places they have visited. The implementation contains robust code structure, which follows the latest convention. It effectively separates concerns, making the app more maintainable and scalable. <br>
> * **entity**: For JPA entities.
> * **dto**: For Data Transfer Objects, used to encapsulate data sent to and from clients.
> * **service**: For service classes, where business logic resides.
> * **controller**: For REST controllers, handling API requests and responses.
> * **repository**: For Spring Data JPA repositories.
> * **util**: For utility classes.
> * **filter**: For filters, which intercept requests.
> * **config**: For configuration classes, such as security and filter configurations. <br><br>

>This API was written in Java Spring Boot, which further utilises:
> * Spring Data JPA / Hibernate for entity management and access to a PostgreSQL database
> * Auth0 for generating access tokens
> * JWT for client-side authentication
