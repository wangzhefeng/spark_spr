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
