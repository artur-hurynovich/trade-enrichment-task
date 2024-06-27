1. The service can be run in any IDE (IntelliJ IDEA, Eclipse, etc.);
2. The service has the only POST endpoint `http://localhost:8080/api/v1/enrich`. Original CSV file can be sent as a 
request parameter with name `file`
3. By default, the service handles files with size of up to 1MB and requests with size of up to 
10MB. To configure this values please add `spring.servlet.multipart.max-file-size` and 
`spring.servlet.multipart.max-request-size` properties to the `application.properties` file;
4. The enrichment process might take much time for large CSV files which in turn might lead
to blocking of the available threads. It's recommended to migrate to asynchronous execution to
avoid such issues
