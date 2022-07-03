### Запуск MySQL в контейнере:

#### Запуск с рандомным паролем MYSQL_ROOT_PASSWORD:
/var/lib/mysql - место хранения БД 
> docker run -p 6603:3306 --name social-mysql \
    -e MYSQL_RANDOM_ROOT_PASSWORD=yes \
    -v <Путь на локальной машине>:/var/lib/mysql \
    -d mysql:8

#### В логах можно посмотреть MYSQL_ROOT_PASSWORD:
> docker logs social-mysql

#### Запуск с заданным паролем MYSQL_ROOT_PASSWORD:
> docker run -p 6603:3306 --name social-mysql \
    -e MYSQL_ROOT_PASSWORD=pass \
    -v <Путь на локальной машине>:/var/lib/mysql \
    -d mysql:8 
    
#### удаленное подключение к mysql:
> mysql -u root -ppass -h <ip сервера> --port=6603


#### Подключение директории с конфигурацией
Для подключения конфигурации извне нужно использовать свой образ
> docker build -t msn-mysql .

/etc/mysql - место хранениия конфигурации БД
> docker run -p 6604:3306 --name social-mysql-test \
    -e MYSQL_ROOT_PASSWORD=pass \
    -v <Путь на локальной машине>:/var/lib/mysql \
    -v <Путь на локальной машине>:/etc/mysql \
    -d uoles/mysql

### Литература:
[1] https://www.k-max.name/linux/replikaciya-mysql-master-slave/
[2] https://it-lux.ru/%D1%80%D0%B5%D0%BF%D0%BB%D0%B8%D0%BA%D0%B0%D1%86%D0%B8%D1%8F-mysql-%D1%81-%D0%B8%D1%81%D0%BF%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5%D0%BC-gtid/ 
[3] https://www.redhat.com/sysadmin/gtid-replication-mysql-servers#:~:text=GTIDs%20bring%20transaction%2Dbased%20replication,when%20starting%20the%20replica%20servers.
[4] https://russianblogs.com/article/69421238993/ 

### Проблема с liquibase "Waiting for changelog lock...."
Инфо: https://stevenschwenke.de/multipleWaitingForChangelogLockLiquibaseLockedYourDatabase
> SELECT * FROM DATABASECHANGELOGLOCK; 
> 
> UPDATE DATABASECHANGELOGLOCK \
SET locked=0, lockgranted=null, lockedby=null \
WHERE id=1; 
> 
> commit;