// !usr/bin/env/scala

/* SimpleApp.scala */ 
import org.apache.spark.sql.SparkSession

object SimpleApp {
	def main(args: Array[String]) {
		val logFile = "D:/spark/README.md"
		
		val spark = SparkSession
			.builder
			.appName("Simple Application")
			.getOrCreate()
		
		val logData = spark.read.textFile(logFile).cache()
		val numAs = logData.filter(line => line.contains("a")).count()
		val numBs = logData.filter(line => line.contains("b")).count()
		println(s"Lines with a: $numAs\n, Line with b: $numBs")
		
		spark.stop()
	}
}
