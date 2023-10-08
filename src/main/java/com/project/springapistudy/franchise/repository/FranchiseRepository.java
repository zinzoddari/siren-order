package com.project.springapistudy.franchise.repository;

import com.project.springapistudy.franchise.entity.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
}
