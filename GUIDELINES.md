# FruitShop E-Commerce - Development Guidelines

## 1. Project Overview

| Attribute | Value |
|-----------|-------|
| **Project Name** | FruitShop |
| **Type** | Java Web Application (E-Commerce) |
| **Technology** | Java Servlet, JSP, Maven |
| **Package** | `com.fruitshop` |
| **Architecture** | MVC (Model-View-Controller) |

---

## 2. Project Structure

```
d:\Ky8\SWP391
├── src/main/java/com/fruitshop/
│   ├── controller/          # HTTP Request Handlers
│   │   ├── admin/            # Admin dashboard controllers
│   │   ├── auth/              # Authentication controllers
│   │   ├── seller/            # Seller operations controllers
│   │   └── web/                # Public web controllers
│   ├── dao/                   # Data Access Layer
│   │   └── impl/               # DAO implementations
│   ├── service/               # Business Logic Layer
│   │   └── impl/               # Service implementations
│   ├── filter/                # Servlet Filters (Auth, Encoding)
│   └── util/                   # Utility classes
├── src/main/webapp/
│   ├── assets/
│   │   ├── css/               # Stylesheets
│   │   ├── js/                # JavaScript files
│   │   └── images/             # Static images
│   │       ├── avatars/       # User avatars
│   │       └── products/       # Product images
│   ├── WEB-INF/
│   │   ├── views/             # JSP templates
│   │   │   ├── admin/          # Admin views
│   │   │   ├── auth/           # Login/Register views
│   │   │   ├── layout/         # Shared layouts (header, footer)
│   │   │   ├── seller/         # Seller views
│   │   │   └── web/            # Public views
│   │   ├── error/             # Error pages
│   │   └── web.xml            # Web application config
│   └── index.jsp              # Entry point
├── pom.xml                    # Maven configuration
└── .gitignore                 # Git ignore rules
```

---

## 3. Coding Conventions

### 3.1 Java Naming Conventions

| Type | Convention | Example |
|------|------------|---------|
| Class | PascalCase | `UserController`, `ProductService` |
| Method | camelCase | `getUserById()`, `updateProduct()` |
| Variable | camelCase | `userId`, `productName` |
| Constant | UPPER_SNAKE_CASE | `MAX_RETRY_COUNT`, `DEFAULT_PAGE_SIZE` |
| Package | lowercase | `com.fruitshop.controller` |

### 3.2 Package Responsibilities

| Package | Responsibility |
|---------|----------------|
| `controller` | Handle HTTP requests, validate input, call services |
| `service` | Business logic, transaction management |
| `dao` | Database operations, CRUD |
| `filter` | Cross-cutting concerns (auth, encoding, logging) |
| `util` | Helper methods, constants, validators |

### 3.3 JSP File Organization

- Views are located in `src/main/webapp/WEB-INF/views/`
- Use role-based subdirectories: `admin/`, `auth/`, `seller/`, `web/`
- Shared components in `layout/` (header, footer, sidebar)

---

## 4. Database Design

### 4.1 Recommended Tables

```
users
├── id (PK)
├── email
├── password (hashed)
├── full_name
├── phone
├── address
├── role (ADMIN, SELLER, CUSTOMER)
├── created_at
└── updated_at

products
├── id (PK)
├── name
├── description
├── price
├── quantity
├── category_id (FK)
├── image_url
├── seller_id (FK)
├── status (ACTIVE, INACTIVE)
├── created_at
└── updated_at

categories
├── id (PK)
├── name
└── description

orders
├── id (PK)
├── user_id (FK)
├── total_amount
├── status (PENDING, CONFIRMED, SHIPPING, DELIVERED, CANCELLED)
├── shipping_address
├── order_date
└── notes

order_items
├── id (PK)
├── order_id (FK)
├── product_id (FK)
├── quantity
└── price
```

### 4.2 Connection Configuration

- Store database credentials in `src/main/resources/config.properties`
- **DO NOT commit config.properties to Git**
- Use environment variables for production

---

## 5. Security Guidelines

### 5.1 Authentication & Authorization

- Hash passwords using **BCrypt** or **Argon2**
- Use **HTTPS** in production
- Implement **CSRF protection** for forms
- Validate all user inputs on server-side
- Use **prepared statements** for SQL queries

### 5.2 Sensitive Files (Already in .gitignore)

```
src/main/resources/config.properties
src/main/resources/config.local.properties
.env
.env.local
```

---

## 6. Git Workflow

### 6.1 Branch Naming

| Branch | Purpose |
|--------|---------|
| `main` | Production-ready code |
| `develop` | Integration branch |
| `feature/<feature-name>` | New features |
| `bugfix/<issue-id>` | Bug fixes |
| `hotfix/<issue-id>` | Critical production fixes |

### 6.2 Commit Message Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Formatting
- `refactor`: Code restructuring
- `test`: Adding tests
- `chore`: Maintenance

**Example:**
```
feat(auth): add password reset functionality

- Add forgot password form
- Implement email verification
- Add reset password endpoint

Closes #123
```

### 6.3 Pre-commit Checklist

- [ ] Code compiles without errors
- [ ] No hardcoded credentials
- [ ] Sensitive files not staged
- [ ] Commit message follows format
- [ ] Tests pass (if applicable)

---

## 7. Development Setup

### 7.1 Prerequisites

| Tool | Version |
|------|---------|
| JDK | 11+ |
| Apache Maven | 3.6+ |
| Apache Tomcat | 9.x or 10.x |
| MySQL | 8.0+ |

### 7.2 IDE Setup

**IntelliJ IDEA:**
- Import as Maven project
- Configure Tomcat server
- Enable annotation processing

**VS Code / Cursor:**
- Install Java Extension Pack
- Configure `launch.json` for Tomcat

### 7.3 Build & Run

```bash
# Build project
mvn clean package

# Run locally (with Maven Tomcat plugin)
mvn tomcat7:run

# Skip tests
mvn clean package -DskipTests
```

---

## 8. Testing Guidelines

### 8.1 Test Coverage Targets

| Layer | Target |
|-------|--------|
| Service | 80%+ |
| DAO | 70%+ |
| Controller | 50%+ |

### 8.2 Test Naming

```
MethodName_Scenario_ExpectedResult
```

**Example:**
```
calculateTotalPrice_WithMultipleItems_ReturnsCorrectSum
```

---

## 9. Code Review Checklist

- [ ] Follows naming conventions
- [ ] No code duplication
- [ ] Proper error handling
- [ ] SQL injection prevention
- [ ] XSS prevention
- [ ] Performance considerations
- [ ] Unit tests added/updated
- [ ] Documentation updated

---

## 10. Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| `ClassNotFoundException` | Check Maven dependencies |
| `404 on JSP` | Verify view path in controller |
| `Encoding issues` | Add filter with UTF-8 encoding |
| `Session lost` | Check cookie settings |
| `DB connection failed` | Verify config.properties |

---

## 11. Resources

- [Java Servlet Documentation](https://docs.oracle.com/javaee/7/tutorial/part servlets.htm)
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [JSP Tutorial](https://www.oracle.com/java/technologies/jsp.html)

---

**Last Updated:** May 2026
**Version:** 1.0.0
