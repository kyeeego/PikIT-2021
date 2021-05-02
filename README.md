# Кейс на стипендию Пик IT 2021.

****

### Требования для запуска на локальной машине:

```docker, docker-compose```

```Java 11```

```yaml
# application.yml

spring:
  jpa:
    database: POSTGRESQL
    show-sql: true
    hibernate:
      ddl-auto: create-drop
  datasource:
    platform: postgres
    url: jdbc:postgresql://db:5432/pikit
    username: user
    password: pass
    driverClassName: org.postgresql.Driver

mail:
  smtp:
    host: <host>
    port: <port>
    username: <username>
    password: <password>

document:
  path: "static/"

accesstoken:
  secret: "Any random string" 
 ```
### Запустить:

```docker-compose up --build```

Приложение будет запущено на порту ```8081```
