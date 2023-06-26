package com.example.hwcache.repository;

import com.example.hwcache.model.CacheObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CachingRepository extends JpaRepository<CacheObject, String> {
}
