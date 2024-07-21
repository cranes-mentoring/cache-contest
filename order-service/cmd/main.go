package main

import (
	"log"
	"net/http"

	"order-service/internal/cache"
	"order-service/internal/db"
	"order-service/internal/handler"

	"github.com/gorilla/mux"
)

func main() {
	pgDb := db.InitDB()
	defer db.CloseDB(pgDb)

	orderCache := cache.InitCache()
	defer cache.Close(orderCache)

	hndl := handler.NewHandler(pgDb, orderCache)

	r := mux.NewRouter()
	r.HandleFunc("/orders", hndl.CreateOrder).Methods("POST")
	r.HandleFunc("/orders/{id}", hndl.GetOrder).Methods("GET")
	r.HandleFunc("/orders/{id}", hndl.DeleteOrder).Methods("DELETE")

	log.Fatal(http.ListenAndServe(":8085", r))
}
