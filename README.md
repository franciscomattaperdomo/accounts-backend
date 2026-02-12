# Microservicio de Apertura de Cuentas para clientes nuevos (Backend)

## 📋 Descripción General

Este es un microservicio desarrollado con **Spring Boot 4.0.0** que gestiona cuentas bancarias y clientes de una entidad financiera. Proporciona una API REST completa y robusta para operaciones CRUD de clientes y cuentas, con validación exhaustiva de datos, manejo centralizado de excepciones, auditoría automática de entidades y cobertura integral de pruebas unitarias.

El microservicio implementa las mejores prácticas de desarrollo incluyendo:
- Separación clara de responsabilidades (Controller-Service-Repository)
- DTOs para transferencia de datos
- Mapeadores de entidades
- Excepciones personalizadas
- Validaciones bean-based
- Documentación interactiva con Swagger/OpenAPI

## 🎯 Características Principales

- ✅ **Gestión completa de Clientes** (Create, Read, Update, Delete)
  - Búsqueda por ID, Email, Número de móvil
  - Listado de todos los clientes
  - Validación de datos únicos (Email, Móvil, Documento)
  
- ✅ **Gestión completa de Cuentas** (Create, Read, Update, Delete)
  - Búsqueda por múltiples criterios (Cliente, Sucursal, Tipo, Estado)
  - Generación automática de números de cuenta
  - Gestión del estado de cuentas
  
- ✅ **Validación de datos** integrada con Jakarta Validation
- ✅ **Manejo centralizado de excepciones** con GlobalExceptionHandler
- ✅ **Auditoría automática** de creación y modificación
- ✅ **Cobertura de pruebas unitarias** >80% (59 tests)
- ✅ **Documentación OpenAPI/Swagger** interactiva
- ✅ **Base de datos H2** embebida (desarrollo) / PostgreSQL (producción)
- ✅ **Métricas de cobertura** con JaCoCo
- ✅ **CORS habilitado** para cliente Angular

## 🏗️ Arquitectura

### Estructura del Proyecto

```
accounts/
├── src/
│   ├── main/
│   │   ├── java/com/fmattaperdomo/accounts/
│   │   │   ├── controller/        # Controladores REST
│   │   │   ├── service/           # Interfaces de servicios
│   │   │   ├── service/impl/      # Implementaciones de servicios
│   │   │   ├── entity/            # Entidades JPA
│   │   │   ├── dto/               # Data Transfer Objects
│   │   │   ├── mapper/            # Mapeadores de datos
│   │   │   ├── repository/        # Repositorios JPA
│   │   │   ├── exception/         # Excepciones personalizadas
│   │   │   ├── audit/             # Auditoría de entidades
│   │   │   ├── constant/          # Constantes de la aplicación
│   │   │   └── AccountsApplication.java
│   │   └── resources/
│   │       ├── application.yml     # Configuración de la aplicación
│   │       └── schema.sql          # DDL de la base de datos
│   └── test/
│       └── java/com/fmattaperdomo/accounts/
│           ├── controller/        # Tests de controladores
│           └── service/impl/      # Tests de servicios
├── pom.xml
└── README.md
```

## 🔧 Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|-----------|---------|------------|
| Java | 21 | Lenguaje de programación |
| Spring Boot | 4.0.0 | Framework web |
| Spring Data JPA | 4.0.0 | ORM y acceso a datos |
| H2 Database | Latest | Base de datos embebida |
| Lombok | Latest | Reducción de boilerplate |
| SpringDoc OpenAPI | 2.8.14 | Documentación automática |
| JUnit 5 | Latest | Framework de testing |
| Mockito | Latest | Mocking para tests |
| AssertJ | Latest | Assertions fluidas |
| JaCoCo | 0.8.14 | Cobertura de código |

## 📦 Módulos Principales

### 1. Controllers

#### **CustomerController**
API REST para gestión de clientes:
- `POST /api/customers` - Crear cliente
- `GET /api/customers/{customerId}` - Obtener cliente por ID
- `GET /api/customers/mobileNumber/{mobileNumber}` - Obtener cliente por móvil
- `GET /api/customers/email/{email}` - Obtener cliente por email
- `GET /api/customers` - Obtener todos los clientes
- `PUT /api/customers/{customerId}` - Actualizar cliente
- `DELETE /api/customers/{customerId}` - Eliminar cliente

#### **AccountController**
API REST para gestión de cuentas:
- `POST /api/accounts` - Crear cuenta
- `GET /api/accounts/customerId/{customerId}` - Obtener cuentas por cliente (lista)
- `GET /api/accounts/branchName/{branchName}` - Obtener cuentas por sucursal
- `GET /api/accounts/accountType/{accountType}` - Obtener cuentas por tipo
- `GET /api/accounts/accountStatus/{accountStatus}` - Obtener cuentas por estado
- `GET /api/accounts?customerId={customerId}` - Obtener cuenta activa por cliente
- `GET /api/accounts/all` - Obtener todas las cuentas
- `PUT /api/accounts/{accountId}` - Actualizar cuenta
- `DELETE /api/accounts/{accountId}` - Eliminar cuenta

### 2. Servicios

#### **CustomerService**
- `createCustomer(CustomerRequestDto)` - Crear nuevo cliente
- `updateCustomer(CustomerRequestDto, Long)` - Actualizar cliente
- `deleteCustomer(Long)` - Eliminar cliente
- `getCustomerById(Long)` - Buscar cliente por ID
- `getCustomerByMobileNumber(String)` - Buscar por número móvil
- `getCustomerByEmail(String)` - Buscar por email
- `getCustomers()` - Obtener todos los clientes

#### **AccountService**
- `createAccount(AccountCreateRequestDto)` - Crear nueva cuenta
- `updateAccount(AccountUpdateRequestDto, Long)` - Actualizar cuenta
- `deleteAccount(Long)` - Eliminar cuenta
- `getAccountByCustomerId(Long)` - Obtener cuenta activa del cliente
- `getAccountsByCustomerId(Long)` - Obtener todas las cuentas del cliente
- `getAccountsByBranchName(String)` - Buscar cuentas por sucursal
- `getAccountsByAccountType(String)` - Buscar cuentas por tipo
- `getAccountsByAccountStatus(String)` - Buscar cuentas por estado
- `getAccounts()` - Obtener todas las cuentas

### 3. Entidades

#### **Customer**
```java
- customerId: Long (PK)
- documentType: String
- documentNumber: String
- name: String
- email: String
- mobileNumber: String
- createdAt: LocalDateTime
- createdBy: String
- updatedAt: LocalDateTime
- updatedBy: String
```

#### **Account**
```java
- accountId: Long (PK)
- customerId: Long (FK)
- accountNumber: Long (generado aleatoriamente)
- accountType: String
- branchName: String
- accountStatus: String (default: "Active")
- createdAt: LocalDateTime
- createdBy: String
- updatedAt: LocalDateTime
- updatedBy: String
```

### 4. DTOs

#### **CustomerRequestDto**
- documentType: String (2-3 caracteres)
- documentNumber: String (8-15 caracteres)
- name: String (5-100 caracteres)
- email: String (válido)
- mobileNumber: String (10 dígitos)

#### **CustomerResponseDto**
- customerId: Long
- documentType: String
- documentNumber: String
- name: String
- email: String
- mobileNumber: String
- accountResponseDto: AccountResponseDto

#### **AccountCreateRequestDto**
- customerId: Long
- accountType: String
- branchName: String (5-100 caracteres)

#### **AccountUpdateRequestDto**
- customerId: Long
- accountType: String
- branchName: String (5-100 caracteres)
- accountStatus: String (5-10 caracteres)

#### **AccountResponseDto**
- accountId: Long
- customerId: Long
- accountNumber: Long
- accountType: String
- branchName: String
- accountStatus: String
- customerName: String

## 🧪 Pruebas Unitarias

### Cobertura Total

Se han implementado **59 tests unitarios** con una cobertura superior al 80%:

#### **CustomerControllerTest** (9 tests)
- ✅ `createCustomer()` - Creación exitosa
- ✅ `getCustomerById()` - Obtención exitosa
- ✅ `getCustomerByMobileNumber()` - Búsqueda por móvil
- ✅ `getCustomerByEmail()` - Búsqueda por email
- ✅ `getCustomer()` - Obtener todos
- ✅ `updateCustomer()` - Actualización exitosa
- ✅ `updateCustomerFailure()` - Actualización fallida
- ✅ `deleteCustomer()` - Eliminación exitosa
- ✅ `deleteCustomerFailure()` - Eliminación fallida

#### **AccountControllerTest** (11 tests)
- ✅ `createAccount()` - Creación exitosa
- ✅ `getAccountsByCustomerId()` - Cuentas por cliente
- ✅ `getAccountsByBranchName()` - Cuentas por sucursal
- ✅ `getAccountsByAccountType()` - Cuentas por tipo
- ✅ `getAccountsByAccountStatus()` - Cuentas por estado
- ✅ `getAccountByCustomerId()` - Cuenta activa
- ✅ `getAccounts()` - Obtener todas
- ✅ `updateAccount()` - Actualización exitosa
- ✅ `updateAccountFailure()` - Actualización fallida
- ✅ `deleteAccount()` - Eliminación exitosa
- ✅ `deleteAccountFailure()` - Eliminación fallida

#### **CustomerServiceImplTest** (19 tests)
- ✅ `getCustomerByMobileNumber()` - Búsqueda exitosa
- ✅ `getCustomerByMobileNumber_NotFound()` - No encontrado
- ✅ `getCustomerByEmail()` - Búsqueda exitosa
- ✅ `getCustomerByEmail_NotFound()` - No encontrado
- ✅ `getCustomerById()` - Búsqueda exitosa
- ✅ `getCustomerById_NotFound()` - No encontrado
- ✅ `getCustomers()` - Lista exitosa
- ✅ `getCustomers_Empty()` - Lista vacía
- ✅ `createCustomer()` - Creación exitosa
- ✅ `createCustomer_EmailAlreadyExists()` - Email duplicado
- ✅ `createCustomer_MobileNumberAlreadyExists()` - Móvil duplicado
- ✅ `createCustomer_DocumentNumberAlreadyExists()` - Documento duplicado
- ✅ `updateCustomer()` - Actualización exitosa
- ✅ `updateCustomer_EmailAlreadyExists()` - Email duplicado
- ✅ `updateCustomer_MobileNumberAlreadyExists()` - Móvil duplicado
- ✅ `updateCustomer_DocumentNumberAlreadyExists()` - Documento duplicado
- ✅ `updateCustomer_CustomerNotFound()` - No encontrado
- ✅ `deleteCustomer()` - Eliminación exitosa
- ✅ `deleteCustomer_CustomerNotFound()` - No encontrado

#### **AccountServiceImplTest** (22 tests)
- ✅ `createAccount()` - Creación exitosa
- ✅ `createAccount_CustomerNotFound()` - Cliente no encontrado
- ✅ `createAccount_AccountAlreadyExists()` - Cuenta ya existe
- ✅ `updateAccount()` - Actualización exitosa
- ✅ `updateAccount_AccountNotFound()` - Cuenta no encontrada
- ✅ `updateAccount_CustomerNotFound()` - Cliente no encontrado
- ✅ `updateAccount_InvalidCustomerId()` - ID de cliente inválido
- ✅ `deleteAccount()` - Eliminación exitosa
- ✅ `deleteAccount_AccountNotFound()` - Cuenta no encontrada
- ✅ `getAccountByCustomerId()` - Obtención exitosa
- ✅ `getAccountByCustomerId_CustomerNotFound()` - Cliente no encontrado
- ✅ `getAccountByCustomerId_AccountNotFound()` - Cuenta no encontrada
- ✅ `getAccountsByCustomerId()` - Lista exitosa
- ✅ `getAccountsByCustomerId_NotFound()` - No encontrado
- ✅ `getAccountsByBranchName()` - Búsqueda exitosa
- ✅ `getAccountsByBranchName_NotFound()` - No encontrado
- ✅ `getAccountsByAccountType()` - Búsqueda exitosa
- ✅ `getAccountsByAccountType_NotFound()` - No encontrado
- ✅ `getAccountsByAccountStatus()` - Búsqueda exitosa
- ✅ `getAccountsByAccountStatus_NotFound()` - No encontrado
- ✅ `getAccounts()` - Obtención exitosa
- ✅ `getAccounts_Empty()` - Lista vacía

### Características de los Tests

- ✅ Patrón **Arrange-Act-Assert** en todos los tests
- ✅ Mocks correctamente configurados con Mockito
- ✅ Validaciones exhaustivas de respuestas
- ✅ Casos de éxito y error para cada método
- ✅ Verificación de interacciones con repositorios
- ✅ Uso de AssertJ para assertions fluidas
- ✅ Métodos helper reutilizables para datos de prueba

## 🚀 Cómo Ejecutar

### Requisitos Previos
- Java 21 o superior
- Maven 3.8+

### Instalar Dependencias
```bash
mvn clean install
```

### Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

### Acceder a Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### Ejecutar Tests
```bash
# Todos los tests
mvn test

# Tests de un módulo específico
mvn test -Dtest=CustomerControllerTest
mvn test -Dtest=AccountControllerTest
mvn test -Dtest=CustomerServiceImplTest
mvn test -Dtest=AccountServiceImplTest
```

### Generar Reporte de Cobertura
```bash
mvn clean test jacoco:report
# Reporte en: target/site/jacoco/index.html
```

## 🔒 Manejo de Excepciones

### Excepciones Personalizadas

#### **ResourceNotFoundException**
Se lanza cuando un recurso no es encontrado:
- Status: 404 NOT FOUND
- Casos: Cliente no encontrado, Cuenta no encontrada

#### **CustomerAlreadyExistsException**
Se lanza cuando se intenta crear un cliente con datos duplicados:
- Status: 400 BAD REQUEST
- Casos: Email, móvil o documento duplicados

#### **AccountAlreadyExistsException**
Se lanza cuando se intenta crear una cuenta con datos duplicados:
- Status: 400 BAD REQUEST
- Casos: Cuenta activa ya existe para el cliente

### GlobalExceptionHandler
Manejo centralizado de excepciones con respuestas consistentes:
```json
{
  "timeStamp": "2026-02-11T12:00:00",
  "statusCode": "404",
  "statusMsg": "Customer not found with the given input data customerId: '1'",
  "apiPath": "/api/customers/1"
}
```

## 📝 Constantes de Respuesta

### CustomerConstant
```java
STATUS_200 = "200"
MESSAGE_200 = "Request processed successfully"
STATUS_201 = "201"
MESSAGE_201 = "Customer created successfully"
```

### AccountConstant
```java
STATUS_200 = "200"
MESSAGE_200 = "Request processed successfully"
STATUS_201 = "201"
MESSAGE_201 = "Account created successfully"
STATUS_417 = "417"
MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact Dev team"
MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact Dev team"
```

## 🗄️ Esquema de Base de Datos

### Tabla: customers
```sql
CREATE TABLE customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    document_type VARCHAR(3) NOT NULL,
    document_number VARCHAR(15) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    mobile_number VARCHAR(10) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100)
);
```

### Tabla: accounts
```sql
CREATE TABLE accounts (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    account_number BIGINT NOT NULL UNIQUE,
    account_type VARCHAR(50) NOT NULL,
    branch_name VARCHAR(100) NOT NULL,
    account_status VARCHAR(10) DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);
```

## 📊 Validaciones

### Cliente (Customer)
- **Tipo de Documento**: 2-3 caracteres, no puede estar vacío
- **Número de Documento**: 8-15 caracteres, único
- **Nombre**: 5-100 caracteres, no puede estar vacío
- **Email**: Formato válido de email, único
- **Número Móvil**: Exactamente 10 dígitos, único

### Cuenta (Account)
- **ID del Cliente**: Mayor que cero
- **Tipo de Cuenta**: No puede estar vacío
- **Nombre de Sucursal**: 5-100 caracteres, no puede estar vacío
- **Estado de Cuenta**: 5-10 caracteres (solo para actualización)

## 🔐 Auditoría

Las entidades cuentan con auditoría automática:
- `createdAt`: Fecha de creación (generada automáticamente)
- `createdBy`: Usuario que creó (configurable)
- `updatedAt`: Fecha de actualización
- `updatedBy`: Usuario que actualizó

## 📋 Configuración Aplicación

### application.yml
```yaml
spring:
  application:
    name: accounts
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password:
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
  h2:
    console:
      enabled: true
server:
  port: 8080
```

## 🐛 Troubleshooting

### La aplicación no inicia
- Verificar que Java 21 esté instalado: `java -version`
- Limpiar cache: `mvn clean install`

### Tests fallan
- Actualizar dependencias: `mvn clean install`
- Verificar que H2 esté disponible
- Ejecutar: `mvn clean test`

### Puerto 8080 en uso
- Cambiar puerto en `application.yml`:
```yaml
server:
  port: 8081
```

## 📚 Documentación Adicional

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [OpenAPI 3.0](https://swagger.io/specification/)
- [JUnit 5](https://junit.org/junit5/)

## 👨‍💻 Autor

**Nombre**: Desarrollador del Microservicio  
**Email**: fmattaperdomo@correo.com  
**Fecha**: Febrero 2026

## 📄 Licencia

Este proyecto está bajo licencia MIT.

## ✨ Características Futuras

- [ ] Integración con servicio de notificaciones
- [ ] Soporte para múltiples monedas
- [ ] Transferencias entre cuentas
- [ ] Historial de transacciones
- [ ] Autenticación y autorización OAuth2
- [ ] Rate limiting
- [ ] Caché con Redis
- [ ] Pruebas de integración
- [ ] Despliegue en Docker

## 📞 Soporte

Para reportar problemas o sugerencias, contactar al equipo de desarrollo.

------

**Última revisión**: 11 de Febrero, 2026  
**Versión del README**: 2.1  
**Versión de la Aplicación**: 0.0.1-SNAPSHOT  
**Estado**: ✅ Completado y documentado
