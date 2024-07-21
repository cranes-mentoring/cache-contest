package org.ere.contest.cfdbservice.repository;

import org.ere.contest.cfdbservice.model.entity.CacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CacheRepository extends JpaRepository<CacheEntity, String> {
}
