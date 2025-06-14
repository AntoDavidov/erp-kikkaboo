# ERP KikkaBoo (Backend)

> A personal management system disguised as an ERP — but not quite an ERP 😄

This is the **backend** of a custom management application for KikkaBoo, a baby products company. The project is built using **Java**, powered by **Spring Boot**, and packaged with **Gradle**. It connects to a **MySQL** database and uses **Docker** for local development.

---

## 🚀 Tech Stack

- **Java 17**
- **Spring Boot**
- **Gradle**
- **MySQL**
- **Docker**
- **Spring Data JPA**
- **Spring Security**
- **Lombok**
- **JWT Tokens**
- **Sonarqube**

---

## 🛠️ Features

- Employee and role management
- Product creation and management
- Announcements (internal communication -> Websockets)
- Department-specific restrictions
- Custom business logic (not a generic ERP)
- AI Chatbot - developed with Voiceflow

---

## 🧰 Prerequisites

Make sure you have the following installed:

- [Java 17+](https://adoptium.net/)
- [Gradle](https://gradle.org/)
- [Docker](https://www.docker.com/)
- [MySQL 8+](https://dev.mysql.com/downloads/mysql/)

---


🧪 Local Development
Clone the repo:

```bash
git clone https://github.com/AntoDavidov/erp-kikkaboo.git
cd erp-kikkaboo
```
Create a .env file or configure your application.yml with your local database settings.

Start MySQL manually or via Docker.

Run the backend:

``` bash
./gradlew bootRun
```
