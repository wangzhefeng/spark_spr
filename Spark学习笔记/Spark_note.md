# Spark

* Spark管理和协调集群计算机上的数据执行任务;
	- Spark将用于执行任务的集群交给集群管理器管理,如:Spark Standalone集群管理器,YARN集群管理器,Mesos集群管理器,然后我们向这些集群管理器提交Spark应用程序,它将为我们的应用程序提供资源,以便我们能够完成我们的工作;
* Spark应用程序
	- Spark使用一个集群管理器来跟踪可用的资源;
	- Spark应用程序由一个driver进程(驱动程序)和一组executor进程组成, dirver驱动程序负责执行驱动程序的命令,在executor执行器中完成给定的任务;
		- driver驱动程序是Spark应用程序的核心,在Spark应用程序的生命周期内维护所有相关信息.driver进程负责运行`main`函数,此进程位于集群中的一个节点上,负责三件事:
			- 维护有关Spark应用程序的信息;
			- 响应用户的程序或输入;
			- 分配和调度executor的工作;
		- executor进程实际执行driver分配给他们的工作,每个executor负责两件事:
			- 执行由driver驱动程序分配给它的代码;
			- 将执行器executor的计算状态报告给dirver驱动节点;
* Spark API
	- 驱动Spark的语言: 
		- Scala
		- Java
		- Python
		- R
			- SparkR
			- sparklyr
	- 两个基本的API集合:
		- low-level的"非结构化"API
		- higher-level的"高级的结构化"API
* Spark启动模式
	- 集群模式
	- 本地模式
		- Scala: 
			- `./bin/spark-shell`
		- Python: 
			- `./bin/pyspark`
	- spark-submit
		- 提交一个预编译的应用程序到Spark集群
		- `val spark = SparkSession.builder().master().appName().config().getOrCreate()`
* Spark的核心抽象,它们都表示数据的分布式集合,最简单的是DataFrame:
	- Datasets
	- DataFrame
	- SQL表
	- RDD(弹性分布式数据集)


<!--======================================================================== -->


## SparkSession

> 创建一个DataFrame,其中一列包含1000行,值从0到999,这一系列数字代表一个分布式集合. 当在一个集群上运行时,这个范围的每个部分都存在于一个不同的executor上.

```scala
val myRange = spark.range(1000).toDF("number")
```

```python
myRange = spark.range(1000).toDF("number")
```

## DataFrame


> * DataFrame是最常见的结构化API,它只是表示包含行和列的数据表.定义列和列类型的列表称为schema(模式);
> * Spark具有Python和R的语言接口,很容易将Pandas(Python)的DataFrame, R DataFrame转换为Spark DataFrame;


## Partitions

> * 为了使每个executor执行器并行执行任务,Spark将数据分解成"数据块",这些`数据块`称为partition(分区); 一个分区是集群中的一个物理机器上的行集合;
> * 如果有一个分区,Spark将只有一个并行任务,即使有数千个executor执行器; 如果由多个分区,但只有一个executor执行器,Spark仍然只有一个并行任务,因为只有一个计算资源;
> * DataFrame的分区表示了在执行过程中数据是如何在机器集群中物理分布的;对于DataFrame,不需要手动或单独操作分区,只需在物理分区中指定数据的高级转换,Spark将确定该工作将如何在集群上执行;


## Transformations

> * Spark中,核心数据结构是不可变的,这意味着它们在创建之后无法更改;
> * Transformations是使用Spark表达业务逻辑的核心方法,有两种类型的转换, 窄依赖的转换,宽依赖的转换
	- 窄依赖的转换(Narrow transformations, 1 to 1): 是每个输入数据分区只对一个数据输出分区
		- Spark将自动执行称为流水线pipeline的操作, 意味着如果在DataFrames上指定多个过滤器,它们都将在内存中执行,不会产生shuffle;
		- where
	- 宽依赖的转换(Wide transformations, 1 to n): 是一个输入数据分区对应多个输出分区
		- Shuffle: Spark将在集群中交换分区中的数据,当进行shuffle洗牌时,Spark会将结果写入磁盘;
> * Lazy Evaluation
	- 在 Spark 中,不会在执行某个转换操作时立即修改数据,spark 会构建了一个您想要应用于您的源数据的转换计划。通过等待直到最后一分钟执行代码,Spark将这个计划从原始的转换到一个流线型的物理计划,该计划将在整个集群中尽可能高效地运行。这提供了巨大的好处,因为 Spark 可以从端到端优化整个数据流;


窄依赖的转换where:

```scala
val myRange = spark.range(1000).toDF("number")
val divisBy2 = myRange.where("number % 2 = 0")
```

```python
divisBy2 = myRange.where("number % 2 = 0")
```


## Actions

> * transformation允许我们构建逻辑转换计划,为了触发计算,需要运行一个action操作;
> * action操作指示Spark通过执行一系列transformation计算结果;
> * Spark有三种类型的action:
	- 在控制台中查看数据的action
		- `show()`
		- `count()`
	- 数据收集的action操作
		- `collect()`
	- 输出到第三方存储系统的action操作
		 - saveAsTextFle()


在指定这个count操作时,启动了一个Spark Job,运行过滤器filter转换(一个窄依赖转换), 然后是一个聚合(一个宽依转换), 它在每个分区基础上执行计数,然后是一个收集action,它将结果driver端:

```scala
val myRange = spark.range(1000).toDF("number")
val divisBy2 = myRange.where("number % 2 = 0")
divisBy2.count()
````

## 完整例子:

1.查看集群上的美国运输统计局的飞行数据:

```shell
head /data/flight-data/csv/2015-summary.csv
```


2.Spark读取一些数据

> 每个DataFrame都有一组列,其中列的数据行数不确定,行数不确定的原因是读取数据是一个transformation操作,因此是一个延迟操作,Spark只查看了几行数据,试图猜测每个列应该是什么类型;

```scala
val flightData2015 = spark
	.read
	.option("inferSchema", "true") // schema inference(模式推理)
	.option("header", "true")
	.csv("/data/flight-data/csv/2015-summary.csv")
```

```python
flightData2015 = spark \
	.read \
	.option("inferSchema", "true") \
	.option("header", "true") \
	.csv("/data/flight-data/csv/2015-summary.csv")
```


3.执行操作

查看数据前三行:

```scala
flightData2015.take(3)
```

根据count列对数据进行排序, 查看Spark构建的计划:

```scala
flightData2015.sort("count").explain()
```

默认情况下,当我们执行shuffle时,Spark会输出200个shuffle分区,可以通过配置项进行配置:

```scala
spark.conf.set("spark.sql.shuffle.partitions", "5")
flightData2015.sort("count").take(2)
```




# 结构化学API基本操作

* DataFrame操作
* 聚合操作
* 窗口函数
* 连接操作

> * 从定义上看,一个DataFrame包括一系列的record(记录,就像table中的rows),这些行的类型是`Row`类型,包括一系列的columns,作用于数据集每条记录上的计算表达式,实际上是作用于数据记录中的columns之上.
> * Schema
	- Schema定义了DataFrame每一列的列名和数据类型, 可以让数据源定义schema模式(称为schema-on-read), 或者自己定义它;
	- 一个schema就是一个StructType,由多个StructField类型的fields组成,每个field包括一个列名称,一个列类型,一个布尔型的标识(是否可以有缺失值和null值);
	- Schema可以包含其他StructType(Spark的复杂类型),如果数据中的类型(在运行时)与模式不匹配,Spark将抛出一个错误;
> * DataFrame的partition(分区)定义了DataFrame或Dataset在整个集群中的物理分布情况;
> * Columns 和 Expressions
	- Spark中的列类似于电子表格中的列,可以从DataFrame中选择列,操作列,删除列,这些操作称为Expressions表达式;
	- 对 Spark 来说,列是逻辑结构,它仅仅表示通过一个表达式按每条记录计算出的一个值。这意味着,要得到一个 column 列的真实值,我们需要有一行 row 数据,为了得到一行数据,我们需要有一个 DataFrame。不能在 DataFrame 的上下文之外操作单个列。必
须在 DataFrame 内使用 Spark 转换来修改列的内容;
	- 列是表达式,表达式是在DataFrame中数据记录的一个或多个值上的一组转换, 把它想象成一个函数,它将一个或多个列名作为输入,解析它们,然后潜在地应用更多的表达式,为数据集中的每个记录创建一个单一值;
	- 在最简单的情况下,通过`expr`函数创建的表达式只是一个DataFrame列引用,在最简单的情况下,`expr("someCol")`等价于`col("someCol")`;
> * Records 和 Rows
	- 在 Spark 中,DataFrame 中的每一行都是单个记录。Spark 表示此记录为 Row 类型的对象。即一个 record 是一个 Row 类型的对象
	- Spark 使用列表达式 expression 操作 Row 对象,以产生有效的结果值
	- Row 对象的内部表示为:字节数组
	- 只有 DataFrame 有 schema,Row 本身没有 schema。这意味着,如果您手动创建一个 Row 对象,则必须以与它们可能被附加的 DataFrame 的 schema 相同的顺序指定列值
	- 访问创建的Row对象中的数据非常容易,只需制定想要列值的位置;
> * DataFrame Transformations
	- 添加行,列
	- 删除行,列
	- 变换一行成一列
	- 根据列值对rows进行排序



1.创建一个DataFrame

```scala
val df = spark.read.format("json")
	.load("/data/flight-data/json/2015-summary.json")
```

```python
df = spark.read.format("json").load("/data/flight-data/json/2015-summary.json")
```

2.查看DataFrame上的schema

```scala
df.printSchema()
```

```scala
spark.read.format("json")
	.load("/data/flight-data/json/2015-summary.json")
	.schema
```

3.在DataFrame上创建和执行特定的schema

```scala
import org.apache.spark.sql.types.{StructField, StructType, StringType, LongType}
import org.apache.spark.sql.types.Metadata

val myManualSchema = new StructType(Array(
	StructField("DEST_COUNTRY_NAME", StringType, true),
	StructField("ORIGIN_COUNTRY_NAME", StringType, true),
	StructField("count", LongType, false, Metadata.fromJson("{\"hello\":\"world\"}"))
))

val df = spark.read.format("json")
	.schema(myManualSchema)
	.load("/data/flight-data/json/2015-summary.json")
```

4.创建和引用Columns

```scala
import org.apache.spark.sql.functions.{col, column}

col("someColumnName")
column("someColumnName")

// 创建一个列,但不提供性能改进
$"myColumn"
'myColumn
```

5.访问DataFrame中的columns

```scala
spark.read.format("json")
	.load("/data/flight-data/json/2015-summary.json")
	.columns
```

6.创建和访问Rows


```scala
import org.apache.spark.sql.Row

val myRow = Row("Hello", null, 1, false)

myRow(0) 					  // type Any
myRow(0).asInstanceOf[String] // String
myRow.getString(0) 			  // String
myRow.getInt(2)			 	  // Int
```

7.DataFrame Transformation

7.1 创建DataFrame

```scala
val df = spark.read.format("json")
	.load("/data/flight-data/json/2015-summary.json")
df.createOrReplaceTempView("dfTable")
```

```python
df = spark.read.format("json") /
	.load("/data/flight-data/json/2015-summary.json")
df.createOrReplaceTempView("dfTable")
```

```scala
// 动态创建DataFrame
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructField, StructType, StringType, LongType}

val myManualSchema = new StructType(Array(
	new StructField("some", StringType, true),
	new StructField("col", StringType, true),
	new StructField("names", LongType, false)
))

val myRows = Seq(Row("Hello", null, 1L))
val myRDD = spark.sparkContext.parallelize(myRows)
val myDF = spark.createDataFrame(myRDD, myManualSchema)
myDF.show()
```

```scala
// 在 Scala 中,我们还可以利用控制台中的 Spark 的 implicits(如果您将它们导入到 JAR代码中),则可以在 Seq 类型上运行toDF。这不能很好地使用 null 类型,因此并不推荐用于生产用例
import spark.implicits._
val myDF = Seq(("Hello", 2, 1L)).toDF("col1", "col2", "col3")
```


7.2 DataFrame方法

* select()
	- 接收column或表达式expression为参数
* selectExpr()
	- 接收字符串表达式expression为参数
* org.apache.spark.sql.functions


```scala
// SQL: SELECT DEST_COUNTRY_NAME FROM dfTable LIMIT 2;
df.select("DEST_COUNTRY_NAME")
  .show(2)

// SQL: SELECT DEST_COUNTRY_NAME, ORIGIN_COUNTRY_NAME FROM dfTable LIMIT 2;
df.select("DEST_COUNTRY_NAME", 
		  "ORIGIN_COUNTRY_NAME")
  .show(2)

import org.apache.spark.sql.functions.{expr, col, column}
df.select(
	df.col("DEST_COUNTRY_NAME"),
	col("DEST_COUNTRY_NAME"),
	column("DEST_COUNTRY_NAME"),
	'DEST_COUNTRY_NAME,
	$DEST_COUNTRY_NAME,
	expr("DEST_COUNTRY_NAME"))
  .show(2)


df.select(expr("DEST_COUNTRY_NAME AS destination"))
  .show(2)

df.select(expr("DEST_COUNTRY_NAME AS destination").alias("DEST_COUNTRY_NAME"))
  .show(2)

df.selectExpr("DEST_COUNTRY_NAME AS newColumnName", "DEST_COUNTRY_NAME")
  .show(2)
```


```scala
df.selectExpr(
	"*",
	"(DEST_COUNTRY_NAME = ORIGIN_COUNTRY_NAME) as withinCountry")
  .show()
```


```scala
df.selectExpr("avg(count)", "count(distinct(DEST_COUNTRY_NAME))")
  .show(2)
```
