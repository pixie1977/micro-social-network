## Разворачивание приложения и бд на CentOS 8

### Git
> - sudo dnf install git
---
### Java 
https://linuxize.com/post/install-java-on-centos-8/
> - sudo yum install java-11-openjdk-devel
> - sudo alternatives --config java
----
### Maven 
https://linuxize.com/post/how-to-install-apache-maven-on-centos-8/
> - sudo yum install maven
----
https://stackoverflow.com/questions/19654557/how-to-set-specific-java-version-to-maven
1. On terminal vim ~/.bash_profile
2. After that, paste the below path in the base profile file and save it by vim command wq!
   export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home
   export M2_HOME=/Users/mj/Tools/apache-maven-3.8.1

3. Run source ~/.bash_profile to make it work forever
4. After that, you can run mvn -v in your terminal to check

> - export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-11.0.14.1.1-6.el8.x86_64
> - export M2_HOME=/usr/share/maven
------------------
### Docker:
1. https://stackoverflow.com/questions/59359793/installed-docker-and-i-got-podman
>  - sudo yum install -y yum-utils device-mapper-persistent-data lvm2 
>  - sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

2. https://linuxconfig.org/how-to-install-docker-in-rhel-8
>  - sudo dnf repolist -v
>  - sudo dnf list docker-ce --showduplicates | sort -r

берем последнюю версию из списка доступных

3. Install docker in CentOS 8 (https://feitam.es/how-to-fix-error-problem-with-installed-package-podman-and-buildah-installed-docker-ce-in-centos/)
>  - sudo yum erase podman buildah
>  - sudo dnf install docker-ce-3:20.10.8-3.el8
>  - sudo systemctl enable docker
>  - sudo systemctl start docker
>  - sudo usermod -a -G docker uoles
>  - sudo chmod 777 /var/run/docker.sock
>  - reboot
------------------
### Сборка приложения
Образ: docker.io/library/openjdk:11

>  - docker build -t uoles/mooncake-social .
>  - docker run --name social-project -p 8081:8081 --network=host -d uoles/mooncake-social
>  - docker logs social-project

------------------
### Сборка базы
Образ: docker.io/library/mysql:8

>  - docker run -p 6603:3306 --name social-mysql \
-e MYSQL_ROOT_PASSWORD=8b03005ef88c426498523bbe0757d101 \
-v /home/uoles/project/mysql_db:/var/lib/mysql \
-d mysql:8
>  - docker exec -it social-mysql bash
>  - mysql -u root -p
>  - создать схему и пользователей, выдать гранты

------------------
### Настройка портов на CentOS 8:
https://linuxconfig.org/redhat-8-check-open-ports

>  - sudo firewall-cmd --permanent --add-port 8081/tcp
>  - sudo firewall-cmd --permanent --add-port 6603/tcp
>  - sudo firewall-cmd --reload
>  - sudo firewall-cmd --list-ports

------------------