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
   staticfiles("webroot");
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
<img width="2879" height="1693" alt="image" src="https://github.com/user-attachments/assets/5bb6961b-2d07-4ea7-b5c2-721a55907acb" />

## Unit Tests

<img width="2211" height="615" alt="image" src="https://github.com/user-attachments/assets/d2717ee9-5531-4a91-ba40-c9d90550e5f0" />

## Services defined using the microframework

<img width="1901" height="1146" alt="image" src="https://github.com/user-attachments/assets/a71381f6-cc5f-4e43-b570-aded5c8ed1b6" />



##  ✅ Testing of the developed microframework


### Testing implementation of the static GET method 

From web page:
<img width="2877" height="1710" alt="image" src="https://github.com/user-attachments/assets/5b6dd7ae-7b32-4366-b61b-b1aa694c2f4b" />

Testing directly:
<img width="944" height="337" alt="image" src="https://github.com/user-attachments/assets/c01d796d-d949-4602-99cd-f3fa9a439491" />


### Testing Query Value Extraction Mechanism

From web page:

<img width="2879" height="1707" alt="image" src="https://github.com/user-attachments/assets/850888ae-bf22-46b1-a3dc-f76b340f6372" />

With endpoint:
<img width="856" height="303" alt="image" src="https://github.com/user-attachments/assets/e79ebcbb-70e5-47cc-a0c4-03be4c433729" />


## 🗂️ Static Files with `staticfiles()`

The framework provides the **`staticfiles()`** method to define the folder where static resources (HTML, CSS, JavaScript, images, etc.) are stored.  

When the project is compiled with Maven, these resources are automatically copied into the directory: 
target/classes/webroot


<img width="2879" height="1688" alt="image" src="https://github.com/user-attachments/assets/7e230a69-0a92-481b-a569-65fe62be7db4" />

### Test - POST Method

<img width="2879" height="1701" alt="image" src="https://github.com/user-attachments/assets/e375234b-b740-4bef-8646-0d7c54f527f9" />



## Prototype architecture

![Sin título (1)](https://github.com/user-attachments/assets/bca97b72-bf81-4d51-8e2f-a0f19b84db1b)

<img width="847" height="634" alt="image" src="https://github.com/user-attachments/assets/ca71f216-30f6-4c6a-a1eb-8e523a0baf06" />

The system follows a **lightweight web server and REST application architecture**.  
It is divided into three main parts: the **Client**, the **Web Application (API REST)**, and the **Web Server / Microframework**.

---

### 1. Client
- The client (e.g., **Browser**, **Postman**, or another HTTP client) sends an **HTTP Request** to the application.  
- It later receives an **HTTP Response** back from the server.  

---

### 2. Web Application (API REST)
- Represented by the `WebApplication` class.  
- Defines the **REST endpoints** and the **business logic**.  
- Does **not** directly manage sockets or raw HTTP connections.  
- Relies on the underlying `HttpServer` to handle HTTP communication.  
- Registers services (routes/endpoints) and delegates execution to the server infrastructure.  

---

### 3. Web Server / Microframework
The core of the custom framework, composed of several classes working together:

#### 🔹 `HttpServer`
- Core of the framework; listens for incoming client connections.  
- Routes HTTP requests (`GET`, `POST`, etc.) to the appropriate service.  
- Serves **dynamic content** (via `Service`) and **static files** (from `/target/classes/webroot/...`).  
- Handles request parsing, response building, and error responses (e.g., **404 Not Found**).  

#### 🔹 `HttpRequest`
- Encapsulates HTTP request details: **URI**, **method**, **query parameters**, **headers**, and **body**.  
- Provides helper methods to access and parse request data.  

#### 🔹 `HttpResponse`
- Represents the server’s response back to the client.  
- Includes **status code**, **status message**, **headers**, and **body**.  
- Provides helper methods to set status, add headers, and build the final response.  

#### 🔹 `Service`
- Defines the **business logic contract** for handling requests.  
- Each service implements the method `executeService(req, res)` to process a request and return a response.  
- Allows `HttpServer` to stay generic, delegating execution to services.  

---

✅ **Summary of Relationships**
- **WebApplication** → defines REST endpoints (API).  
- **HttpServer** → acts as a lightweight web framework.  
- **HttpRequest / HttpResponse** → represent the HTTP communication.  
- **Service** → executes the business logic.  
## Author

Sergio Bejarano
