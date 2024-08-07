# cache contest

version 0.0.3-SNAPSHOT.

### Stack: Java 22, Spring boot 3, Jpa, Postgres.

### Services:
1. cf-service -> simple service with caffeine cache.
2. cf-db-service -> caffeine + db synch.
3. cf-kafka-service -> caffeine + kafka.
4. order-starter -> common starter for Java services.
5. order-service -> Golang service with memcached (for extra tests)
