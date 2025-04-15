package com.soloproject.bts.controller.check;

import com.soloproject.bts.dto.request.checks.CheckRequest;
import com.soloproject.bts.dto.request.users.LoginRequest;
import com.soloproject.bts.service.check.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class ChecklistController {

    @Autowired
    CheckService checkService;

    @PostMapping("/checklist")
    public ResponseEntity<Object> create(@RequestBody CheckRequest request){
        return checkService.createCheck(request);
    }

    @DeleteMapping("/checklist/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id){
        return checkService.delete(id);
    }

    @GetMapping("/checklists")
    public ResponseEntity<Object> getAll(){
        return checkService.getAll();
    }


}
