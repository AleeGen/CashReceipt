<h1 align="center">Cash Receipt</h1>

<h4 align="center">The application generates and displays a receipt of products on request </h4>


              Java '17'         |         Gradle '7.6'          |         Spring Boot '3.0.4'         |         PostgreSQL          
---
### Stack:

- [`org.springframework.boot:spring-boot-starter-data-jpa`]()
- [`org.springframework.boot:spring-boot-starter-validation`]()
- [`org.springframework.boot:spring-boot-starter-web`]()
- [`org.springframework.boot:spring-boot-starter-test`]()
- [`org.projectlombok:lombok:1.18.22`]()
- [`org.assertj:assertj-core:3.24.2`]()
- [`org.postgresql:postgresql`]()
- [`com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.14.2`]()
---

### Quick start:

java -jar cheque-0.0.1-SNAPSHOT.jar

### Used example:

> Format request "cheque/get?itemId-P&card-NNNN"
> 
> P is the product ID, NNNN is the four-digit card number
> 
> The card may be missing

- #### Get: http://localhost:8080/cheque/get?itemId=1&itemId=1&itemId=2&card=3333
![image](https://user-images.githubusercontent.com/100039077/224175445-efaa885e-6931-4839-946c-0862cb7cb217.png)