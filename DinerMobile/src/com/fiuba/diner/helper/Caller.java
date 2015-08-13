package com.fiuba.diner.helper;

public interface Caller<T> {

	void afterCall(T result);

}
