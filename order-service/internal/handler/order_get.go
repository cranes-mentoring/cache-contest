package handler

import (
	"encoding/json"
	"net/http"
	"order-service/pkg/model"

	"github.com/bradfitz/gomemcache/memcache"
	"github.com/gorilla/mux"
)

func (h *Handler) GetOrder(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	id := vars["id"]

	// Check cache
	if item, err := h.OrderCache.Get(id); err == nil {
		w.Write(item.Value)
		return
	}

	var order model.Order
	err := h.OrderDb.QueryRow("SELECT id, username, timestamp FROM orders WHERE id = $1", id).Scan(&order.ID, &order.Username, &order.Timestamp)
	if err != nil {
		http.Error(w, err.Error(), http.StatusNotFound)
		return
	}

	response, err := json.Marshal(order)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	// Set cache
	h.OrderCache.Set(&memcache.Item{Key: id, Value: response, Expiration: 60})

	w.Write(response)
}
