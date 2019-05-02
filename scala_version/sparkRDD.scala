// =========================================================
// 连接、初始化Spark
// =========================================================
//-------------------------------------
// Add a Maven dependency on Spark
//-------------------------------------
/*
groupId = org.apache.spark
artifactId = spark-core_2.11
version = 2.4.0
*/

//-------------------------------------
// Add a dependency on 'hadoop-client' for version of HDFS
//-------------------------------------
/*
groupId = org.apache.hadoop
artifactId = hadoop-client
version = <your-hdfs-version>
*/

//-------------------------------------
// import some Spark classes into program
//-------------------------------------
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

//-------------------------------------
// create a SparkContext to acces a cluster
//-------------------------------------
object objectName {
	def main(args: Array[String]) = {
		val conf = new SparkConf()
			.setAppName("HelloWorld")
			.setMaster("local[*]")
		val sc = new SparkContext(conf)



	}
}

/*                 Master URLs
 * local
 * local[K]
 * local[K, F]
 * local[*]
 * local[*, F]
 * spark://HOST:PORT
 * spark://HOST1:PORT1,HOST2:PORT2
 * mesos://HOST:PORT
 * yarn
 * k8s://HOST:PORT
*/


// =========================================================
// Resilient Distributed Datasets (RDDs)
// =========================================================
//-------------------------------------
// Parallelized Collections
//-------------------------------------
val data = Array(1, 2, 3, 4, 5)
val distData = sc.parallelize(data, parallelize = 10)


//-------------------------------------
// External Datasets
// local file system, HDFS, Cassandra, HBase, Amazon S3
// text files, SequenceFiles, Hadoop InputFormat
//-------------------------------------
// local file system
val distFile = sc.textFile("data.txt")
val distFiles = sc.wholeTextFiles("my/directory")
val distFile_files = sc.textFile("my/directory")
val distFile_txts = sc.textFile("my/directory/*.txt")
val distFile_gzs = sc.textFile("my/directory/*.gz")
// HDFS
val distFile_hdfs = sc.textFile("hdfs://data.txt")
// Amazon S3
val distFile_s3a = sc.textFile("s3a://data.txt")
// SequenceFiles
val distFiles2 = sc.sequenceFile[K, V]

sc.hadoopRDD()
sc.newAPIHadoopRDD()
sc.saveAsObjectFile()
sc.objectFile()


//-------------------------------------
// RDD Operations
//-------------------------------------
// basic
val lines = sc.textFile("data.txt")
val lineLengths = line.map(s => s.length)
val totalLengths = lineLengths.reduce((a, b) => a + b)
lineLengths.persist()


// passing functions to Spark
object MyFunctions {
	def func1(s: String): String = {...}
}
myRdd.map(MyFunctions.func1)

class MyClass {
	def func1(s: String): String = {...}
	def doStuff(rdd: RDD[String]): RDD[String] = {rdd.map(func1)}
}

class Mycalss {
	val field = "Hello"
	def doStuff(rdd: RDD[String]): RDD[String] = {rdd.map(x => field + x)}
}

def doStuff(rdd: RDD[String]): RDD[String] = {
	val field_ = this.field
	rdd.map(x => field_ + x)
}



// 打印RDD中的元素
// 本地模式
rdd.foreach(println)
rdd.map(println)
// 集群模式
rdd.collect().foreach(println)
rdd.take(100).foreach(println)



//-------------------------------------
// RDD 持久化
//-------------------------------------


// =========================================================
// 共享变量
// =========================================================


// =========================================================
// 部署到集群上
// =========================================================


// =========================================================
// 从Java / Scala启动Spark作业
// =========================================================


// =========================================================
// 单元测试
// =========================================================


