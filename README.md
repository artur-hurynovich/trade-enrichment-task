1. The service can be run in any IDE (IntelliJ IDEA, Eclipse, etc.), It also can be run using Docker. 
In the terminal navigate to the `docker` directory and run `docker compose up`;
2. The service has the only POST endpoint `http://localhost:8080/api/v1/enrich`. Original CSV file can be sent as a 
request parameter with name `file`
3. By default, the service handles files with size of up to 1MB and requests with size of up to 
10MB. To configure this values please add `spring.servlet.multipart.max-file-size` and 
`spring.servlet.multipart.max-request-size` properties to the `application.properties` file.
