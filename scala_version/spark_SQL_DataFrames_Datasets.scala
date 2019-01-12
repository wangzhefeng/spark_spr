// SQL 
	// execute SQL queries
	// run SQL from within another programming language => Dataset/DataFrame
	// Hive tables: HiveSQL
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
		// R: 

// ===============================================================
import org.apache.spark.sql.SparkSession
import spark.implicits._


object spark_SQL_DataFrames_Datasets {
	def main(args: Array[String]) {

		// --------------------------------
		// SparkSession
		// --------------------------------
		val spark = SparkSession
			.builder()
			.appName("Spark SQL")
			.config("spark.some.config.option", "some-value")
			.getOrCreate()

		// --------------------------------
		// Create DataFrames
		// create a DataFrame from an existing RDD, Hive table, Spark data sources
		// --------------------------------
		val df = spark.read.json("D:/spark/example/src/main/resources/people.json")
		df.show()

		// --------------------------------
		// Untyped Dataset Operations(aka DataFrame Operations)
		// --------------------------------
		df.printSchema() 					// print the shcema
		df.select("name").show()			// select only the "name" column
		df.select($"name", $"age" + 1).show()
		df.filter($"age" > 21).show()
		df.groupBy("age").count().show()

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







// ===============================================================
// 数据源(data sources)
// ===============================================================
// 加载数据，保存数据
	// DataFrame API
	// 关系转换(relational transformations)
	// 临时视图(temporary view)

// -----------------------------------------
// 加载数据,保存数据
// -----------------------------------------
val userDF = spark.read.load("examples/src/main/resources/users.parquet")
userDF
	.select("name", "favorite_color")
	.write
	.save("namesAndFavColors.parquet")

// 手动指定选项
val peopleDF = spark.read.format("json").load("examples/src/main/resources/people.json")
peopleDF
	.select("name", "age")
	.write
	.format("parquet")
	.save("namesAndAges.parqute")

// val peopleDF = spark.read.json("examples/src/main/resources/people.json")
// val peopleDF = spark.read.parquet("examples/src/main/resources/people.json")
// val peopleDF = spark.read.jdbc("examples/src/main/resources/people.json")
// val peopleDF = spark.read.orc("examples/src/main/resources/people.json")
// val peopleDF = spark.read.libsvm("examples/src/main/resources/people.json")
// val peopleDF = spark.read.csv("examples/src/main/resources/people.json")
// val peopleDF = spark.read.textFile("examples/src/main/resources/people.json")
// peopleDF.select("name", "age").write.json("namesAndAges.parqute")
// peopleDF.select("name", "age").write.parquet("namesAndAges.parqute")
// peopleDF.select("name", "age").write.jdbc("namesAndAges.parqute")
// peopleDF.select("name", "age").write.orc("namesAndAges.parqute")
// peopleDF.select("name", "age").write.libsvm("namesAndAges.parqute")
// peopleDF.select("name", "age").write.csv("namesAndAges.parqute")
// peopleDF.select("name", "age").write.textFile("namesAndAges.parqute")

// 直接在文件上运行SQL
val sqlDF = spark.sql("SELECT * FROM parquet.'./data/examples/src/main/resources/users.parquet'")
sqlDF
	.select("name", "favorite_color")
	.write
	.save("namesAndFavColors.parquet")

// -----------------------------------------
// 保存模式
// -----------------------------------------
SaveMode.ErrorIfExists
SaveMode.Append
SaveMode.Overwrite
SaveMode.Ignore

// -----------------------------------------
// 保存到持久表(Hive metastore)
// -----------------------------------------
// 分桶，排序，保存到持久表
peopleDF
	.write
	.bucketBy(42, "name")
	.sortBy("age")
	.saveAsTable("people_bucketed")
// 分区，保存
usersDF
	.write
	.partitionBy("favorite_color")
	.format("parquet")
	.save("namesPartByColor.parquet")

// 分区，分桶，保存到持久表
peopleDF
	.write
	.partitionBy("favorite_color")
	.bucketBy(42, "name")
	.saveAsTable("people_partitioned_bucketed")



// ===============================
// Parquet文件
// ===============================
import spark.implicits._

val peopleJsonDF = spark.read.json("./data/examples/src/main/resources/people.json")
peopleJsonDF.write.json("people.json")
peopleJsonDF.write.parquet("people.parquet")

val peopleParquetDF = spark.read.parquet("./data/examples/src/main/resources/people.parquet")
peopleParquetDF.write.json("people.json")
peopleParquetDF.write.parquet("people.parquet")

peopleParquetDF.createOrReplaceTempView("peopleParquetFile")
val namesDF = spark.sql("SELECT name FROM peopleParquetFile WHERE age BETWEEN 13 AND 19")
namesDF
	.map(attributes => "Name:" + attributes(0))
	.show()

// Parquet数据源可以自动发现和infer推断分区信息
// 支持的数据类型
	// numeric
	// string
// spark.sql.sources.parititonColumnTypeInference.enable=true : 允许自动类型推断分区列的数据类型
// spark.sql.sources.parititonColumnTypeInference.enable=false：禁止类型推断（String将用于分区列）

// SparkSession.read.parquet("path/to/table/gender=male")
// SparkSession.read.load("path/to/table/gender=male")


// 模式演进(schema evolution)
// SparkSession.read.parquet(, mergeSchema = true)


// ===============================
// Json文件
// ===============================
import spark.implicits._

// Load json file
val path = "./data/examples/src/main/resources/people.json"
val peopleDF = spark.read.json(path)
peopleDF.printSchema()

// SQL 
peopleDF.createOrReplaceTempView("people")
val teenagerNamesDF = spark.sql("SELECT name FROM people WHERE age >= 13 AND age <= 19")
teenagerNamesDF.show()


// DataFrame(Dataset[String])
val otherPeopleDataset = spark.createDataset(
	"""{"name": "Yin", "address": {"city": "Columbus", "state": "Ohio"}}"""::Nil
)
val otherPeople = spark.read.json(otherPeopleDataset)
otherPeople.show()

// ===============================
// Hive表
// ===============================
// Spark SQL reading and writing data stored in Apache Hive
// Hive serdes
// Hive user-defined function
// Hive metastore: enable Spark SQL to access metadata of Hive tables

import java.io.File
import org.apache.spark.sql.{Row, SaveMode, SparkSession}
import spark.implicits._
import spark.sql

object HiveTable {
	def main(args: Array[String]) {
		case class Record(key: Int, value: String)

		// warehouseLocation points to the default location for managed databases and tables
		val warehouseLocation = new File("spark-warehouse").getAbsolutePath
		val spark = SparkSession
			.builder()
			.appName("Spark Hive Example")
			.config("spark.sql.warehouse.dir", warehouseLocation)
			.enableHiveSupport()
			.getOrCreate()

		// HiveSQL
		sql("CREATE TABLE IF NOT EXISTS src(key Int, value STRING) USING hive")
		sql("LOAD DATA LOCAL INPATH 'D:/spark-2.3.2-bin-hadoop2.7/example/src/main/resources/kv1.txt' INTO TABLE src")
		sql("SELECT * FROM src").show()
		sql("SELECT COUNT(*) FROM src").show()

		val sqlDF = sql("SELECT key, value FROM src WHERE key < 10 ORDER BY key")
		val stringDS = sqlDF.map {
			case Row(key: Int, value: String) => s"Key: $key, Value: $value"
		}
		stringDS.show()

		// close spark
		spark.close()
	}
}


// ===============================
// JDBC连接其他数据库
// ===============================
// $bin/spark-shell -driver-class-path postgresql-9.4.1207.jar --jars postgresql-9.4.1207.jar
// Data Sources API
	// DataFrame
	// Spark SQL


// version 1
val jdbcDF1 = spark.read
	.format("jdbc")
	// .jdbc()
	.option("url", "jdbc:postgresql:dbserver")
	// .option("url", "jdbc:postgresql://localhost/port?user=fred&password=secret")
	.option("dbtable", "schema.tablename")
	.option("driver", "")
	.option("partitionColum", "")
	.option("lowerBound", "")
	.option("upperBound", "")
	.option("numPartitions", "")
	.option("fetchsize", "")
	.option("batchsize", "")
	.option("isolationLevel", "")
	.option("truncate", "")
	.option("createTableOptions", "")
	.option("createTableColumnTypes", "")
	.option("user", "username")
	.option("password", "password")
	.load()

// version 2
val connectionProperties = new Properties()
connectionProperties.put("user", "username")
connectionProperties.put("password", "password")
val jdbcDF2 = spark.read
	.jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties)



// -----------------------------------------
// version 1
jdbcDF1.write
	.format("jdbc")
	.option("url", "jdbc:postgresql:dbserver")
	.option("dbtable", "schema.tablename")
	.option("user", "username")
	.option("password", "password")
	.save()

// version 2
jdbcDF.write
  .option("createTableColumnTypes", "name CHAR(64), comments VARCHAR(1024)")
  .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties)

jdbcDF2.write
	.jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties)




// ===============================
// 
// ===============================

