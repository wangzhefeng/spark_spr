//!/usr/bin/env scala


import ChecksumAccumulator.calculate

object FallWinterSpringSummer extends App {
	for (season <- List("fall", "winter", "spring")) {
		print(season + ": " + calculate(season))
	}
}