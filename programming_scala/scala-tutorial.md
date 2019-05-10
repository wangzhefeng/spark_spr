
[TOC]

# Scala 入门

## 1.使用Scala解释器、sbt包管理器、Maven包管理器

##### 启动Scala Shell

```shell
$ scala
```

##### 退出解释器

```shell
$ :quit
```

```shell
$ :q
```


## 2.定义Scala变量

> * Scala变量分为两种：
	- `val`：一旦初始化就不能被重新赋值；
	- `var`：在整个生命周期内可以被重新赋值；

```scala
val msg = "Hello world!"

val msg2: java.lang.String = "Hello again, world!"

val msg3: String = "Hello yet again, world!"
```


## 3.定义Scala函数

> * 函数定义由`def`开始，然后是函数名和圆括号中的以逗号隔开的参数列表；
      - 每个参数的后面都必须加上冒号(`:`)开始的类型标注。因为Scala编译器并不会推断函数参数的类型；
  * 可以在参数列表的后面加上以冒号开始的函数的结果类型(reslut type)。有时，Scala编译器需要给出函数的结果类型，比如：如果函数是**递归的(recursive)**，就必须显式地给出函数的结果类型；
  * 在函数的结果类型之后，是一个等号和用花括号括起来的函数体。
      - 函数体之前的等号有特别的含义，表示在函数式的世界观里，函数定义的是一个可以获取到结果值的表达式；
      - 如果函数只有一条语句，也可以不使用花括号；
      - 函数返回类型为`()Unit`表示该函数并不返回任何有实际意义的结果，Scala的Unit类型和Java的void类型类似，每一个Java中返回void的方法都能被映射称Scala中返回Unit的方法。因此，结果类型为Unit的方法之所以被执行，完全是为了它们的副作用；


一般形式：

```scala
def max(x: Int, y: Int): Int = {
	if (x > y) 
		x
	else 
		y
}
```

最复杂形式：

```scala
def max(x: Int, y: Int): Int = {
	if (x > y) {
		x
	}
	else {
		y
	}
}

```

最简形式：

```scala
def max(x: Int, y: Int) = if (x > y) x else y
```

## 4.编写Scala脚本

> * 脚本不过是一组依次执行的语句；
> * 命令行参数可以通过名为`args`的Scala数组(Array)获取；


**编写脚本1,执行脚本1：**

```scala
// hello.scala

println("Hello world, from a script!")
```

```shell
$ scala hello.scala
```

**编写脚本2,执行脚本2：**

```scala
// helloarg.scala

/* 对第一个命令行参数说hello */
println("Hello, " + args(0) + "!")
```

```shell
$ scala helloarg.scala plant
```

## 5.用while做循环；用if做判断

**打印命令行参数(带换行符)：**

```scala
// printargs.scala

var i = 0
while (i < args.length) {
	println(args(i))
	i += 1
}
```

```shell
$ scala printargs.scala Scala is fun
```

**打印命令行参数(不带换行符)：**

```scala
// echoargs.scala

var i = 0
while (i < args.length) {
	if (i != 0)
		print(" ")
	print(args(i))
	i += 1
}
println()
```

```shell
$ scala echoargs.scala Scala is more even fun
```

## 6. 用foreach和for遍历

> * 指令式编程风格(imperative)
>	    - 依次给出指令，通过循环来遍历，经常变更别不同函数共享的状态；
>	* 函数式编程风格(functional)
>	    - 函数式编程语言的主要特征之一就是函数是一等的语法单元；

#### foreach

```scala
// foreachargs.scala

args.foreach(arg => println(arg))
args.foreach((arg: String) => println(arg))
args.foreach(println)
```

```shell
$ scala foreachargs.scala Concise is nice
```

#### for

> Scala只支持指令式for语句的`for表达式`

```
// forargs.scala

for (arg <- args) {
	println(arg)
}
```

```shell
$ scala forargs.scala for arg in args
```

* *注意：*在for表达式中，符号`<-`左边的arg是一个`val`变量的名字，尽管arg看上去像是`var`，因为每一次迭代都会拿到新的值，但他确实是个`val`，虽然arg不能在for表达式的循环体内被重新赋值，但对于`<-`右边的args数组中的每一个元素，一个新的名为arg的`val`会被创建出来，初始化成元素的值，这时for表达式的循环体才被执行；


## 7.[Array] 用类型参数化数组

> * 用`new`来实例化`对象(object)`或`类(class)`的`实例(instance)`；
	- 用值来参数化一个实例，做法是在构造方法的括号中传入对象参数；
	- 用类型来参数化一个实例，做法是在方括号里给出一个或多个类型；
		- 当同时用类型和值来参数化一个实例时，先是方括号包起来的类型(参数)，然后才是用圆括号包起来的值(参数)；

##### 用值来参数化一个实例：

```scala
val big = new java.math.BigInteger("12345")
``` 

##### 用类型来参数化一个实例：

```scala
val greetStrings = new Array[String](3)
greetStrings(0) = "Hello"
greetStrings(1) = ", "
greetStrings(2) = "world!\n"
for (i <- 0 to 2) {
	print(greetStrings(i))
}
```

```scala
val greetStrings: Array[String] = new Array[String](3)
```


* *注意：*当用`val`定义一个变量时，变量本身不能被重新赋值，但它指向的那个对象是有可能发生变化的。可以改变`Array[String]`的元素，因此`Array`本身是可变的；


## 8.[List] 使用列表

> * Scala数组`Array`是一个拥有相同类型的对象的`可变序列`。虽然无法在数组实例化后改变其长度，却可以改变它的元素值，因此数组是可变的对象；
> * 对于需要拥有相同类型的对象的不可变序列的场景，可以使用Scala的List类。Scala的List（即scala.List）跟Java的java.util.List的不同在于Scala的List不可变的，而Java的List是可变的。更笼统的说，Scala的List被设计为`允许函数是风格的编程`。


##### 创建并初始化列表

```scala
val oneTwoThree = List(1, 2, 3)
```

##### 列表拼接方法 `:::`

```scala
val oneTwo = List(1, 2)
val threeFour = List(3, 4)
val oneTwoThreeFour = oneTwo ::: threeFour
println(oneTwo + " and " + threeFour + " were not mutated.")
println("Thus, " + oneTwoThreeFour + " is a new list.")
```

##### 列表增加方法 `::`

```scala
val twoThree = List(2, 3)
val oneTwoThree = 1 :: twoThree
println(oneTwoThree)
```

##### 空列表快捷方式 `Nil`

> 初始化一个新的列表的另一种方式是用`::`将元素串接起来，并将`Nil`作为最后一个元素；

```scala
val oneTwoThree = 1 :: 2 :: 3 :: Nil
println(oneTwoThree)
```


##### 列表的一些方法和用途

| List方法                                        | 方法用途                |
|:------------------------------------------------|:-----------------------|
| `List()`, `Nil`                                 | 空List				   
| `List("Cool", "tools", "rules")`                | 创建一个新的List[String]
| `val thril = "Will" :: "fill" :: "until" :: Nil`| 创建一个新的List[String]
| `List("a", "b") ::: List("c", "d")`             | 将两个List拼接起来
| `thril.mkString(", ")`                          | 返回一个用List的所有元素组合成的字符串	   
| `thril(2)`                                      | 返回List中下标为2的元素
| `thril.head`                                    | 返回List首个元素
| `thril.init`                                    | 返回List除最后一个元素之外的其他元素组成的List
| `thril.last`                                    | 返回List的最后一个元素
| `thril.tail`									  | 返回List除第一个元素之外的其他元素组成的List
| `thril.length`                                  | 返回List的元素个数
| `thril.isEmpty`                                 | 判断List是否是空List
| `thril.drop(2)`								  | 返回去掉了List的头两个元素的List
| `thril.dropRight(2)`							  | 返回去掉了List的后两个元素的List
| `thril.reverse`								  | 返回包含List的所有元素但顺序反转的List
| `thril.count(s => s.length == 4)`				  | 对List中满足条件表达式的元素计数
| `thril.filter(s => s.length == 4)`              | 按顺序返回List中所有长度为4的元素List
| `thril.filterNot(s => s.length == 4)`           | 按顺序返回List中所有长度不为4的元素List
| `thril.exists(s => s == "until")`               | 判断List中是否有字符串元素为"until"
| `thril.forall(s => s.endsWith("l"))`            | 判断List中是否所有元素都以字母"l"结尾
| `thril.foreach(s => println(s))`                | 对List中的每个字符串进行print
| `thril.foreach(println)`                          | 对List中的每个字符串进行print,精简版
| `thril.map(s => s + "y")`                       | 返回一个对List所有字符串元素末尾添加"y"的新字符串的List
| `thril.sort((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)`|返回包含List的所有元素，按照首字母小写的字母顺序排列的List


## 9.[Tuple] 使用元组

> * 元组是不可变的；
> * 元组可以容纳不同类型的元素；
> * 要实例化一个新的元组，只需要将对象放在圆括号当中，用逗号隔开即可；
> * 实例化好一个元组后，就可以用`._n, n = 1, 2,...`来访问每一个元素；
	- 元组中的每个元素有可能是不同的类型；
	- 目前Scala标准类库仅定义到Tuple22，即包含22个元素的数组；
> * 元组类型：`Tuplel[Type1, Type2, ...]`， 比如： `Tuple2[Int, String]`

```scala
val pair = (99, "Luftballons")
println(pair._1)
println(pair._2)
```


## 10.[Set, Map]使用集和映射

> * Array永远是可变的
	- 必须容纳相同类型的元素；
	- 长度不可变；
	- 元素可变；
> * List永远是不可变的
	- 必须容纳相同类型的元素；
	- 长度不可变；
	- 元素不可变；
> * Tuple永远是不可变的
	- 可以容纳不同类型的元素；
	- 长度不可变；
	- 元素不可变；
> * Scala通过不同的类继承关系来区分Set、Map的可变和不可变；
	- Scala的API包含了一个基础的`特质(trait)`来表示Set、Map，Scala提供了两个`子特质(subtrait)`，一个用于表示可变Set、可变Map，另一个用于表示不可变Set、不可变Map；
> * 要向Set添加新元素，

### Set

##### Scala Set 的类继承关系

* scala.collection **Set** 《trait》
	- scala.collection.immutable **Set** 《trait》
	    - scala.collection.immutable 
	    	- **HashSet**
	- scala.collection.mutable **Set** 《trait》
	    - scala.collection.immutable 
	    	- **HashSet**


##### 创建、初始化一个不可变Set

```scala
var jetSet = Set("Boeing", "Airbus")
jetSet += "Lear"
println(jetSet.contains("Cessna"))
```

```scala
import scala.collection.immutable

var jetSet = immutable.Set("Boeing", "Airbus")
jetSet += "Lear"
println(jetSet.contains("Cessna"))
```

##### 创建、初始化一个可变Set

```scala
import scala.collection.mutable

val movieSet = mutable.Set("Hitch", "Poltergeist")
movieSet += "Shrek"
println(movieSet)
```

##### 创建、初始化一个不可变HashSet

```scala
import scala.collection.immutable.HashSet

val hashSet = HashSet("Tomatoes", "Chilies")
println(hashSet + "Coriander")
```

### Map

##### Scala Map 的类继承关系

* scala.collection **Map** 《trait》
	- scala.collection.immutable **Map** 《trait》
	    - scala.collection.immutable 
	    	- **HashMap**
	- scala.collection.mutable **Map** 《trait》
	    - scala.collection.immutable 
	    	- **HashMap**


##### 创建、初始化一个不可变Map

```scala
val romanNumeral = Map(
	1 -> "I",
	2 -> "II",
	3 -> "III",
	4 -> "IV",
	5 -> "V"
)
println(romanNumeral)
```

```scala
import scala.colection.immutable

val romanNumeral = immutable.Map(
	1 -> "I",
	2 -> "II",
	3 -> "III",
	4 -> "IV",
	5 -> "V"
)
println(romanNumeral)
```

##### 创建、初始化一个可变HashMap

```scala
import scala.collection.mutable

val treasureMap = mutable.Map[Int, String]()
treasureMap += (1 -> "Go to island.")
treasureMap += (2 -> "Find big X on ground.")
treasureMap += (3 -> "Dig.")
println(treasureMap(2))
```

## 11.识别函数式编程风格


> * 代码层面：
	   - 一个显著的标志是：如果代码包含任何var变量，通常是指令式风格的，而如果代码完全没有var（只包含val），那么很可能是函数式风格的。因此，一个向函数式风格转变的方向是尽可能不用var；
> * 每个有用的程序都会有某种形式的副作用。否则，它对外部世界就没有任何价值。倾向于使用无副作用的函数鼓励你设计出将带有副作用的代码最小化的额程序。这样做的好处之一就是让你的程序更容易测试；

##### 指令式示例

```scala
def printArgs(args: Array[String]): Unit = {
	var i = 0
	while (i < args.length) {
		println(args(i))
		i += 1
	}
}
```

##### 函数式示例

“不纯”的函数式：

函数有副作用(向标准输出流输出打印)，带有副作用的函数的标志特征是结果类型是Unit。

```scala
def printArgs(args: Array[String]): Unit = {
	for (arg <- args) {
		println(s)
	}
}
```

```scala
def printArgs(args: Array[String]): Unit = {
	args.foreach(println)
}
```

“纯”函数式：

函数没有副作用，没有var

```scala
def formatArgs(args: Array[String]) = {
	args.mkString("\n")
}
```

## 12.从文件读取文本行

> 日常任务的脚本处理文件中的文本行

```scala
import scala.io.Source

if (args.length > 0) {
	for (line <- Source.fromFile(args(0)).getLines()) {
		println(line.length + " " + line)
	}
}
else {
	Console.err.println("Please enter filename")
}
```

```shell
$ scala countchars1.scala countchars1.scala
```


```scala
import scala.io.Source

def widthOfLength(s: String) = {
	s.length.toString.length
}

if (args.length > 0) {
	val lines = Source.fromFile(args(0)).getLines().toList
	val longestLine = lines.reduceLeft((a, b) => if (a.length > b.length) a else b)
	val maxWidth = widthOfLength(longestLine)
	for (line <- lines) {
		val numSpace = maxWidth - widthOfLength(line)
		val padding = " " * numSpace
		println(padding + line.length + " | " + line)
	}
}
else {
	Console.err.println("Please enter filename.")
}
```

```shell
$ scala countchars2.scala countchars2.scala
```

# 2. Scala面向对象编程

## 2.1 类、字段、方法

> * 类是对象的蓝本(blueprint)；一旦定义好了一个类，就可以用`new`关键字从这个类蓝本创建对象；
> * 在类定义中，可以填入`字段(field)`和`方法(method)`，这些被统称为`成员(member)`；
	   - 通过`val`或`var`定义的`字段`是指向对象的变量；字段保留了对象的状态，或者说是数据；
	   	   - 字段又叫做`实例变量(instance variable)`，因为每个实例都有自己的变量，这些实例变量合在一起，构成了对象在内存中的映像；
	       - 追求健壮性的一个重要手段是确保对象的状态(实例变量的值)在其整个声明周期都是有效的
	   	       - 首先，通过将字段标记为`私有(private)`来防止外部直接访问字段，因为私有字段只能被定义在同一个类中的方法访问，所有对状态的更新的操作的代码，都在类的内部；
	   	   - 在Scala中，除非显式声明`private`，否则变量都是公共访问的(public)；
	   - 通过`def`定义的`方法`则包含了可执行的代码；方法用字段定义的数据来对对象执行计算；
	       - 传递给方法的任何参数都能在方法内部使用。Scala方法参数的一个重要特征是他们都是val。因此，如果试图在Scala的方法中对参数重新赋值，编译会报错；
	   	   - 在Scala方法定义中，在没有任何显式的return语句时，方法返回的是该方法计算出的最后一个值；仅仅因为其副作用而被执行的方法被称作`过程(procedure)`；
>  - 当`实例化`一个类，运行时会指派一些内存来保存对象的状态图（即它的变量的内容）；
	   
	   

### 2.1.1 创建类

```scala
class ChecksumAccumulator {
	// 类定义
}
```

### 2.1.2 创建对象

```scala
new ChecksumAccumulator
```

### 2.1.3 创建类、定义字段

```scala
class ChecksumAccumulator {
	// 类定义
	var sum = 0
}
```

```scala
val acc = ChecksumAccumulator
val csa = ChecksumAccumulator
```

* acc和csa是同一个类的两个不同的ChecksumAccumulator对象，它们都有一个实例变量`sum`，并且指向相同的内存对象
`0`；
* 由于`sum`是定义在类ChecksumAccumulator中的可变`var`字段，可以对其重新进行赋值`acc.sum = 3`，此时，acc和csa的实例变量指向了不同的内存对象, acc.sum指向了`3`，而csa.sum指向了`0`；
	- acc和csa本身是val对象，不能将他们重新赋值指向别的`对象,object`，但是可以将他们的实例变量指向不同的对象；

### 2.1.4 创建类、定义私有字段、方法

```scala
// ChecksumAccumulator.scala

class ChecksumAccumulator {
	private var sum = 0
	
	def add(b: Byte): Unit = {
		sum += b
	}

	def checksum(): Int = {
		~(sum & 0xFF) + 1
	}
}
```

因为类`ChecksumAccumulator`的字段`sum`现在是`private`，所以对`ChecksumAccumulator`的对象`acc`在类的外部重新赋值是不能编译的：

```scala
val acc = new ChecksumAccumulator

// 下面的定义不能编译 
acc.sum = 5
```

## 2.2 单例对象

> * Scala比Java更面向对象的一点，是Scala的class不允许有`static`成员；对于这种使用场景，Scala提供了`单例对象(singleton object)`；
> * `单例对象(singleton object)`的定义跟类定义很像，只不过`class`关键字换成了`object`关键字；
> * 当单例对象跟某个类共用同一个名字时，它被称为这个类的`伴生对象(companion object)`；同名的类又叫作这个单例对象的`伴生类(companion class)`；必须在同一个源码文件中定义类和类的伴生对象；类和它的伴生对象可以互相访问对方的私有成员；
> * 没有同名的伴生类的单例对象称为`孤立对象(standalone object)`；孤立对象有很多用途，包括将工具方法归集在一起，或定义Scala应用程序的入口等；
> * 定义单例对象并不会定义类型；不过单例对象可以扩展自某个超类，还可以混入特质，可以通过这些类型来调用他的方法，用这些类型的变量来引用它，还可以将它传入那些预期这些类型的入参的方法中；
> * 类和单例对象的一个区别是单例对象不接收参数，而类可以；
	- 每个单例对象都是通过一个静态变量引用合成类(synthetic class)的实例来实现的，因此，单例对象从初始化的语义上跟Java的静态成员是一致的，尤其体现在单例对象有代码首次访问时才被初始化；
		- 合成类的名称为对象加上美元符号: `objectName$`


### 2.2.1 单例对象举例

```scala
// ChecksumAccumulator.scala

import scala.collection.mutable

class ChecksumAccumulator {
	private var sum = 0
	
	def add(b: Byte): Unit = {
		sum += b
	}

	def checksum(): Int = {
		~(sum & 0xFF) + 1
	}
}

object ChecksumAccumulator {
	
	// field cache
	private val cache = mutable.Map.empty[String, Int]

	// method calculate
	def calculate(s: String): Int = {
		if (cache.contains(s)) {
			cache(s)
		}
		else {
			val acc = new ChecksumAccumulator
			for (c <- s) {
				acc.add(c.toByte)
			}
			val cs = acc.checksum()
			cache += (s -> cs)
			cs
		}
	}
}
```

调用单例对象：

```scala
ChecksumAccumulator.calculate("Every value is an object.")
```


### 2.2.2 单例对象创建Scala应用程序入口

> * 要运行一个Scala程序，必须提供一个独立对象的名称，这个独立对象需要包含一个`main`方法，该方法接收一个`Array[String]`作为参数，结果类型为`Unit`；
	- 任何带有满足正确签名的`main`方法的独立对象都能被用作Scala应用程序的入口；
> * Scala在每一个Scala源码文件都隐式地引入了`java.lang`和`scala`包的成员，以及名为`Predef`的单例对象的所有成员；
	- `java.lang`中包含的常用方法：
		- 
	- `scala`中包含的常用方法：
		- 
	- `Predef`中包含了很多有用的方法：
		- `Predef.println()`
		- `Predef.assert`
> * Scala和Java的区别之一是，Scala总可以任意命名`.scala`文件，不论放什么类或代码到这个文件中；
	- 通常对于非脚本的场景，把类放入以类名命名的文件是推荐的做法；
	- 非脚本：以定义结尾；
	- 脚本: 必须以一个可以计算出结果的表达式结尾；


**Scala应用程序：**

```scala
// Summer.scala
import ChecksumAccumulator.calculate

object Summer {
	def main(args: Array[String]) {
		for (arg <- args) {
			println(arg + ":" + calculate(arg))
		}
	}
}
```

**调用Scala应用程序：**

> * 需要用Scala编译器实际编译程序文件，然后运行编译出来的类；
	- `scalac` or `fsc`编译程序文件；
	- `scala`运行编译出的类；


* Scala基础编译器`scalac`:
	- 编译源文件，会有延迟，因为每一次编译器启动，都会花时间扫描jar文件的内容以及执行其他一些初始化的工作，然后才开始关注提交给它的新的源码文件；

```shell
$ scalac ChecksumAccumulator.scala Summer.scala
```

* Scala编译器的守护进程`fsc`:
	- Scala的分发包包含了一个名为`fsc`的Scala编译器的守护进程(daemon)，第一次运行fsc时，它会创建一个本地的服务器守护进程，绑定到计算机的某个端口上；然后它会通过这个端口将需要编译的文件发送给这个守护进程。下次运行fsc的时候，这个守护进程已经在运行了，所以fsc会简单地将文件清单发给这个守护进程，然后守护进程就会立即编译这些文件，使用fsc，只有在首次运行时才需要等待java运行时启动。

```shell 
$ fsc ChecksumAccumulator.scala Summer.scala
```

如果想停止fsc这个守护进程，可以执行

```shell
$ fsc -shutdown
```

* scala运行Java类文件：
	- 不论是运行`scalac`还是`fsc`命令，都会产生出Java类文件，这些类文件可以用`scala`命令来运行；

```shell
$ scala Summer of love
```

**App特质调用Scala应用程序：**

> * Scala提供了一个特质`scala.App`，帮助节省敲键盘的动作；
	- 要是用这个特质，首先要在单例对象名后加上`extends App`，然后，并不是直接编写`main`方法，而是通过将打算放在main方法中的代码直接写在单例对象的花括号中；可以通过名为`args`的字符串数组来访问命令行参数；


```scala
// FallWinterSpringSummer.scala
import ChecksumAccumulator.calculate

object FallWinterSpringSummer extends App {
	for (season <- List("fall", "winter", "spring")) {
		print(season + ": " + calculate(season))
	}
}
```


## 2.3 基础类型、操作

**内容：**

* Scala基础类型
	- String
	- 数值类型：
		- Int
		- Long
		- Short
		- Byte
		- Float
		- Double
		- Char
		- Boolean
* Scala基础类型支持的操作
	- 操作
	- Scala表达式的操作符优先级
* 隐式转换“增强”(enrich)基础类型


**基础类型：** 

* 数值类型
	- 整数类型
		- Byte
			- 8位带符号二进制补码整数
		- Short
			- 16位带符号二进制补码整数
		- Int
			- 32位带符号二进制补码整数
		- Long
			- 64位带符号二进制补码整数
		- Char
			- 16位无符号Unicode字符
	- 浮点数类型
		- Float
			- 32位IEEE754单精度浮点数
		- Double
			- 64位IEEE754单精度浮点数
* String
	Char的序列
* Boolean
	- true
	- false


**字面量：**



## 2.4 函数式对象

**主要内容：**

* 定义函数式对象的类；
* 类参数和构造方法；
* 方法和操作符；
* 私有成员
* 重写；
* 前置条件检查；
* 重载；
* 自引用；

### 2.4.1 背景、类设计

* 设计类对有理数的各项行为进行建模，包括允许它们被加、减、乘、除：
	- 有理数(rational number): 
		- $\frac{n}{d}$
			- $n$: 分子(numerator)
			- $d$: 分母(denominator)
	- 有理数相加、相减：
		- 首先得到一个公分母，然后将分子相加；
	- 有理数相乘：
		- 将另个有理数的分子和分母相乘；
	- 有理数相除：
		- 将右操作元的分子分母对调，然后相乘；
* 数学中有理数没有可变的状态，可以将一个有理数跟另一个有理数相加，但结果是一个新的有理数，原始的有理数并不会“改变”；
	- 每一个有理数都会有一个`Rational`对象表示；


### 2.4.2 构建类

> * 类名后面圆括号中的标识符称作类参数，Scala编译器会采集到类参数，并且创建一个主构造方法，接收同样的参数；
	- 类参数(class parameter)
	- 主构造方法(primary constructor)
> * 在Scala总，类可以直接接收参数，Scala的表示法精简，类定义体内可以直接使用类参数，不需要定义字段并编写将构造方法参数赋值给字段的代码；
> * Scala编译器会将在类定义体中给出的非字段或方法定义的代码编译进类的主构造方法中；

#### 2.4.2.1 重新实现toString方法

* Scala中的类中调用`println()`时，默认继承了java.lang.Object类的`toString`实现；
	- java.lang.Object.toString的主要作用是帮助程序员在调试输出语句，日志消息，测试失败报告，以及解释器和调试输出给出相应的信息；
	- 可以在类定义中重写(override)默认的toString实现； 


```scala
// Rational.scala

class Rational(n: Int, d: Int) {
	override def toString = {
		n + "/" + d
	}
}
```

#### 2.4.2.2 检查前置条件

* 面向对象编程的好处是可以将数据封装在对象里，以确保整个生命周期中的数据都是合法的；
* 对Rational类(class)，要确保对象(object)在构造时数据合法；
	- 分母$b$不能为0；
* 解决方式是对构造方法定义一个前置条件(precondition)，$d$必须为非0；
	- 前置条件是对传入方法或构造方法的值的约束，这是方法调用者必须要满足的，实现这个目的的一种方式是用`require`；
	- `require`方法接收一个boolean的参数。如果传入的参数为true，require将会正常返回；否则，require会抛出`IllegalArgumentException`来阻止对象的构建；

```scala
// Rational.scala

class Rational(n: Int, d: Int) {
	
	// 前置条件检查
	require(d != 0)
	
	// 重写toString方法
	override def toString = {
		n + "/" + d
	}
}
```

#### 2.4.2.3 添加字段

* 支持有理数加法：
	- 定义一个`add`方法：接收另一个Rational对象作为参数，为了保持Rational对象不变，这个add方法不能将传入的有理数加到自己身上，它必须创建并返回一个新的持有这两个有理数的和的Rational对象；
	- 当在`add`方法实现中用到类参数`n`和`d`时，编译器会提供这些类参数对应的值，但它不允许使用`that.n`和`that.d`，因为`that`并非指向执行`add`调用的那个参数对象；要访问`that`的分子和分母，需要将他们做成字段；

```scala
// Rational.scala

class Rational(n: Int, d: Int) {
	
	// 前置条件检查
	require(d != 0)
	
	// 初始化n和d的字段
	val numer: Int = n
	val denom: Int = d

	// 重写toString方法
	override def toString = {
		numer + "/" + denom
	}

	// 有理数加法
	def add(that: Rational): Rational = {
		new Rational(
			numer * that.denom + that.numer * denom, 
			denom * that.denom
		)
	}
}
```

#### 2.4.2.4 自引用

* 关键字`this`指向当前执行方法的调用对象，当被用在构造方法里的时候，指向被构造的对象实例；

```scala
def lessThan(that: Rational) = {
	this.numer * that.denom < that.numer * this denom
}

// or

def lessThan(that: Rational) = {
	numer * that.denom < that.numer * denom
}
```


```scala
def max(that: Rational) = {
	if (this.lessThan(that)) {
		that
	}
	else {
		this
	}
}
```

#### 2.4.2.5 辅助构造方法

* 有时需要给某个类定义多个构造方法，在Scala中，主构造方法之外的构造方法称为**辅助构造方法(auxiliary constructor)**；
	- Scala中的每个辅助构造方法都必须首先调用同一个类的另一个构造方法；
		- Scala的辅助构造方法以`def this(...)`开始；
	- Scala被调用的这个构造方法要么是主构造方法(类的实例)，要么是另一个出现在发起调用的构造方法之前的另一个辅助构造方法；
* 定义一个当分母为1时的Rational类方法，只接受一个参数，即分子，而分母被定义为1；



```scala
// Rational.scala

class Rational(n: Int, d: Int) {
	
	// 前置条件检查
	require(d != 0)
	
	// 初始化n和d的字段
	val numer: Int = n
	val denom: Int = d

	// 辅助构造方法
	def this(n: Int) = {
		this(n, 1)
	}

	// 重写toString方法
	override def toString = {
		numer + "/" + denom
	}

	// 有理数加法
	def add(that: Rational): Rational = {
		new Rational(
			numer * that.denom + that.numer * denom, 
			denom * that.denom
		)
	}
}
```

#### 2.4.2.5 私有字段和方法

* 实现正规化：分子分母分别除以它们的最大公约数；

```scala
// Rational.scala

class Rational(n: Int, d: Int) {
	
	// 前置条件检查
	require(d != 0)
	
	// 分子分母的最大公约数
	private val g = gcd(n.abs, d.abs)

	// 初始化n和d的字段
	val numer = n / g
	val denom = d / g

	// 辅助构造方法
	def this(n: Int) = {
		this(n, 1)
	}

	// 重写toString方法
	override def toString = {
		numer + "/" + denom
	}

	// 有理数加法
	def add(that: Rational): Rational = {
		new Rational(
			numer * that.denom + that.numer * denom, 
			denom * that.denom
		)
	}

	// 求最大公约数方法
	private def gcd(a: Int, b: Int): Int = {
		if (b == 0) {
			a
		}
		else {
			gcd(b, a % b)
		}
	}
}
```

#### 2.4.2.6 定义操作符

```scala
// Rational.scala

class Rational(n: Int, d: Int) {
	
	// 前置条件检查
	require(d != 0)
	
	// 分子分母的最大公约数
	private val g = gcd(n.abs, d.abs)

	// 初始化n和d的字段
	val numer = n / g
	val denom = d / g

	// 辅助构造方法
	def this(n: Int) = {
		this(n, 1)
	}

	// 重写toString方法
	override def toString = {
		numer + "/" + denom
	}

	// 有理数加法
	def + (that: Rational): Rational = {
		new Rational(
			numer * that.denom + that.numer * denom, 
			denom * that.denom
		)
	}

	def * (that: Rational): Rational = {
		new Rational(numer * that.numer, denom * that.denom)
	}

	// 求最大公约数方法
	private def gcd(a: Int, b: Int): Int = {
		if (b == 0) {
			a
		}
		else {
			gcd(b, a % b)
		}
	}
}
```


<<<<<<< HEAD
### 2.5 内建控制结构

> Scala只有为数不多的几个内建控制结构
	- while
	- for
	- try
	- match
	- 函数调用
> Scala所有的控制结构都返回某种值作为结果，这是函数式编程语言采取的策略，程序被认为是用来计算出某个值，因此程序的各个组成部分也应该计算出某个值；


#### 2.5.1 if表达式

**指令式风格：**

```scala
var filename = "default.txt"
if (!args.isEmpty) 
	filename = args(0)
```

**函数式风格：**

* val变量filename一旦初始化就不会改变，省去了扫描该变量整个作用域的代码来搞清楚他会不会变的必要；

```scala
val filename = 
	if (!args.isEmpty) args(0)
	else "default.txt"
```

* 使用val的另一个好处是对等推理(equational reasoning)的支持；引入的变量等于计算出它的表达式(假设这个变量没有副作用),因此，可以在任何打算写变量的地方都可以直接用表达式来替换；

```scala
println(if (!args.isEmpty) args(0) else "default.txt")
```



#### 2.5.2 while循环

两种循环：

* while 
* do-while

> * while和do-while不是表达式，因为它们并不会返回一个有意义的值，返回值的类型是Unit；
> * 实际上存在一个也是唯一一个类型为Unit的值，这个值叫做单元值(unit value)，写作`()`；


```scala
def gcdLoop(x: Long, y: Long): Long = {
	var a = x
	var b = y
	while (a != 0) {
		val temp = a
		a = b % a
		b = temp
	}
	b
}
```


```scala
var line = ""

do {
	line = readLine()
	println("Read: " + line)
} while (line != "")
```



#### 2.5.3 for表达式

#### 2.5.4 try异常处理


#### 2.5.5 match表达式

#### 2.5.6 没有break和continue

#### 2.5.7 变量作用域



### 2.6 函数和闭包
=======

## 2.5 内建控制结构

>>>>>>> 0b42cb304c8894168f4a6fa3a6c057e3478a2c21

### 2.5.1 if语句


### 2.5.2 while循环


### 2.5.3 for 表达式

* `<-`:生成器(generator)；


#### 2.5.3.1 遍历集合

遍历数组：

```scala
val fileHere = (new java.io.File(".")).listFiles
for (file <- fileHere) {
	println(file)
}
```

遍历区间(Range)：

```scala
for (i <- 1 to 4) {
	println("Iteration " + i)
}
```

遍历区间(不包含区间上届)：

```scala
for (i <- 1 until 4) {
	println("Iteration " + i)
}
```

#### 2.5.3.2 过滤

> 如果不想完整地遍历集合，只想把集合过滤成一个子集，可以给for表达式添加**过滤器(filter)**，过滤器是for表达式的圆括号中的一个if子句；


```scala
val fileHere = (new java.io.File(".")).listFiles
for (file <- fileHere if file.getName.endsWith(".scala")) {
	println(file)
}
```

添加更多的过滤器：

```scala
for (
	file <- fileHere
	if file.isFile
	if file.getName.endsWith(".scala")
) {
	println(file)
}
```

#### 2.5.3.3 嵌套迭代

> * 如果想添加多个<-子句，将得到嵌套的“循环”；

```scala
def fileLines(file: java.io.File) = {
	scala.io.Source.fromFile(file).getLines().toList()
}

def grep(pattern: String) = {
	for (
		file <- fileHere
		if file.getName.endsWith(".scala")
		line <- fileLines(file)
		if line.trim.matches(pattern)
	) {
		println(file + ":" + line.trim)
	}
}

grep(".*gcd.*")
```

可以省去分号：

```scala
val fileHere = (new java.io.File(".")).listFiles

def fileLines(file: java.io.File) = {
	scala.io.Source.fromFile(file).getLines().toList()
}

def grep(pattern: String) = {
	for {
		file <- fileHere
		if file.getName.endsWith(".scala")
		line <- fileLines(file)
		if line.trim.matches(pattern)
	} {
		println(file + ":" + line.trim)
	}
}

grep(".*gcd.*")
```

#### 2.5.3.4 中途(mid-stream)变量绑定

> * 可以用`=`将表达式的结果绑定到新的变量上，被绑定的这个变量引入和使用起来跟val一样；

```scala
val fileHere = (new java.io.File(".")).listFiles

def fileLines(file: java.io.File) = {
	scala.io.Source.fromFile(file).getLines().toList()
}

def grep(pattern: String) = {
	for (
		file <- fileHere
		if file.getName.endsWith(".scala")
		line <- fileLines(file)
		trimed = line.trim
		if trimed.matches(pattern)
	) {
		println(file + ": " + trimed)
	}
}

grep(".*gcd.*")
```

#### 2.5.3.4 输出一个新的集合

> * 可以在每次迭代中生成一个可以被记住的值；做法是在for表达式的代码体之前加上关键字yield；

```scala
def scalaFiles = {
	for {
		file <- fileHere
		if file.getName.endsWith(".scala")
	} yield file
}
```

### 2.6 try表达式异常处理

> * 方法除了正常地返回某个值外，也可以通过抛出异常终止执行；
> * 方法的调用方要么捕获并处理这个异常，要么自我终止，让异常传播到更上层调用方；
> * 异常通过这种方式传播，逐个展开调用栈，直到某个方法处理该异常或者没有更多的方法了为止；







## 2.6 函数和闭包

> * 随着程序变大，需要某种方式将它们切成更小的、便于管理的块；
> * Scala将代码切分成函数；

### 2.6.1 方法

> * 定义函数最常用的方式是作为某个对象的成员，这样的函数被称为方法(method)；

示例：

```scala
// LongLines.scala

import scala.io.Source

object LongLines {
	def processFile(filename: String, width: Int) = {
		val source = Source.fromFile(filename)
		for (line <- source.getLines()) {
			processLine(filename, width, line)
		}
	}

	// processFile方法的助手方法
	private def processLine(filename: String, width: Int, line: String) = {
		if (line.length > width) {
			println(filename + ": " + line.trim)
		}
	}
}
```


```scala
// FindLongLines.scala

import LongLines

object FindLongLines {
	def main(args: Array[String]) = {
		val width = args(0).toInt
		for (arg <- args.drop(1)) {
			LongLines.processFile(arg, width)
		}
	}
}
```

```shell
# 运行程序
$ fsc LongLines.scala FindLongLines.scala
$ scala FindLongLines 45 LongLines.scala
```

### 2.6.2 局部函数

> * 函数式编程风格的一个重要设计原则：程序应该被分解成许多小函数，每个函数都只做明确的任务；
> * 上面的设计带来的问题：助手函数的名称会污染整个程序的命名空间；
> * 局部函数：可以在函数内部定义函数，就像局部变量一样，这样的函数只在包含它的代码块中可见；
	- 局部函数可以访问包含他们的函数的参数；


```scala
//

import scala.io.Source

object LongLines {
	def processFile(filename: String, width: Int) = {
		// processLine只在函数processFile内部有效
		def processLine(line: String) = {
			if (line.length > width) {
				println(filename + ": " + line.trim)
			}
		}

		val source = Source.fromFile(filename)
		for (line <- source.getLines()) {
			processLine(line)
		}
	}
}
```


### 2.6.3 一等函数

> * Scala支持**一等函数(first-class function)**；
	- 不仅可以定义函数并调用它们，还可以用**匿名的字面量**来编写函数并将它们作为**值(value)**进行传递； 
	- **函数字面量**被编译成类，并在运行时实例化成**函数值(function value)**，因此，函数字面量和函数值的区别在于，函数字面量存在于源码，而函数值以对象的形式存在于运行时，这跟类和对象的区别很相似；
	- 函数值是对象，因此可以将他们存放在变量中，它们同时也是函数，所以可以用常规的圆括号来调用它们；


#### 2.6.3.1 函数字面量

```scala
(x: Int) => x + 1
```
* 这里`=>`表示该函数将左侧的内容(任何整数 $x$)转换成右侧的内容$(x + 1)$；
	- 这是一个将任何整数 $x$ 映射成 $x + 1$ 的函数


**函数字面量示例 1：**

```scala
// 将函数值存放在变量中
var increase = (x: Int) => x + 1

// 调用函数值
increase(0)
```

**函数字面量示例 2：**

```scala
increase = (x: Int) => {
	println("We")
	println("are")
	println("here!")
	x + 1
}

// 函数调用
increase(10)
```

**函数字面量示例 3：**

```scala
// 所有的集合类都提供了foreach, filter方法
val someNumbers = List(-11, -10, -5, 0, 5, 10)

someNumbers.foreach((x: Int) => println(x))
someNumbers.filter((x: Int) => x > 0) 
```

#### 2.6.3.2 函数字面量简写

**省去类型声明：**

> * 函数字面量简写形式：略去参数类型声明
	- Scala编译器知道变量是什么类型，因为它看到这个函数用来处理的集合是一个什么类型元素组成的集合，这被称作**目标类型(target typing)**，因为一个表达式的目标使用场景可以影响该表达式的类型；
	- 当编译器报错时再加上类型声明，随着经验的积累，什么时候编译器能推断类型，什么时候不可以就慢慢了解了；

```scala
val someNumbers = List(-11, -10, -5, 0, 5, 10)
someNumbers.filter((x) => x > 0)
```


**省去圆括号：**

> * 函数字面量简写形式：省去某个靠类型判断的参数两侧的圆括号

```scala
val someNumbers = List(-11, -10, -5, 0, 5, 10)
someNumbers.filter(x => x > 0)
```

**占位符语法：**

> * 为了让函数字面量更加精简，还可以使用下划线作为占位符，用来表示一个或多个参数，只要满足每个参数只在函数字面量中出险一次即可；
	- 可以将下划线当成是表达式中需要被“填”的“空”，函数每次被调用，这个“空”都会被一个入参“填”上；
		- 多个下划线意味着多个参数，而不是对单个参数的重复使用；
	- 可以用冒号给出入参的类型，当编译器没有足够多的信息来推断缺失的参数类型时；


```scala
val someNumbers = List(-11, -10, -5, 0, 5, 10)
someNumbers.filter(_ > 0)
``` 

```scala
val f = (_: Int) + (_: Int)
f(5, 10)
```

**部分应用函数(partially applied function)：**

> * 用下划线替换整个参数列表；
> * 部分应用函数是一个表达式，在这个表达式中，并不给出函数需要的所有参数，而是给出部分，或者完全不给；

```scala
val someNumbers = List(-11, -10, -5, 0, 5, 10)

// 一般形式
someNumbers.foreach(x => println(x))

// 部分应用函数
someNumbers.foreach(println _)
```

```scala
def sum(a: Int, b: Int, c: Int) = {
	a + b + c
}

val a = sum _
```

这里，名为a的变量指向一个函数值对象，这个函数值是一个从Scala编译器自动从`sum _`这个部分应用函数表达式生成的类的实例，由编译器生成的这个类有一个接收三个参数的apply方法；


### 2.6.4 闭包

闭包示例：

```scala
var more = 1
val addMore = (x: Int) => x + more
```

* 运行时从函数字面量`(x: Int) => x + more`创建出来的函数值(对象)`val addMore`被称作**闭包(closure)**；
* 自由变量(free varialbe): `more`
* 绑定变量(bound variable): `x`




### 2.6.5 特殊的函数调用形式(传参)


### 2.6.6 尾递归





## 2.7 控制抽象



## 2.8 组合继承

### 2.8.1 


### 2.8.2 Scala的继承关系


## 2.9 特质


# 3. 包(package)和包引入(import)

> 在处理程序，尤其是大型程序时，减少耦合(coupling)是很重要的。所谓的耦合就是指程序不同部分依赖其他部分的程度。低耦合能减少程序某个局部的某个看似无害的改动对其他部分造成严重后果的风险。减少耦合的一种方式是以模块化的风格编写代码。可以将程序切分成若干较小的模块，每个模块都有所谓的内部和外部之分。


## 3.1 将代码放进包里(模块化)

**在Scala中，可以通过两种方式将代码放进带名字的包里：**

* 在文件顶部放置一个`package`子句，让整个文件的内容放进指定的包：
	- 也可以包含多个包的内容，可读性不好；
```scala
package bobsrockets.naviagation
class Navigator {}
```

* 在package子句之后加上一段用花括号包起来的代码块:
	- 更通用，可以在一个文件里包含多个包的内容；

```scala
package bobsrockets {
	package naviagation {
		class Navigator {}
		package test {
			class NavigatorSuite {}
		}	
	}
}
```

## 3.2 对相关代码的精简访问

1. 一个类不需要前缀就可以在自己的包内被别人访问；
2. 包自身也可以从包含他的包里不带前缀地访问到；
3. 使用花括号打包语法时，所有在包外的作用域内可被访问的名称，在包内也可以访问到；
4. Scala提供了一个名为`__root__`的包，这个包不会跟任何用户编写的包冲突，每个用户能编写的顶层包都被当做是`__root__`的成员；

```scala
package bobsrockets {
	package navigation {
		class Navigation {
			// 一个类不需要前缀就可以在自己的包内被别人访问
			val map = new StarMap
		}
		class StarMap {}
	}

	class Ship {
		// 包自身也可以从包含他的包里不带前缀地访问到
		val nav = new navigation.Naviagtor
	}

	package fleets {
		class Fleet {
			def addShip() = {
				// 使用花括号打包语法时，所有在包外的作用域内可被访问的名称，在包内也可以访问到
				new Ship
			}
		}
	}
}
```

```scala
// =========================================
// launch.scala
// =========================================
// launch_3
package launch {
	class Booster3 {}
}

// =========================================
// bobsrockets.scala
// =========================================
package bobsrockets {
	package navigation {

		// launch_1
		package launch {
			class Booster1 {}
		}

		class MissionControl {
			val booster1 = new launch.Booster1
			val booster2 = new bobsrockets.launch.Booster2
			val booster3 = new __root__launch.Booster3
		}
	}

	// launch_2
	package launch {
		class Booster2 {}
	}
}
```


## 3.3 包引入

> 在Scala中，可以用`import`子句引入包和它们的成员；

**Scala包引入方式：**

* 对应Java的单类型引入；
* 对应Java的按需(on-demand)引入；
* 对应Java的对静态字段的引入；


编写包：

```scala
package bobsdelights {
	abstract class Fruit(val name: String, val color: String)

	object Fruits {
		object Apple extends Fruit("apple", "red")
		object Orange extends Fruit("orange", "orange")
		object Pear extends Fruit("pear", "yellowwish")

		val menu = List(Apple, Orange, Pear)
	}
}
```

包引入：

```scala
// 到bobsdelights包中Fruit类的便捷访问, 对应Java的单类型引入
import bobsdelights.Fruit

// 到bobsdelights包中所以成员的便捷访问, 对应Java的按需(on-demand)引入
import bobsdelights._

// 到Fruits对象所有成员的便捷访问, 对应Java的对静态字段的引入
import bobsdelights.Furits._

// 引入函数showFruit的参数fruit(类型为Fruit)的所有成员
def showFruit(fruit: Fruit) = {
	import fruit._
	println(name + "s are "+ color)
}
```

**Scala包引入的灵活性：**

1. 引入可以出现在任意位置；
2. 引入可以引用对象(不论是单例还是常规对象)，而不只是包；
3. 引入可以重命名并隐藏某些被引入的成员；\
	- 做法是将需要选择性引入的对象包在花括号内的引入选择器子句(import selector clause)中，引入选择器子句跟在要引入成员的对象后面；
		- 引入选择器可以包含：
			- 一个简单的名称`x`。这将把x包含在引入的名称集里；
			- 一个重命名子句 `x => y`。这会让名为x的成员以y的名称可见；
			- 一个隐藏子句`x => _`。这会从引入的名称集里排除掉x；
			- 一个捕获所有(catch-all)的`_`。这会引入除了之前子句中提到的成员之外的所有成员。如果要给出捕获所有子句，它必须出现在引入选择器的末尾；

```scala
// 引入对象(object)
import bobsdelights.Fruits.{Apple, Orange}

// 引入对象的所有成员
import Fruits.{_}

// 对引入对象(Apple)重命名
import bobsdelights.Fruits.{Apple => McIntosh, Orange}

import java.sql.{Date => SDate}
import java.{sql => s}

// 引入Fruits对象的所有成员，并把Apple重命名为McIntosh
import Fruits.{Apple => McIntosh, _}

// 引入Pear之外的所有成员
import Fruits.{Pear => _, _}
```


## 3.4 隐式引入

Scala对每个程序都隐式地添加了一些引入；即每个扩展名为`.scala`的源码文件的顶部都添加了如下三行引入子句：

* `java.lang包`包含了标准的Java类
	- 总是被隐式地引入到Scala文件中，由于java.lang是隐式引入的，举例来说，可以直接写Thread，而不是java.lang.Thread；
* `scala包`包含了Scala的标准库
	- 包含了许多公用的类和对象，由于scala是隐式引入的，举例来说，可以直接写List，而不是scala.List
* `Predef`对象包含了许多类型、方法、隐式转换的定义，由于Predef是隐式引入的，举例来说，可以直接写assert，而不是Predef.assert；

```scala
// java.lang包的全部内容
import java.lang._ 

// scala包的全部内容
import scala._

// Predef对象的全部内容
import Predef._
```

## 3.5 访问修饰符

> 包、类、对象的成员可以标上`private`和`protected`等访问修饰符，这些修饰符将对象的访问限定在特定的代码区域。

### 3.5.1 私有成员(private)

> 标为private的成员只在包含该定义的类(class)或对象(object)内部可见；

```scala
class Outer {
	class Inner {
		private def f() = {println("f")}
		class InnerMost {
			// 可以访问f
			f()
		}
	}
	// 错误：无法访问f, Java可以
	(new Inner).f()
}
```


### 3.5.2 受保护成员(protected)

> 标为protected的成员只能从定义该成员的子类访问；

```scala
package p {
	class Super {
		protected def f() = {println("f")}
	}

	class Sub extends Super {
		// 可以访问f，Sub是Super的子类
		f()
	}

	class Other {
		// 错误：无法访问f, Java可以
		(new Super).f()
	}
}
```

### 3.5.3 公共成员

> Scala没有专门的修饰符用来标记公共成员：任何没有标为private或protected的成员 都是公共的；公共成员可以从任意位置访问到；


### 3.5.4 保护的范围

> * 可以用限定词对Scala中的访问修饰符机制进行增强
	- 形如`private[X]`，`protected[X]`的修饰符的含义是对此成员的访问限制“上至”X都是私有或受保护的，其中X表示某个包含该定义的包、类、对象；

```scala
package bobsrockets {

	package navigation {

		// Navigator类对bobsrockets包内的所有类和对象都可见，比如：launch.Vehicle对象中对Navigator的访问是允许的
		private[bobsrockets] class Navigator {

			// 
			protected[navigation] def useStarChart() = {}

			class LegOfJourney {
				//
				private[Navigator] val distance = 100
			}

			// 仅在当前对象内访问
			private[this] var speed = 200
		}
	}

	package launch {
		import navigation._

		object Vehicle {
			private[launch] val guide = new Navigator
		}
	}
}

```

### 3.5.5 可见性和伴生对象


## 3.6 包对象(package object)


> * 任何能放在类级别的定义，都能放在包级别；
> * 每个包都允许有一个包对象，任何放在包对象里的定义都会被当做这个包本身的成员；
> * 包对象经常用于包级别的类型别名和隐式转换；
> * 包对象会被编译为名为package.class的类文件，改文件位于它增强的包的对应目录下；

举例：



```scala
package bobsdelights {
	abstract class Fruit(val name: String, val color: String)

	object Fruits {
		object Apple extends Fruit("apple", "red")
		object Orange extends Fruit("orange", "orange")
		object Pear extends Fruit("pear", "yellowwish")

		val menu = List(Apple, Orange, Pear)
	}
}
```

```scala
// bobsdelights/package.scala文件
// 包对象
package object bobsdelights {
	def showFruit(fruit: Fruit) = {
		import fruit._
		println(name + "s are " + color)
	}
}
```

```scala
// PrintMenu.scala文件

package printmenu
import bobsdelights.Fruits
import bobsdelights.showFruit

object PrintMenu {
	def main(args: Array[String]) = {
		for (fruit <- Fruits.menu) {
			showFruit(fruit)
		}
	}
}
```

# 4.断言和测试

> 断言和测试是用来检查程序行为符合预期的两种重要手段；

## 4.1 断言

> 在Scala中，断言的写法是对预定义方法`assert`的调用；


* `assert`方法定义在`Predef`单例对象中，每个Scala源文件都会自动引入该单例对象的成员；
* `assert(condition)`
	- 若condition不满足，抛出AssertionError
* `assert(condition, explanation)`
	- 首先检查condition是否满足，如果不满足，抛出包含给定explanation的AssertionError;
	- explanation的类型时Any,因此可以传入任何对象，assert方法将调用explanation的toString方法来获取一个字符串的解释放入AssertionError；



用assert进行断言：

```scala
def above(that: Element): Element = {
	val this1 = this widen that.width
	val that1 = that widen this.width
	assert(this1.width == that1.width)
	elem(this1.contents ++ that1.contents)
}
```

用Predef.ensuring进行断言：

```scala
private def widen(w: Int): Element = {
	if (w <= width) {
		this
	}
	else {
		val left = elem(" ", (w - width) / 2, height)
		var right = elem(" ", w - widht - left.width, height)
		left beside this beside right
	} ensuring (w <= _.width)
}
```






## 4.2 测试







# 5.样例类和匹配模式


## 5.1 样例类

> * 样例类是Scala用来对对象进行模式匹配二进行的不需要大量的样板代码的方式。笼统的说，要做的就是对那些希望能做模式匹配的类加上一个`case`关键字；
> * 样例类会让Scala编译器对类添加一些语法上的便利；
	- 1.首先，它会添加一个跟类同名的工厂方法；
	- 2.其次，参数列表中的参数都隐式地获得了一个val前缀，因此它们会被当做字段处理；
	- 3.再次，编译器会帮我们以自然地方式实现toString,hashCode和equals方法；
	- 4.最后，编译器还会添加一个copy方法用于制作修改过的拷贝，这个方法可以用于制作除了一两个属性不同之外其余完全相同的该类的新实例；
	- 5.样例类最大的好处是他们支持模式匹配；

示例：

```scala
abstract class Expr

// 变量
case class Var(name: String) extends Expr
// 数
case class Number(num: Double) extends Expr
// 一元操作符
case class UnOp(operator: String, arg: Expr) extends Expr
// 二元操作符
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
```

* 样例类会添加一个跟类同名的工厂方法，嵌套定义，不需要`new`

```scala
val v = Var("x")
val op = BinOp("+", Number(1), v)
```

* 参数列表中的参数都隐式地获得了一个val前缀，因此它们会被当做字段处理

```scala
v.name
op.operator
op.left
op.right
```

* 编译器会帮我们以自然地方式实现toString,hashCode和equals方法

```scala
println(op)
op.right == Var("x")
```

* 编译器还会添加一个copy方法用于制作修改过的拷贝

```scala
op.copy(operator = "-")
print(op)
```


## 5.2 模式匹配


### 5.2.1 模式匹配形式

> * 模式匹配包含一系列以case关键字开头的可选分支(alternative)
	- 每一个可选分支都包括一个模式(pattern)以及一个或多个表达式，如果模式匹配成功了，这些表达式就会被求值，箭头`=>`用于将模式和表达式分开;
	- 一个mathc表达式的求值过程是按照模式给出的顺序逐一进行尝试的；
> * 模式匹配mathc特点
	- Scala的match是一个表达式，也就是说它总是能得到一个值；
	- Scala的可选分支不会贯穿到下一个case；
	- 如果没有一个模式匹配上，会抛出MatchError的异常，所以需要确保所有的case被覆盖到，哪怕意味着需要添加一个什么都不做的缺省case；


基本形式：

**选择器 match {可选分支}**

示例函数：

```scala
def simplifyTop(expr: Expr): Expr = expr match {
	case UnOp("-", UnOp("-", e)) => e
	case BinOP("+", e, Number(0)) => e
	case BinOp("+", e, Number(1)) => e
	case _ => expr
}
```


### 5.2.2 模式种类

#### 通配模式

#### 常量模式


#### 变量模式


#### 构造方法模式


#### 序列模式



#### 带类型的模式



#### 变量绑定





# 6.Scala集合对象

> * 可变集合
	- List()
> * 不可变集合
	- Array()


> * 同构
	- List()
	- Array()
> * 异构
	- 



## 6.1 数组 Array

## 6.1 列表 List

> 1. List是不可变的，即List的元素不能通过赋值改变；
> 2. List的机构是递归的，即链表(linked list)；


### 6.1.1 List 字面量

示例：

```scala
val fruit = List("apples", "oranges", "pears")

val nums = List(1, 2, 3)

val diag3 = List(
	List(1, 0, 0),
	List(0, 1, 0),
	List(0, 0, 1)
)

val empty = List()
```

### 6.1.2 List 类型

1. List是同构的(homogeneous)的，即同一个List的所有元素都必须是相同的类型；
	- 元素类型为T的List的类型写作`List[T]`；
2. List类型是协变的(covariant)，即对每一组类型S和T，如果S是T的子类型，那么`List[S]`就是`List[T]`的子类型；
	- 例如：List[String]是List[Object]的子类型；
	- 空列表的类型为`List[Nothing]`；在Scala的累继承关系中，Nothing是底类型，所以对于任何T类型而言，List[String]都是List[T]的子类型；


示例：

```scala
val xs: List[String] = List()
```


### 6.1.3 List 构建

> * 所有的List都构建自两个基础的构建单元：`Nil`，`::`(读作“cons”)；
	- `Nil`：空List；
	- `::`：在List前追加元素；
> * 用`List()`定义的List字面量不过是最终展开成由`Nil`和`::`组合成的这样形式的包装方法而已；
> * 因为`::`是右结合的，因此，可以在构建List时去掉圆括号；

示例：

```scala
val fruit = "apples" :: ("oranges" :: ("pears" :: Nil))
val nums = 1 :: (2 :: (3 :: (4 :: Nil)))
val diag3 = (1 :: (0 :: (0 :: Nil))) ::
			(0 :: (1 :: (0 :: Nil))) ::
			(0 :: (0 :: (1 :: Nil))) :: Nil
val empty = Nil

// 去掉圆括号
val fruit = "apples" :: "oranges" :: "pears" :: Nil
val nums = 1 :: 2 :: 3 :: 4 :: Nil
val diag3 = (1 :: 0 :: 0 :: Nil) ::
			(0 :: 1 :: 0 :: Nil) ::
			(0 :: 0 :: 1 :: Nil) :: Nil
val empty = Nil
```

### 6.1.4 List 基本操作


> * 对List的所有操作都可以用三项来表述：
	- `head`: 返回List的第一个元素；
		- 只对非空List有定义，否则抛出异常
	- `tail`: 返回List中除第一个元素之外的所有元素；
		- 只对非空List有定义，否则抛出异常
	- `isEmpty`: 返回List是否为空List；

| List方法                                        | 方法用途                |
|:------------------------------------------------|:-----------------------|
| `List()`, `Nil`                                 | 空List				   
| `List("Cool", "tools", "rules")`                | 创建一个新的List[String]
| `val thril = "Will" :: "fill" :: "until" :: Nil`| 创建一个新的List[String]
| `List("a", "b") ::: List("c", "d")`             | 将两个List拼接起来
| `thril.mkString(", ")`                          | 返回一个用List的所有元素组合成的字符串	   
| `thril(2)`                                      | 返回List中下标为2的元素
| `thril.head`                                    | 返回List首个元素
| `thril.init`                                    | 返回List除最后一个元素之外的其他元素组成的List
| `thril.last`                                    | 返回List的最后一个元素
| `thril.tail`									  | 返回List除第一个元素之外的其他元素组成的List
| `thril.length`                                  | 返回List的元素个数
| `thril.isEmpty`                                 | 判断List是否是空List
| `thril.drop(2)`								  | 返回去掉了List的头两个元素的List
| `thril.dropRight(2)`							  | 返回去掉了List的后两个元素的List
| `thril.reverse`								  | 返回包含List的所有元素但顺序反转的List
| `thril.count(s => s.length == 4)`				  | 对List中满足条件表达式的元素计数
| `thril.filter(s => s.length == 4)`              | 按顺序返回List中所有长度为4的元素List
| `thril.filterNot(s => s.length == 4)`           | 按顺序返回List中所有长度不为4的元素List
| `thril.exists(s => s == "until")`               | 判断List中是否有字符串元素为"until"
| `thril.forall(s => s.endsWith("l"))`            | 判断List中是否所有元素都以字母"l"结尾
| `thril.foreach(s => println(s))`                | 对List中的每个字符串进行print
| `thril.foreach(println)`                          | 对List中的每个字符串进行print,精简版
| `thril.map(s => s + "y")`                       | 返回一个对List所有字符串元素末尾添加"y"的新字符串的List
| `thril.sort((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)`|返回包含List的所有元素，按照首字母小写的字母顺序排列的List


示例：对一个数字List `x :: xs` 的元素进行升序排列，采用插入排序(insertion sort)

```scala
def isort(xs: List[Int]): List[Int] = {
	if (xs.isEmpty) {
		Nil
	} else {
		insert(xs.head, isort(xs.tail))
	}
}

def insert(x: Int, xs: List[Int]): List[Int] = {
	if (xs.isEmpty || x <= xs.head) {
		x :: xs
	} else {
		xs.head :: insert(x, xs.tail)
	}
}

val L = 1 :: (3 :: 4 :: 2 :: Nil)
```

###  6.1.5 List 模式

> * List可以用模式匹配解开， List模式可以逐一对应到List表达式；
	- 用List(...)这样的模式来匹配List的所有元素；
	- 用::操作符和Nil常量一点点地将List解开；

示例 1：

```scala
val fruit = List("apples", "oranges", "pears")
val fruit = "apples" :: "oranges" :: "pears" :: Nil
```

```scala
// List(...)模式匹配
val List(a, b, c) = fruit
```

```
// output
a: String = apples
b: String = oranges
c: String = pears
```


```scala
// ::和Nil将List解开
val a :: b :: rest = fruit
```

```
// output
a: String = apples
b: String = oranges
rest: List[String] = List(pears)
```

示例 2：实现插入排序


```scala
def isort(xs: List[Int]): List[Int] =  xs match {
	case List() => List()
	case x :: xs1 => insert(x, isort(xs1))
}

def insert(x: Int, xs: List[Int]): List[Int] = xs match {
	case List() => List(x)
	case y :: ys => if (x <= y) {
						x :: xs
					} else {
						y :: insert(x, ys)
					}
}
```

### 6.1.6 List 方法

#### 6.1.6.1 List 类的初阶方法

> * 如果一个方法不接收任何函数作为入参，就被称为初阶(first-order)方法；

##### List拼接方法

> * `xs ::: ys`：接收两个列表参数作为操作元；
> * 跟`::`类似，`:::`也是右结合的，因此`xs ::: ys ::: zs`等价于`xs ::: (ys ::: zs)`； 


示例：

```scala
List(1, 2) ::: List(3, 4, 5)
List() ::: List(1, 2, 3, 4, 5)
List(1, 2, 3, 4) ::: List(4)
```


##### 分治(Divide and Conquer)原则





#### 6.1.6.2 List类的高阶方法

> * 





## 6.2 序列 Seq

> 序列类型可以用来处理依次排列分组的数据；

* List()
* Array()
* StringOps()

### 6.2.1 List

> List支持在头部快速添加和移除条目，不过并不提供快速地按下标访问的功能，因为事先这个功能需要线性地遍历List；

相关内容：

* 第三章第8步；
* 第十六章；
* 第22章；


### 6.2.2 Array

> Array保存一个序列的元素，并使用从0开始的下标高效地访问(获取或更新)指定位置的元素值；

相关内容：

* 第三章第步；
* 7.3节使用for表达式遍历数组；
* 第10章的二维布局类库；

```scala
// 创建Array
val fiveInts = new Array[Int](5)
val fiveToOne = Array(5, 4, 3, 2, 1)

// 访问数组中的元素,改变数组的元素
fiveInts(0)
fiveToOne(4)
fiveInts(0) = fiveToOne(4)
println(fiveInts)
```

### 6.2.3 List buffer (List 缓冲)

> * List类提供对List头部的快速访问，对尾部访问则没有那么高效，因此，当需要往List尾部追加元素来构建List时，通常要考虑反过来往头部追加元素，追加完成后，再调用`reverse`来获得想要的顺序；
> * 另一种可选方案是`ListBuffer`，ListBuffer是一个可变对象，在需要追加元素来构建List时可以更高效。
	- ListBuffer提供了常量时间的往后追加和往前追加的操作，可以用`+=`操作符来往后追加元素，用`+=:`来往前追加元素，完成构建后再调用ListBuffer的`toList`来获取最终的List；
	- ListBuffer可以防止出现栈溢出；


```scala
import scala.collection.mutable.ListBuffer

// 创建一个类型为Int的ListBuffer
val buf = new ListBuffer[Int]

// 往List后追加元素
buf += 1
buf += 2
println(buf)

// 往List前追加元素
3 +=: buf

buf.toList
```

### 6.2.4 Arrray buffer (Arrray 缓冲)

> * ArrayBuffer 跟数组很像，除了可以额外从序列头部或尾部添加或移除元素；
> * 所有的 Array 操作在 ArrayBuffer都可用，不过由于实现的包装，会稍慢一些；
> * `+=`: 往 Array 后面追加元素；

```scala
import scala.collection.mutable.ArrayBuffer

// 创建一个类型为Int的ArrayBuffer
val buf = new ArrayBuffer[Int]()

// 往Array后追加元素
buf += 12
buf += 15
println(buf)

// Array操作
buf.length
buf(0)
```

### 6.2.5 StringOps

> 由于`Predef`有一个从 String 到 StringOps 的隐式转换，可以将任何字符串当做序列来处理；

```scala
// 在下面的函数里，对字符串调用了其本身没有的方法`exists`
// Scala编译器会隐式地将 s 转换成 StringOps，StringOps 有 exists 方法，exists 方法将字符串当做字符的序列
def hasUpperCase(s: String) = {
	s.exists(_.isUpper)
}

hasUpperCase("Wang Zhefeng")
hasUpperCase("e e cumming")
```

## 6.3 集合 Set





## 6.4 映射 Map

## 6.5 元组 Tuple
