# 🛒 E-Commerce Microservices Application

A full-stack, cloud-native **E-Commerce Application** built using **Spring Boot 3**, **Spring Cloud**, **Apache Kafka**, **MongoDB**, **PostgreSQL**.
This system follows a **microservices architecture**, with distributed tracing, service discovery, centralized configuration, and asynchronous communication using Kafka.

![Architecture Diagram](./ecommerce.png)

---

## 📦 Tech Stack

### 🔧 Backend (Microservices)
- **Spring Boot 3**
- **Spring Cloud** (Eureka, Config Server, Gateway)
- **Apache Kafka** (Async messaging)
- **MongoDB** & **PostgreSQL**
- **Spring Data JPA** / **Spring Data MongoDB**
- **Zipkin** (Distributed Tracing)
- **Docker**


### 🔗 Infrastructure
- **Eureka Server** – Service discovery
- **Spring Cloud Config Server** – Centralized configuration
- **API Gateway** – Routing and request forwarding
- **Kafka** – Message broker for async events

---

## 📁 Microservices Overview

### 1. **Customer Service**
- Manages customer profiles.
- Persists data in **MongoDB**.
- API endpoint: `/customers`

### 2. **Product Service**
- Manages product catalog.
- Persists data in **PostgreSQL**.
- API endpoint: `/products`

### 3. **Order Service**
- Manages order placement.
- Interacts with Customer and Product services.
- Sends **Order Confirmation** events to Kafka.
- Persists data in **PostgreSQL**.
- API endpoint: `/orders`

### 4. **Payment Service**
- Handles payment processing.
- Sends **Payment Confirmation** to Kafka.
- Persists data in **PostgreSQL**.

### 5. **Notification Service**
- Listens to Kafka for Order and Payment confirmation.
- Sends notification emails to users.
- Stores logs in **MongoDB**.

---

## 🔁 Async Communication

- **Order Service ➝ Kafka ➝ Notification Service**
- **Payment Service ➝ Kafka ➝ Notification Service**

---

## 🔍 Distributed Tracing

- All services are instrumented with **Zipkin** to trace request flow across microservices.

---

## 📡 API Gateway

- Built with **Spring Cloud Gateway**.
- Routes:
  - `/customers/** ➝ Customer Service`
  - `/products/** ➝ Product Service`
  - `/orders/** ➝ Order Service`

---

## ⚙️ Service Discovery

- All services register with **Eureka Server**.
- **API Gateway** uses Eureka to route requests dynamically.

---

## 📃 Centralized Configuration

- Each microservice pulls configuration from **Spring Cloud Config Server**.
- Configs are stored in a Git-backed repo.

---

## 🚀 Getting Started

### Prerequisites
- Docker & Docker Compose
- Java 21
- Maven
- Git

### Clone the Repository
```bash
git clone https://github.com/yourusername/ecommerce-microservices.git
cd ecommerce-microservices
