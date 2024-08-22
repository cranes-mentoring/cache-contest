# Cache Contest

## Overview
Welcome to **Cache Contest**, a project designed to demonstrate various caching mechanisms and their integration with modern technologies. This project includes services built with Java, Spring Boot, and Go, utilizing different caching strategies such as Caffeine and Memcached, as well as data synchronization with PostgreSQL and Kafka.

## Version
**Current Version:** `0.1.0`

## Tech Stack
- **Java 22**
- **Spring Boot 3**
- **JPA**
- **PostgreSQL**
- **Caffeine Cache**
- **Kafka**
- **Redis**
- **Hazelcast**

## For extra tests
- **Golang**
- **Memcached**

## REST API Overview

Each service exposes the following endpoints for interacting with the Order entities. The API version depends on the specific caching implementation used by the service:

1. Create an Order  
Endpoint: POST /v{version}/api/order  
Request Body: OrderRequest object (JSON)   
Response: 201 Created with the UUID of the created order

2. Delete an Order  
Endpoint: GET /v{version}/api/order/delete/{orderUuid}  
Response: 200 OK with confirmation of deletion  

3. Get an Order by UUID  
Endpoint: GET /v{version}/api/order/{orderUuid}  
Response: 200 OK with the Order object if found, otherwise 404 Not Found


Note: Replace {version} in the endpoints with the respective version number of the service you are interacting with (e.g., /v1, /v2, etc.).

## Services
The project comprises the following services:

### cf-service
A simple service that implements Caffeine cache.

### cf-db-service
A service that uses Caffeine cache with database synchronization.

### cf-kafka-service
A service that combines Caffeine cache with Kafka for messaging.

### redis-service
Same service like before, but wit Redis and nothing else.

### hz-service
A service that leverages Hazelcast as the caching mechanism.

### order-starter
A common starter library for Java services, providing shared configurations and utilities.

Extra:
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
- Redis

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
