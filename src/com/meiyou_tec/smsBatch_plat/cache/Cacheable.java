package com.meiyou_tec.smsBatch_plat.cache;

/**
 * Cache公共接口  缓存接口，所有数据的缓存都实现该接口
 * @author liugeng
 */
public interface Cacheable extends Runnable {
	public Object getCache();
}