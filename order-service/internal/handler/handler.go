package handler

import (
	"database/sql"

	"github.com/bradfitz/gomemcache/memcache"
)

type Handler struct {
	OrderDb    *sql.DB
	OrderCache *memcache.Client
}

func NewHandler(orderDb *sql.DB, orderCache *memcache.Client) *Handler {
	return &Handler{
		OrderDb:    orderDb,
		OrderCache: orderCache,
	}
}
