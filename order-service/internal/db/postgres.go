package db

import (
	"database/sql"
	"log"

	_ "github.com/lib/pq"
)

func InitDB() *sql.DB {
	var err error
	db, err := sql.Open("postgres", "user=postgres dbname=postgres password=postgres sslmode=disable")
	if err != nil {
		log.Fatal(err)
	}

	return db
}

func CloseDB(db *sql.DB) {
	db.Close()
}
