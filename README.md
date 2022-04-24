# JAX-RS and JAX-WS REST and SOAP E-Commerce Web Service

>An E-Commerce RESTful and SOAP web service built using JAX-RS and JAX-WS.
# 🏛 Architecture
This software project was built using a layered architecture. The two APIs are different presentations of the same core service layer.
![image](https://user-images.githubusercontent.com/73137611/164950417-dd696e3a-6675-494d-8339-68d666009298.png)

# 📃 Documentation
📧[Postman RESTful API Docs](https://www.postman.com/mahmoudfawzykhalil98/workspace/my-workspace/documentation/20505853-01fc7ac2-9e17-494c-bcfb-c5846fcacfbe)

🧼[SOAP-UI project](https://github.com/MahmoudFawzyKhalil/rest-soap-ecommerce-web-service/blob/main/src/main/resources/SoapUI/soapui-project.xml)

# 📦 Features
* Partial response
* Pagination
* HATEOAS
* Custom ExceptionMappers
* Content negotiation (support for both XML and JSON payloads and responses)
* SOAP 1.2

# ⚙ Technologies used
* JAX-RS (Jersey)
* JAX-WS (Metro)
* JSON-B
* JAX-B
* Maven
* Tomcat
* Intellij IDEA Ultimate
* Postman
* SOAP-UI

# 🛠 Run with Maven
**Maven**

* Change the configuration of Tomcat in `pom.xml`. 
* Deploy the application using the following maven command:
```
mvn clean compile tomcat7:redeploy
```
* REST: change the `{{host}}` variable in the Postman collection environment variables to match the port you chose for your Tomcat deployment
* SOAP: import the project into SOAP UI

**MySQL**
* Create a database schema and provide the username and password in the persistence.xml
* Hibernate will automatically create the tables for you
* Run DatabasePopulator.java to populate the database with some test data

# 👷‍♂️ Contributors
* [Mahmoud Fawzy](https://github.com/MahmoudFawzyKhalil)
