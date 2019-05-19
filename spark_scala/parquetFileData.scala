
import org.apache.spark.sql.SparkSession
import spark.implicits._

object parquetFileData {
    def main(args: Array[String]): Unit = {
        // build the SparkSession as spark
        val spark = SparkSession.
          .builder()
          .master("local")
          .appName("Parquet File Data Read/Write")
          .config("spark-config-option", "spark-config-value")
          .getOrReplace()

        // Loading data programmatically
        val peopleDF = spark.read.json("/home/wangzhefeng/project/bigdata/data/examples/src/main/resources/people.json")

        // Save the DataFrame as Parquet files, maintaing the schema infomation
        peopleDF.write.parquet("people.parquet")

        // Read the parquet file to a DataFrame
        val parquetFileDF = spark.read.parquet("/home/wangzhefeng/project/bigdata/data/spark_data/people.parquet")

        // parquet files used to create a temporary view and then used in SQL statements
        parquetFileDF.createOrReplaceTempView("parquetFile")
        val namesDF = spark.sql("SELECT name FROM parquetFile WHERE age BETWEEN 13 AND 19")
        namesDF.map(attributes => "Name:" + attributes(0)).show()

        // stop the SparkSession
        spark.stop()
    }
}