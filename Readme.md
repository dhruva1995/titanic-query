# Note:

This project utilizes the API exposed by EverSQL to convert the text to SQL, post that the api executes SQL and produces the results.

# How to run ?

1. Ensure you have java 17, maven and docker installed
2. clone the repository `mvn clean install`.
3. Run `mvn clean install`.
4. Run `docker compose up`.
5. Do a HTTP Get to localhost:8080/api/v1/query passing a request parameter of query. For example `http://localhost:8080/api/v1/query?query=how many passengers name contains James`
6. Probably the output is not formated exactly but does shows the necessay result.
