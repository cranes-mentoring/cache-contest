package handler

import (
	"net/http"

	"github.com/gorilla/mux"
)

func (h *Handler) DeleteOrder(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	id := vars["id"]

	_, err := h.OrderDb.Exec("DELETE FROM orders WHERE id = $1", id)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	// Invalidate cache
	h.OrderCache.Delete(id)

	w.WriteHeader(http.StatusNoContent)
}
