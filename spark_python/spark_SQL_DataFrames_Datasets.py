#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Spark SQL, DataFrames, Datasets Guide

# SQL
	# SQL
	# Hive Table
	# other programming language
# Datasets
	# distributed collection of data
	# Scala, Java
# DataFrame
	# Datasets organized into named columns
	# Table
	# Data Frame in R
	# DataFrame in python




from pyspark.sql import SparkSession
from pyspark.sql import DataFrame
from pyspark.sql import Column
from pyspark.sql import Row
from pyspark.sql import GroupedData
from pyspark.sql import DataFrameNaFunctions
from pyspark.sql import DataFrameStatFunctions
from pyspark.sql import types
from pyspark.sql.types import *
from pyspark.sql import functions
from pyspark.sql import streaming
from pyspark.sql import Window


# *********************************************************
# SparkSession
# *********************************************************
spark = SparkSession \
	.builder \
	.master("local[4]")
	.appName("Python Spark SQL") \
	.config("spark.some.config.option", "some-value") \
	.getOrCreate()


# *********************************************************
# DataFrames
# *********************************************************
people = spark.read.json("D:/spark-2.3.2-bin-hadoop2.7/examples/src/main/resources/people.json")
department = spark.read.parquet("D:/spark-2.3.2-bin-hadoop2.7/examples/src/main/resources/users.parquet")
people.show()
department.show()

people.printSchema()
people.select("name").show()
people.select(people['name'], (people['age'] + 1).alias("age_new")).show()
people.filter(people['age'] > 21).show()
people.groupBy('age').count().show()


# *********************************************************
# SQL Queries
# *********************************************************
# Register the DataFrame as a SQL temporary view
people.createOrReplaceTempView("people")
sqlDF = spark.sql("SELECT * FROM people")
sqlDF.show()

# Global temporary view
people.createOrReplaceGlobalTempView("people_global")
sqlDF_2 = spark.sql("SELECT * FROM global_temp.people_global")
sqlDF_2.show()

# cross-session
spark.newSession().sql("SELECT * FROM global_temp.people_global").show()


# *********************************************************
# Datasets
# *********************************************************
""" {scala}
case class Person(name: String, age:Long)
val caseClassDS = Seq(Person("Andy", 32)).toDS()
caseClassDS.show()

val primitiveDS = Seq(1, 2, 3).toDS()
primitiveDS.map(_ + 1).collect()

val path = "D:/spark-2.3.2-bin-hadoop2.7/examples/src/main/resources/people.json"
val peopleDS = spark.read.json(path).as[Person]
peopleDS.show()
"""

# *********************************************************
# Spark SQL and RDD
# *********************************************************
# Inferring the schema using reflection
sc = spark.sparkContext

# Load a text file and convert each line to a Row
lines = sc.textFile("D:/spark-2.3.2-bin-hadoop2.7/examples/src/main/resources/people.txt")
parts = lines.map(lambda l: l.split(","))
people = parts.map(lambda p: Row(name = p[0], age = int(p[1])))

# Infer the schema, and register the DataFrame as a table
schemaPeople = spark.createDataFrame(people)
schemaPeople.createOrReplaceGlobalTempView("people")

# SQL can be run over DataFrame that have been registered as table
teenagers = spark.sql("SELECT name FROM people WHERE age >= 13 AND age <= 19")

# The results of SQL queries are DataFrame object, rdd returns the contents as an :class: pyspark.RDD of :class:Row
teenNames = teenagers.rdd.map(lambda p: "Name: " + p.name).collect()
for name in teenNames:
	print(name)

# ------------------------------------------

# Programmatically specifying the schema
sc = spark.sparkContext

# RDD
parts = lines.map(lambda l: l.split(","))
people = parts.map(lambda p: (p[0], p[1].strip()))

# Apply the schema to RDD
schemaString = "name age"
fields = [StructField(field_name, StringType(), True) for field_name in schemaString.split()]
schema = StructType(fields)
schemaPeople = spark.createDataFrame(people, schema)

# Create a temporary view using the DataFrame
schemaPeople.createOrReplaceTempView("people")
results = spark.sql("SELECT name FROM people")
results.show()

# *********************************************************
# Aggregations
# *********************************************************



# *********************************************************
# Data Sources
# *********************************************************





# *********************************************************
# Data Sources
# *********************************************************



# *********************************************************
# Data types
# *********************************************************
from pyspark.sql.types import NullType
from pyspark.sql.types import StringType
from pyspark.sql.types import BinaryType
from pyspark.sql.types import BooleanType
from pyspark.sql.types import DateType
from pyspark.sql.types import TimestampType
from pyspark.sql.types import DecimalType
from pyspark.sql.types import DoubleType
from pyspark.sql.types import FloatType
from pyspark.sql.types import ByteType
from pyspark.sql.types import IntegerType
from pyspark.sql.types import LongType
from pyspark.sql.types import ShortType
from pyspark.sql.types import ArrayType
from pyspark.sql.types import MapType
from pyspark.sql.types import StructField
from pyspark.sql.types import StructType

