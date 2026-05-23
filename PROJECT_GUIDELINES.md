# FruitStore E-Commerce - Project Guidelines

> **Website Bán Hoa Quả Online** | SWP391 - Enterprise Web Software Development

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Architecture](#2-architecture)
3. [Project Structure](#3-project-structure)
4. [Coding Conventions](#4-coding-conventions)
5. [Controller Guidelines](#5-controller-guidelines)
6. [Model Guidelines](#6-model-guidelines)
7. [DAO Guidelines](#7-dao-guidelines)
8. [JSP Guidelines](#8-jsp-guidelines)
9. [SQL Guidelines](#9-sql-guidelines)
10. [Validation Rules](#10-validation-rules)
11. [Error Handling](#11-error-handling)
12. [Security Guidelines](#12-security-guidelines)
13. [Git Workflow](#13-git-workflow)
14. [Naming Conventions](#14-naming-conventions)

---

## 1. Project Overview

| Field | Value |
|-------|-------|
| Project Name | FruitStore |
| Type | Java Web E-Commerce Application |
| Tech Stack | JSP, Servlet, MVC, DAO Pattern |
| Database | SQL Server |
| Build Tool | Maven |
| Server | Apache Tomcat 9 |

### Actors/Roles

| Role | Description |
|------|-------------|
| **Customer** | Mua sắm hoa quả online |
| **Seller** | Quản lý cửa hàng, sản phẩm |
| **Admin** | Quản trị toàn hệ thống |

---

## 2. Architecture

### MVC Pattern

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   Views     │◄────│ Controller  │◄────│   Model     │
│   (JSP)     │     │ (Servlet)   │     │  (DAO)      │
└─────────────┘     └─────────────┘     └─────────────┘
                           │
                           ▼
                    ┌─────────────┐
                    │   Filter    │
                    │(Auth/Role)  │
                    └─────────────┘
```

### Data Flow

```
User Request → Filter → Controller → DAO → Database
                ↓
           View (JSP) ←── Model Data
```

---

## 3. Project Structure

```
src/main/
├── java/com/fruitshop/
│   ├── controller/          # Servlets (HTTP Request Handlers)
│   │   ├── AuthController.java
│   │   ├── ProductController.java
│   │   ├── CartController.java
│   │   ├── OrderController.java
│   │   ├── UserController.java
│   │   ├── AdminController.java
│   │   ├── SellerController.java
│   │   ├── SearchController.java
│   │   └── PageController.java
│   │
│   ├── model/              # Entity Classes
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Category.java
│   │   ├── Cart.java
│   │   ├── CartItem.java
│   │   ├── Order.java
│   │   ├── OrderItem.java
│   │   ├── Review.java
│   │   ├── Coupon.java
│   │   ├── Address.java
│   │   ├── Wishlist.java
│   │   ├── enums/         # Enumerations
│   │   └── dto/           # Data Transfer Objects
│   │
│   ├── dao/               # Data Access Objects
│   │   ├── BaseDAO.java
│   │   ├── UserDAO.java
│   │   ├── ProductDAO.java
│   │   ├── CategoryDAO.java
│   │   ├── CartDAO.java
│   │   ├── OrderDAO.java
│   │   ├── ReviewDAO.java
│   │   ├── CouponDAO.java
│   │   ├── AddressDAO.java
│   │   └── WishlistDAO.java
│   │
│   ├── filter/            # Servlet Filters
│   │   ├── EncodingFilter.java
│   │   ├── AuthFilter.java
│   │   └── RoleFilter.java
│   │
│   ├── util/             # Utility Classes
│   │   ├── DBConnection.java
│   │   ├── PasswordUtil.java
│   │   ├── EmailUtil.java
│   │   ├── ValidationUtil.java
│   │   ├── FileUploadUtil.java
│   │   ├── Constants.java
│   │   └── JsonUtil.java
│   │
│   ├── constant/         # Constants
│   │   ├── AppConstants.java
│   │   └── RouteConstants.java
│   │
│   └── exception/        # Custom Exceptions
│       ├── DAOException.java
│       ├── ValidationException.java
│       └── AuthenticationException.java
│
├── resources/
│   ├── config.properties
│   ├── sql/
│   │   └── schema.sql
│   └── email-templates/
│
└── webapp/
    ├── WEB-INF/
    │   ├── web.xml
    │   └── jsp/
    │       ├── layout/        # Shared layouts
    │       ├── web/           # Customer pages
    │       ├── admin/         # Admin pages
    │       ├── seller/        # Seller pages
    │       ├── auth/          # Login/Register pages
    │       └── error/         # Error pages
    │
    └── assets/
        ├── css/
        ├── js/
        └── images/
```

---

## 4. Coding Conventions

### 4.1 Java Code Style

#### File Header Comment

```java
/**
 * File: ProductController.java
 * Description: Handle all product-related HTTP requests.
 * Created by: [Author Name]
 * Created date: [YYYY-MM-DD]
 * Last modified: [YYYY-MM-DD]
 * Layer: Controller
 */
package com.fruitshop.controller;
```

#### Class Naming
- **Model**: PascalCase (VD: `UserAccount`, `ProductCategory`)
- **DAO**: PascalCase + DAO suffix (VD: `UserDAO`, `ProductDAO`)
- **Controller**: PascalCase + Controller suffix (VD: `AuthController`)
- **Filter**: PascalCase + Filter suffix (VD: `AuthFilter`)
- **Enum**: PascalCase (VD: `OrderStatus`, `Role`)

#### Method Naming
```java
// CRUD Operations
public Entity create(Entity e) { }
public Entity findById(int id) { }
public List<Entity> findAll() { }
public boolean update(Entity e) { }
public boolean delete(int id) { }

// Custom Queries
public List<Entity> findByField(Type value) { }
public Entity findByFieldUnique(Type value) { }
```

#### Variable Naming
```java
// Good
private int userId;
private String productName;
private List<Product> products;
private User currentUser;

// Avoid
private int uid;
private String pName;
private List<Product> list;
private User u;
```

### 4.2 JSP Code Style

#### File Header Comment

```jsp
<%--
File: home.jsp
Description: Homepage displaying featured products and promotions.
Created by: [Author Name]
Created date: [YYYY-MM-DD]
Last modified: [YYYY-MM-DD]
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

#### Attribute Naming (Request Scope)
```jsp
<%-- Model attributes --%>
request.setAttribute("products", products);
request.setAttribute("categories", categories);

// EL Expression
${products}
${user.username}
```

### 4.3 SQL Naming Conventions

#### Table Naming
- Table name: PascalCase (VD: `UserAccount`, `ProductCategory`)
- Primary Key: `[TableName]Id` (VD: `UserId`, `ProductId`)
- Foreign Key: `[ReferencedTable]Id` (VD: `CategoryId`, `UserId`)

#### Column Naming
- Column name: snake_case (VD: `user_id`, `product_name`)
- Boolean columns: `is_[status]` or `has_[feature]`
- Timestamp columns: `[action]_at` (VD: `created_at`, `updated_at`)

---

## 5. Controller Guidelines

### 5.1 Controller Structure

```java
/**
 * File: ProductController.java
 * Description: Handle product-related HTTP requests.
 */
@WebServlet("/product/*")
public class ProductController extends HttpServlet {

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action) {
            case "/list":
                listProducts(request, response);
                break;
            case "/detail":
                showProductDetail(request, response);
                break;
            case "/add":
                showAddForm(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/delete":
                deleteProduct(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if ("/insert".equals(action)) {
            insertProduct(request, response);
        } else if ("/update".equals(action)) {
            updateProduct(request, response);
        }
    }

    // Private helper methods
    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation
    }
}
```

### 5.2 URL Mapping Rules

| URL Pattern | Controller | Description |
|-------------|------------|-------------|
| `/auth/*` | AuthController | Login, Register, Logout |
| `/product/*` | ProductController | Product CRUD |
| `/category/*` | CategoryController | Category CRUD |
| `/cart/*` | CartController | Shopping Cart |
| `/order/*` | OrderController | Order Management |
| `/admin/*` | AdminController | Admin Actions |
| `/seller/*` | SellerController | Seller Actions |
| `/search` | SearchController | Search Functionality |

### 5.3 Redirect vs Forward

```java
// Redirect - Use when changing URL (POST-Redirect-GET)
response.sendRedirect(request.getContextPath() + "/product/list");

// Forward - Use when staying on same URL
request.getRequestDispatcher("/views/product/list.jsp").forward(request, response);

// AJAX Response - Return JSON
response.setContentType("application/json");
response.getWriter().write(jsonString);
```

---

## 6. Model Guidelines

### 6.1 Entity Class Structure

```java
/**
 * File: Product.java
 * Description: Product entity representing items for sale.
 */
public class Product {

    // Fields
    private int productId;
    private String productName;
    private String description;
    private double price;
    private int stockQuantity;
    private String imageUrl;
    private boolean isActive;
    private int categoryId;
    private int sellerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public Product() {
    }

    public Product(int productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    // ... other getters and setters

    // toString for debugging
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }
}
```

### 6.2 DTO Classes

```java
/**
 * DTO for cart item display
 */
public class CartItemDTO {
    private int productId;
    private String productName;
    private String imageUrl;
    private double price;
    private int quantity;
    private double subtotal;
}
```

---

## 7. DAO Guidelines

### 7.1 DAO Structure

```java
/**
 * File: ProductDAO.java
 * Description: Data access layer for Product entity.
 */
public class ProductDAO implements AutoCloseable {

    private Connection connection;

    public ProductDAO() {
        this.connection = DBConnection.getConnection();
    }

    // CREATE
    public int insert(Product product) {
        String sql = "INSERT INTO Product (ProductName, Description, Price, StockQuantity, CategoryId, SellerId) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStockQuantity());
            ps.setInt(5, product.getCategoryId());
            ps.setInt(6, product.getSellerId());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error inserting product", e);
        }
        return -1;
    }

    // READ
    public Product findById(int id) {
        String sql = "SELECT * FROM Product WHERE ProductId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding product by ID", e);
        }
        return null;
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM Product WHERE IsActive = 1 ORDER BY CreatedAt DESC";
        List<Product> products = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding all products", e);
        }
        return products;
    }

    // UPDATE
    public boolean update(Product product) {
        String sql = "UPDATE Product SET ProductName = ?, Description = ?, Price = ?, "
                   + "StockQuantity = ?, UpdatedAt = GETDATE() WHERE ProductId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStockQuantity());
            ps.setInt(5, product.getProductId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error updating product", e);
        }
    }

    // DELETE
    public boolean delete(int id) {
        // Soft delete - set IsActive = 0
        String sql = "UPDATE Product SET IsActive = 0 WHERE ProductId = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("Error deleting product", e);
        }
    }

    // Helper method to map ResultSet to Product
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("ProductId"));
        product.setProductName(rs.getString("ProductName"));
        product.setDescription(rs.getString("Description"));
        product.setPrice(rs.getDouble("Price"));
        product.setStockQuantity(rs.getInt("StockQuantity"));
        product.setImageUrl(rs.getString("ImageUrl"));
        product.setIsActive(rs.getBoolean("IsActive"));
        product.setCategoryId(rs.getInt("CategoryId"));
        product.setSellerId(rs.getInt("SellerId"));
        product.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        return product;
    }

    @Override
    public void close() {
        DBConnection.closeConnection(connection);
    }
}
```

### 7.2 Transaction Management

```java
public boolean createOrderWithItems(Order order, List<OrderItem> items) {
    Connection conn = null;
    try {
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false);

        // Insert order
        OrderDAO orderDAO = new OrderDAO(conn);
        int orderId = orderDAO.insert(order);

        // Insert items
        for (OrderItem item : items) {
            item.setOrderId(orderId);
            OrderItemDAO itemDAO = new OrderItemDAO(conn);
            itemDAO.insert(item);
        }

        conn.commit();
        return true;

    } catch (Exception e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        throw new DAOException("Error creating order", e);
    } finally {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```

---

## 8. JSP Guidelines

### 8.1 Page Structure

```jsp
<%--
File: product-list.jsp
Description: Display paginated list of products with filtering options.
Created by: [Author]
Created date: [YYYY-MM-DD]
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Danh sách sản phẩm - FruitStore</title>
    <jsp:include page="/WEB-INF/jsp/layout/head.jsp" />
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/layout/navbar.jsp" />

    <main class="container">
        <div class="row">
            <aside class="col-md-3">
                <jsp:include page="/WEB-INF/jsp/layout/sidebar-categories.jsp" />
            </aside>
            <section class="col-md-9">
                <h1>Danh sách sản phẩm</h1>

                <%-- Product Grid --%>
                <div class="row">
                    <c:forEach var="product" items="${products}">
                        <div class="col-md-4 mb-4">
                            <jsp:include page="/WEB-INF/jsp/web/product-card.jsp">
                                <jsp:param name="product" value="${product}" />
                            </jsp:include>
                        </div>
                    </c:forEach>
                </div>

                <%-- Pagination --%>
                <jsp:include page="/WEB-INF/jsp/layout/pagination.jsp" />
            </section>
        </div>
    </main>

    <jsp:include page="/WEB-INF/jsp/layout/footer.jsp" />
    <jsp:include page="/WEB-INF/jsp/layout/scripts.jsp" />
</body>
</html>
```

### 8.2 Using JSTL Tags

```jsp
<%-- Core Tags --%>
<c:if test="${not empty products}">
    <c:forEach var="product" items="${products}" varStatus="status">
        <p>${status.count}. ${product.productName}</p>
    </c:forEach>
</c:if>

<c:choose>
    <c:when test="${user.role == 'ADMIN'}">
        <jsp:include page="/admin-menu.jsp" />
    </c:when>
    <c:when test="${user.role == 'SELLER'}">
        <jsp:include page="/seller-menu.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:include page="/customer-menu.jsp" />
    </c:otherwise>
</c:choose>

<%-- Formatting Tags --%>
<fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫" />
<fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" />

<%-- Functions --%>
<c:if test="${fn:length(products) > 0}">
    Found ${fn:length(products)} products
</c:if>
```

### 8.3 Form Handling

```jsp
<%-- Form with CSRF Protection --%>
<form action="${pageContext.request.contextPath}/product/insert" method="POST">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <div class="mb-3">
        <label for="productName" class="form-label">Tên sản phẩm</label>
        <input type="text" class="form-control" id="productName"
               name="productName" required maxlength="200"
               value="${product.productName}">
        <small class="text-danger">${errors.productName}</small>
    </div>

    <button type="submit" class="btn btn-primary">Lưu</button>
    <a href="${pageContext.request.contextPath}/product/list"
       class="btn btn-secondary">Hủy</a>
</form>
```

---

## 9. SQL Guidelines

### 9.1 Table Creation

```sql
-- ============================================
-- User Account Table
-- ============================================
CREATE TABLE UserAccount (
    UserId INT IDENTITY(1,1) PRIMARY KEY,
    Email VARCHAR(255) NOT NULL UNIQUE,
    PasswordHash VARCHAR(255) NOT NULL,
    FullName NVARCHAR(100) NOT NULL,
    PhoneNumber VARCHAR(20),
    Role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    Status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE()
);

-- ============================================
-- Category Table
-- ============================================
CREATE TABLE Category (
    CategoryId INT IDENTITY(1,1) PRIMARY KEY,
    CategoryName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(500),
    ParentCategoryId INT NULL,
    IsActive BIT DEFAULT 1,
    CreatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (ParentCategoryId) REFERENCES Category(CategoryId)
);

-- ============================================
-- Product Table
-- ============================================
CREATE TABLE Product (
    ProductId INT IDENTITY(1,1) PRIMARY KEY,
    ProductName NVARCHAR(200) NOT NULL,
    Description NVARCHAR(MAX),
    Price DECIMAL(18,2) NOT NULL,
    StockQuantity INT NOT NULL DEFAULT 0,
    ImageUrl VARCHAR(500),
    CategoryId INT NOT NULL,
    SellerId INT NOT NULL,
    IsActive BIT DEFAULT 1,
    CreatedAt DATETIME DEFAULT GETDATE(),
    UpdatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (CategoryId) REFERENCES Category(CategoryId),
    FOREIGN KEY (SellerId) REFERENCES UserAccount(UserId)
);
```

### 9.2 Common Queries

```sql
-- Pagination Query
SELECT * FROM Product
WHERE IsActive = 1
ORDER BY CreatedAt DESC
OFFSET (@page - 1) * @pageSize ROWS
FETCH NEXT @pageSize ROWS ONLY;

-- Search with Filters
SELECT p.*, c.CategoryName, u.FullName as SellerName
FROM Product p
JOIN Category c ON p.CategoryId = c.CategoryId
JOIN UserAccount u ON p.SellerId = u.UserId
WHERE p.IsActive = 1
  AND (@keyword IS NULL OR p.ProductName LIKE '%' + @keyword + '%')
  AND (@categoryId IS NULL OR p.CategoryId = @categoryId)
  AND (@minPrice IS NULL OR p.Price >= @minPrice)
  AND (@maxPrice IS NULL OR p.Price <= @maxPrice)
ORDER BY p.CreatedAt DESC;

-- Order with Items
SELECT o.*, u.FullName, u.Email,
       oi.ProductId, oi.Quantity, oi.Price as ItemPrice,
       p.ProductName
FROM [Order] o
JOIN UserAccount u ON o.UserId = u.UserId
JOIN OrderItem oi ON o.OrderId = oi.OrderId
JOIN Product p ON oi.ProductId = p.ProductId
WHERE o.OrderId = @orderId;
```

---

## 10. Validation Rules

### 10.1 Input Validation

| Field | Rules |
|-------|-------|
| Email | Valid email format, max 255 chars |
| Password | Min 8 chars, 1 uppercase, 1 number, 1 special char |
| Phone | Vietnam phone format (0xxx) |
| Name | 2-100 chars, no special characters |
| Price | Positive number, max 2 decimal places |
| Quantity | Non-negative integer |
| Address | Max 500 characters |

### 10.2 Validation Example

```java
public class ValidationUtil {

    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email không được để trống";
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(regex)) {
            return "Email không hợp lệ";
        }
        if (email.length() > 255) {
            return "Email quá dài";
        }
        return null;
    }

    public static String validatePassword(String password) {
        if (password == null || password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Mật khẩu phải chứa ít nhất 1 chữ hoa";
        }
        if (!password.matches(".*\\d.*")) {
            return "Mật khẩu phải chứa ít nhất 1 số";
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            return "Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt";
        }
        return null;
    }

    public static String validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return null; // Phone is optional
        }
        String regex = "^(0[0-9]{9,10})$";
        if (!phone.matches(regex)) {
            return "Số điện thoại không hợp lệ";
        }
        return null;
    }
}
```

---

## 11. Error Handling

### 11.1 Exception Classes

```java
public class DAOException extends RuntimeException {
    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class ValidationException extends Exception {
    private Map<String, String> errors;

    public ValidationException(String message) {
        super(message);
        this.errors = new HashMap<>();
    }

    public ValidationException(Map<String, String> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
```

### 11.2 Error Pages

```jsp
<%-- error-404.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<h1>404 - Trang không tìm thấy</h1>
<p>Trang bạn đang tìm kiếm không tồn tại.</p>
<a href="${pageContext.request.contextPath}/">Về trang chủ</a>
```

---

## 12. Security Guidelines

### 12.1 Password Security

```java
// Hashing password with BCrypt
public class PasswordUtil {

    private static final int BCRYPT_ROUNDS = 12;

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
```

### 12.2 SQL Injection Prevention

```java
// ALWAYS use PreparedStatement
// BAD
String sql = "SELECT * FROM User WHERE email = '" + email + "'";

// GOOD
String sql = "SELECT * FROM User WHERE email = ?";
PreparedStatement ps = connection.prepareStatement(sql);
ps.setString(1, email);
```

### 12.3 XSS Prevention

```java
// In JSP, always escape output
${fn:escapeXml(userInput)}

// Or use JSTL c:out
<c:out value="${userInput}" />
```

### 12.4 CSRF Protection

```java
// Generate token on form display
session.setAttribute("csrfToken", UUID.randomUUID().toString());

// Verify on form submission
String token = request.getParameter("csrfToken");
if (!token.equals(session.getAttribute("csrfToken"))) {
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
}
```

---

## 13. Git Workflow

### 13.1 Branch Naming

```
feature/[issue-id]-short-description
bugfix/[issue-id]-short-description
hotfix/[issue-id]-short-description
release/[version]
```

**Examples:**
- `feature/23-user-registration`
- `feature/25-product-search`
- `bugfix/30-cart-quantity-bug`
- `hotfix/45-login-error`
- `release/v1.0.0`

### 13.2 Commit Messages

```
[type]: [short description]

[Optional body with more details]

[Optional footer with issue reference]

Types:
- feat: New feature
- fix: Bug fix
- refactor: Code refactoring
- docs: Documentation
- style: Formatting, style changes
- test: Adding tests
- chore: Maintenance, dependencies
- perf: Performance improvements
```

**Examples:**

```
feat: add user registration functionality

- implement registration form with validation
- add email verification flow
- integrate with UserDAO

Closes #23
```

```
fix: resolve cart quantity update issue

- fix race condition in concurrent updates
- add proper error handling
- update unit tests

Closes #30
```

### 13.3 Pull Request Template

```markdown
## Description
[Describe what this PR does]

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests added/updated
- [ ] Manual testing performed

## Screenshots (if applicable)

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex code
- [ ] Documentation updated
```

---

## 14. Naming Conventions

### 14.1 Java

| Type | Convention | Example |
|------|------------|---------|
| Class/Interface | PascalCase | `UserDAO`, `ProductController` |
| Method | camelCase | `findById`, `updateUser` |
| Variable | camelCase | `userId`, `productList` |
| Constant | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT` |
| Package | lowercase | `com.fruitshop.dao` |
| Enum | PascalCase | `OrderStatus.PENDING` |

### 14.2 Database

| Type | Convention | Example |
|------|------------|---------|
| Table | PascalCase | `UserAccount`, `OrderItem` |
| Column | snake_case | `user_id`, `created_at` |
| Primary Key | `[Table]Id` | `UserId`, `OrderId` |
| Foreign Key | `[Table]Id` | `SellerId`, `CategoryId` |
| Boolean | `is_[status]` | `is_active`, `is_verified` |

### 14.3 JSP/HTML

| Type | Convention | Example |
|------|------------|---------|
| File | kebab-case | `product-list.jsp` |
| ID | camelCase | `productNameInput` |
| Class | kebab-case | `btn-primary` |
| Form Name | snake_case | `registration_form` |

### 14.4 URL Routes

| URL | HTTP Method | Description |
|-----|-------------|-------------|
| `/auth/login` | GET/POST | Login page |
| `/auth/register` | GET/POST | Registration page |
| `/product/list` | GET | Product listing |
| `/product/detail?id=1` | GET | Product detail |
| `/product/add` | GET/POST | Add product |
| `/product/edit?id=1` | GET/POST | Edit product |
| `/product/delete?id=1` | POST | Delete product |
| `/cart/add` | POST | Add to cart |
| `/order/checkout` | GET/POST | Checkout page |

---

## Quick Reference

### Common HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK |
| 201 | Created |
| 302 | Redirect |
| 400 | Bad Request |
| 401 | Unauthorized |
| 403 | Forbidden |
| 404 | Not Found |
| 500 | Internal Server Error |

### Common Session Attributes

```java
// User session
session.setAttribute("user", user);
session.setAttribute("userId", user.getUserId());
session.setAttribute("role", user.getRole());

// Cart session
session.setAttribute("cart", cart);
session.setAttribute("cartItemCount", cart.size());

// Flash messages
session.setAttribute("success", "Operation successful!");
session.setAttribute("error", "Something went wrong!");
```

---

**Document Version**: 1.0
**Last Updated**: 2026-05-23
**Maintained By**: FruitStore Development Team
