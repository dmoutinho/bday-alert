# Bday Alert

A simple CRUD to manage a birthday model: name and date. The model is stored in a MySQL or H2 database and made available through a REST API.

### Prerequisites

- Maven in 5 Minutes
https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html

### Clone

```sh
git clone https://github.com/dmoutinho/bday-alert.git
```

### Build

```sh
$ cd bday-alert
$ mvn clean install
```

### Run

```sh
$ cd target
$ java -Dserver.port=9080 -jar bday-alert-0.0.1-SNAPSHOT.jar
```

### References

- Cloud Native Java - Josh Long
https://www.youtube.com/watch?v=5q8B6lYhFvE

- Accessing data with MySQL
https://spring.io/guides/gs/accessing-data-mysql/

- Spring Boot Reference Guide
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
