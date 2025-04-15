package com.soloproject.bts.service.check;

import com.soloproject.bts.dto.request.checks.CheckListItemIdRequest;
import com.soloproject.bts.dto.request.checks.CheckListItemRequest;
import org.springframework.http.ResponseEntity;

public interface CheckListItemService {

    ResponseEntity<Object> getAllByChecklistId(String id);
    ResponseEntity<Object> createChecklistItem(CheckListItemRequest request);
    ResponseEntity<Object> getId(String id);
    ResponseEntity<Object> updateIsDone(String id);
    ResponseEntity<Object> delete(String id);
    ResponseEntity<Object> update(String id, CheckListItemIdRequest request);
}
