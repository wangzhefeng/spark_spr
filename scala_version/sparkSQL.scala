
// ===============================================================
// SQL
// ===============================================================
/*
* Spark SQL => execute SQL queries
* Spark SQL => read data from an existing Hive installation
* Spark SQL => run SQL from within another programming language(scala, java, python)
*   - Dataset/DataFrame
* Spark SQL => interact with SQL interface
*   - Spark SQL CLI: spark-sql
*   - JDBC/ODBC
* */

// ===============================================================
// Datasets and DataFrame
// ===============================================================
/*
* --------------------------------
* Dataset
* --------------------------------
* distributed collection of data
* optimized RDD
* scala
* --------------------------------
* DataFrame
* --------------------------------
* Dataset organized into named columns => table/dataframe
* sources:
*   - structured data files
*   - Hive table
*   - external databases
*   - existing RDD
* scala, python, R
*   - scala: Dataset of RowS: Dataset[Row]
*   - Python: DataFrame
*   - R: data.frame
* */

// ===============================================================
// Spark SQL
// ===============================================================
import org.apache.spark.sql.SparkSession
import spark.implicits._


object sparkSQL {
	def main(args: Array[String]) {

    // ==========================================================================================
		// 程序入口: SparkSession
    // ==========================================================================================
		val spark = SparkSession
			.builder()
			.appName("Spark SQL")
			.config("spark.some.config.option", "some-value")
			.getOrCreate()

    // ==========================================================================================
		// 创建 DataFrames
		// From: existing RDD, Hive table, Spark data sources
    // ==========================================================================================
    // existing RDD
    // testRDD =

    // Spark data sources
    val df = spark.read.json("/home/wangzhefeng/project/bigdata/data/examples/src/main/resources/people.json")
		df.show()
    df.printSchema()

    // Hive table

		// ------------------------------------------------------
		// Untyped(弱类型) Dataset Operations(aka DataFrame Operations)
		// ------------------------------------------------------
    df.apply()
    df.col("salary")
    df.colRegex("^s")



		df.select("name").show()
    df.select($"name").show()
		df.select($"name", $"age" + 1).show()
    df.selectExpr("name", "age as age2", "abs(salary)")
    df.select(expr("name"), expr("age as age2"), expr("abs(salary)"))
		df.filter($"age" > 21).show()

    // cube
    df.cube("department", "group").avg()
    df.cube($"department", $"gender").agg(Map(
      "salary" -> "avg",
      "age" -> "max"
    ))

    // drop
    df.drop("age")

    // groupBy, agg
    df.agg(max($"age"), avg($"salary"))
    df.groupby().agg(max($"age"), avg($"salary"))
    df.agg(Map("age" -> "max", "salary" -> "avg"))
    df.groupBy().agg(Map("age" -> "max", "salary" -> "avg"))
		df.groupBy("age").count().show()
    df.groupBy($"department", $"gender").agg(Map("salary" -> "avg", "age" -> "max"))

    // join
    import org.apache.spark.sql.functions._
    df1.join(right = df2, joinExprs, joinType = "full")
    df1.join(df2, joinExprs, joinType = "outer")
    df1.join(df2, joinExprs, joinType = "full_outer")
    df1.join(df2, joinExprs, joinType = "inner")
    df1.join(df2, joinExprs)
    df1.join(df2).where()
    df1.join(df2, joinExprs, joinType = "left")
    df1.join(df2, joinExprs, joinType = "left_out")
    df1.join(df2, joinExprs, joinType = "right")
    df1.join(df2, joinExprs, joinType = "right_out")
    df1.join(df2, joinExprs, joinType = "left_semi")
    df1.join(df2, joinExprs, joinType = "left_anti")
    df1.join(df2, joinExprs, joinType = "corss")
    df1.join(df2, usingColumns = "", join_type = "")
    df1.join(df2, usingColumn = "")
    df1.join(df2)
    df.crossJoin(df2)

    // na
    df.na.drop()

    // rollup
    df.rollup("department", "group").avg()
    df.rollup($"department", $"group").agg(Map(
      "salary" -> "avg",
      "age" -> "max"
    ))

    // DataFrame 统计函数
    df1.stat.freqItems(Seq("a"))

    // withColumn
    df1.withColumn(colName = "", col = "")
    df1.withColumnRenamed(existingName = "", newName = "")





    // ------------------------------------------------------
		// Running SQL Queries Programmatically
    // ------------------------------------------------------
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

    // ==========================================================================================
		// Create Datasets
		// ==========================================================================================
    // Encoders are created for case classes
    case class Person(name: String, age: Long)
		val caseClassDS = Seq(Person("Andy", 32)).toDS()
		caseClassDS.show()

		// Encoders for most common types are automatically provided by importing spark.implicits._
		val primitiveDS = Seq(1, 2, 3).toDS()
		primitiveDS.map(_ + 1).collect()

		// DataFrame can be converted to a Dataset by providing a class.
		val path = "/home/wangzhefeng/project/bigdata/data/example/src/main/resources/people.json"
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






// ===============================================================
// 聚合操作
// ===============================================================
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










// 聚合函数
// class RelationalGroupedDataset extends AnyRef
groupBy()
cube()
rollup()

agg()
avg()
count()
max()
mean()
min()
pivot()
sum()
toString()


// DataFrames的统计函数
approxQuantile()
bloomFilter()
corr()
countMinSketch()
cov()
crosstab()
freqItems()
sampleBy()


// DataFrames的缺失值函数
drop()
fill()
replace()

