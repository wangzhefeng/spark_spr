

// 分布式SQL引擎(Distributed SQL Engine)



// ================================================
// Thrift JDBC/ODBC server
// ================================================
// 启动JDBC/ODBC服务器(Linux环境)

// ./sbin/start-thriftserver.sh

export HIVE_SERVER2_THRIFT_PORT=<listening-port>
export HIVE_SERVER2_THRIFT_BIND_HOST=<listening-host>
./sbin/start-thriftserver.sh \
	--master <master-uri> \
	...

./sbin/start-thriftserver.sh \
  --hiveconf hive.server2.thrift.port=<listening-port> \
  --hiveconf hive.server2.thrift.bind.host=<listening-host> \
  --master <master-uri>
  ...




