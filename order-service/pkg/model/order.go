package model

import (
	"time"

	"github.com/google/uuid"
)

type Order struct {
	ID        uuid.UUID `json:"id"`
	Username  string    `json:"username"`
	Timestamp time.Time `json:"timestamp"`
}
