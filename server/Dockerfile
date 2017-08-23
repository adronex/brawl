FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD /build/libs/clockworks.jar /www/
RUN apk --update add mysql-client
COPY wait-database.sh /usr/local/bin/
CMD sh /usr/local/bin/wait-database.sh && java \
    -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n \
    -Djava.security.egd=file:/dev/./urandom \
    -Dspring.datasource.url=jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/$MYSQL_DATABASE \
    -Dspring.datasource.username=$MYSQL_USER \
    -Dspring.datasource.password=$MYSQL_PASSWORD \
    -jar /www/clockworks.jar