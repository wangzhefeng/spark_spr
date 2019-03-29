// SQL 
	// Spark SQL => execute SQL queries
	// Spark SQL => read data from an existing Hive installation
	// Spark SQL => run SQL from within another programming language(scala, java, python) 
		// Dataset/DataFrame
	// Spark SQL => interact with SQL interface
		// Spark SQL CLI: spark-sql
		// JDBC/ODBC

// Datasets and DataFrame
// Dataset 
	// distributed collection of data
	// optimized RDD
	// scala
// DataFrame
	// Dataset organized into named columns => table/dataframe
	// sources:
		// structured data files
		// Hive table
		// external databases
		// existing RDD
	// scala, python, R
		// scala: Dataset of RowS: Dataset[Row]
		// Python: DataFrame
		// R: data.frame





// ===============================================================
// Spark SQL
// ===============================================================
import org.apache.spark.sql.SparkSession
import spark.implicits._

object spark_SQL_DataFrames_Datasets {
	def main(args: Array[String]) {

		// ------------------------------------------------------
		// 程序入口: SparkSession
		// ------------------------------------------------------
		val spark = SparkSession
			.builder()
			.appName("Spark SQL")
			.config("spark.some.config.option", "some-value")
			.getOrCreate()

		// -------------------------------------------------------
		// 创建 DataFrames
		// existing RDD, Hive table, Spark data sources
		// -------------------------------------------------------
		val df = spark.read.json("E:/DataScience/spark_data/data/examples/src/main/resources/people.json")
		df.show()

		// ------------------------------------------------------
		// Untyped(弱类型) Dataset Operations(aka DataFrame Operations)
		// DataFrames are just Dataset of Rows in Scala and Java API. 
		// These operations are also referred as “untyped transformations” 
		// in contrast to “typed transformations” come with strongly typed Scala/Java Datasets.
		// ------------------------------------------------------
		df.printSchema() 						// print the shcema in a tree format
		df.select("name").show()				// select only the "name" column
		df.select($"name", $"age" + 1).show()	// select name and age, increment the age by 1
		df.filter($"age" > 21).show()			// select people older than 21
		df.groupBy("age").count().show()		// Count people by age




		// --------------------------------
		// Running SQL Queries Programmatically
		// --------------------------------
		// Temporary View
		df.createOrReplaceTempView("people")
		val sqlDF = spark.sql("SELECT * FROM people")
		sqlDF.show()

		// Global temporary view
		df.createGlobalTempView("people")
		val sqlDF_Global = spark.sql("SELECT * FROM global_temp.people")
		sqlDF_Global.show()

		// Global temporary view is cross-session
		spark.newSession().sql("SELECT * FROM global_temp.people").show()

		// --------------------------------
		// Create Datasets
		// --------------------------------
		case class Person(name: String, age: Long)

		// Encoders are created for case classes
		val caseClassDS = Seq(Person("Andy", 32)).toDS()
		caseClassDS.show()

		// Encoders for most common types are automatically provided by importing spark.implicits._
		val primitiveDS = Seq(1, 2, 3).toDS()
		primitiveDS.map(_ + 1).collect()

		// DataFrame can be converted to a Dataset by providing a class.
		val path = "D:/spark/example/src/main/resources/people.json"
		val peopleDS = spark.read.json(path).as[Person]
		peopleDS.show()

		// --------------------------------
		// Dataset and RDD
		// Inferring the Schema Using Reflection

		// Programmatically Specifying the Schema

		// --------------------------------





		// stop the spark
		spark.stop()
	}
}







// 聚合操作
// Untyped User-Defined Aggregate Functions
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.expressions.MutableAggregationBuffer
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction
import org.apache.spark.sql.types._

object Myaverage extends UserDefinedAggregateFunction {
	// data types of input arguments of this aggregation function
	def inputSchema: StructType = 
}


// Type-Safe User-Defined Aggregate Functions






