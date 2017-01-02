package com.meiyou_tec.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

@Component("serviceLocator")
public class SpringServiceUtil implements BeanFactoryAware {
	private static BeanFactory beanFactory = null;

	private static SpringServiceUtil servlocator = null;

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		beanFactory = factory;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public static SpringServiceUtil getInstance() {
		if (servlocator == null) {
			servlocator = (SpringServiceUtil) beanFactory.getBean("serviceLocator");
		}
		return servlocator;
	}

	public static Object getService(String servName) {
		return getInstance().getBeanFactory().getBean(servName);
	}

	public static Object getService(String servName, Class clazz) {
		return getInstance().getBeanFactory().getBean(servName, clazz);
	}

	public static boolean containsBean(String name) {
		return getInstance().getBeanFactory().containsBean(name);
	}

	public static boolean isSingleton(String name) {
		return getInstance().getBeanFactory().isSingleton(name);
	}

	public static Class getType(String name) {
		return getInstance().getBeanFactory().getType(name);
	}

	public static String[] getAliases(String name) {
		return getInstance().getBeanFactory().getAliases(name);
	}
}