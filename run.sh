#!/bin/bash

set -e

# before start:
sudo docker network create cache_network

sudo docker compose -f docker-compose.yaml up -d