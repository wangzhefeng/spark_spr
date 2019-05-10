
//=======================================================================
// A Quick Example
//=======================================================================
package org.apahce.spark.examples.streaming

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

//=======================================================================
// 创建一个2线程、每隔1秒的 batch interval 的本地 StreamingContext
//=======================================================================
// 配置
val conf = new SparkConf()
	.setMaster("local[2]")
	.setAppName("NetworkWordCount")

// SparkStreaming入口：StreamingContext
val ssc = new StreamingContext(conf, Seconds(1))

//=======================================================================
// 使用上面定义的 StreamingContext 创建一个 DStream 对象用来表示一个 TCP source 的数据流(streaming data)
// TCP socket source: localhost:9999
//=======================================================================
// val lines = ssc.socketTextStream("localhost", 9999)
// val words = lines.flatMap(_.split(" "))
// val pairs = words.map(word => (word, 1))
// val wordCounts = pairs.reduceByKey(_ + _)

val wordCounts = ssc.socketTextStream("localhost", 9999)
	.flatMap(_.split(" "))
	.map(word => (word, 1))
	.reduceByKey(_ + _)
//=======================================================================
// 开始Spark Streaming
//=======================================================================
ssc.start()
ssc.awaitTermination()

//=======================================================================
// run example using Netcat
//=======================================================================
// $ nc -lk 9999





/**
 * Counts words in UTF8 encoded, '\n' delimited text received from the network every second.
 *
 * Usage: NetworkWordCount <hostname> <port>
 * <hostname> and <port> describe the TCP server that Spark Streaming would connect to receive data.
 *
 * To run this on your local machine, you need to first run a Netcat server
 *    `$ nc -lk 9999`
 * and then run the example
 *    `$ bin/run-example org.apache.spark.examples.streaming.NetworkWordCount localhost 9999`
 */
package org.apahce.spark.examples.streaming

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

object NetworkWordCount {
	def main(args: Array[String]) {
		if (args.length < 2) {
			System.err.println("Usage: NetworkWordCount <hostname> <port>")
			System.exit(1)
		}
		
		StreamingExamples.setStreamingLogLevels("")
		
		// Create the context with a 1 second batch size
		val sparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
		val ssc = new StreamingContext(sparkConf, Second(1))
		
		// words counts
		val lines = ssc.socketTextStream(args(0), args(1).toInt, 
										 StorageLevel.MEMORY_AND_DISK_SER)
		val words = lines.flatMap(_.split(" "))
		val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
		wordCounts.print()

		// start the streaming
		ssc.start()
		ssc.awaitTermination()
	}
}


