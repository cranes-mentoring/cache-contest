# build AMD
# ./gradlew bootBuildImage --imageName=ere/hz-service

# build ARM + Dockerfile
docker build -t ere/hz-service .
