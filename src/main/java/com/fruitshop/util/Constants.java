package com.fruitshop.util;

/**
 * File: Constants.java
 * Description: Application-wide constants.
 */
public final class Constants {

    private Constants() {
        // Prevent instantiation
    }

    // ============================================
    // User Roles
    // ============================================
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    public static final String ROLE_SELLER = "SELLER";
    public static final String ROLE_ADMIN = "ADMIN";

    // ============================================
    // Account Status
    // ============================================
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_SUSPENDED = "SUSPENDED";
    public static final String STATUS_PENDING = "PENDING";

    // ============================================
    // Order Status
    // ============================================
    public static final String ORDER_PENDING = "PENDING";
    public static final String ORDER_CONFIRMED = "CONFIRMED";
    public static final String ORDER_PROCESSING = "PROCESSING";
    public static final String ORDER_SHIPPING = "SHIPPING";
    public static final String ORDER_DELIVERED = "DELIVERED";
    public static final String ORDER_CANCELLED = "CANCELLED";
    public static final String ORDER_RETURNED = "RETURNED";

    // ============================================
    // Payment Status
    // ============================================
    public static final String PAYMENT_PENDING = "PENDING";
    public static final String PAYMENT_PAID = "PAID";
    public static final String PAYMENT_FAILED = "FAILED";
    public static final String PAYMENT_REFUNDED = "REFUNDED";

    // ============================================
    // Payment Methods
    // ============================================
    public static final String PAYMENT_COD = "COD";
    public static final String PAYMENT_CREDIT_CARD = "CREDIT_CARD";
    public static final String PAYMENT_BANK_TRANSFER = "BANK_TRANSFER";
    public static final String PAYMENT_E_WALLET = "E_WALLET";

    // ============================================
    // Session Keys
    // ============================================
    public static final String SESSION_USER = "user";
    public static final String SESSION_USER_ID = "userId";
    public static final String SESSION_ROLE = "role";
    public static final String SESSION_CART = "cart";
    public static final String SESSION_CART_COUNT = "cartCount";
    public static final String SESSION_FLASH_SUCCESS = "success";
    public static final String SESSION_FLASH_ERROR = "error";
    public static final String SESSION_CSRF_TOKEN = "csrfToken";

    // ============================================
    // Request Attributes
    // ============================================
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_PRODUCTS = "products";
    public static final String ATTR_CATEGORIES = "categories";
    public static final String ATTR_USER = "user";
    public static final String ATTR_PAGINATION = "pagination";

    // ============================================
    // Pagination
    // ============================================
    public static final int DEFAULT_PAGE_SIZE = 12;
    public static final int ADMIN_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;

    // ============================================
    // File Upload
    // ============================================
    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    public static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/png", "image/gif", "image/webp"};
    public static final String UPLOAD_DIRECTORY = "/uploads/";

    // ============================================
    // URL Paths
    // ============================================
    public static final String REDIRECT_HOME = "/web/home.jsp";
    public static final String REDIRECT_LOGIN = "/auth/login.jsp";
    public static final String REDIRECT_ADMIN = "/admin/dashboard.jsp";

    // ============================================
    // Error Messages
    // ============================================
    public static final String ERR_INVALID_LOGIN = "Email hoặc mật khẩu không đúng";
    public static final String ERR_EMAIL_EXISTS = "Email đã được sử dụng";
    public static final String ERR_USER_NOT_FOUND = "Không tìm thấy người dùng";
    public static final String ERR_ACCESS_DENIED = "Bạn không có quyền truy cập trang này";
    public static final String ERR_PRODUCT_NOT_FOUND = "Không tìm thấy sản phẩm";
    public static final String ERR_ORDER_NOT_FOUND = "Không tìm thấy đơn hàng";
    public static final String ERR_INSUFFICIENT_STOCK = "Số lượng sản phẩm trong kho không đủ";

    // ============================================
    // Success Messages
    // ============================================
    public static final String MSG_LOGIN_SUCCESS = "Đăng nhập thành công";
    public static final String MSG_REGISTER_SUCCESS = "Đăng ký thành công";
    public static final String MSG_LOGOUT_SUCCESS = "Đăng xuất thành công";
    public static final String MSG_PRODUCT_ADDED = "Sản phẩm đã được thêm";
    public static final String MSG_PRODUCT_UPDATED = "Sản phẩm đã được cập nhật";
    public static final String MSG_PRODUCT_DELETED = "Sản phẩm đã được xóa";
    public static final String MSG_ORDER_PLACED = "Đơn hàng đã được đặt thành công";
}
