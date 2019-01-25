

# pyspark API

[TOC]

## Package 和 Subpackages

* pyspark
* pyspark.sql
* pyspark.streaming
* pyspark.ml
* pyspark.mllib


## pyspark内容

* class: `pyspark.SparkConf`
	- 配置Spark
* class: `pyspark.SparkContext`
	- Spark功能的主要入口
* class: `pyspark.SparkFiles`
* class: `pyspark.RDD`
* class: `pyspark.StorageLevel`
* class: `pyspark.`
* class: `pyspark.`
* class: `pyspark.`
* class: `pyspark.`
* class: `pyspark.`
* class: `pyspark.`
* class: `pyspark.`
* class: `pyspark.`
* class: `pyspark.`
* class: `pyspark.`
* class: `pyspark.`


## class pyspark.SparkConf()

> * 配置一个 Spark 应用，设置一系列key-value形式的 Spark  参数
>     - `spark.master`: 用于连接的master URL
> 	  - `spark.app.name`: App名称
>     - `spark.ExecutorEnv`: 用于传递到Executors的环境变量
>     - `spark.home`: 在工作节点上(worker nodes)Spark安装路径
> * 将 SparkConf 对象传递给 Spark 后，它将被克隆，用户无法再对其进行修改

#### 方法

* 获取配置信息
	- contains()
	- get(key = None)
	- getAll()
	- toDebugString()
* 设置配置
	- set()
	- setAll()
	- setAppName()
	- setExecutorEnv()
	- setIfMissing
	- setMaster()
	- setSparkHome()

#### 示例


```python
import pyspark 

# 在Python中初始化Spark
conf = pyspark.SparkConf() \
	.setMaster("local") \
	.setAppName("My First Spark App") \
	.setExecutorEnv(key = None, value = None, pairs = None) \
	.setSparkHome(value = "D:/spark/bin") \
	.setIfMissing(key = None, value = None)


# conf2 = SparkConf() \
# 	.set(key = "spark.master", value = "local") \
# 	.set(key = "spark.app.name", value = "My First Spark App") \
# 	.set(key = "spark.home", value = "D:/spark/bin")

# conf3 = SparkConf() \
# 	.setAll([{
# 		"spark.master": "local",
# 		"spark.app.name": "My First Spark App",
# 	}])


print(conf.contains("spark.master"))
print(conf.contains("spark.app.name"))
print(conf.contains("spark.home"))

print(conf.get("spark.master"))
print(conf.get("spark.app.name"))
print(conf.get("spark.home"))

print(conf.getAll())
print(conf.toDebugString())
```

结果：

```
True
True
True

local
My First Spark App
D:/spark/bin

dict_items([('spark.master', 'local'), ('spark.app.name', 'My First Spark App'), (None, 'None'), 
('spark.home', 'D:/spark/bin')])

spark.master=local
spark.app.name=My First Spark App
None=None
spark.home=D:/spark/bin
```


## class pyspark.SparkContext()

> Spark功能的主要入口，SparkContext表示`Spark集群的连接`，可以用于在该集群上创建`RDD`和`广播变量`

#### 方法

* PACKAGE_EXTENSIONS = (".zip", ".egg", ".jar")
* 信息
	- .version
	- .applicationId
		- Spark App 的唯一表示, 格式依赖于调度的任务类型
	- .getConf().getAll()
	- .getConf().get(key = None)
* accumulator()
* addFile(path, recursive = False)
	- 在这个Spark任务上为每个节点增加一个需要下载的文件。`path`可以是一个本地文件，也可以是一个HDFS文件，或者一个HTTP、HTTPS、FTP URL
* addPyFile(path)
	- 为将来在这个Spark任务上运行的所有任务增加一个.py或者.zip依赖。`path`可以是一个本地文件，也可以是一个HDFS文件，或者一个HTTP、HTTPS、FTP URL


* binaryFiles(path, minPartitions = None)
	- 读取一个来自HDFS或者本地文件系统中目录下的binary文件
* binaryRecords(path, recordLength)


* 



#### 示例

```python
import pyspark

conf = pyspark.SparkConf() \
	.setMaster("local") \
	.setAppName("My First Spark App") \
	.setExecutorEnv(key = None, value = None, pairs = None) \
	.setSparkHome(value = "D:/spark/bin") \
	.setIfMissing(key = None, value = None)

sc = pyspark.SparkContext(conf)

print(sc.version)
print(sc.getConf().getAll())
```

* addFile()

```python
from pyspark import SparkFiles

path = os.path.join(tempdir, "test.txt")
with open(path, "w") as testFile:
	_ = testFile.write("100")

sc.addFile(path)

def func(iterator):
	with open(SparkFiles.get("test.txt")) as testFile:
		fileVal = int(testFile.readline())
		return [x * fileVal for x in iterator]

sc.parallelize([1, 2, 3, 4]).mapPartitions(func).collect()
```


## class pyspark.SparkFiles()

> 

#### 方法

#### 示例

```python
sc.addFile(path)
file = SparkFiles.get("test.txt")
rootDir = SparkFiles.getRootDirectory("test.txt")
```









## class pyspark.BarrierTaskInfo()

> 携带屏障任务的所有任务信息

#### 方法

* .address
	- IPv4地址(host:port)

#### 示例

```python
barrierTaskInfos = pyspark.BarrierTaskInfo()
barrierTaskInfos.address
```