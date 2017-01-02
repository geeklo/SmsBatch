package com.meiyou_tec.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

public class MsgObservableQueue<E> extends Observable implements Queue<E>, Serializable {
	private static final long serialVersionUID = 1L;
	private Queue<E> queue;

	public MsgObservableQueue(Queue<E> _queue) {
		this.queue = _queue;
	}

	public void setObserver(List<Observer> observerList) {
		for (Observer observer : observerList) {
			addObserver(observer);
		}
		if (!this.queue.isEmpty()) {
			setChanged();
			notifyObservers();
		}
	}

	public boolean add(E e) {
		boolean flag = this.queue.offer(e);
		setChanged();
		notifyObservers();
		return flag;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean flag = this.queue.addAll(c);
		setChanged();
		notifyObservers();
		return flag;
	}

	public void clear() {
		this.queue.clear();
	}

	public boolean contains(Object o) {
		return this.queue.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.queue.containsAll(c);
	}

	public boolean isEmpty() {
		return this.queue.isEmpty();
	}

	public Iterator<E> iterator() {
		return this.queue.iterator();
	}

	public boolean remove(Object o) {
		return this.queue.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return this.queue.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return this.queue.retainAll(c);
	}

	public int size() {
		return this.queue.size();
	}

	public Object[] toArray() {
		return this.queue.toArray();
	}

	public Object[] toArray(Object[] a) {
		return this.queue.toArray(a);
	}

	public E element() {
		return this.queue.element();
	}

	public boolean offer(E e) {
		return this.queue.offer(e);
	}

	public E peek() {
		return this.queue.peek();
	}

	public E poll() {
		return this.queue.poll();
	}

	public E remove() {
		return this.queue.remove();
	}
}