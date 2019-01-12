// urs/bin/env scala


// object Test
object Test {
	def main(args: Array[String]) {	}
}




// **********************************************************
// if
// **********************************************************
// 指令式type
var filename1 = "default.txt"
if (!args.isEmpty)
	filename = args(0)

// function style
val filename2 = 
	if (!args.isEmpty)
		args(0)
	else
		"default.txt"
println(filename2)
println(if (!args.isEmpty) args(0) else "default")


// **********************************************************
// while, do...while...
// **********************************************************
// while
def gcdLoop(x: Long, y: Long): Long = {
	var a = x
	var b = y
	while(a != 0) {
		val temp = a
		a = b % a
		b = temp
	}
}

// do...while...
var line = ""
do {
	line = readLine()
	println("Read: " + line)
} while (line != "")

// **********************************************************
// Exception
// **********************************************************
// 抛出异常
val half = 
	if (n % 2 == 0)
		n / 2
	else
		throw new RuntimeException("n must be even")
// 捕获异常
import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException



// **********************************************************
// for
// **********************************************************
val filesHere = (new java.io.File(".")).listFiles
for (file <- filesHere if file.getName.endsWith(".scala"))
	println(file)

// or
val filesHere = (new java.io.File(".")).listFiles
for (file <- filesHere)
	if (file.getName.endsWith(".scala"))
		println(file)


val filesHere = (new java.io.File(".")).listFiles
for (
	file <- filesHere
	if file.isFile
	if file.getName.endsWith(".scala")
) println(file)


