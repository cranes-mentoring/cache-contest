package cache

import (
	"github.com/bradfitz/gomemcache/memcache"
)

func InitCache() *memcache.Client {
	return memcache.New("memcached-server1:11211", "memcached-server2:11211")
}

func Close(cache *memcache.Client) {
	cache.Close()
}
