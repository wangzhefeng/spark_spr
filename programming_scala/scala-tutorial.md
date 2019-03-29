
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


编写脚本1：

```scala
// hello.scala

println("Hello world, from a script!")
```

执行脚本1：

```shell
$ scala hello.scala
```

编写脚本2：

```scala
// helloarg.scala

/* 对第一个命令行参数说hello */
println("Hello, " + args(0) + "!")
```

执行脚本2：

```shell
$ scala helloarg.scala plant
```

## 5.用while做循环；用if做判断

```scala
// printargs.scala

var i = 0
while (i < args.length) {
	println(args(i))
	i += 1
}
```

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
$ scala printargs.scala Scala is fun
$ scala echoargs.scala Scala is more even fun
```

## 6. 用foreach和for遍历

> * 指令式编程风格(imperative)
>	    - 依次给出指令，通过循环来遍历，经常变更别不同函数共享的状态
>	* 函数式编程风格(functional)
>	    - 函数式编程语言的主要特征之一就是函数是一等的语法单元；




#### foreach

```scala
// pa.scala

args.foreach(arg => println(arg))
args.foreach((arg: String) => println(arg))
args.foreach(println)
```

```shell
$ scala pa.scala Concise is nice
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

> * 用`new`来实例化`对象`或`类`的`实例`；
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
for (i -> 0 to 2) {
	print(greetStrings(i))
}
```

```scala
val greetStrings: Array[String] = new Array[String](3)
```


* *注意：*当用`val`定义一个变量时，变量本身不能被重新赋值，但它指向的那个对象是有可能发生变化的。可以改变`Array[String]`的元素，因此`Array`本身是可变的；


## 8.[List] 使用列表

> * Scala数组`Array`是一个拥有相同类型的对象的可变序列。虽然无法在数组实例化后改变其长度，却可以改变它的元素值，因此数组是可变的对象；
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
| `List()`, `Nil`                                 | 空List|
| `List("Cool", "tools", "rules")`                | 创建一个新的List[String]|
| `val thril = "Will" :: "fill" :: "until" :: Nil`| 创建一个新的List[String]|
| `List("a", "b") ::: List("c", "d")`             | 将两个List拼接起来|
| `thril(2)`                                      | 返回List中下标为2的元素|
| `thril.count(s => s.length == 4)`								| 对List中满足条件表达式的元素计数|
| `thril.drop(2)`																	| 返回去掉了List的头两个元素的List|
| `thril.dropRight(2)`														| 返回去掉了List的后两个元素的List|
| `thril.exists(s => s == "until")`               |
| `thril.filter(s => s.length == 4)`              |
| `thril.forall(s => s.endsWith("l"))`            |
| `thril.foreach()`                               |
| `thril.head()`                                  |
| `thril.init()`                                  |
| `thril.isEmpty`                                 |
| `thril.last`                                    |
| `thril.length`                                  |
| `thril.map(s => s + "y")`                       |
| `thril.mkString(", ")`                          | 
| `thril.filterNot(s => s.length == 4)`           | 
| `thril.reverse`																	|
| `thril.sort((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)`|
| `thril.tail`																		|



## 9.[Tuple] 使用元组

> * 元组是不可变的；
> * 元组可以容纳不同类型的元素；
> * 要实例化一个新的元组，只需要将对象放在圆括号当中，用逗号隔开即可；
> * 实例化好一个元组后，就可以用`._n, n = 1, 2,...`来访问每一个元素；
> * 元组类型：`Tuplel[Type1, Type2, ...]`， 比如： `Tuple2[Int, String]`

```scala
val pair = (99, "Luftballons")
println(pair._1)
println(pair._2)
```


## 10.[Set, Map]使用集和映射

> * Array永远是可变的(元素)；
> * List永远是不可变的；
> * Tuple永远是不可变的；
> * Scala通过不同的类继承关系来区分Set、Map的可变和不可变；
	    - Scala的API包含了一个基础的`特质(trait)`来表示Set、Map，Scala提供了两个`子特质(subtrait)`，一个用于表示可变Set、可变Map，另一个用于表示不可变Set、不可变Map；
> 

### Set

##### Scala Set 的累继承关系

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

##### Scala Map 的累继承关系

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
> 每个有用的程序都会有某种形式饿副作用。否则，它对外部世界就没有任何价值。倾向于使用无副作用的函数鼓励你设计出将带有副作用的代码最小化的额程序。这样做的好处之一就是让你的程序更容易测试；

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

# 类、对象、字段、方法

### 类、字段、方法

> * 类是对象的蓝本(blueprint)；一旦定义好了一个类，就可以用`new`关键字从这个类蓝本创建对象；
> * 在类定义中，可以填入`字段(field)`和`方法(method)`，这些被统称为`成员(member)`；
	   - 通过`val`或`var`定义的`字段`是指向对象的变量；字段保留了对象的状态，或者说是数据；
	       - 追求健壮性的一个重要手段是确保对象的状态在其整个声明周期都是有效的
	   	       - 首先，通过将字段标记为`私有(private)`来防止外部直接访问字段因为私有字段只能被定义在同一个类中的方法访问，所有对状态的更新的操作的代码，都在类的内部；
	   - 通过`def`定义的`方法`则包含了可执行的代码；方法用字段定义的数据来对对象执行计算；
	       - 传递给方法的任何参数都能在方法内部使用。Scala方法参数的一个重要特征是他们都是val。因此，如果试图在Scala的方法中对参数重新赋值，编译会报错；
	   	   - 仅仅因为其副作用而被执行的方法被称作`过程(procedure)`；
>  - 当`实例化`一个类，运行时会指派一些内存来保存对象的状态图（即它的变量的内容）；
	   
	   

##### 创建类

```scala
class ChecksumAccumulator {
	// 类定义
}
```

##### 创建对象

```scala
new ChecksumAccumulator
```

##### 创建类、定义字段

```scala
class ChecksumAccumulator {
	// 类定义
	var sum = 0
}
```

```scala
val acc = ChecksumAccumulator
val csa = ChecksumAccumulator

acc.sum = 3
```

##### 创建类、定义私有字段、方法

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

```scala
val acc = new ChecksumAccumulator

// 下面的定义不能编译 
acc.sum = 5
```

### 单例对象

> * `单例对象(singleton object)`的定义跟类定义很想，只不过`class`关键字换成了`object`关键字；
> * 当单例对象跟某个类共用同一个名字时，它被称为这个类的`伴生对象(companion object)`；必须在同一个源码文件中定义类和类的伴生对象；
> * 同名的类又叫作这个单例对象的`伴生类(companion class)`；
> * 类和它的伴生对象可以互相访问对方的私有成员；

```scala
// ChecksumAccumulator.scala

import scala.collection.mutable

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

### 基础类型、操作

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





### 函数式对象


### 内建控制结构


# 函数和闭包










# 包(package)和包引入(import)

> 在处理程序，尤其是大型程序时，减少耦合(coupling)是很重要的。所谓的耦合就是指程序不同部分依赖其他部分的程度。低耦合能减少程序某个局部的某个看似无害的改动对其他部分造成严重后果的风险。减少耦合的一种方式是以模块化的风格编写代码。可以将程序切分成若干较小的模块，每个模块都有所谓的内部和外部之分。


## 将代码放进包里(模块化)

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

## 对相关代码的精简访问

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


## 包引入

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


## 隐式引入

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

## 访问修饰符

> 包、类、对象的成员可以标上`private`和`protected`等访问修饰符，这些修饰符将对象的访问限定在特定的代码区域。

### 私有成员(private)

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


### 受保护成员(protected)

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

### 公共成员

> Scala没有专门的修饰符用来标记公共成员：任何没有标为private或protected的成员 都是公共的；公共成员可以从任意位置访问到；



### 保护的范围

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

### 可见性和伴生对象




## 包对象(package object)


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
