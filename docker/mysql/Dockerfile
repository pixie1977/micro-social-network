FROM mysql:8
MAINTAINER Eugen Mac <eugenmac56@gmail.com>

RUN apt-get update -y
RUN apt-get upgrade -y
RUN apt-get install mc -y
RUN apt-get install nano -y

RUN mkdir /var/log/mysql
RUN chown -R mysql:mysql /var/log/mysql

EXPOSE 3306