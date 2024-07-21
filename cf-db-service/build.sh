# build AMD
# ./gradlew bootBuildImage --imageName=ere/cf-db-service

# build ARM + Dockerfile
docker build -t ere/cf-db-service .
