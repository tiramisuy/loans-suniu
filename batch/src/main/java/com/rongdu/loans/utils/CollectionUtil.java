package com.rongdu.loans.utils;

import java.util.Collection;

import com.google.common.collect.Multimap;

public class CollectionUtil {
	
	public static Collection values(Multimap map,String key){
		return map.get(key);
	}
	
	public static boolean contains(Collection collection,Long object){
		return collection.contains(object);
	}
	
}
