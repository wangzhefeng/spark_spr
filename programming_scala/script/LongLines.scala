//!/usr/bin/env scala


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