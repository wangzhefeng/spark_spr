

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




// ================================================
// Spark SQL CLI
// ================================================
// Spark SQL CLI是一个能够在本地模式以并从命令行输入查询时运行Hive metastore服务方便的工具；
// 注意：Spark SQL CLI 不能与Thrift JDBC服务器进行通信；

// --------------------------------------
// 启动Spark SQL CLI
// --------------------------------------
// $./bin/spark-sql

// --------------------------------------
// 查看spark-sql可用的完整列表
// --------------------------------------
// $./bin/spark-sql --help