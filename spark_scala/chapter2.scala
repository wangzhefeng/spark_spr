

import org.apache.spark.sql.SparkSession
import spark.implicits._

object chapter2 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local")
      .appName("Chapter 2")
      .getOrCreate()

    // read the data
    val flightData2015 = spark
      .read
      .option("inferSchema", "true")
      .option("header", "true")
      .csv("/home/wangzhefeng/bigdata/data/spark_data/data/flight-data/csv/2015-summary.csv")

    //
    flightData2015.take(3)



    val sqlWay = spark.sql(""
      SELECT DEST_COUNTRY_NAME, COUNT(1)
      FROM flight_data_2015
      GROUP BY DEST_COUNTRY_NAME
      "")

    val dataFrameWay = flightData2015
      .groupBy("DEST_COUNTRY_NAME")
      .count()


  }
}



