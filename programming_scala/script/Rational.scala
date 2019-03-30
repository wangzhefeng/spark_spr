//!/usr/bin/env scala


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