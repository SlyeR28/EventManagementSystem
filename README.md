# 🎉 Event Management System

> A full-stack, enterprise-grade **Event Management Platform** built with **Spring Boot 3.5**, **MySQL**, **Redis**, **Elasticsearch**, **Cloudinary**, **JWT**, and **Google ZXing**.  
> Designed to simplify event organization, ticketing, and analytics — while ensuring scalability, performance, and a great user experience.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?style=flat-square&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=flat-square&logo=mysql)
![Redis](https://img.shields.io/badge/Redis-Cache-red?style=flat-square&logo=redis)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-Search-yellow?style=flat-square&logo=elasticsearch)
![Kibana](https://img.shields.io/badge/Kibana-Analytics-pink?style=flat-square&logo=kibana)
![Cloudinary](https://img.shields.io/badge/Cloudinary-Images-blue?style=flat-square&logo=cloudinary)

---

## 🚀 Overview

The **Event Management System** is a modern web application that empowers users, organizers, and administrators to manage events, tickets, and live analytics through a secure, distributed, and optimized architecture.

### ✅ Key Highlights
- Developed from scratch adhering to **SOLID principles** and **clean architecture**.
- **JWT-based authentication** with **role-based access control (RBAC)** for user and admin operations.
- **Redis caching** for performance and scalability.
- **Elasticsearch integration** for fast, intelligent event search and analytics.
- **Cloudinary integration** for storing and managing event images efficiently.
- **Google ZXing** for generating secure, scannable **QR tickets**.
- **Dynamic Pricing Engine** allowing flexible pricing based on demand/time.
- **Multi-provider Payment Gateway** with secure webhook handling.
- **Asynchronous Notification System** powered by event-driven architecture and reusable design patterns.

---

## 🧠 System Design (Add Images Here)

> Add your diagrams or screenshots below for better visualization.

- 🏗️ **High-Level Architecture**  
  ![High Level Design]()

- 💳 **Payment Gateway Flow**  
  ![Payment Gateway Design]()

- 📢 **Notification System Architecture**  
  ![Notification Design]()

- 🎟️ **Discount & Coupon Module Design**  
  ![Discount Coupon Design]()

---

## 🧭 Upcoming Enhancements

| Feature | Description |
|----------|-------------|
| 🛡️ **OAuth2 + Keycloak Integration** | Advanced security and centralized identity management. |
| 📍 **Location Service** | Help purchasers navigate to event venues with intelligent routing. |
| 🤖 **AI-Assisted Management** | AI-generated descriptions, discount recommendations, and personalized event promotions. |
| 💬 **Chatbot Support** | Real-time chat between users, organizers, and admins for faster support. |
| 🎥 **WebRTC Integration** | <ul><li>Internal: Real-time group discussions among staff and organizers.</li><li>External: Live streaming for users who purchased tickets but couldn’t attend physically.</li></ul> |
| ⚙️ **Scalability Enhancements** | Modular microservices for distributed scaling. |

---

## 🏗️ Architecture Overview

```mermaid
flowchart TD
    A[Frontend (React/Next.js)] --> B[Spring Boot 3.5 Backend]
    B --> C[(MySQL Database)]
    B --> D[(Redis Cache)]
    B --> E[(Elasticsearch)]
    E --> F[Kibana Dashboard]
    B --> G[(Cloudinary - Image Storage)]
    B --> H[Payment Gateway]
    B --> I[Notification Service]
    B --> J[AI & Chatbot Module]
    B --> K[WebRTC Service]

```
## ⚙️ Tech Stack

| Layer                 | Technology / Tools                                      |
|-----------------------|--------------------------------------------------------|
| 🖥️ Backend            | Spring Boot 3.5                                       |
| 🗄️ Database           | MySQL                                                 |
| 🛠️ Caching            | Redis                                                 |
| 🔍 Search & Analytics | Elasticsearch + Kibana                                 |
| 🌄 Image Storage      | Cloudinary                                            |
| 🔒 Security           | JWT, planned Keycloak (OAuth2)                        |
| 📡 Communication      | WebRTC                                                |
| 🤖 AI / ML            | OpenAI / LLM APIs (planned)                           |
| 🐳 Containerization   | Docker                                                |
| 🛠️ Tools              | IntelliJ IDEA, Postman, Swagger                       |




---
## 💡 Features Summary

### 👤 User
- ✅ **Account Management:** Register, login, and manage profile
- 🔎 **Event Discovery:** View, search, and book events
- 🎫 **Digital Tickets:** Access QR-based tickets for events
- 🎥 **Live Events:** Watch live events via WebRTC (planned)

### 🧑‍💼 Organizer
- 🛠️ **Event Management:** Create, update, and manage events
- 💰 **Dynamic Pricing:** Adjust ticket prices based on demand or time
- 📊 **Analytics:** Analyze sales, attendance, and audience insights
- 🤖 **AI Assistance:** Generate event descriptions automatically (planned)

### 🧠 Admin
- ✔️ **Moderation:** Approve or reject events
- 🧑‍💼 **User Management:** Manage users and roles
- 🎟️ **Discounts & Coupons:** Create and manage promotional codes
- 📈 **Analytics Dashboard:** View revenue, ticket sales, and event statistics via Kibana


---


User ───< Event >─── Ticket
│
├── Category
└── Payment

## ⚡ Installation & Setup
### 📝 Prerequisites
Before running the **Event Management System**, make sure you have the following installed:

- ☕ **Java 17+**
- 🛠️ **Maven**
- 🗄️ **MySQL**
- 🛡️ **Redis**
- 🔍 **Elasticsearch**
- 💻 **IntelliJ IDEA** (recommended for development)


Get the **Event Management System** running locally in a few simple steps! 🚀



### 1️⃣ Clone the Repository
```bash
git clone https://github.com/SlyeR28/EventManagementSystem.git
cd EventManagementSystem

# 🔹 MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/eventdb
spring.datasource.username=root
spring.datasource.password=yourpassword

# 🔹 Redis Configuration
spring.data.redis.host=localhost

# 🔹 Elasticsearch Configuration
spring.elasticsearch.uris=http://localhost:9200

# 🔹 Cloudinary Configuration
cloudinary.cloud_name=your_cloud
cloudinary.api_key=your_key
cloudinary.api_secret=your_secret

# 🔧 Build the project
mvn clean package

# ▶️ Run the application
java -jar target/event-management-system.jar

```
## 📊 Kibana Analytics

Monitor and analyze your event system in real-time:

- 👥 **Total Users** – Track registered and active users
- 🎟️ **Ticket Sales** – Monitor sales trends per event
- 📈 **Event Creation Rate** – Keep an eye on new events being created
- 💰 **Revenue Metrics** – Analyze revenue streams and trends
- 🌐 **Access Dashboard:** [http://localhost:5601](http://localhost:5601)
- 🔍 **Powered by:** Elasticsearch indices for fast and accurate analytics

---

## ☁️ Cloudinary Integration

Efficient and scalable image management for your platform:

- 🖼️ **Event Banners & Thumbnails** – Store and serve images seamlessly
- ⚡ **Auto-Optimization** – Images optimized for performance automatically
- 🌍 **CDN Delivery** – Fast and global access to images

---

## ⚡ Optimizations

Designed for high performance, scalability, and reliability:

- 🚀 **Redis Caching** – Speed up frequently accessed data
- ✉️ **Async Processing** – Email and notification handling without blocking
- 📚 **Pagination & Sorting** – Efficient data loading for large datasets
- 🛡️ **Resilient Error Handling** – Retry mechanisms and robust exception management
- 🏗️ **Modular Service Design** – Clean, reusable, and scalable architecture

---

## 🔮 Future Scope

Plans to enhance the platform with advanced features:

- 🧩 **Kafka Event Streaming** – Real-time event-driven architecture
- ✉️ **Automated Email Notifications** – Smart and scheduled communication
- 🌐 **GraphQL APIs** – Optimized queries for frontend consumption
- 🔧 **CI/CD Integration** – Automated builds and deployments with GitHub Actions

````

👨‍💻 Author

Rishabh Kumar
💼 https://github.com/SlyeR28

🌐 https://github.com/SlyeR28/EventManagementSystem.git

📧 jaatboykumar12@gmail.com

---

⭐ If you like this project, please give it a star on GitHub!
