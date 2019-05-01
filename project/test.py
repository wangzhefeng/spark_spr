#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
from pyspark import SparkConf, SparkContext
from pyspark import SparkFiles
from pyspark.sql import SparkSession



s1 = SparkSession.builder.config('k1', 'v1').getOrCreate()
s2 = SparkSession.builder.config('k2', 'v2').getOrCreate()
print(s1.conf.get('k1') == s2.conf.get('k1'))
print(s1.conf.get('k1') == s2.conf.get('k1'))

print(s1.catalog)

