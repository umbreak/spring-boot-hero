#Install (you will require Java and Maven)
cd source
mvn clean install


#Create docker image:
cd source
mvn clean install
cd backend
mvn package docker:build


#Install with docker
This package is available on Docker Hub:
docker pull umbreak/backend
docker run -p 8181:8181 -p 8082:8082 -t umbreak/backend

#Tests
cd source
mvn clean test


#Run
##Backend
cd source/backend
mvn spring-boot:run


#Demo

The Webservice is running on a micro AMI from Amazon EC2. You can test it as follows:

curl -u 'user1:secret1' -H "Content-Type: application/json" -X POST -d '{"name": "Hero 1", "pseudonym": "H1", "publisher": "MARVEL", "skills": ["high_jump", "fly", "super_strenght"], "allies": ["Batman", "Hero 2"], "firstAppearance": "2015-10-20"}' "http://52.59.232.115/hero"
curl -u 'user1:secret1' "http://52.59.232.115/hero"
curl -u 'user1:secret1' "http://52.59.232.115/hero/Hero%201"
curl -u 'user1:secret1' -X DELETE "http://52.59.232.115/hero/Hero%201"
curl -u 'user1:secret1' "http://52.59.232.115/hero"


#DB
For this environment I am using a in memory H2 DB. Once the backend is running, the DB can be accessed through http://localhost:8082 and then set in the URL the following:
jdbc:h2:mem:dataSource;Mode=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MV_STORE=FALSE;MVCC=FALSE;IGNORECASE=TRUE
That will open a web interface where one can see the DB tables and perform queries on it.
