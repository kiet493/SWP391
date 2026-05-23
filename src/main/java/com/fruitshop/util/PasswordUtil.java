package com.fruitshop.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * File: PasswordUtil.java
 * Description: Utility class for password hashing and verification using BCrypt.
 */
public class PasswordUtil {

    private static final int BCRYPT_ROUNDS = 12;

    /**
     * Hash a plain text password using BCrypt.
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    /**
     * Verify a plain text password against a hashed password.
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * Validate password strength.
     * Requirements: min 8 chars, 1 uppercase, 1 number, 1 special char.
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        return hasUppercase && hasLowercase && hasDigit && hasSpecial;
    }

    /**
     * Generate a random password (for reset functionality).
     */
    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return password.toString();
    }
}
