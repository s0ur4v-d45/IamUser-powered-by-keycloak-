# User Management

This Spring Boot application includes functionalities for user management leveraging Keycloak's Management APIs.

### How to Run:

In order to run this MicroService application make sure you meet these pre-requisites:

- Java JDK 17 
- Maven 3.2.0+
- Some Java IDE
- Postman (Optional).

Then before running this App, some infrastructure services should be already started. See how to make them work properly in the next section.

## Running Keycloak

In the Development Environment, KeyCloak and its dependencies services can be run with this below provided configs:

- Log in KeyCloak Admin Console:

````
* URL:      http://192.168.0.21:8080
* User:     admin
* Password: admin
````
- Generating Access Tokens With Keycloak's API:

Keycloak provides a REST API for generating and refreshing access tokens. To acquire an access token from Keycloak by sending a POST request to this URL:

`http://localhost:8082/keycloak/token`
The request should have this body in a raw Json format:
````
{
"username": "um1",
"password": "um1",
"realmName": "UM"
}
````
In response, we'll get an access_token and a refresh_token.
The access token should be used in every request to a Keycloak-protected resources by simply placing it in the Authorization header (UserManagement, etc).

## Building and Running this App

After running KeyCloak, import the project in your preferred IDE and run it as "Spring Boot Application".
Or make sure you have your JAVA_HOME and Maven setup and working properly.

This application should start on [http://localhost:8082](https://localhost:8082). All configuration settings are in: `/src/main/resources/application.yml`.
