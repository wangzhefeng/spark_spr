#!/usr/bin/env python
# -*- coding: utf-8 -*-

__author__ = "wangzhefeng"


from pyspark import SparkContext as sc
import random
import numpy as np

# **************************************************************************
# version 1
# **************************************************************************
# ------------------------------------------
# 加载数据集
# ------------------------------------------
### iterate number
total = int(100 * 10000)
local_collection = np.arange(1, total)

### parallelize a data set into the cluster
rdd = sc.parallelize(local_collection) \
		.setName("parallelized_data") \
		.cache()

# ------------------------------------------
# 处理数据
# ------------------------------------------
### ramdom generate points
def map_func(element):
	x = random.random()
	y = random.random()

	return (x, y)

def map_func_2(element):
	x, y = element
	return 1 if x ** 2 + y ** 2 < 1 else 0

rdd2 = rdd.map(map_func) \
	.setName("random_point") \
	.cache()

### calculate the number of points in and out the circle
rdd3 = rdd2.map(map_func_2) \
	.setName("points_in_out_circle") \
	.cache()

# ------------------------------------------
# 结果展示
# ------------------------------------------
### how many points are in the circle
in_circle = rdd3.reduce(operator.add)
pi = 4.0 * in_circle / total

print("iterate {} times".format(total))
print("estimated pi: {}".format(pi))



# **************************************************************************
# version 2
# **************************************************************************
total = int(100 * 10000)
sc.parallelize(range(total)) \
	.map(lambda x: (random.random(), random.random())) \
	.map(lambda x: 1 if x[0] ** 2 + x[1] ** 2 < 1 else 0) \
	.reduce(lambda x, y: x + y) \
	/ float(total) * 4




# **************************************************************************
# version 3
# **************************************************************************
total = int(100 * 10000)
sc.parallelize(range(total)) \
	.map(lambda x: 1 if sum(np.random.random(2) ** 2) else 0) \
	.reduct(lambda x, y: x + y) \
	/ float(total) * 4
