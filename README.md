# 🌐 Web Server Framework

A lightweight **Java web framework** designed for serving static files and building RESTful services using lambda functions.  
It extends a simple HTTP server into a fully functional framework for modern web application development.

---

## 📌 Project Statement

**Project:** Development of a web framework for REST services and static file management.

**Objective:**  
This project enhances an existing web server (supporting HTML, JavaScript, CSS, and images) into a complete **web framework**.  
The framework enables developers to:

- Define **REST services** using Java lambda functions.
- Handle **query parameters** in incoming requests.
- Specify the **location of static files** for serving web assets.

---

## 🔑 Scope & Features

1. **Static GET method for REST services**  
   Developers can define REST endpoints with simple lambda expressions:

   ```java
   get("/hello", (req, res) -> "hello world!");

   ```

2. **Query parameter extraction**
   Provides a mechanism to access query parameters directly in REST services.

   ```java
   get("/hello", (req, res) -> "hello " + req.getValues("name"));

   ```

3. **Static file directory specification**
   Allows developers to define where static files (HTML, CSS, JS, images) are located.

   ```java
   staticfiles("webroot/public");
   ```

## Verifying Java and Maven Versions

Before compiling or running this project, make sure that both **Java** and **Maven** are properly installed and configured on the system.

### Check Java version

Run the following command in the terminal:

```bash
java -version
```

Check Maven version:

```bash
mvn -version
```

<img width="2878" height="507" alt="image" src="https://github.com/user-attachments/assets/aa9e3773-707e-4f9b-9924-c60059b555b7" />

## How to Build and Run

1. **Compile and package the project using Maven:**

   ```sh
   mvn clean package
   ```

   <img width="2879" height="1663" alt="image" src="https://github.com/user-attachments/assets/75947074-437a-4984-9a83-a12f7a2bb9f9" />

2. **Run the server:**

   ```sh
   mvn clean compile exec:java -Dexec.mainClass="co.edu.escuelaing.webexample.WebApplication"
   ```

   <img width="2875" height="1004" alt="image" src="https://github.com/user-attachments/assets/41407984-811d-43a0-88fe-0c28abd2d4b5" />

3. **Open browser and go to:**
   ```
   http://localhost:8000/
   ```
   <img width="2879" height="1702" alt="image" src="https://github.com/user-attachments/assets/faf59590-5a0b-4243-a9ec-d8910bd5c6bd" />

## Unit Tests

<img width="2211" height="615" alt="image" src="https://github.com/user-attachments/assets/d2717ee9-5531-4a91-ba40-c9d90550e5f0" />

## Static Files

## Prototype architecture

## Author

Sergio Bejarano
