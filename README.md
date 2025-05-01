# Task Manager App

A Quarkus-based app to manage tasks, using Keycloak for authentication and role-based authorization.

## Prerequisites
1. **Keycloak Server **: Ensure Keycloak is running.
2. **Keycloak Client**: Set up with these environment variables:
    - `KEYCLOAK_URL`
    - `KEYCLOAK_CLIENT_ID`
    - `KEYCLOAK_CLIENT_SECRET`
3. **Keycloak User**: Create a user with the `admin` or `user` role.
4. **Database Setup**: The following environment variables are needed:
   - `DB_TYPE`
   - `DB_URL`
   - `DB_USER`
   - `DB_PASSWORD`

## Configuration

Create a `.env` file in the root of your project for the following:
- `KEYCLOAK_URL`
- `KEYCLOAK_CLIENT_ID`
- `KEYCLOAK_CLIENT_SECRET`
- `DB_TYPE`
- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

## Running the App

Run the app with the command:


    ./mvnw quarkus:dev

The app will start at http://localhost:8081/main/