# (I) Apache Spark


## 1.Spark 的哲学和历史

* Apache Spark is **a unified computing engine** and **a set of libraries for parallel data processing(big data) on computer cluster**, and Spark **support multiple widely used programming language** (Python, Java, Scala, and R), and Spark **runs anywhere** from a laptop to a cluster of thousand of servers. This makes it an easy system to start with and scale-up to big data processing or incredibly large scale.
	- **A Unified Computing Engine**
		- [Unified] 
			- Spark's key driving goal is to offer a unified platform for writing big data applications. Spark is designed to support a wide range of data analytics tasks, range from simple data loading and SQL queries to machine learning and streaming computation, over the same computing engine and with a consistent set of APIs.
		- [Computing Engine] 
			- Spark handles loading data from storage system and performing computation on it, not permanent storage as the end itself, you can use Spark with a wide variety of persistent storage systems.
				* cloud storage system
					- Azure Stroage
					- Amazon S3
				* distributed file systems
					- Apache Hadoop
				* key-value stroes
					- Apache Cassandra
				* message buses 
					- Apache Kafka
	- **A set of libraries for parallel data processing on computer cluster**
		+ Standard Libraries
			* SQL and sturctured data
				* SparkSQL
			* machine learning
				* MLlib
			* stream processing
				* Spark Streaming
				* Structured Streaming
			* graph analytics
				- GraphX
		+ [External Libraries](https://spark-packages.org/) published as third-party packages by open source communities



## 2.Spark 开发环境

- Language API
	+ Python
	+ Java
	+ Scala
	+ R
	+ SQL
- Dev Env
	+ local
		* [Java(JVM)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
		* [Scala](https://www.scala-lang.org/download/)
		* [Python interpreter(version 2.7 or later)](https://repo.continuum.io/archive/)
		* [R](https://www.r-project.org/)
		* [Spark](https://spark.apache.org/downloads.html)
	+ web-based version in [Databricks Community Edition](https://community.cloud.databricks.com/)


## 3.Spark's Interactive Consoles

Python:

```shell
./bin/pyspark
```

Scala:

```shell
./bin/spark-shell
```

SQL:

```shell
./bin/spark-sql
```

## 4.云平台、数据

- [Project's Github](https://github.com/databricks/Spark-The-Definitive-Guide)
- [Databricks](https://community.cloud.databricks.com/)


# (II) Spark

## 1.Spark's Architecture

### **Cluster**

> Challenging: data processing

* **Cluser(集群)**:
	- Single machine do not have enough power and resources to perform computations on huge amounts of information, or the user probably dose not have the time to wait for the computationto finish;
	- A cluster, or group, of computers, pools the resources of many machines together, giving us the ability to use all the cumulative resources as if they were a single computer.
	- A group of machines alone is not powerful, you need a framework to coordinate work across them. Spark dose just that, managing and coordinating the execution of task on data across a cluster of computers.
* **Cluster manager(集群管理器)**:
	- Spark's standalone cluster manager
	- YARN
	- Mesos

### **Spark Application**

* **Cluster Manager**
	* A **Driver** process
		- the heart of a Spark Appliction and maintains all relevant information during the lifetime of the application;
		- runs `main()` functions;
		- sits on a node in the cluster;
		- responsible for:
			- maintaining information about the Spark Application
			- responding to user's program or input
			- analyzing, distributing and scheduling work across the **executors**
	* A Set of **Executor** process
		- responsible for actually carrying out the work that the **driver** assigns them
		- repsonsible for :
			- executing code assigned to it by the driver
			- reporting the state of the computation on that executor back to the dirver node


* **Spark Application**
	- Spark employs a **cluster manager** that keeps track of the **resources** available;
	- The **dirver** process is responsible for executing the **dirver program's commands** across the **executors** to complete a given task;
		- The executors will be running Spark code


## 2.Spark's Language API

- Scala
	- Spark's "default" language.
- Java
- Python
	- `pyspark`
- SQL
	- Spark support a subset of the ANSI SQL 2003 standard.
- R
	- Spark core
		- `SparkR`
	- R community-driven package
		- `sparklyr`

## 3.Spark's API

**Spark has two fundamental sets of APIS:**

* Low-level "unstructured" APIs
	- RDD
	- Streaming
* Higher-level structured APIs
	- Dataset
	- DataFrame
		- `org.apache.spark.sql.functions`
		- Partitions
		- DataFrame(Dataset) Methods
			- DataFrameStatFunctions
			- DataFrameNaFunctions
		- Column Methods
			- alias
			- contains
	- Spark SQL
	- Structured Streaming

## 4.开始 Spark


* 启动 Spark's local mode、
	- 交互模式
		- `./bin/spark-shell`
		- `./bin/pyspark`
	- 提交预编译的 Spark Application
		- `./bin/spark-submit`
* 创建 `SparkSession`
	- 交互模式，已创建
		- `spark`
	- 独立的 APP
		- Scala: 
			- `val spark = SparkSession.builder().master().appName().config().getOrCreate()`
		- Python: 
			- `spark = SparkSession.builder().master().appName().config().getOrCreate()`

### 4.1 SparkSession


> * **Spark Application** controled by a **Driver** process called the **SparkSession**；
> * **SparkSession** instance is the way Spark executes user-defined manipulations across the cluster, and there is a one-to-one correspondence between a **SparkSession** and a **Spark Application**;



示例：

Scala 交互模式：

```shell
# in shell
$ spark-shell
```

```scala
// in Scala
val myRange = spark.range(1000).toDF("number")
```

Scala APP 模式：

```scala
// in Scala
import org.apache.spark.SparkSession
val spark = SparkSession 
	.builder()
	.master()
	.appName()
	.config()
	.getOrCreate()
```

Python 交互模式：

```shell
# in shell
$ pyspark
```


```python
# in Pyton
myRange = spark.range(1000).toDF("number")
```

Python APP 模式：

```python
# in Python
from pyspark import SparkSession
spark = SparkSession \
	.builder() \
	.master() \
	.appName() \
	.config() \
	.getOrCreate()
```

### 4.2 DataFrames

> * A DataFrame is the most common Structured API;
> * A DataFrame represents a table of data with rows and columns;
	-  The list of DataFrame defines the columns, the types within those columns is called the schema;
> * Spark DataFrame can span thousands of computers:
	- the data is too large to fit on one machine
	- the data would simply take too long to perform that computation on one machine


### 4.3 Partitions





### 4.4 Transformation

### 4.5 Lazy Evaluation

### 4.6 Action

### 4.7 Spark UI


> * **Spark job** represents **a set of transformations** triggered by **an individual action**, and can monitor the Spark job from the Spark UI;
> * User can monitor the progress of a Spark job through the **Spark web UI**:
	- Spark UI is available on port `4040` of the **dirver node**;
		- Local Mode: `http://localhost:4040`
	- Spark UI displays information on the state of:
		- Spark jobs
		- Spark environment
		- cluster state
		- tunning 
		- debugging







# (III) Spark Low-Level API

> * What are the Low-Level APIs ?
	- Resilient Distributed Dataset (RDD)
	- Distributed Shared Variables
		+ Accumulators
		+ Broadcast Variable
> * When to Use the Low-Level APIs ?
	- 在高阶 API 中针对具体问题没有可用的函数时；
	- Maintain some legacy codebase written using RDDs;
	- 需要进行自定义的共享变量操作时；
> * How to Use the Low-Level APIs ?
	- `SparkContext` 是 Low-Level APIs 的主要入口:
		+ `SparkSession.SparkContext`
		+ `spark.SparkContext`

## 1.RDD

### 1.1 创建 RDD

#### 1.1.1 DataFrame, Dataset, RDD 交互操作

**从 DataFrame 或 Dataset 创建 RDD:**

```scala
// in Scala: converts a Dataset[Long] to  RDD[Long]
spark.range(500).rdd

// convert Row object to correct data type or extract values
spark.range(500).toDF().rdd.map(rowObject => rowObject.getLong(0))
```

```python
# in Python: converts a DataFrame to RDD of type Row
spark.range(500).rdd

spark.range(500).toDF().rdd.map(lambda row: row[0])
```

**从 RDD 创建 DataFrame 和 Dataset:**


```scala
// in Scala
spark.range(500).rdd.toDF()
```


```python
# in Python
spark.range(500).rdd.toDF()
```


#### 1.1.2 从 Local Collection 创建 RDD

* `SparkSession.SparkContext.parallelize()`

```scala
// in Scala
val myCollection = "Spark The Definitive Guide: Big Data Processing Made Simple"
	.split(" ")
val words = spark.sparkContext.parallelize(myCollection, 2)
words.setName("myWords")
println(words.name)
```

```python
# in Python
myCollection = "Spark The Definitive Guide: Big Data Processing Made Simple" \
	.split(" ")
words = spark.sparkContext.parallelize(myCollection, 2)
words.setName("myWords")
print(word.name())
```

#### 1.1.3 从数据源创建 RDD

```scala
// in Scala
// each record in the RDD is the a line in the text file
spark.sparkContext.textFile("/some/path/withTextFiles")

// each text file is a single record in RDD
spark.sparkContext.wholeTextFiles("/some/path/withTextFiles")
```

```python
# in Python
# each record in the RDD is the a line in the text file
spark.sparkContext.textFile("/some/path/withTextFiles")

# each text file is a single record in RDD
spark.sparkContext.wholeTextFiles("/some/path/withTextFiles")
```

### 1.2 操作 RDD

* 操作 raw Java or Scala object instead of Spark types;

#### 1.2.1 Transformation

##### distinct 

```scala
// in Scala
words
	.distinct()
	.count()
```

##### filter

```scala
// in Scala
def startsWithS(individual: String) = {
	individual.startsWith("S")
}

words
	.filter(word => startsWithS(word))
	.collect()
```

```python
# in Python
def startsWithS(individual):
	return individual.startsWith("S")

words \
	.filter(lambda word: startsWithS(word)) \
	.collect()
```

##### map

```scala
val words2 = words.map(word => (word, word(0), word.startsWith("S")))
words2
	.filter(record => record._3)
	.take(5)
```

```python
# in Python
words2 = words.map(lambda word: (word, word[0], word.startsWith("S")))
words2 \
	.filter(lambda record: record[2]) \
	.take(5)
```

###### flatMap

```scala
// in Scala
words
	.flatMap(word => word.toSeq)
	.take()
```

```python
# in Python
words \
	.flatMap(lambda word: list(word)) \
	.take()
```

##### sort

```scala
// in Scala
words
	.sortBy(word => word.length() * -1)
	.take(2)
```

```python
# in Python
words \
	.sortBy(lambda word: word.length() * -1) \
	.take(2)
```

##### Random Splits

```scala
// in Scala
val fiftyFiftySplit = words.randomSplit(Array[Double](0.5, 0.5))
```

```python
# in Python 
fiftyFiftySplit = words.randomSplit([0.5, 0.5])
```


#### 1.2.2 Action

##### reduce

```scala
spark.sparkContext.parallelize(1 to 20)
	.reduce(_ + _) 
```

```python
spark.sparkContext.parallelize(range(1, 21)) \
	.reduce(lambda x, y: x + y)
```

##### count


###### countApprox


###### countApproxDistinct

###### countByValue

###### countByValueApprox

##### first

```scala
// in Scala
words.first()
```

```python
# in Python
words.first()
```

##### max/min

##### take

#### 1.2.3 Saving Files

#### 1.2.4 Caching

#### 1.2.5 Checkpointing

#### 1.2.6 Pipe RDDs to System Commands

## 2.Key-Value RDD


## 3.Distributed Shared Variables(分布式共享变量)

# (IV) Spark Structrued API


> Spark three core types of disturbuted collection structured API

* DataFrame
* SQL tables and views
* Dataset


> Data

* unstructured log files
* semi-structrued CSV files
* highly structured Parquet files

> Data flow computation

* batch computation
* streaming computation

> API types

* typed API
* untyped API
