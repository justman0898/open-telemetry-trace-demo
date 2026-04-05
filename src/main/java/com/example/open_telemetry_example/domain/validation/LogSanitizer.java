package com.example.open_telemetry_example.domain.validation;

public final class LogSanitizer {

    public static String sanitize(String input) {
        return input == null ? null : input.replaceAll("[\\n\\r\\t]", "_");
    }

    public static String maskEmail(String email) {
        if (email == null) return null;
        return email.replaceAll("(^.).*(@.*$)", "$1***$2");
    }
}