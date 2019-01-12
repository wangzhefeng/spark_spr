// !/usr/bin/env scala


// ================================================================
// Interactive analysis with spark shell
// ================================================================
// play the spark shell
// $spark-shell

// make as Dataset from the text of the README file in the spark source directory
val textFile = spark.read.textFile("D:/spark/README.md")
textFile.count() // get values from data set by calling actions
textFile.first() // get values from data set by calling transform

val lineWithSpark = textFile.filter(line => line.contains("Spark"))

textFile.filter(line => line.contains("Spark")).count()


// ================================================================
// More on Dataset Operations
// ================================================================
textFile
	.map(line => line.split(" ").size)
	.reduce((a, b) => if(a > b) a else b)


import java.lang.Math
textFile
	.map(line => line.split(" ").size)
	.reduct((a, b) => Math.max(a, b))

val wordCounts = textFile
	.flatMap(line => line.split(" "))
	.groupByKey(identity)
	.count()
wordCounts.collect()


// ================================================================
// Caching
// ================================================================
lineWithSpark.cache()
lineWithSpark.count()
lineWithSpark.count()


// ================================================================
// Self-Contained Applications
// ================================================================
// SimpleApp.scala
/* build.sbt
name := "HelloWold"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.2"
*/

// Package a jar containing your applications
sbt package

// Run applications
spark-submit \
	--class "SimpleApp" \
	-- master local[4] \
	target/scala-2.12/simple-project_2.12-7.0.jar



// ================================================================
// RDD programming
// ================================================================
// spark_RDD.scala
// spark_RDD.py

// ================================================================
// SQL programming
// ================================================================
// spark_SQL_DataFrames_Datasets.scala
// spark_SQL_DataFrames_Datasets.py

// ================================================================
// Deployment
// ================================================================
// Deployment.scala
