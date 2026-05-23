package com.fruitshop.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * File: ValidationUtil.java
 * Description: Utility class for input validation.
 */
public class ValidationUtil {

    // Email regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Vietnam phone pattern (mobile and landline)
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((09|03|07|08|05)[0-9]{8}|0[0-9]{9,10})$");

    private ValidationUtil() {
        // Prevent instantiation
    }

    // ============================================
    // Email Validation
    // ============================================
    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống";
        }
        email = email.trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            return "Email không hợp lệ";
        }
        if (email.length() > 255) {
            return "Email quá dài (tối đa 255 ký tự)";
        }
        return null;
    }

    // ============================================
    // Password Validation
    // ============================================
    public static String validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return "Mật khẩu không được để trống";
        }
        if (password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự";
        }
        if (password.length() > 100) {
            return "Mật khẩu quá dài (tối đa 100 ký tự)";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ hoa";
        }
        if (!password.matches(".*\\d.*")) {
            return "Mật khẩu phải chứa ít nhất 1 số";
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return "Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt";
        }
        return null;
    }

    public static String validateConfirmPassword(String password, String confirmPassword) {
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return "Xác nhận mật khẩu không được để trống";
        }
        if (!password.equals(confirmPassword)) {
            return "Mật khẩu xác nhận không khớp";
        }
        return null;
    }

    // ============================================
    // Name Validation
    // ============================================
    public static String validateFullName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Họ tên không được để trống";
        }
        name = name.trim();
        if (name.length() < 2) {
            return "Họ tên phải có ít nhất 2 ký tự";
        }
        if (name.length() > 100) {
            return "Họ tên quá dài (tối đa 100 ký tự)";
        }
        if (!name.matches("^[\\p{L}\\s]+$")) {
            return "Họ tên chỉ được chứa chữ cái và khoảng trắng";
        }
        return null;
    }

    // ============================================
    // Phone Validation
    // ============================================
    public static String validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return null; // Phone is optional
        }
        phone = phone.trim();
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            return "Số điện thoại không hợp lệ";
        }
        return null;
    }

    // ============================================
    // Product Validation
    // ============================================
    public static String validateProductName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Tên sản phẩm không được để trống";
        }
        name = name.trim();
        if (name.length() < 3) {
            return "Tên sản phẩm phải có ít nhất 3 ký tự";
        }
        if (name.length() > 200) {
            return "Tên sản phẩm quá dài (tối đa 200 ký tự)";
        }
        return null;
    }

    public static String validatePrice(String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            return "Giá không được để trống";
        }
        try {
            double price = Double.parseDouble(priceStr.trim());
            if (price <= 0) {
                return "Giá phải lớn hơn 0";
            }
            if (price > 999999999) {
                return "Giá quá lớn";
            }
        } catch (NumberFormatException e) {
            return "Giá không hợp lệ";
        }
        return null;
    }

    public static String validateQuantity(String quantityStr) {
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            return "Số lượng không được để trống";
        }
        try {
            int quantity = Integer.parseInt(quantityStr.trim());
            if (quantity < 0) {
                return "Số lượng không được âm";
            }
            if (quantity > 999999) {
                return "Số lượng quá lớn";
            }
        } catch (NumberFormatException e) {
            return "Số lượng không hợp lệ";
        }
        return null;
    }

    // ============================================
    // Address Validation
    // ============================================
    public static String validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "Địa chỉ không được để trống";
        }
        address = address.trim();
        if (address.length() < 10) {
            return "Địa chỉ phải có ít nhất 10 ký tự";
        }
        if (address.length() > 500) {
            return "Địa chỉ quá dài (tối đa 500 ký tự)";
        }
        return null;
    }

    // ============================================
    // General Validation
    // ============================================
    public static String validateRequired(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            return fieldName + " không được để trống";
        }
        return null;
    }

    public static String validateMaxLength(String value, String fieldName, int maxLength) {
        if (value != null && value.length() > maxLength) {
            return fieldName + " quá dài (tối đa " + maxLength + " ký tự)";
        }
        return null;
    }

    /**
     * Validate multiple fields and return all errors.
     */
    public static Map<String, String> validateRegistration(String email, String password,
            String confirmPassword, String fullName, String phone) {
        Map<String, String> errors = new HashMap<>();

        String emailError = validateEmail(email);
        if (emailError != null) errors.put("email", emailError);

        String passwordError = validatePassword(password);
        if (passwordError != null) errors.put("password", passwordError);

        String confirmError = validateConfirmPassword(password, confirmPassword);
        if (confirmError != null) errors.put("confirmPassword", confirmError);

        String nameError = validateFullName(fullName);
        if (nameError != null) errors.put("fullName", nameError);

        String phoneError = validatePhone(phone);
        if (phoneError != null) errors.put("phone", phoneError);

        return errors;
    }

    /**
     * Sanitize input to prevent XSS.
     */
    public static String sanitize(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&#x27;")
                    .replaceAll("/", "&#x2F;");
    }
}
