package com.example.ecommercedemo.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
public class ErrorDetail {
    private Date timestamp;
    private String message;
    private String detail;

}
