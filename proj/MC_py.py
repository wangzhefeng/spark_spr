#!/usr/bin/env python
# -*- coding: utf-8 -*-

__author__ = "wangzhefeng"

import numpy as np

def mc_pi(n = 100):
	"""
	Use Monte Calo Method to estimate pi.
	:param n:
	:return: total_point, point_in_circle, estimated_pi
	"""
	m = 0
	i = 0
	while i < n:
		x, y = np.random.rand(2)
		if x ** 2 + y ** 2 < 1:
			m += 1
		i += 1

	pi = 4.0 * m / n
	res = {"total_point": n, "point_in_circle": m, "estimated_pi": pi}

	return res


def main():
	result = mc_pi(n = 10000)
	print(result)

if __name__ == "__main__":
	main()