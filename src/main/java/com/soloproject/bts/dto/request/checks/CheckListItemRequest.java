package com.soloproject.bts.dto.request.checks;

public class CheckListItemRequest {

    private String content;

    private String checklist;

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
