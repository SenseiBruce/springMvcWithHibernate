package com.websystique.springmvc.service;

import java.util.Collections;
import java.util.List;

/**
 * Simple 0-based page slice for list endpoints.
 */
public final class PageResult<T> {

	private final List<T> items;
	private final int page;
	private final int size;
	private final int total;

	public PageResult(List<T> items, int page, int size, int total) {
		this.items = items;
		this.page = page;
		this.size = size;
		this.total = total;
	}

	public static <T> PageResult<T> of(List<T> all, int page, int size) {
		int safeSize = size < 1 ? 10 : size;
		int safePage = page < 0 ? 0 : page;
		int total = all == null ? 0 : all.size();
		if (total == 0) {
			return new PageResult<T>(Collections.<T>emptyList(), safePage, safeSize, 0);
		}
		int from = Math.min(safePage * safeSize, total);
		int to = Math.min(from + safeSize, total);
		return new PageResult<T>(all.subList(from, to), safePage, safeSize, total);
	}

	public List<T> getItems() {
		return items;
	}

	public int getPage() {
		return page;
	}

	public int getSize() {
		return size;
	}

	public int getTotal() {
		return total;
	}

	public int getTotalPages() {
		if (size <= 0) {
			return 0;
		}
		return (total + size - 1) / size;
	}
}
