package com.soloproject.bts.repository;

import com.soloproject.bts.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ChecklistRepository extends JpaRepository<Checklist, String> {
    Checklist findByUserId(@Param("userid") String userId);
}
