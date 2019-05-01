

# DataFrame 操作函数 API


## 1.1内置函数

```scala
// API
function.expr()
```

## 1.2 DataFrame 操作函数

> * Annotations: @Stable()
> * Source: functions.scala
> * Since: 1.3.0

### 基本操作函数

* .show()


```scala
import org.apache.spark.sql.SparkSession

object SparkSQLAggregateFunc {
	def main(args: Array[String]) {
		val spark = SparkSession
			.builder
			.appName("Spark SQL Aggregate Functions")
			.config()
			.getOrCreate()

		val df = spark.read.json("")

		// -------------------------------------------------
		// functions
		// -------------------------------------------------
		df.show()
	}
}
```



### 聚合函数(Aggregate functions)

```scala
import org.apache.spark.sql.SparkSession

object SparkSQLAggregateFunc {
	def main(args: Array[String]) {
		val spark = SparkSession
			.builder
			.appName("Spark SQL Aggregate Functions")
			.config()
			.getOrCreate()

		val df = spark.read.json("")

		// -------------------------------------------------
		// functions
		// -------------------------------------------------
		// count()
		// countDistinct()
		// approx_count_distinct()
		df..select("").filter("").groupBy("").count().show()
		// sum()
		// sumDistinct()
		// avg()
		// collect_list()
		// collect_set()
		// first()
		// last()
		// max()
		// min()
		// mean()
		// variance()
		// var_pop()
		// var_samp()
		// skewness()
		// kurotsis()
		// stddev()
		// stddev_pop()
		// stddev_samp()
		// corr()
		// covar_pop()
		// covar_samp()
		// grouping()
		// grouping_id()
	}
}
```

### 集合函数(Collection functions)

```scala
import org.apache.spark.sql.SparkSession

object SparkSQLAggregateFunc {
	def main(args: Array[String]) {
		val spark = SparkSession
			.builder
			.appName("Spark SQL Aggregate Functions")
			.config()
			.getOrCreate()

		val df = spark.read.json("")

		// -------------------------------------------------
		// functions
		// -------------------------------------------------
		// array_contains()
		
	}
}
```


### 日期时间函数(Date Time functions)



### 数学函数(Math functions)

* abs()


```scala
import org.apache.spark.sql.SparkSession

object SparkSQLMathFunc {
	def main(args: Array[String]) {
		val spark = SparkSession
			.builder
			.appName("Spark SQL Math Functions")
			.config()
			.getOrCreate()

		val df = spark.read.json("")

		// abs()
		df..select("").abs()
	}
}
```


### 非聚合函数(Non-aggregate functions)


### 排序函数(Sorting functions)



### 字符函数(String functions)


### UDF函数(UDF functions)


### 窗口函数(Window functions)

* rank()
* dense_rank()
* percent_rank()
* row_number()


