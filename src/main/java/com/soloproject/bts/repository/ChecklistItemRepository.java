package com.soloproject.bts.repository;

import com.soloproject.bts.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, String> {

    @Query("select c from ChecklistItem c where c.checklist.id = :id")
    List<ChecklistItem> findAllByCheklistId(String id);
}
