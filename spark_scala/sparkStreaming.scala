//=======================================================================
// 基本概念
//=======================================================================

//=======================================================================
// 连接(Link)
// Add the dependency to SBT or Maven project
//=======================================================================
//============================
// Maven
//============================
// Spark Streaming core API
<dependency>
	<groupId>org.apache.spark</groupId>
	<artifactId>spark-streaming_2.11</artifactId>
	<version>2.4.0</version>
</dependency>
// Kafka
<dependency>
	<groupId>org.apache.spark</groupId>
	<artifactId>spark-streaming_kafka-0-10_2.11</artifactId>
	<version>2.4.0</version>
</dependency>
// Flume
<dependency>
	<groupId>org.apache.spark</groupId>
	<artifactId>spark-streaming-flume_2.11</artifactId>
	<version>2.4.0</version>
</dependency>
// Kinesis
<dependency>
	<groupId>org.apache.spark</groupId>
	<artifactId>spark-streaming-kinesis-asl_2.11</artifactId>
	<version>2.4.0</version>
</dependency>

//============================
// SBT
//============================
// Spark Streaming core API
libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.4.0"
// Kafka
libraryDependencies += "org.apache.spark" % "spark-streaming_kafka-0-10_2.11" % "2.4.0"
// Flume
libraryDependencies += "org.apache.spark" % "spark-streaming-flume_2.11" % "2.4.0"
// Kinesis
libraryDependencies += "org.apache.spark" % "spark-streaming-kinesis-asl_2.11" % "2.4.0"


//=======================================================================
// 初始化 StreamingContext
//=======================================================================
// Quick Example

import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import spark.implicits._

// SparkSession
val spark = SparkSession
.builder
.appName("StructuredNetworkWordCount")
.getOrCreate()

// Streaming
val lines = spark.readStream
.format("socket")
.option("host", "localhost")
.option("port", 9999)
.load()

val words = lines.as[String].flatMap(_.split(" "))
val wordCounts = words.groupBy("value").count()

val query = wordCounts.writeStream
.outputMode("complete")
.format("console")
.start()

query.awaitTermination()

// $nc -lk 9999
// run-example org.apache.spark.examples.sql.streaming.StructuredNetworkWordCount localhost 9999



// Programming Model


//=======================================================================
// 离散型Streams (Discretized Streams, DStreams)
//=======================================================================




//=======================================================================
// Input DStreams and Receivers
//=======================================================================




//=======================================================================
// DStreams 上的转换操作
//=======================================================================




//=======================================================================
// DStreams 上的输出操作
//=======================================================================
