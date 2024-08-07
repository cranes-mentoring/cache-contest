package handler

import (
	"encoding/json"
	"net/http"
	"time"

	"github.com/google/uuid"

	"order-service/pkg/model"
)

func (h *Handler) CreateOrder(w http.ResponseWriter, r *http.Request) {
	var order model.Order
	err := json.NewDecoder(r.Body).Decode(&order)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	order.ID = uuid.New()
	order.Timestamp = time.Now()

	_, err = h.OrderDb.Exec("INSERT INTO orders (id, username, timestamp) VALUES ($1, $2, $3)", order.ID, order.Username, order.Timestamp)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(order)
}
