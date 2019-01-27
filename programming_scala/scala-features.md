
[TOC]


# Scala 语言语法特点



* 函数式编程的最重要理念之一是方法不能有副作用。一个方法唯一要做的是计算并返回一个值；
* 所有Java的基本类型在Scala包中都有对应的类；
	- scala.Boolean <=> java.boolean
	- scala.Int <=> java.int
	- scala.Float <=> java.float
	- scala.String <=> java.lang.String
* 跟java不同，Scala并不是在变量名之前给出类型，而是在变量名之后，变量名和类型之间用冒号`:`分开；
* 代码快缩进2个空格是Scala推荐的缩进风格
* Scala中，wihle或if语句中的boolean表达式必须放在圆括号里；
* Scala中也支持用分号分隔语句，只不过Scala中的分号通常都不是必须的；
* Scala的数组的访问方式是将下标放在圆括号`()`里，而不是方括号`[]`里；
* Scala从技术上讲并没有操作符重载(operator overloading)，因为它实际上并没有传统意义上的操作符。类似`+、-、*、/`这样的字符可以被用作方法名；
* Scala中所有操作符都是方法调用；
	- .apply()
	- .update()
* 如果一个方法被用在操作符表示法(operator notation)当中，比如：$a*b$，方法调用默认都发生在`左操作元(left operand)`，除非方法以冒号(:)结尾。如果方法名的最后一个字符是冒号，该方法的调用会发生在它的`右操作元`上。
	- `a * b`： `a.*(b)`
	- `a :: b`: `b ::.(a)`
* 在没有任何显式的return语句时，Scala方法返回的时该方法计算出的最后一个值；
