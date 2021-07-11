# e-commerce-shop
University project for Software and Data Engineering subject Veb programming in II year of study at Singidunum University.

Professor: Djordje Obradović

Assistant Professor: Ivan Radosavljević

Student: Filip Zobić - 2019270036

### Architecture

The backend has been implemented with the Spring Boot framework.

The API is following the Monolithic Architecture design pattern.

The API is following Onion Architecture design pattern.

Authentication has been implemented using sessions stored in  a Redis database.

Relational database of choice is PostgreSql. 

### Installation

#### Requirements:

* Redis installed
* PostgreSql
  * Configure System ENV variables (see application.yml)
* Java 11
