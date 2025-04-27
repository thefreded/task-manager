# Task Manager App

A Quarkus-based app to manage tasks, using Keycloak for authentication and role-based authorization.

## Prerequisites
1. **Keycloak Server**: Ensure Keycloak is running.
2. **Keycloak Client**: Set up with these environment variables:
    - `KEYCLOAK_URL`
    - `KEYCLOAK_CLIENT_ID`
    - `KEYCLOAK_CLIENT_SECRET`
3. **Keycloak User**: Create a user with the `admin` or `user` role.

## Configuration

Create a `config.env` file in the root of your project for the following:
- `KEYCLOAK_URL`
- `KEYCLOAK_CLIENT_ID`
- `KEYCLOAK_CLIENT_SECRET`

## Running the App

1. Install `dotenv-cli` globally (this is used to load the env variables):
  

        npm install -g dotenv-cli
   
  

2. Run the app:


    dotenv -e config.env ./mvnw quarkus:dev

The app will start at http://localhost:8081/main/