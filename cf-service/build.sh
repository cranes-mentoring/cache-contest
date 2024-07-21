# build AMD
# ./gradlew bootBuildImage --imageName=ere/cf-service

# build ARM + Dockerfile
docker build -t ere/cf-service .
