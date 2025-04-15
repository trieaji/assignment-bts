package com.soloproject.bts.service.check;

import com.soloproject.bts.dto.request.checks.CheckIdRequest;
import com.soloproject.bts.dto.request.checks.CheckRequest;
import org.springframework.http.ResponseEntity;

public interface CheckService {
    ResponseEntity<Object> createCheck(CheckRequest request);
//    ResponseEntity<Object> getAllByCategoryAction(String name);
    ResponseEntity<Object> getAll();
//    ResponseEntity<Object> getId(String id);
    ResponseEntity<Object> delete(String id);
//    ResponseEntity<Object> update(CheckIdRequest request);
}
