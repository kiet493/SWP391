# FreshNest - Project Development Guidelines

**Version:** 1.0.0
**Last Updated:** May 2026
**Type:** Java Web E-Commerce Application (Fruit Shop Online)

---

## 1. Project Overview

### 1.1 Project Information

| Attribute | Value |
|-----------|-------|
| **Project Name** | FreshNest (Fruit Shop Online) |
| **Type** | E-Commerce Web Application |
| **Technology** | Java Servlet, JSP, Maven, JDBC |
| **Database** | SQL Server |
| **Package** | `com.fruitshop` |
| **Architecture** | MVC (Model-View-Controller) |

### 1.2 Project Description

Dự án xây dựng hệ thống web thương mại điện tử bán trái cây online, cung cấp đầy đủ chức năng cho:

- **Customer (Khách hàng):** Đăng ký/đăng nhập, xem/tìm kiếm sản phẩm, giỏ hàng, thanh toán, lịch sử đơn hàng, quản lý profile
- **Seller (Người bán):** Quản lý shop, CRUD sản phẩm, quản lý đơn hàng, theo dõi doanh thu
- **Admin (Quản trị viên):** Quản lý user/seller/sản phẩm/category/đơn hàng, thống kê hệ thống, kiểm duyệt nội dung

### 1.3 Main Features

| Category | Features |
|----------|----------|
| **Authentication** | Login, Register, Logout, Forgot password, Verify email, Role-based access |
| **Product** | Danh sách sản phẩm, Product detail, Category, Search, Filter, Pagination |
| **Cart** | Add to cart, Update quantity, Remove item, Checkout |
| **Order** | Tạo đơn hàng, Quản lý trạng thái đơn, Order history |
| **Seller Dashboard** | CRUD sản phẩm, Quản lý shop, Quản lý đơn hàng |
| **Admin Dashboard** | User/Seller/Product/Category/Order management, Revenue statistics |

---

## 2. Architecture (MVC)

Dự án sử dụng mô hình **MVC (Model - View - Controller)** với các tầng rõ ràng:

```
┌─────────────────────────────────────────────────────────┐
│                    Controller Layer                      │
│  (Nhận request, Validate, Gọi Service, Forward JSP)   │
│  Ví dụ: LoginController, HomeController, CartController │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                    Service Layer                         │
│  (Xử lý logic nghiệp vụ, Tính toán, Kiểm tra dữ liệu)  │
│  Ví dụ: ProductService, OrderService, UserService      │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                      DAO Layer                           │
│  (CRUD database, Query, Mapping ResultSet → Model)      │
│  Ví dụ: ProductDAO, UserDAO, OrderDAO                  │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                   Model / Database                       │
│  (Entity đại diện bảng, SQL Server)                     │
└─────────────────────────────────────────────────────────┘
```

---

## 3. Folder Structure Convention

```
src/main/java/com/fruitshop/
├── controller/              # HTTP Request Handlers
│   ├── admin/              # Admin dashboard controllers
│   ├── auth/               # Authentication controllers
│   ├── seller/             # Seller operations controllers
│   └── web/                # Public web controllers
├── dao/                    # Data Access Layer
│   └── impl/               # DAO implementations
├── service/                # Business Logic Layer
│   └── impl/               # Service implementations
├── filter/                 # Servlet Filters (Auth, Encoding)
├── model/                  # Entity classes
└── util/                   # Utility classes

src/main/webapp/
├── assets/
│   ├── css/                # Stylesheets
│   ├── js/                 # JavaScript files
│   └── images/
│       ├── avatars/        # User avatars
│       └── products/       # Product images
├── WEB-INF/
│   ├── views/              # JSP templates
│   │   ├── admin/          # Admin views
│   │   ├── auth/            # Login/Register views
│   │   ├── layout/          # Shared layouts (header, footer)
│   │   ├── seller/          # Seller views
│   │   └── web/             # Public views
│   ├── error/              # Error pages
│   └── web.xml             # Web application config
└── index.jsp               # Entry point

src/main/resources/
├── config.properties       # Database credentials (NOT commit)
└── config.local.properties
```

---

## 4. Coding Standards (Java)

### 4.1 Java Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Class | PascalCase | `UserController`, `ProductService` |
| Method | camelCase | `getUserById()`, `updateProduct()` |
| Variable | camelCase | `userId`, `productName` |
| Constant | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT`, `DEFAULT_PAGE_SIZE` |
| Package | lowercase | `com.fruitshop.controller` |

### 4.2 Package Responsibilities

| Package | Responsibility |
|---------|----------------|
| `controller` | Handle HTTP requests, validate input, call services, forward views |
| `service` | Business logic, transaction management, calculations |
| `dao` | Database operations, CRUD, ResultSet mapping |
| `filter` | Cross-cutting concerns (authentication, encoding, logging) |
| `model` | Entity classes representing database tables |
| `util` | Helper methods, constants, validators |

---

## 5. Controller Rules

### 5.1 Controller Responsibilities

- Nhận request từ client
- Validate dữ liệu đầu vào
- Gọi Service layer
- Forward/SendRedirect sang JSP
- Xử lý errors và exceptions

### 5.2 Controller Naming Convention

- Đặt tên theo chức năng: `<Feature>Controller`
- Ví dụ: `LoginController`, `ProductController`, `OrderController`
- Đặt trong package phù hợp: `admin/`, `auth/`, `seller/`, `web/`

### 5.3 Controller Example

```java
@WebServlet("/product/*")
public class ProductController extends HttpServlet {
    private ProductService productService = new ProductServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null) action = "/list";

        switch (action) {
            case "/list":
                listProducts(request, response);
                break;
            case "/detail":
                getProductDetail(request, response);
                break;
            default:
                response.sendRedirect("/error/404");
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/views/web/products.jsp").forward(request, response);
    }
}
```

---

## 6. Service Layer Rules

### 6.1 Service Responsibilities

- Xử lý logic nghiệp vụ
- Tính toán và validation
- Điều phối các DAO
- Quản lý transaction
- Không truy cập trực tiếp HttpServletRequest/Response

### 6.2 Service Interface Pattern

```java
public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(int id);
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int id);
}

public class ProductServiceImpl implements ProductService {
    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    @Override
    public boolean addProduct(Product product) {
        // Business validation
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        return productDAO.insert(product);
    }
}
```

---

## 7. DAO Layer Rules

### 7.1 DAO Responsibilities

- CRUD database operations
- Execute SQL queries
- Map ResultSet → Model objects
- Close resources properly (Connection, PreparedStatement, ResultSet)

### 7.2 DAO Implementation Pattern

```java
public class ProductDAOImpl implements ProductDAO {
    private static final String SELECT_ALL = "SELECT * FROM products WHERE status = 'ACTIVE'";
    private static final String SELECT_BY_ID = "SELECT * FROM products WHERE id = ?";
    private static final String INSERT = "INSERT INTO products (name, price, quantity, ...) VALUES (?, ?, ...)";
    private static final String UPDATE = "UPDATE products SET name = ?, price = ?, ... WHERE id = ?";
    private static final String DELETE = "UPDATE products SET status = 'INACTIVE' WHERE id = ?";

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setQuantity(rs.getInt("quantity"));
        // ... other fields
        return p;
    }
}
```

### 7.3 Prepared Statement Rule

**LUÔN sử dụng PreparedStatement** để ngăn SQL Injection:

```java
// ✅ ĐÚNG
PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
ps.setString(1, email);

// ❌ SAI - SQL Injection vulnerability
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE email = '" + email + "'");
```

---

## 8. JSP/View Rules

### 8.1 View Organization

- Views located in `src/main/webapp/WEB-INF/views/`
- Use role-based subdirectories: `admin/`, `auth/`, `seller/`, `web/`
- Shared components in `layout/` (header, footer, sidebar)

### 8.2 JSP Best Practices

- Sử dụng JSTL và EL expression thay vì scriptlet
- Include header/footer qua `jsp:include`
- Sử dụng `<c:if>`, `<c:forEach>` cho logic
- Escape output để ngăn XSS: `<c:out value="${variable}"/>`

### 8.3 JSP Example

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/WEB-INF/views/layout/header.jsp" />

<div class="container">
    <h1>Danh sách sản phẩm</h1>

    <c:choose>
        <c:when test="${not empty products}">
            <div class="row">
                <c:forEach var="product" items="${products}">
                    <div class="col-md-4 mb-3">
                        <div class="card">
                            <img src="${pageContext.request.contextPath}/assets/images/products/${product.imageUrl}"
                                 class="card-img-top" alt="${product.name}">
                            <div class="card-body">
                                <h5 class="card-title">${product.name}</h5>
                                <p class="card-text">
                                    <fmt:formatNumber value="${product.price}" pattern="#,### VND" />
                                </p>
                                <a href="${pageContext.request.contextPath}/product/detail?id=${product.id}"
                                   class="btn btn-primary">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p class="text-center">Không có sản phẩm nào.</p>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
```

---

## 9. Database Guidelines (SQL Server)

### 9.1 Database Tables

```
users
├── id (PK, INT IDENTITY)
├── email (UNIQUE, NVARCHAR)
├── password (NVARCHAR - hashed)
├── full_name (NVARCHAR)
├── phone (NVARCHAR)
├── address (NVARCHAR)
├── role (ENUM: ADMIN, SELLER, CUSTOMER)
├── created_at (DATETIME)
└── updated_at (DATETIME)

products
├── id (PK, INT IDENTITY)
├── name (NVARCHAR)
├── description (NVARCHAR)
├── price (DECIMAL(10,2))
├── quantity (INT)
├── category_id (FK → categories.id)
├── image_url (NVARCHAR)
├── seller_id (FK → users.id)
├── status (ENUM: ACTIVE, INACTIVE)
├── created_at (DATETIME)
└── updated_at (DATETIME)

categories
├── id (PK, INT IDENTITY)
├── name (NVARCHAR)
└── description (NVARCHAR)

orders
├── id (PK, INT IDENTITY)
├── user_id (FK → users.id)
├── total_amount (DECIMAL(10,2))
├── status (ENUM: PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED)
├── shipping_address (NVARCHAR)
├── order_date (DATETIME)
└── notes (NVARCHAR)

order_items
├── id (PK, INT IDENTITY)
├── order_id (FK → orders.id)
├── product_id (FK → products.id)
├── quantity (INT)
└── price (DECIMAL(10,2))

carts
├── id (PK, INT IDENTITY)
├── user_id (FK → users.id, UNIQUE)
└── created_at (DATETIME)

cart_items
├── id (PK, INT IDENTITY)
├── cart_id (FK → carts.id)
├── product_id (FK → products.id)
├── quantity (INT)
└── added_at (DATETIME)

reviews
├── id (PK, INT IDENTITY)
├── product_id (FK → products.id)
├── user_id (FK → users.id)
├── rating (INT, 1-5)
├── comment (NVARCHAR)
└── created_at (DATETIME)

shops
├── id (PK, INT IDENTITY)
├── seller_id (FK → users.id, UNIQUE)
├── shop_name (NVARCHAR)
├── shop_description (NVARCHAR)
└── rating (DECIMAL(3,2))
```

### 9.2 Connection Configuration

```properties
# src/main/resources/config.properties (DO NOT COMMIT)
db.url=jdbc:sqlserver://localhost:1433;databaseName=FreshNest
db.username=sa
db.password=your_password
db.driver=com.microsoft.sqlserver.jdbc.SQLServerDriver
```

### 9.3 Database Rules

- Store credentials in `src/main/resources/config.properties`
- **DO NOT commit config.properties to Git**
- Use environment variables for production
- Always use transactions for multi-table operations

---

## 10. Authentication & Authorization Rules

### 10.1 Authentication System

Dự án có các filter:

| Filter | Purpose |
|--------|---------|
| `SecurityFilter` | Kiểm tra login, session |
| `AdminFilter` | Phân quyền Admin |
| `SellerFilter` | Phân quyền Seller |

### 10.2 Role-Based Access Control

```java
// Trong SecurityFilter
switch (role) {
    case "ADMIN":
        if (!requestedPath.startsWith("/admin/")) {
            response.sendRedirect("/admin/dashboard");
        }
        break;
    case "SELLER":
        if (!requestedPath.startsWith("/seller/")) {
            response.sendRedirect("/seller/dashboard");
        }
        break;
    case "CUSTOMER":
        if (requestedPath.startsWith("/admin/") || requestedPath.startsWith("/seller/")) {
            response.sendRedirect("/");
        }
        break;
}
```

### 10.3 Access Rules

| Role | Accessible Paths |
|------|------------------|
| Guest | `/`, `/auth/*`, `/product/*`, `/search` |
| Customer | Above + `/cart/*`, `/order/*`, `/profile/*` |
| Seller | Above + `/seller/*` |
| Admin | All paths |

---

## 11. Git Workflow

### 11.1 Branch Naming Convention

| Branch | Purpose | Example |
|--------|---------|---------|
| `main` | Production-ready code | - |
| `develop` | Integration branch | - |
| `feature/<feature-name>` | New features | `feature/user-authentication` |
| `bugfix/<issue-id>` | Bug fixes | `bugfix/fix-login-error-123` |
| `hotfix/<issue-id>` | Critical production fixes | `hotfix/security-patch-456` |

### 11.2 Workflow Steps

```
1. Tạo branch từ develop: git checkout -b feature/add-product-review
2. Làm việc và commit thường xuyên
3. Push branch lên remote
4. Tạo Pull Request (PR) vào develop
5. Review code
6. Merge sau khi approved
```

---

## 12. Commit Convention

### 12.1 Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### 12.2 Commit Types

| Type | Description |
|------|-------------|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation changes |
| `style` | Formatting, no code change |
| `refactor` | Code restructuring |
| `test` | Adding tests |
| `chore` | Maintenance, dependencies |

### 12.3 Commit Examples

```
feat(auth): add password reset functionality

- Add forgot password form
- Implement email verification
- Add reset password endpoint

Closes #123
```

```
fix(cart): resolve quantity update bug

- Fixed negative quantity issue
- Added validation for max stock

Closes #456
```

---

## 13. Branch Naming Convention

| Pattern | Example | Usage |
|---------|---------|-------|
| `feature/<name>` | `feature/user-authentication` | New features |
| `bugfix/<id>-<name>` | `bugfix/123-login-error` | Bug fixes |
| `hotfix/<id>-<name>` | `hotfix/456-security-patch` | Urgent fixes |
| `refactor/<name>` | `refactor DAO-layer` | Code improvements |

---

## 14. Business Rules Summary

### 14.1 Order Status Flow

```
PENDING → CONFIRMED → SHIPPING → DELIVERED
    ↓         ↓           ↓
CANCELLED  CANCELLED  CANCELLED
```

### 14.2 Product Rules

- Price must be > 0
- Quantity must be >= 0
- Inactive products hidden from customers
- Seller can only manage own products

### 14.3 Cart Rules

- One cart per user
- Max 99 items per product
- Auto-remove when product becomes unavailable

### 14.4 User Roles

| Role | Permissions |
|------|-------------|
| CUSTOMER | Browse, Cart, Order, Profile |
| SELLER | Customer + Manage products, View orders, Shop settings |
| ADMIN | Seller + User management, All statistics, System settings |

---

## 15. Security Rules

### 15.1 Password Security

- Hash passwords using **BCrypt** or **Argon2**
- Never store plain text passwords
- Minimum password length: 8 characters

### 15.2 Input Validation

- Validate ALL user inputs on server-side
- Use prepared statements (anti SQL injection)
- Escape output in JSP (anti XSS)
- Sanitize file uploads

### 15.3 Session Security

- Use HTTPS in production
- Set secure cookie flags
- Implement CSRF protection for forms
- Session timeout: 30 minutes

### 15.4 Sensitive Files (Already in .gitignore)

```
src/main/resources/config.properties
src/main/resources/config.local.properties
.env
.env.local
```

---

## 16. Performance Guidelines

### 16.1 Database Optimization

- Index frequently queried columns
- Use pagination for large lists
- Avoid SELECT * (specify columns)
- Close database resources properly

### 16.2 Caching

- Cache static data (categories, settings)
- Consider Redis for session management

### 16.3 JSP Performance

- Use `<c:out>` for output
- Avoid nested loops in JSP
- Use JSTL instead of scriptlets
- Enable JSP caching for static content

---

## 17. Deployment Notes

### 17.1 Prerequisites

| Tool | Version |
|------|---------|
| JDK | 11+ |
| Apache Maven | 3.6+ |
| Apache Tomcat | 9.x or 10.x |
| SQL Server | 2019+ |

### 17.2 Build & Run Commands

```bash
# Build project
mvn clean package

# Run locally (with Maven Tomcat plugin)
mvn tomcat7:run

# Skip tests
mvn clean package -DskipTests
```

### 17.3 IDE Setup

**IntelliJ IDEA:**
- Import as Maven project
- Configure Tomcat server
- Enable annotation processing

**VS Code / Cursor:**
- Install Java Extension Pack
- Configure `launch.json` for Tomcat

### 17.4 Environment Variables (Production)

```bash
# Database
DB_URL=jdbc:sqlserver://prod-server:1433;databaseName=FreshNest
DB_USERNAME=prod_user
DB_PASSWORD=secure_password

# Application
APP_ENV=production
SESSION_TIMEOUT=1800
```

---

## 18. Testing Guidelines

### 18.1 Test Coverage Targets

| Layer | Target |
|-------|--------|
| Service | 80%+ |
| DAO | 70%+ |
| Controller | 50%+ |

### 18.2 Test Naming Convention

```
MethodName_Scenario_ExpectedResult
```

**Example:**
```
calculateTotalPrice_WithMultipleItems_ReturnsCorrectSum
```

---

## 19. Code Review Checklist

- [ ] Follows naming conventions
- [ ] No code duplication
- [ ] Proper error handling
- [ ] SQL injection prevention
- [ ] XSS prevention
- [ ] Performance considerations
- [ ] Unit tests added/updated
- [ ] Documentation updated

### Pre-commit Checklist

- [ ] Code compiles without errors
- [ ] No hardcoded credentials
- [ ] Sensitive files not staged
- [ ] Commit message follows format
- [ ] Tests pass (if applicable)

---

## 20. Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| `ClassNotFoundException` | Check Maven dependencies |
| `404 on JSP` | Verify view path in controller |
| `Encoding issues` | Add filter with UTF-8 encoding |
| `Session lost` | Check cookie settings |
| `DB connection failed` | Verify config.properties |
| `SQLServer driver not found` | Add dependency to pom.xml |

---

## 21. Academic Objectives

Dự án này phù hợp để học:

- MVC architecture
- Java Web (Servlet/JSP)
- JDBC database integration
- Authentication & Authorization
- E-commerce workflow
- Clean Architecture
- Layered Design
- SQL Server integration
- Session & Cookie handling
- JSP/Servlet lifecycle

### Future Upgrade Path

```
Current: Java Servlet + JSP
    ↓
Spring MVC → Spring Boot
    ↓
RESTful API
    ↓
ReactJS Frontend
    ↓
Microservices + Docker + Cloud
```

---

## 22. Resources

- [Java Servlet Documentation](https://docs.oracle.com/javaee/7/tutorial/part servlets.htm)
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [JSP Tutorial](https://www.oracle.com/java/technologies/jsp.html)
- [SQL Server Documentation](https://docs.microsoft.com/en-us/sql/sql-server/)
- [Bootstrap 5 Documentation](https://getbootstrap.com/docs/5.3/)

---

**Document Version:** 1.0.0
**Last Updated:** May 2026
**Project:** FreshNest E-Commerce Platform
