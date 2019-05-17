

[TOC]

# 1.环境配置


# 2.基础


## 2.1 Not Hello World



## 2.2 注释



## 2.3 数据类型



## 2.4 变量



## 2.5 运算符



## 2.6 字符串



## 2.7 输入、输出



## 2.8 控制流程

> * 条件语句
> * 循环语句
> * switch 语句
> * break 关键字
> * continue 关键字

### 2.8.1 块作用域

> 块(block)，即复合语句，是指由一对大括号括起来的若干条简单的Java语句，块确定了变量的作用域；一个块可以嵌套在另一个块中；

```java
public class Test {
	public static void main(String[] args) {
		int n;
		{
			int k;
		}
	}
}
```

### 2.8.2 条件语句

> * `if (condition) statement;`
> * `if (condition) statement1 else statement2;`
> * `if (condition) statement1 else if (condition) statement else if ...;`

示例1：

```java
if (yourSales >= target) {
	performance = "Satisfactory";
	bonus = 100;
}
```

示例2：

```java
if (yourSales >= target) {
	performance = "Satisfactory";
	bonus = 100 + 0.01 * (yourSales - target)
}
else {
	performance = "UnStatifactory";
	bonus = 0;
}
```

示例3：

```java
if (yourSales >= 2 * target) {
	performance = "Excellent";
	bonus = 1000;
}
else if (yourSales >= 1.5 * target) {
	performance = "Fine";
	bonus = 500;
}
else if (yourSales >= "target") {
	performance = "Satisfactory";
	bonus = 100;
}
else {
	System.out.println("You'r fired");
}
```

### 2.8.3 循环语句

> * `while (condition) statement;`
> * `for (int i; i = 0; i++);`; 
> * `do statement while (condition);`

示例1：

```java
// Retirement.java

import java.util.*;

/**
 * This program demonstrates a <code>while<code> loop.
 * @version 1.00 2019-05-10
 * @author Tinker
 */

public class Retirement {
	public static void main(String[] args) {
		// read inputs
		Scanner in = new Scanner(System.in);

		System.out.print("How much money do you need to retire?");
		double goal = in.nextDouble();

		System.out.print("How much money will you contribute every year?");
		double payment = in.nextDouble();

		System.out.print("Interest rate in %: ");
		double interestRate = in.nextDouble();

		double balance = 0;
		int years = 0;

		// update account balance while goal isn't reached
		while (balance < goal) {
			// add this year's payment and interest
			balance += payment;
			double interest = balance * interest / 100;
			balance += interest;
			years++;
		}

		System.out.println("You can retire in " + years + " years.")
	}
}
```

示例2：

```java
// Retirement2.java

import java.util.*;

/**
 * This program demonstrates a <code>while<code> loop.
 * @version 1.00 2019-05-10
 * @author Tinker
 */

public class Retirement2 {
	public static void main(String[] args) {
		// read inputs
		Scanner in = new Scanner(System.in);

		System.out.print("How much money will you contribute every year?");
		double payment = in.nextDouble();

		System.out.print("Interest rate in %: ");
		double interestRate = in.nextDouble();

		double balance = 0;
		int years = 0;

		String input;

		// update account balance while goal isn't reached
		do {
			// add this year's payment and interest
			balance += payment;
			double interest = balance * interestRate / 100;
			year++;

			// print current balance
			System.out.print("After year %d, your balance is %,.2f%n", year, balance);

			// ask if ready to retire and get input
			System.out.print("Ready to retire? (Y/N)");
			input = in.next();
		}
		while (input.equals("N"));
	}
}
```

示例3：

```java
for (int i; i <= 10; i++) {
	System.out.println(i);
}
```

```java
int i;
for (int i; i <= 10; i++) {
	System.out.println(i);
}
```

```java
// LotteryOdds.java

import java.util.*;

/**
 * This program demonstrates a <code>while<code> loop.
 * @version 1.00 2019-05-10
 * @author Tinker
 */

public clas LotteryOdds {
	public static void main(String[] args) {
		Scanner in new Scanner(System.in);

		System.out.print("How many numbers do you need to draw?");
		int k = in.nextInt();

		System.out.print("What is the highest number you can draw?");
		int n = in.nextInt();

		/*
		 * compute binomial coefficient n*(n-1)*(n-2)*...(n-k+1)/(1*2*...*k)
		 */
		int lotteryOdds = 1;
		for (int i; i <= k; i++) {
			lotteryOdds = lotteryOdds * (n - i + 1) / i;
		}

		System.out.println("Your odds are 1 in " + lotteryOdds + ". Good luck!");
	}
}
```

### 2.8.4 switch 语句

> * 在处理多个选项时，使用`if/esle`结构显得有些笨拙；
> * case 标签可以是：
	- 类型为char, byte, short, int 的常量表达式；
	- 枚举常量；
	- 从Java SE7开始，case标签还可以是字符串字面量；

```java
import java.util.*;

public class {
	public static void main(String[] args) {
		Scanner in new Scanner(System.in);
		System.out.print("Select an option (1, 2, 3, 4) ");
		int choice in.nextInt();

		switch (choice) {
			case 1:
				// statement1;
				break;
			case 2:
				// statement2;
				break;
			case 3:
				// statement3;
				break;
			case 4:
				// statement4;
				break;
			default:
				// bad input;
				break;
		}
	}
}
```

编译代码使加上`-Xlint:fallthrough`选项，如果某个分支最后缺少一个`break`语句，编译器就会给出一个警告消息：

```shell
javac -Xlint:fallthrough Test.java
```

### 2.8.5 中断控制流程的 break 和 continue 关键字

> * break 语句：
	- 不带标签的 break 语句；
	- 带标签的 break 语句；
		- 用于跳出多重潜逃的循环语句；
> * continue 语句

不带标签的 break 语句：

```java
while (years <= 100) {
	balance += payment;
	double interest = balance * interestRate / 100;
	balance += interest;
	if (balance >= goal) 
		break;
	years++;
}
```

带标签的 break 语句：

```java
Scanner in new Scanner(System.in);
int n;
read_data:
while (...) {
	...
	for () {
		System.out.print("Enter a number >= 0: ");
		n = in.nextInt();
		if (n < 0) 
			break read_data;
		...
	}
}

if (n < 0) {
	// ...
}
else {
	// ...
}
```

不带标签的 continue 语句：

```java
Scanner in = new Scanner(System.in);
while (sum < goal) {
	System.out.print("Enter a number: ");
	n = in.nextInt();
	if (n < 0) 
		continue;
	sum += n;
}
```

```java
for (count = 1; count <= 100; count++) {
	System.out.print("Enter a number, -1 to quit: ");
	n = in.nextInt();
	if (n < 0) 
		continue;
	sum += n;
}
```

带标签的 continue 语句：

```java

```

## 2.9 数组

> * Java 数组是一种数据结构，用来存储同一种类型值的集合；
> * Java 数组可以通过一个括在中括号中的整数下标访问数组中的每一个元素；
	- a[i]
> * 在声明数组变量时，需要指出数组的类型(数据元素类型紧跟`[]`)和数组变量的名字；
	- int[] a;
	- float[] a;
	- String[] a;
	- boolean[] a;
> * 创建一个数字数组时，所有的元素都初始化为0；boolean 数组的元素会初始化为false；对象数组的元素会初始化为一个特殊的null，表示这些元素还未存放任何对象；
> * 可以通过`.length`获取数组中元素的个数；
> * 数组一旦创建，就不能再改变它的大小，但可以改变每一个数组的元素；如果经常需要在运行过程中扩展数组的大小，就应该使用另一种数据结构--`数组列表(array list)`；

声明数组：

```java
// 声明一个整型数组
int[] a;
int a[];

// 声明并初始化一个长度为100的整数数组
int[] a = new int[100];


// 给数组元素赋值
int[] a = new int[100];
for (int i; i < 100; i++) {
	a[i] = i
}

// boolean 数组
boolean[] a;

// 字符串对象数组
String[] names = new String[100];
```

获取数组中元素的个数：

```java
int[] a = new int[100];
for (int i = 0; i < a.length, i++) {
	System.out.println(a[i]);
}
```


# 3.对象与类


## 3.1 object 和 class


## 3.2 class 继承

# 4.接口和 lambda表达式


# 5.异常、断言、日志


# 6.泛型程序设计


# 7.集合

# 8.应用程序部署


