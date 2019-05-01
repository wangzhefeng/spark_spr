//!/usr/bin/env scala


import LongLines.processFile

object FindLongLines {
	def main(args: Array[String]) = {
		val width = args(0).toInt
		for (arg <- args.drop(1)) {
			processFile(arg, width)
		}
	}
}