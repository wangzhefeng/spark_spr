#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from pyspark import SparkConf, SparkContext
import pandas as pd
import numpy as np

# 在Python中初始化Spark
conf = SparkConf().setMaster("local").setAppName("My First Spark App")
sc = SparkContext(conf = conf)








