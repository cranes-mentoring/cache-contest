# Cache Contest

## Overview
Welcome to **Cache Contest**, a project designed to demonstrate various caching mechanisms and their integration with modern technologies. This project includes services built with Java, Spring Boot, and Go, utilizing different caching strategies such as Caffeine and Memcached, as well as data synchronization with PostgreSQL and Kafka.

## Version
**Current Version:** `0.0.3-SNAPSHOT`

## Tech Stack
- **Java 22**
- **Spring Boot 3**
- **JPA**
- **PostgreSQL**
- **Caffeine Cache**
- **Kafka**
## For extra tests
- **Golang**
- **Memcached**

## Services
The project comprises the following services:

### CF-Service
A simple service that implements Caffeine cache.

### CF-DB-Service
A service that uses Caffeine cache with database synchronization.

### CF-Kafka-Service
A service that combines Caffeine cache with Kafka for messaging.

### Order-Starter
A common starter library for Java services, providing shared configurations and utilities.

### Order-Service
A Golang service that uses Memcached for caching, designed for additional testing purposes.

## Getting Started
To get started with this project, follow the steps below:

### Prerequisites
- Java 22
- Gradle kts
- PostgreSQL
- Kafka
- Memcached

### Installation
1. **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/cache-contest.git
    cd cache-contest
    ```

### Usage
Each service exposes various endpoints for interacting with the cache and performing operations. Refer to the individual service documentation for specific API details.

## Contact
For any questions or inquiries, please reach out to the project maintainers or create an issue on GitHub.

Happy caching!