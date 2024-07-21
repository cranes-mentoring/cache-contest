#!/bin/bash

set -e

DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-5432}
DB_USER=${DB_USER:-yourusername}
DB_PASSWORD=${DB_PASSWORD:-yourpassword}
DB_NAME=${DB_NAME:-yourdbname}

export DATABASE_URL="postgres://$DB_USER:$DB_PASSWORD@$DB_HOST:$DB_PORT/$DB_NAME?sslmode=disable"

goose -dir db/migrations postgres "$DATABASE_URL" up
