package com.augusto.address.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddressException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
}
