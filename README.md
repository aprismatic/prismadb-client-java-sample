# PrismaDB-Client-Java-Sample
A sample client for Prisma/DB Proxy written in Java

## Setup
1. Install Docker: https://docs.docker.com/get-docker/
2. Install Docker Compose: https://docs.docker.com/compose/install/
3. Grab the Docker Compose script for Prisma/DB proxy and database: https://github.com/aprismatic/prismadb/blob/master/sample-scripts/mysql/docker-compose/linux/docker-compose.yml
4. Start up the Prisma/DB proxy and database containers: `docker-compose up`
5. Build and run this project with Maven.

### JDBC Connector Support

This project uses MariaDB Java Client (https://mvnrepository.com/artifact/org.mariadb.jdbc/mariadb-java-client) as MySQL Connector/J (https://mvnrepository.com/artifact/mysql/mysql-connector-java) sends some queries on its own which is not supported by the Prisma/DB proxy.
