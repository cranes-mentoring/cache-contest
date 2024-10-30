#!/bin/bash

set -e

sudo docker compose -f docker-compose.yaml down

sudo docker network rm cache_network