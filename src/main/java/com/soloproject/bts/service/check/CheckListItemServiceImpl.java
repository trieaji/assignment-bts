package com.soloproject.bts.service.check;

import com.soloproject.bts.dto.request.checks.CheckListItemIdRequest;
import com.soloproject.bts.dto.request.checks.CheckListItemRequest;
import com.soloproject.bts.entity.ChecklistItem;
import com.soloproject.bts.exception.ResponseHandler;
import com.soloproject.bts.helper.InfoAccount;
import com.soloproject.bts.repository.ChecklistItemRepository;
import com.soloproject.bts.repository.ChecklistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class CheckListItemServiceImpl implements CheckListItemService{

    @Autowired
    ChecklistRepository checklistRepository;

    @Autowired
    ChecklistItemRepository checklistItemRepository;

    @Autowired
    InfoAccount infoAccount;


    @Override
    public ResponseEntity<Object> getAllByChecklistId(String id) {
        var data = checklistItemRepository.findAllByCheklistId(id);
        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> createChecklistItem(CheckListItemRequest request) {

        var checklistId = checklistRepository.findById(request.getChecklist()).orElseThrow();

        ChecklistItem checklistItem = new ChecklistItem();
        checklistItem.setId(UUID.randomUUID().toString());
        checklistItem.setContent(request.getContent());
        checklistItem.setChecklist(checklistId);

        var data = checklistItemRepository.save(checklistItem);
        return ResponseHandler.generateResponseSuccess(data);

    }

    @Override
    public ResponseEntity<Object> getId(String id) {
        var data = checklistItemRepository.findById(id).orElseThrow();
        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> updateIsDone(String id) {
        var find = checklistItemRepository.findById(id).orElseThrow();
        find.setDone(true);
        var data = checklistItemRepository.save(find);

        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> delete(String id) {
        var data = checklistItemRepository.findById(id).orElseThrow();
        checklistItemRepository.deleteById(data.getId());
        return ResponseHandler.generateResponseSuccess(data);
    }

    @Override
    public ResponseEntity<Object> update(String id, CheckListItemIdRequest request) {
        var find = checklistItemRepository.findById(id).orElseThrow();
        find.setContent(request.getContent());
        var data = checklistItemRepository.save(find);
        return ResponseHandler.generateResponseSuccess(data);
    }
}
