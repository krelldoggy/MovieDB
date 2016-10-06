docker run -d --name LibertyWithVolumes -p 32775:9080 --restart=always \
-e MYSQL_USER=root -e MYSQL_PWD=ChangeMe! -e MYSQL_IP=172.17.0.2 \
-e MYSQL_PORT=3306 -e MYSQL_DB=test_01 \
-v /Users/djones/Documents/workspace/LibertyDemo/LibertyDB/config:/config \
websphere-liberty:webProfile7
