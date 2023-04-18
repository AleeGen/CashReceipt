
<h1 align="center">Cash Receipt</h1>

<h4 align="center">The application generates and displays a receipt of products on request </h4>


           Java '19'      |      Gradle '7.6.1'       |      Spring Boot '3.0.4'      |      PostgreSQL       
---
### Stack:

- `org.springframework.boot`
    - spring-boot-starter-data-jpa
    - spring-boot-starter-validation
    - spring-boot-starter-web
- `org.postgresql:postgresql`
- `org.liquibase:liquibase-core`
- `org.projectlombok:lombok`
- `com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.2`
- `org.assertj`
    - assertj-core:3.24.2
    - aspectjweaver:1.9.19
- `com.itextpdf`
    - kernel:7.2.5
    - layout:7.2.5
    - io:7.2.5
---

### Properties of "resources\application.yml":
- "cache" to select the caching algorithm and cache capacity
- "pagination" to change pagination settings

### Used example entity (All CRUD operations are supported):

> Format request: "products?id=N" or "cards?card=N"
>
> Pagination support: "&page=N&size=N"

- ##### Get: http://localhost:8080/products?page=2&size=5
![image](https://user-images.githubusercontent.com/100039077/230886803-a03e4d6e-9358-496e-9359-56e413d6b7e8.png)

### Used example cash receipt:

> Format request "cashReceipt/(txt | pdf)?id=N&card=N"
>
> The card may be missing

- ##### Get: http://localhost:8080/cashReceipt?id=1&id=1&id=2&card=3333
![image](https://user-images.githubusercontent.com/100039077/224175445-efaa885e-6931-4839-946c-0862cb7cb217.png)
- ##### Get pdf format:
![image](https://user-images.githubusercontent.com/100039077/226687806-2689f851-8837-4ed3-83dd-99b425be6871.png)

