# Cache Contest

## Overview
Welcome to **Cache Contest**, a project designed to demonstrate various caching mechanisms and their integration with modern technologies. This project includes services built with Java, Spring Boot, and Go, utilizing different caching strategies such as Caffeine and Memcached, as well as data synchronization with PostgreSQL and Kafka.

## Version
**Current Version:** `0.1.1`

## Tech Stack
- **Java 22**
- **Spring Boot 3**
- **JPA**
- **PostgreSQL**
- **Caffeine Cache**
- **Kafka**
- **Redis**
- **Hazelcast**

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

1. build project 
```shell
build.sh
```
2. run 
```shell
run.sh
```
3. down
```shell
down.sh
```

### Load test example:
```markdown
| Type | Name                     | # Requests | # Fails | Median (ms) | 95%ile (ms) | 99%ile (ms) | Average (ms) | Min (ms) | Max (ms) | Average size (bytes) | Current RPS | Current Failures/s |
|------|---------------------------|------------|---------|-------------|-------------|-------------|--------------|----------|----------|-----------------------|-------------|---------------------|
| POST | [/cf-db]_create           | 5145       | 148     | 61          | 320         | 510         | 102.39       | 3        | 970      | 40.06                | 62.6        | 6                   |
| GET  | [/cf-db]_find after_delete| 12257      | 7274    | 45          | 300         | 530         | 96           | 3        | 1684     | 46.72                | 146         | 86.2                |
| GET  | [/cf-db]_random_get       | 12442      | 223     | 50          | 340         | 600         | 102.65       | 3        | 1589     | 110.22               | 142         | 9.2                 |
| GET  | [/cf-db]_then_delete      | 4920       | 108     | 58          | 360         | 650         | 114.63       | 4        | 2192     | 10.73                | 57.5        | 5.5                 |
| POST | [/cf-kafka]_create        | 4434       | 134     | 65          | 320         | 510         | 120.32       | 3        | 75018    | 40.22                | 56.2        | 6.2                 |
| GET  | [/cf-kafka]_find after_delete | 10533 | 9441    | 48          | 300         | 540         | 96.16        | 3        | 1822     | 13.91                | 126.4       | 106.5               |
| GET  | [/cf-kafka]_random_get    | 10693      | 189     | 55          | 350         | 620         | 105.69       | 3        | 2236     | 110.2                | 125         | 7.7                 |
| GET  | [/cf-kafka]_then_delete   | 4234       | 86      | 60          | 360         | 670         | 113.24       | 4        | 1051     | 10.45                | 49.8        | 3.3                 |
| POST | [/cf]_create              | 4961       | 150     | 62          | 310         | 500         | 102.55       | 3        | 890      | 40.26                | 60.7        | 6.5                 |
| GET  | [/cf]_find after_delete   | 11733      | 7030    | 47          | 290         | 540         | 94.35        | 3        | 1319     | 46.39                | 140.3       | 86.4                |
| GET  | [/cf]_random_get          | 11946      | 220     | 49          | 330         | 600         | 101.47       | 3        | 1450     | 110.25               | 137.5       | 9.1                 |
| GET  | [/cf]_then_delete         | 4732       | 86      | 54          | 360         | 670         | 111.1        | 3        | 1224     | 10.09                | 56.5        | 2.8                 |
| POST | [/hzct]_create            | 5437       | 156     | 57          | 310         | 510         | 102.05       | 4        | 1359     | 40.05                | 66.4        | 6.4                 |
| GET  | [/hzct]_find after_delete | 12899      | 12665   | 44          | 300         | 540         | 95.31        | 3        | 1492     | 4.46                 | 153.5       | 143.5               |
| GET  | [/hzct]_random_get        | 13203      | 221     | 49          | 340         | 600         | 103.33       | 3        | 2456     | 112.47               | 153.9       | 10                  |
| GET  | [/hzct]_then_delete       | 5207       | 100     | 56          | 360         | 660         | 113.04       | 4        | 2037     | 10.26                | 61.1        | 4.4                 |
| POST | [/redis]_create           | 5255       | 154     | 65          | 310         | 500         | 103.03       | 3        | 1162     | 40.13                | 64.4        | 6.4                 |
| GET  | [/redis]_find after_delete| 12547      | 12330   | 50          | 300         | 550         | 96.74        | 3        | 1206     | 4.33                 | 148.6       | 138.6               |
| GET  | [/redis]_random_get       | 12619      | 214     | 56          | 330         | 600         | 104.52       | 3        | 2190     | 110.15               | 147.6       | 9                   |
| GET  | [/redis]_then_delete      | 5033       | 94      | 58          | 360         | 660         | 113.27       | 4        | 2181     | 10.18                | 59.2        | 4.3                 |
|      | Aggregated                | 170230     | 51023   | 52          | 320         | 580         | 102.42       | 3        | 75018    | 55.1                 | 2015.2      | 658                 |
```

### Happy caching!
