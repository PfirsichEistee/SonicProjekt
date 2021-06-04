package app;

import java.util.ArrayList;

import Objects.Objekt;

public class ChunkedList<E extends Objekt> {
	// ATTRIBUTE //
	private ArrayList<Sub<E>> chunkList;
	
	
	// KONSTRUKTOR //
	public ChunkedList() {
		chunkList = new ArrayList<Sub<E>>();
	}
	public ChunkedList(ArrayList<E> al) {
		chunkList = new ArrayList<Sub<E>>();
		
		for (int i = 0; i < al.size(); i++) {
			add(al.get(i));
		}
	}
	
	
	// METHODEN //
	public void add(E obj) {
		int cnkX = (int)Math.floor(obj.getX() / 10);
		int cnkY = (int)Math.floor(obj.getY() / 10);
		for (Sub<E> sub : chunkList) {
			if (sub.chunkX == cnkX && sub.chunkY == cnkY) {
				sub.add(obj);
				return;
			}
		}
		
		Sub<E> newSub = new Sub<E>();
		newSub.add(obj);
		chunkList.add(newSub);
	}
	public void remove(int chunkX, int chunkY, int index) {
		for (Sub<E> sub : chunkList) {
			if (sub.chunkX == chunkX && sub.chunkY == chunkY) {
				sub.remove(index);
				
				if (sub.size == 0)
					chunkList.remove(sub);
				
				return;
			}
		}
	}
	public void remove(int chunkX, int chunkY, E obj) {
		for (Sub<E> sub : chunkList) {
			if (sub.chunkX == chunkX && sub.chunkY == chunkY) {
				for (int i = 0; i < sub.size; i++) {
					if (sub.get(i) == obj) {
						sub.remove(i);
						
						if (sub.size == 0)
							chunkList.remove(sub);
						
						return;
					}
				}
			}
		}
	}
	public int size(int chunkX, int chunkY) {
		for (Sub<E> sub : chunkList) {
			if (sub.chunkX == chunkX && sub.chunkY == chunkY) {
				return sub.size;
			}
		}
		
		return 0;
	}
	public E get(int chunkX, int chunkY, int index) {
		for (Sub<E> sub : chunkList) {
			if (sub.chunkX == chunkX && sub.chunkY == chunkY) {
				return sub.get(index);
			}
		}
		
		return null;
	}
}


class Sub<E> {
	// ATTRIBUTE //
	public int chunkX;
	public int chunkY;
	public int size;
	private Object[] list;
	
	
	// KONSTRUKTOR //
	public Sub() {
		list = new Object[10];
		size = 0;
	}
	
	
	// METHODEN //
	public void add(E obj) {
		if (size == list.length)
			extendList();
		
		list[size] = obj;
		
		size++;
	}
	public void remove(int index) {
		for (int i = index; i < size; i++) {
			if ((i + 1) != list.length) {
				list[i] = list[i + 1];
			} else {
				list[i] = null;
			}
		}
		
		size--;
		
		if ((list.length - size) > 10)
			trimList();
	}
	@SuppressWarnings("unchecked")
	public E get(int index) {
		return (E)list[index];
	}
	
	
	private void extendList() {
		Object[] newList = new Object[list.length + 10];
		for (int i = 0; i < list.length; i++)
			newList[i] = list[i];
		
		list = newList;
	}
	private void trimList() {
		Object[] newList = new Object[list.length - 10];
		for (int i = 0; i < newList.length; i++)
			newList[i] = list[i];
		
		list = newList;
	}
}




