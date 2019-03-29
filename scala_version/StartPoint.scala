// Starting Point 


// =====================================================
// SparkSession
// =====================================================
import org.apache.spark.sql.SparkSession

val spark = SparkSession
	.builber()
	.appName("Spark SQL")
	.config("spark.some.config.option", "some-value")
	.getOrCreate()


// =====================================================
// StreamingContext
// =====================================================
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.StreamingContext._

val conf = new SparkConf()
	.setMaster("local[2]")
	.setAppName("NetworkWordCount")
val ssc = new StreamingContext(conf, Seconds(1))


