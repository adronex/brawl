FROM openjdk:8u111-jdk-alpine
RUN apk --update add mysql-client
COPY wait-database.sh /usr/local/bin/
ADD /out/oauth-router.war /www/
CMD sh /usr/local/bin/wait-database.sh && java \
    -Djava.security.egd=file:/dev/./urandom \
    -Dspring.datasource.driver-class-name="com.mysql.jdbc.Driver" \
    -Dspring.datasource.url=jdbc:mysql://$DB_HOST:$DB_PORT/$DB_NAME \
    -Dspring.datasource.username=$DB_USER \
    -Dspring.datasource.password=$DB_PASSWORD \
    -jar /www/oauth-router.war