package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

public final class InputValidator {
    private InputValidator() { }

    public static String requireText(String input, String field) {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(field + " cannot be empty.");
        }
        return input.trim();
    }

    public static String optionalEmail(String email) {
        String cleaned = email == null ? "" : email.trim();
        if (!cleaned.isEmpty() && !cleaned.matches("^[^@ ]+@[^@ ]+\\.[^@ ]+$")) {
            throw new InvalidInputException("Enter a valid email or leave it blank.");
        }
        return cleaned;
    }

    public static int requirePositive(int number, String field) {
        if (number <= 0) throw new InvalidInputException(field + " must be greater than zero.");
        return number;
    }
}
