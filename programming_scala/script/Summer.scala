//!/usr/bin/env scala


import ChecksumAccumulator.calculate

object Summer {
	def main(args: Array[String]) {
		for (arg <- args) {
			println(arg + ":" + calculate(arg))
		}
	}
}