#!/usr/bin/env python
# -*- coding: utf-8 -*-

from pyspark import SparkConf, SparkContext


# init SparkContext
conf = SparkConf() \
	.setMaster("local") \
	.setAppName("Read Files")
sc = SparkContext(conf = conf)


# ========================================================================
# 文本文件
# ========================================================================
# read 文本文件
input_test_file = sc.textFile("/home/wangzhefeng/project/data/spark_data/test.txt")
input_sale_file = sc.textFile("/home/wangzhefeng/project/data/spark_data/saleFiles")
input_sales_file = sc.textFile("/home/wangzhefeng/project/data/spark_data/saleFiles/sales-*.txt")
input_files = sc.wholeTextFiles("/home/wangzhefeng/project/data/spark_data/saleFiles")


# save 文本文件
L = ["zhangxin1", "zhangxin2", "zhangxin3", "zhangxin4", "zhangxin5"]
J = {("test1", "wangzhefeng1"),
     ("test2", "wangzhefeng2"),
     ("test3", "wangzhefeng3"),
     ("test3", "wangzhefeng3"),
     ("test3", "wangzhefeng3")}
L_rdd = sc.parallelize(L)
J_rdd = sc.parallelize(J)
L_rdd.saveAsTextFile("/home/wangzhefeng/project/data/spark_data/zhangxin")
J_rdd.saveAsTextFile("/home/wangzhefeng/project/data/spark_data/wangzhefeng")

# ========================================================================
# JSON文件
# ========================================================================
import json
input = sc.textFile("/home/wangzhefeng/project/data/spark_data/test.json")
json_data = input.map(lambda x: json.loads(x))
json_data \
	.filter(lambda x: x["wangzf"]) \
	.map(lambda x: json.dumps(x)) \
	.saveAsTextFile("/home/wangzhefeng/project/data/spark_data/json")


# ========================================================================
# CSV and TSV 文件
# ========================================================================
import pandas as pd
from io import StringIO
import csv

def loadRecord(line):
	input = StringIO(line)
	reader = csv.DictReader(input, fieldnames = ["total_bill","tip","sex","smoker","day","time","size"])
	return next(reader)
input = sc.textFile("/home/wangzhefeng/project/data/spark_data/tips.csv").map(loadRecord)



def loadRecords(fileNameContents):
	input = StringIO(fileNameContents[1])
	reader = csv.DictReader(input, fieldnames = ["total_bill","tip","sex","smoker","day","time","size"])
	return reader
fullFileData = sc.wholeTextFiles("/home/wangzhefeng/project/data/spark_data/tips").flatMap(loadRecord)


def writerRecords(records):
	output = StringIO()
	writer = csv.DictWriter(output, fieldnames = ["total_bill","tip","sex","smoker","day","time","size"])
	for record in records:
		writer.writerow(record)
	return [output.getvalue()]

pandasLovers.mapPartitions(writerRecords).saveAsTextFile("/home/wangzhefeng/project/data/spark_data/tips")
