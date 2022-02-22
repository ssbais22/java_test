package com.test.models;

import lombok.Data;

@Data
public class Response {
    private String message = "success";
    private String status;
}
