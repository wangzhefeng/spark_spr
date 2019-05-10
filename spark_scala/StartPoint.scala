// Starting Point

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf


// =====================================================
// SparkSession
// =====================================================
import org.apache.spark.sql.SparkSession
import spark.implicits._

object objectName1 {
	def main(args: Array[String]) = {
		val spark = SparkSession
			.builber()
			.appName("Spark SQL")
  			.master("local[*]")
			.config("spark.some.config.option", "some-value")
			.getOrCreate()
	}
}


// =====================================================
// StreamingContext
// =====================================================
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._


object objectName2 {
	def main(args: Array[String]) = {
		val conf = new SparkConf()
			.setMaster("local[2]")
			.setAppName("NetworkWordCount")
		val ssc = new StreamingContext(conf, Seconds(1))
	}
}





