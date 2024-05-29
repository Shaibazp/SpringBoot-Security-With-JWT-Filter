package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.userInfo;

public interface userInfoRepository extends JpaRepository<userInfo, Long> {
	Optional<userInfo> findByName(String userName);
}
