apt-get update && apt-get install -y wget
wget https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.5.0/apache-maven-3.5.0-bin.tar.gz -O maven.tar.gz
mkdir /usr/local/apache-maven/ && tar xvf maven.tar.gz -C /usr/local/apache-maven/
mv /usr/local/apache-maven/apache-maven-3.5.0 /usr/local/apache-maven/apache-maven
