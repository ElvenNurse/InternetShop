# Internet Shop

![Header Image](src/main/resources/banner_shop_online.png)

# Table of Contents

[Project purpose](#purpose)

[Project structure](#structure)

[For developer](#developer)

[Author](#author)

## <a name='purpose'></a>Project purpose

This project is a simple version of internet shop.

<hr>

This shop has basic functions for online store such as:

- Registration and log in forms
- Bucket and order services
- Two roles: User and Admin

<hr>

This project has authentication and authorization filters, DAO and Service layers, Servlets and JSP pages.

DAO layer has two implementations: inner storage based on List and outer storage based on MySQL DB.

## <a name='structure'></a>Project structure

- Java 11
- Maven
- MavenCheckstylePlugin 3.1.0
- javax.servlet 3.1.0
- javax.jstl 1.2
- mysql-connector-java 8.0.18
- log4j 2.13.0

## <a name='developer'></a>For developer
To run this project you need to install:

- <a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html">Java 11</a>
- <a href="https://tomcat.apache.org/download-90.cgi">Tomcat</a>
- <a href="https://www.mysql.com/downloads/">MySQL 8</a> (optional)

<hr>

Add this project to your IDE as Maven project.

Add Java SDK 11 in project structure.

Configure Tomcat:
- Add artifact
- Add Java SDK 11

Change a path to your Log file in **src/main/resources/log4j2.xml** on line 4.

<hr>

To work with MySQL you need to:
- Use file **src/main/resources/init_db.sql** to create schema and all the tables required by this app in MySQL DB
- Change username and password to match with MySQL in **src/main/java/mate/academy/internetshop/factory/Factory.java** class on 42 line

<hr>

To work with inner Storage you need to:

- Change **src/main/java/mate/academy/internetshop/factory/Factory.java**
    1. On line 52 **new BucketDaoJdbcImpl(connection)** to **new BucketDaoImpl()**
    2. On line 66 **new ItemDaoJdbcImpl(connection)** to **new ItemDaoImpl()**
    3. On line 80 **new OrderDaoJdbcImpl(connection)** to **new OrderDaoImpl()**
    4. On line 94 **new UserDaoJdbcImpl(connection)** to **new UserDaoImpl()**
    ![from](src/main/resources/bucket_jdbc.png)
    ![to](src/main/resources/bucket_storage.png)    
- Import corresponding classes from **mate.academy.internetshop.dao.impl**

<hr>

Run the project:

Main page is at URL: .../{context_path}/index

![index_url](src/main/resources/index_url.png)

For MySQL DAO only **on first run** of the project, for inner Storage **on every launch**, to create default users open URL: .../{context_path}/inject

![inject url](src/main/resources/inject_url.png)

<p>By default there are one user with an USER role (login = "user", password = "123"),<br>
one with an ADMIN role (login = "admin", password = "123"),<br>
and one with both roles (login = "superuser", password = "123").</p>

## <a name='author'></a>Author
[Andrii Voloshyn](https://github.com/ElvenNurse)

