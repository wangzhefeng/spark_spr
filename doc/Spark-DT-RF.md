

[TOC]

# Spark — 决策树、随机森林模型


## 概念

* 回归
* 特征向量
* 训练样本

## 模型

### 决策树

> * 能自然地处理类别型和数值型特征数据
> * 容易并行化
> * 对数据中的离群点(outlier)具有鲁棒性(robust)
> * 可以接受不同类型和量纲的数据，对数据类型和尺度不相同的情况不需要做数据预处理或规范化
> * 理解和推理简单
> * 容易过拟合

### 随机森林

> * 解决决策树的过拟合问题
> * 提高分类性能


## Spark 模型

* DecisionTree
* RandomForest


## Covtype数据集

数据集下载：

[covtype.data](https://archive.ics.uci.edu/ml/machine-learning-databases/covtype/)

数据集描述：

* 该数据集记录了美国科罗拉多州不同地块的森林植被类型；
* 每个样本包含了描述每块土地的若干特征，包括海拔，坡度，到水源的距离，遮阳情况，土壤类型；
* 每个样本还给出了地块的已知森林植被类型；

数据准备：

* 数据清洗；
* 解压covtype.data文件并复制到HDFS
* Spark MLlib将特征向量抽象为LabeledPoint，它由一个包含多个特征值得Spark MLlib Vector 和一个 label(标号)的目标值组成；


## 构建决策树模型

* 读取集群中的数据、将数据整理为Spark MLlib需要的LabeledPoint格式

```scala
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.regression._

conf = SparkConf.builder.setMaster().setAppName().getOrCreate()
sc = SparkContext(conf = conf)

var rawData = sc.textFile("D:/DataScience/spark_data/data/covtype/covtype.data")

val data = rawData.map {line =>
	val values = line.split(",").map(_.toDouble)
	val featureVector = Vectors.dense(values.init)
	val label = values.last - 1
	LabeledPoint(label, featureVector)
}
```

* 数据集分割为训练集($80\%$)，验证集($10\%$)，测试集($10\%$)

```scala
val Array(trainData, cvData, testData) = 
	data.randomSplit(Array(0.8, 0.1, 0.1))\
trainData.cache()
cvData.cache()
testData.cache()
```

* 利用训练集构建DecisionTreeModel模型

```scala
import org.apache.spark.mllib.evaluation._
import org.apache.spark.mllib.tree._
import org.apache.spark.mllib.tree.model._
import org.apache.spark.rdd._

def getMetrics(model: DecisionTreeModel, data: RDD[LabeledPoint]): MulticlassMetrics ={
	val predictionsAndLabels = data.map(example => (model.predict(example.features), example.label))
	new MulticalssMetrics(predictionsAndLabels)
}

val model = DecisionTree.trainClassifier(trainData, 	  // 训练数据
										 7, 			  // 目标变量的取值个数
										 Map[Int, Int](), // 保存类别型特征的信息
										 "gini",          // 
										 4, 			  // 树的最大深度
										 100)			  // 最大桶数
```

* 利用验证集测试模型的效果

```scala
val metrics = getMetrics(model, cvData)

// 混淆矩阵
metrics.confusionMatrix

// 验证集上的分类准确度(accuracy),精确度(precision)
metrics.precison


```




## 构建随机森林模型

```scala
val forest = RandomForest.trainClassifier(trainData, 
										  7, 
										  Map(10 -> 4, 11 -> 40), 
										  20, 
										  "auto", 
										  "entropy", 
										  30, 
										  300)
```

## 预测


```scala
val input = "2709,125,28,67,23,3224,253,207,61,6094,0,29"
val vector = Vectors.dense(input.split(",").map(_.toDouble))
forest.predict(vector)
```
