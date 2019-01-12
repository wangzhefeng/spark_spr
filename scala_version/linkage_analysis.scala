

// ---------------------------- Linkage ---------------------------------


/*

mkdir linkage
cd linkage
curl -o donation.zip http://bit.ly/1Aoywaq
unzip donation.zip
unzip 'block_*.zip'
hadoop fs -mkdir linkage
hadoop fs -put block_*.csv linkage
spark-shell --master yarn-client
spark-shell --master local[*] --diver-memory 2g
:help
:history
:h?
:paste
sc

*/

// *******************************************
// 获取RDD形式的数据引用
// *******************************************
val rawblocks = sc.textFile("./data/linkage")
// rawblocks.first
// rawblocks.collect()
// rawblocks.take(10)

// *******************************************
// 把数据从集群上获取到客户端
// 把代码从客户端发送到集群
// *******************************************
def isHeader(line: String): Boolean = {
	line.contains("id_1")
}

val noheader = rawblocks.filter(x => !isHeader(x))

// ------------------------------------------
// 用元组和case class 对数据进行结构化
// ------------------------------------------
def toDouble(s: String) = {
	if ("?".equals(s)) 
		Double.NaN
	else
		s.toDouble
}

case class MatchData(id1: Int, 
	                 id2: Int, 
	                 scores: Array[Double], 
	                 matched: Boolean)

def parse(line: String) = {
	val pieces = line.split(",")
	val id1 = pieces(0).toInt
	val id2 = pieces(1).toInt
	val scores = pieces.slice(2, 11).map(toDouble)
	val matched = pieces(11).toBoolean
	MatchData(id1, id2, scores, matched)
}

val parsed = noheader.map(line => parse(line))


// -----------------------------------------------
// RDD缓存
// -----------------------------------------------
parsed.cache()
parsed.count()
parsed.take(10)


// -----------------------------------------------
// 聚合
// -----------------------------------------------
val grouped = parsed.groupBy(md => md.matched)
grouped.mapValues(x => x.size).foreach(println)


// -----------------------------------------------
// 创建直方图 
// -----------------------------------------------
val matchCounts = parsed.map(md => md.matched).countByValue()

val matchCountSeq = matchCounts.toSeq
matchCountSeq.sortBy(_._1).foreach(println)
matchCountSeq.sortBy(_._1).reverse.foreach(println)
matchCountSeq.sortBy(_._2).foreach(println)
matchCountSeq.sortBy(_._2).reverse.foreach(println)


// -----------------------------------------------
// 连续变量的概要统计
// -----------------------------------------------
import java.lang.Double.isNaN
parsed.map(md => md.scores(0)).filter(!isNaN(_)).stats()

val stats = (1 until 9).map(i => {
	parsed.map(md => md.score(i)).filter(!isNaN(_)).stats()
})
