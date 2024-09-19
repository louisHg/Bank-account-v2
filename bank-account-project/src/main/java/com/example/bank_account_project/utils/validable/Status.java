package com.example.bank_account_project.utils.validable;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Status {

    private String message;
    private StatusType type;

    public Status() {
        // empty constructor needed for JSON serialization
    }

    public Status(String message, StatusType type) {
        this.message = message;
        this.type = type;
    }
}
