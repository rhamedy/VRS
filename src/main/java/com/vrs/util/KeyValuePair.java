package com.vrs.util;

import java.util.Map;

/**
 * 
 * @param <K> 
 * @param <V>
 * 
 * Parameter K & V are generic, meaning that, we can replace them with
 * our favorite type, be it String, Integer, or some custom type
 * e.g. <key,value> -> <model,toyota>
 * 
 * @author Rafiullah Hamedy
 * @date	21-03-2013
 * 
 */

public class KeyValuePair<K,V> implements Map.Entry<K,V>{
	
	private K key; 
	private V value; 
	
	public KeyValuePair(K key, V value) { 
		this.key = key; 
		this.value = value; 
	}
	
	@Override
	public V setValue(V value) {
		V oldValue = this.value; 
		this.value = value; 
		return oldValue; 
	}

	@Override
	public K getKey() {
		return key; 
	}

	@Override
	public V getValue() {
		return value; 
	} 
}
