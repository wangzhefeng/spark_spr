#!/usr/bin/env python
# -*- coding: utf-8 -*-

__author__ = "wangzhefeng"

# -----------------------------------------------------------------
# Basic Statistics
# -----------------------------------------------------------------
from pyspark.sql import SparkSession
spark = SparkSession \
	.builder \
	.appName("Spark MLlib") \
	.config("spark.some.config.option", "some-value") \
	.getOrCreate()

# Correlation

from pyspark.ml.linalg import Vectors
from pyspark.ml.stat import Correlation

data = [
	(Vectors.sparse(4, [(0, 1.0), (3, -2.0)]),),
	(Vectors.dense([4.0, 5.0, 0.0, 3.0]),),
	(Vectors.dense([6.0, 7.0, 0.0, 8.0]),),
	(Vectors.sparse(4, [(0, 9.0), (3, 1.0)]),)
]

df = spark.createDataFrame(data, ["features"])
df.show()

r1 = Correlation.corr(df, "features").head()
print(r1)
print("Pearson correlation matrix:\n" + str(r1[0]))

r2 = Correlation.corr(df, "features", "spearman").head()
print(r2)
print("Spearman correlation matrix:\n" + str(r2[0]))


# Hypothesis testing

from pyspark.ml.linalg import Vectors
from pyspark.ml.stat import ChiSquareTest

data = [
	(0.0, Vectors.dense(0.5, 10.0)),
	(0.0, Vectors.dense(1.5, 20.0)),
	(1.0, Vectors.dense(1.5, 30.0)),
	(1.0, Vectors.dense(3.5, 30.0)),
	(1.0, Vectors.dense(3.5, 40.0)),
	(1.0, Vectors.dense(3.5, 40.0))
]
df = spark.createDataFrame(data, ["label", "features"])
df.show()

r = ChiSquareTest.test(df, "features", "label").head()
print("pValue:"+ str(r.pValues))
print("degreesOfFreedom:" + str(r.degreesOfFreedom))
print("Statistics:" + str(r.statistics))


# -----------------------------------------------------------------
# ML Pipelines
# -----------------------------------------------------------------
# Main concepts in Pipelines
	# DataFrame
	# Pipeline components
		# Transformer
		# Estimator
		# Pipeline
		# Parameter

# --------------------------- example 1 ----------------------------------
from pyspark.ml.linalg import Vectors
from pyspark.ml.classification import LogisticRegression

# Prepare training data from a list of (label, features) tuples.
training = spark.createDataFrame([(1.0, Vectors.dense([0.0, 1.1, 0.1])),
								  (0.0, Vectors.dense([2.0, 1.0, -1.0])),
								  (0.0, Vectors.dense([2.0, 1.3, 1.0])),
								  (1.0, Vectors.dense([0.0, 1.2, -0.5]))],
								 ["label", "features"])
# Testing dataset
test = spark.createDataFrame([(1.0, Vectors.dense([-1.0, 1.5, 1.3])),
							  (0.0, Vectors.dense([3.0, 2.0, -0.1])),
							  (1.0, Vectors.dense([0.0, 2.2, -1.5]))],
							 ["label", "features"])

# create a LogisticRegression instance —— Estimator
lr = LogisticRegression(
	maxIter = 10,
	regParam = 0.01
)
print("LogsiticRegression parameters:\n" + lr.explainParams() + "\n")

# Learn a LogisticRegression model —— a transformer produced by an Estimator
model1 = lr.fit(training)
print("Model 1 was fit using parameters: ")
print(model1.extractParamMap())

# Specify parameters using a Python dictionary as a paramMap
paramMap = {lr.maxIter: 20}
# or
paramMap[lr.maxIter] = 30
# or
paramMap.update({
	lr.regParam: 0.1,
	lr.threshold: 0.55
})
# or
paramMap2 = {
	lr.probabilityCol: "myProbability"
}
paramMapCombined = paramMap.copy()
paramMapCombined.update(paramMap2)

# learn a new model using the paramMapConbined parameters
model2 = lr.fit(training, paramMapCombined)
print("Model 2 was fit using parameters: ")
print(model2.extractParamMap())

# Make predictions on test data using the Transformer.transform()
prediction = model2.transform(test)

# results
result = prediction \
	.select("features", "label", "myProbability", "prediction") \
	.collect()

for row in result:
	print("features = %s, lable = %s -> prob=%s, prediction = %s" %
		  (row.features, row.label, row.myProbability, row.prediction))



# --------------------------- example 2 ----------------------------------
from pyspark.ml import Pipeline
from pyspark.ml.classification import LogisticRegression
from pyspark.ml.feature import HashingTF, Tokenizer

# Prepare training documents from a list of (id, text, label) tuples.
training = spark.createDataFrame([(0, "a b c d e spark", 1.0),
								  (1, "b d", 0.0),
								  (2, "spark f g h", 1.0),
								  (3, "hadoop mapreduce", 0.0)],
								 ["id", "text", "label"])
# Prepare test documents, which are unlabeled (id, text) tuples.
test = spark.createDataFrame([(4, "spark i j k"),
							  (5, "l m n"),
							  (6, "spark hadoop spark"),
							  (7, "apache hadoop")],
							 ["id", "text"])

# Configure an ML pipeline, which consists of three stages: tokenizer, hashingTF, and lr
tokenizer = Tokenizer(inputCol = "text", outputCol = "words")
hashingTF = HashingTF(inputCol = tokenizer.getOutputCol(), outputCol = "features")
lr = LogisticRegression(maxIter = 10, regParam = 0.001)
pipeline = Pipeline(stages = [tokenizer, hashingTF, lr])

# Fit the pipeline to training
model = pipeline.fit(training)

# Make predictions on testing
prediction = model.transform(test)

# Results
result = prediction \
	.select("id", "text", "probability", "prediction") \
	.collect()
for row in result:
	rid, text, prob, prediction = row
	print("(%d, %s) --> prob = %s, prediction = %f" % (rid, text, str(prob), prediction))



