# build AMD
# ./gradlew bootBuildImage --imageName=ere/cf-kafka-service

# build ARM + Dockerfile
docker build -t ere/cf-kafka-service .
