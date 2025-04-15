package com.soloproject.bts.controller.check;

import com.soloproject.bts.dto.request.checks.CheckListItemIdRequest;
import com.soloproject.bts.dto.request.checks.CheckListItemRequest;
import com.soloproject.bts.service.check.CheckListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class ChecklistItemController {

    @Autowired
    CheckListItemService checkListItemService;

    @GetMapping("/checklists/{id}")
    public ResponseEntity<Object> getAllByChecklistId(@PathVariable("id") String id){
        return checkListItemService.getAllByChecklistId(id);
    }
//
    @PostMapping("/checklist/items")
    public ResponseEntity<Object> createCheklistItem(@RequestBody CheckListItemRequest request){
        return checkListItemService.createChecklistItem(request);
    }

    @GetMapping("/checklist/items/{id}")
    public ResponseEntity<Object> getId(@PathVariable("id") String id){
        return checkListItemService.getId(id);
    }

    @PatchMapping("/checklist/items/{id}")
    public ResponseEntity<Object> updateIsDone(@PathVariable("id") String id){
        return checkListItemService.updateIsDone(id);
    }

    @DeleteMapping("/checklist/items/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        return checkListItemService.delete(id);
    }


    @PutMapping("/checklist/items/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody CheckListItemIdRequest request){
        return checkListItemService.update(id, request);
    }

}
