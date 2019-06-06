FROM mysql:5.5

ADD my.cnf /etc/mysql/my.cnf

ENV MYSQL_ALLOW_EMPTY_PASSWORD="yes"

COPY Create-Acme-Packages.sql /docker-entrypoint-initdb.d