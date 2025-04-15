package com.soloproject.bts.service.check;

import com.soloproject.bts.dto.request.checks.CheckIdRequest;
import com.soloproject.bts.dto.request.checks.CheckRequest;
import com.soloproject.bts.entity.Checklist;
import com.soloproject.bts.exception.ResponseHandler;
import com.soloproject.bts.helper.InfoAccount;
import com.soloproject.bts.repository.ChecklistItemRepository;
import com.soloproject.bts.repository.ChecklistRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class CheckServiceImpl implements CheckService {

    @Autowired
    ChecklistRepository checklistRepository;

    @Autowired
    ChecklistItemRepository checklistItemRepository;

    @Autowired
    InfoAccount infoAccount;

    @Override
    public ResponseEntity<Object> createCheck(CheckRequest request) {

        Checklist checklist = new Checklist();
        checklist.setId(UUID.randomUUID().toString());
        checklist.setTitle(request.getTitle());
        checklist.setUser(infoAccount.get());
        var data = checklistRepository.save(checklist);

        return ResponseHandler.generateResponseSuccess(data);

    }

//    @Override
//    public ResponseEntity<Object> getAllByCategoryAction(String name) {
//        return null;
//    }

    @Override
    public ResponseEntity<Object> getAll() {
        var data = checklistRepository.findAll();
        return ResponseHandler.generateResponseSuccess(data);
    }

//    @Override
//    public ResponseEntity<Object> getId(String id) {
//        var data = checklistRepository.findById(id).orElseThrow();
//        return ResponseHandler.generateResponseSuccess(data);
//    }

    @Override
    public ResponseEntity<Object> delete(String id) {
        var data = checklistRepository.findById(id).orElseThrow();
        checklistRepository.deleteById(data.getId());
        return ResponseHandler.generateResponseSuccess(data);
    }

//    @Override
//    public ResponseEntity<Object> update(CheckIdRequest request) {
//        var find = checklistRepository.findById(request.getId()).orElseThrow();
//        find.setTitle(request.getTitle());
//        var data = checklistRepository.save(find);
//        return ResponseHandler.generateResponseSuccess(data);
//    }
}
