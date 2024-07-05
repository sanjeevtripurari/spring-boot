* [Spring Boot + Spring Data JPA + PostgreSQL Example](https://www.javaguides.net/2019/08/spring-boot-spring-data-jpa-postgresql-example.html)

* build CRUD RESTFul APIs for a Simple Employee Management System using Spring Boot 3, JPA, and PostgreSQL database. 

```
sanjeev.t@LM-ILB-SANJEEVT ~ % psql -U emp employees  -h localhost   

employees=> \du
                                   List of roles
 Role name |                         Attributes                         | Member of 
-----------+------------------------------------------------------------+-----------
 emp       |                                                            | {}
 postgres  | Superuser, Create role, Create DB, Replication, Bypass RLS | {}
 sanjeev.t | Superuser, Create role, Create DB                          | {}

employees=> \dt
         List of relations
 Schema |   Name    | Type  | Owner 
--------+-----------+-------+-------
 public | employees | table | emp

employees=> select * from employees;
 id |    email_address    | first_name | last_name 
----+---------------------+------------+-----------
  1 | johncena@gmail.com  | John       | Cena
  3 | rameshrao@gmail.com | Rmesh      | Rao
  6 | satish24@gmail.com  | Satish     | Fadatare
(3 rows)

```
```

 curl --location 'http://localhost:8080/api/v1/employees' \
 --header 'Content-Type: application/json' \
 --data-raw '{
    "firstName": "John",
    "lastName": "Cena",
    "emailId": "johncena@gmail.com"
}'


curl -i -X POST 'http://localhost:8080/api/v1/employees' -H  'Content-Type: application/json' --data-binary '@/Users/sanjeev.t/employee-7-ramesh.json'
sanjeev.t@L-ILB-APATNAIK ~ % cat employee-7-ramesh.json 
{ "firstName": "Satish", "lastName": "Fadatare", "emailId": "satish24@gmail.com" }
```

