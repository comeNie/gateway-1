#log flow
log4j -> *.log -> logstash -> kafka -> monitor -> mongodb
 					       -> es -> kibana

#log example
2016-06-23 17:07:21,951 [main] INFO  [com.ehsy.monitor.service.OpLogTrigger] - [OP]-[用户ID]-[操作名称描述2]-[内容2]-[功能子模块1]

#logstash
$logstash -f logstash.conf

#kafka
#1. 启动zookeeper，kafka自带zookeeper
$zookeeper-server-start.sh zookeeper.properties

#2. 启动kafka server
$kafka-server-start.sh server.properties

#3. 创建topic
#其中replication-factor不能大于broker服务器数量，partition一般情况下等于replication-factor
#可使用kafka-console-producer和kafka-console-consumer验证
$kafka-topics.sh --create --topic topic_name --zookeeper zookeeper_server_hosts --replication-factor 3 --partition 3

#mongodb
#1. 开启服务器
$mongod --dbpath dbpath --replSet replSet_name --port port

#2. 连接服务器
mongo 地址
#//连接服务器后
#//replicaSet设置完成，连接三台服务器，分别显示primary，secondary，arbiter
->
->config={_id:"replSet_name",members:[{_id:0,host:"host0_port"},{_id:1,host:"host1_port"},{_id:2,host:"host2_port",arbiterOnly:true}]}
->rs.initiate(config)
->

#monitor monitor.xml
<bean id="dbAccessSupport" class="com.ehsy.monitor.dao.helper.MongoDBAccessSupport">
    <property name="host" value="server1:27017,server2:27017,server3:27017"/>
    <property name="replSet" value="monLog"/>
    <property name="database" value="opLog"/>
</bean>

#elasticSearch
#默认集群elasticsearch，node_name不可重复，host默认端口号9200
$elasticsearch --node.name node_name --network.host host
