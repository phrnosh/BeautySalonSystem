package com.beautysalon.beautysalonsystem.controller.exception;

import jakarta.persistence.OptimisticLockException;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ExceptionWrapper {
    public static Map<Integer, String> getMessage(Exception e) {
        Map<Integer, String> error = new HashMap<>();
        if (e instanceof NullPointerException) {
            error.put(400, "NullPointerException");
        } else if (e instanceof SQLException) {
            error.put(500, "Database Error");
        } else if (e instanceof OptimisticLockException) {
            error.put(500, "Error synchronizing changes on a record");
        } else if (e instanceof AccessDeniedException) {
            error.put(403, "Access denied");
        } else {
            error.put(500, "Unknown Error - please contact with admin");
        }
        return error;
    }
}