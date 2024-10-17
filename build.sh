#!/bin/bash

set -e

build_common_starter() {
  echo "Building common starter..."
  cd order-starter || exit 1
  ./gradlew clean publishToMavenLocal || exit 1
  cd .. || exit 1
  echo "Common starter build was finished!"
}

build_cf_db_service() {
  echo "Building cf-db service..."
  cd cf-db-service || exit 1

  echo "Preparing image..."
  ./gradlew clean build || exit 1

  echo "Building Docker image..."
  cat Dockerfile
  sudo docker build -t ere/cf-db-service . || exit 1

  cd .. || exit 1
  echo "cf-db service build was finished!"
}

build_cf_service() {
  echo "Building cf service..."
  cd cf-service || exit 1

  echo "Preparing image..."
  ./gradlew clean build || exit 1

  echo "Building Docker image..."
  cat Dockerfile
  sudo docker build -t ere/cf-service . || exit 1

  cd .. || exit 1
  echo "cf service build was finished!"
}

build_redis_service() {
  echo "Building redis service..."
  cd redis-service || exit 1

  echo "Preparing image..."
  ./gradlew clean build || exit 1

  echo "Building Docker image..."
  cat Dockerfile
  sudo docker build -t ere/redis-service . || exit 1

  cd .. || exit 1
  echo "redis service build was finished!"
}

build_kafka_service() {
  echo "Building redis service..."
  cd cf-kafka-service || exit 1

  echo "Preparing image..."
  ./gradlew clean build || exit 1

  echo "Building Docker image..."
  cat Dockerfile
  sudo docker build -t ere/cf-kafka-service . || exit 1

  cd .. || exit 1
  echo "cf-kafka service build was finished!"
}

build_hzct_service() {
  echo "Building redis service..."
  cd hzct-service || exit 1

  echo "Preparing image..."
  ./gradlew clean build || exit 1

  echo "Building Docker image..."
  cat Dockerfile
  sudo docker build -t ere/hzct-service . || exit 1

  cd .. || exit 1
  echo "hzct service build was finished!"
}

main() {
  echo "Starting build process..."

  build_common_starter
  build_cf_db_service
  build_cf_service
  build_redis_service
  build_kafka_service
  build_hzct_service

  echo "All services were built successfully!"
}

main
