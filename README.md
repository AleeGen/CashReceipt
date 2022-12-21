# Cash Receipt

In this project Java 17, gradle 7.5, PostgreSQL, Servlet, Hibernate, Tomcat 9 are used.
### There are two ways to launch the application:
### 1. The console option. 
It is launched via the main method of the CashRegister class. In this case, you will need to enter a request in the format "ItemId-quantity card-NNNN", where ItemId is the product id, quantity is the quantity of products, NNNN is the four-digit card number (the card may be missing). You can finish the work by entering "exit" command. Information about products and discount cards is taken from files with "resources/data/*" directory or from the database, depending on the object factory selection. The receipt is displayed on the console, as well as gets written to a file resources/data/cash_receipts.txt .
### 2. The Web option.
It is launched using Tomcat. In this case, you will need to enter a GET request in the format "http://localhost:8080/check?itemId=1&itemId=1itemId=2&card=1111". Information about products and discount cards is taken from the database, and the receipt is displayed on the html page.
