#!/usr/bin/env python
# -*- coding: utf-8 -*-


# ======================================
# 进入spark shell
# ======================================
# $ cd usr/lib/spark-2.1.1-bin-hadoop2.6/bin
# $ pyspark              # python
# $ spark-shell          # scala
# $ sparkR               # R

# ======================================
# 运行python脚本
# ======================================
# $ spark-submit my_script.py     # python

# ======================================
# 添加一个对于spark-core工件的Maven依赖    # java & scala
# ======================================
'''
groupId = org.apache.spark
artifactid = spark-core_2.10
version = 2.3.0
'''




# ======================================
# 初始化SparkContext
# ======================================
from pyspark import SparkConf, SparkContext

conf = SparkConf() \
	.setMaster("local")\
	.setAppName("My First Spark App")
sc = SparkContext(conf = conf)

# import org.apache.spark.SparkConf
# import org.apache.spark.SparkContext
# import org.apache.spark.SparkContext._
# val conf = new SparkConf().setMaster("local").setAppName("My First Spark(scala) App")
# val sc = new SparkContext(conf)

# ======================================
# 创建RDD
# ======================================
# 从外部数据创建一个名为lines的RDD
lines = sc.textFile("README.md")
lines.count()
lines.first()

# 把驱动器程序中一个已有的集合传给SparkContext的parallelize()方法创建RDD(有缺陷)
list_data = ["pandas", "i like pandas"]
lines = sc.parallelize(list_data)
lines.count()
lines.first()

# ======================================
# 转化操作 transformation
# ======================================
# 转化操作对RDD进行转化,定义新的RDD
# RDD.union()
inputRDD = sc.textFile("/home/wangzhefeng/project/spark/log.txt")
inputRDD.persist()
inputRDD.collect()
inputRDD.count()
inputRDD.first()
errorRDD = inputRDD.filter(lambda x: "error" in x)
warningRDD = inputRDD.filter(lambda x: "warning" in x)
badLinesRDD = errorRDD.union(warningRDD)
badLinesRDD.count()
badLinesRDD.first()
badLinesRDD.collect()


# RDD.filter()
lines = sc.textFile("README.md")
pythonLines = lines.filter(lambda line: "Python" in line)
pythonLines.persist()
pythonLines.count()
pythonLines.first()
pythonLines.collect()


# sc.map()
nums = sc.parallelize([1, 2, 3, 4])
squared = nums.map(lambda x: x * x).collect()
for num in squared:
	print("%i" % num)


# sc.flatMap()
lines = sc.parallelize(["hello world", "hi"])
words = lines.flatMap(lambda line: line.split(" "))
words.first()

## 伪集合操作
L1 = ["coffee", "coffee", "panda", "monkey", "tea"]
L2 = ["coffee", "monkey", "kitty"]
L1_RDD = sc.parallelize(L1)
L2_RDD = sc.parallelize(L2)
# RDD.distinct()
L1_RDD.distinct().collect()
# RDD.union(other)
L1_RDD.union(L2_RDD)
# RDD.intersection(other)
L1_RDD.intersection(L2_RDD)
# RDD.subtract()
L1_RDD.subtract(L2_RDD)

# RDD.cartesian()
R1 = ["User(1)", "User(2)", "User(3)"]
R2 = ["Venue('Betabrand')", "Venue('Asha Tea House')", "Venue('Ritual')"]
R1_RDD = sc.parallelize(R1)
R2_RDD = sc.parallelize(R2)
R1_RDD.cartesian(R2_RDD).collect()

# RDD.sample()
L =  [1, 2, 3, 4, 5, 6]
L_RDD = sc.parallelize(L)
L_RDD.sample(withReplacement = False, fraction = 0.5, seed = 1234).collect()


# ================================================
# RDD 缓存、持久化
# ================================================
# pyspark.StorageLevel中的持久化级别:
from pyspark import StorageLevel

L = [1, 2, 3]
pythonLines = sc.parallelize(L)

pythonLines.persist()
pythonLines.persist(StorageLevel.MEMORY_ONLY[_2])
pythonLines.persist(StorageLevel.MEMORY_ONLY_SER[_2])
pythonLines.persist(StorageLevel.MEMORY_AND_DISK[_2])
pythonLines.persist(StorageLevel.MEMORY_AND_DISK_SER[_2])
pythonLines.persist(StorageLevel.DISK_ONLY[_2])

pythonLines.unpersist()

# ================================================
# 行动操作action
# ================================================
# 使用行动操作来触发一次并行运算,Spark会对计算进行优化后再执行.
# RDD.reduce()
L = sc.parallelize([1, 2, 3, 4, 5])
L_sum = L.reduce(lambda x, y: x + y)


# RDD.fold(zero)(func)


# sc.aggregate(zeroValue)(setOp, combOp)
nums = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
nums_RDD = sc.parallelize(nums)
sumCount = nums_RDD.aggregate((0, 0))(
			lambda acc, value: (acc[0] + value, acc[1] + 1),
			lambda acc1, acc2: (acc1[0] + acc2[0], acc1[1] + acc2[1] + 1)
)
print(sumCount[0] / float(sumCount[1]))


# RDD.count()
inputRDD = sc.textFile("/home/wangzhefeng/project/spark/log.txt")
inputRDD.persist()
errorRDD = inputRDD.filter(lambda x: "error" in x)
warningRDD = inputRDD.filter(lambda x: "warning" in x)
badLinesRDD = errorRDD.union(warningRDD)
badLinesRDD.persist()
print("Input had " + str(badLinesRDD.count()) + " concerning lines.")
print("Here are 10 examples:")
for line in badLinesRDD.take(10):
	print(line)
badLinesRDD.collect()

# RDD.collect()

# RDD.take(n)

# RDD.top(n)
L_top = [1, 2, 3, 4, 5, 6, 7]
L_top_RDD = sc.parallelize(L_top)
L_top_RDD.top(5)

# RDD.takeSample(withReplacement, num, seed)

# RDD.foreach(func)

# RDD.countByValue()
L_count = [1, 2, 3, 4, 5, 6, 3, 4, 5, 6, 3, 4, 5, 6]
L_count_RDD = sc.parallelize(L_count)
L_count_RDD.countByValue()

# RDD.takeOrdered(num)(ordering)


# ================================================
# 向Spark传递函数
# ================================================
inputRDD = sc.textFile("/home/wangzhefeng/project/spark/log.txt")
inputRDD.persist()
errorRDD = inputRDD.filter(lambda x: "error" in x)
warningRDD = inputRDD.filter(lambda x: "warning" in x)
badLinesRDD = errorRDD.union(warningRDD)
badLinesRDD.persist()

# lambda
word1 = badLinesRDD.filter(lambda s: "error" in s)
word1.collect()

# function
def containError(s):
	return "error" in s
word2 = badLinesRDD.filter(containError)
word2.collect()
# ================================================
# 在不同的RDD类型间转换
# ================================================
# Python的API结构与java和Scala有所不同,在Python中,所有函数都实现在基本的RDD类中
# 但如果操作对应的RDD数据类型不正确, 就会导致运行时错误



# ================================================
# pair RDD
# ================================================
# create pairRDD
lines = sc.textFile("/home/wangzhefeng/project/spark/log.txt")
pairs = lines.map(lambda x: (x.split(" ")[0], x)) # pairs = lines.map(x => (x.split(" ")[0], x))
pairs.collect()

L1 = [('a', 1), ('b', 2), ('c', 3), ('a', 2), ('b', 3), ('c', 4)]
pairs_L1 = sc.parallelize(L1)

# ----------------------------------
# pairRDD.reduceByKey(fun) -- RDD.reduce()
L = {(1, 2), (1, 3), (3, 4), (3, 6)}
L_RDD = sc.parallelize(L)
L_RDD.reduceByKey(lambda x, y: x + y).collect()

# pairRDD.foldByKey() -- RDD.fold()
data = [("panda", 0), ("pink", 3), ("pitate", 3), ("panda", 1), ("pink", 4)]
data_RDD = sc.parallelize(data)
data_RDD.mapValues(lambda x: (x, 1)).reduceByKey(lambda x, y: (x[0] + y[0], x[1] + y[1]))


# pairRDD.groupByKey()
L_RDD.groupByKey().collect()

# pairRDD1.cogroup(pairRDD2)
L_RDD.cogroup(K_RDD)


# pairRDD.combineByKey(createConbiner, mergeValue, MergeConbiners, partitioner)

# pairRDD.mapValues(func)
L_RDD.mapValues(lambda x: x ** 2).collect()
L_RDD.mapValues(lambda x: x + 1).collect()


# pairRDD.flatMapValues(func)
L_RDD.flatMapValues(lambda x: )

# pairRDD.keys()
L_RDD.keys()

# pairRDD.values()
L_RDD.values()

# pairRDD.sortByKey()
L_RDD.sortByKey()

# ------------------------------------
L = {(1, 2), (1, 3), (3, 4), (3, 6)}
K = {(3, 9)}
L_RDD = sc.parallelize(L)
K_RDD = sc.parallelize(K)

# pairRDD1.subtractByKey(pairRDD2)
L_RDD.subtractByKey(K_RDD)

# pairRDD1.join(pairRDD2)
# pairRDD1.rightOuterJoin(pairRDD2)
# pairRDD1.leftOuterJoin(pairRDD2)
L_RDD.join(K_RDD)
L_RDD.rightOuterJoin(K_RDD)


# ----------------------------------
# pairRDD.filter()
lines = sc.textFile("/home/wangzhefeng/project/spark/log.txt")
pairs = lines.map(lambda x: (x.split(" ")[0], x)) # pairs = lines.map(x => (x.split(" ")[0], x))
result = pairs.filter(lambda keyValue: len(keyValue[1]) < 18)
result.collect()



# pairRDD.groupByKey()

# pairRDD.cogroup()





# ================================================
# 关闭Spark
# ================================================
sc.stop()
# System.exit(0)
# sys.exit()