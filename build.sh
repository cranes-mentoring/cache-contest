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
#  if [[ "$(sudo docker images -q ere/cf-db-service 2> /dev/null)" == "" ]]; then
    echo "Building cf-db service..."
    cd cf-db-service || exit 1

    echo "Preparing image..."
    ./gradlew clean build || exit 1

    echo "Building Docker image..."
    cat Dockerfile
    sudo docker build -t ere/cf-db-service . || exit 1

    cd .. || exit 1
    echo "cf-db service build was finished!"
#  else
#    echo "cf-db service image already exists. Skipping build."
#  fi
}

build_cf_service() {
#  if [[ "$(sudo docker images -q ere/cf-service 2> /dev/null)" == "" ]]; then
    echo "Building cf service..."
    cd cf-service || exit 1

    echo "Preparing image..."
    ./gradlew clean build || exit 1

    echo "Building Docker image..."
    cat Dockerfile
    sudo docker build -t ere/cf-service . || exit 1

    cd .. || exit 1
    echo "cf service build was finished!"
#  else
#    echo "cf service image already exists. Skipping build."
#  fi
}

build_redis_service() {
#  if [[ "$(sudo docker images -q ere/redis-service 2> /dev/null)" == "" ]]; then
    echo "Building redis service..."
    cd redis-service || exit 1

    echo "Preparing image..."
    ./gradlew clean build || exit 1

    echo "Building Docker image..."
    cat Dockerfile
    sudo docker build -t ere/redis-service . || exit 1

    cd .. || exit 1
    echo "redis service build was finished!"
#  else
#    echo "redis service image already exists. Skipping build."
#  fi
}

build_kafka_service() {
#  if [[ "$(sudo docker images -q ere/cf-kafka-service 2> /dev/null)" == "" ]]; then
    echo "Building kafka service..."
    cd cf-kafka-service || exit 1

    echo "Preparing image..."
    ./gradlew clean build || exit 1

    echo "Building Docker image..."
    cat Dockerfile
    sudo docker build -t ere/cf-kafka-service . || exit 1

    cd .. || exit 1
    echo "cf-kafka service build was finished!"
#  else
#    echo "cf-kafka service image already exists. Skipping build."
#  fi
}

build_hzct_service() {
#  if [[ "$(sudo docker images -q ere/hzct-service 2> /dev/null)" == "" ]]; then
    echo "Building hzct service..."
    cd hzct-service || exit 1

    echo "Preparing image..."
    ./gradlew clean build || exit 1

    echo "Building Docker image..."
    cat Dockerfile
    sudo docker build -t ere/hzct-service . || exit 1

    cd .. || exit 1
    echo "hzct service build was finished!"
#  else
#    echo "hzct service image already exists. Skipping build."
#  fi
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
