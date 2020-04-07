package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A Singly-Linked List is a list that has only knowledge of its very first
 * element. Elements after that are chained, ending with a null node.
 * 
 * @author jfoley
 *
 * @param <T> - the type of the item stored in this list.
 */
public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {
		checkNotEmpty();
		T firstValue = this.start.value;
		this.start = this.start.next;
		return firstValue;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();
		if (this.size() == 1) {
			return removeFront();
		}
		Node <T> penul = null;
		for (Node<T> current = this.start; current.next != null; current = current.next) {
			penul = current;
		}
		T fin = penul.next.value;
		penul.next = null;
		return fin;
		
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		checkExclusiveIndex(index);
		if (index == 0) {
			return removeFront();
		} else if (index == this.size() - 1) {
			return removeBack();
		}
		
		T empty = null;
		Node<T> current = this.start;
		for (int at = 0;  at < index-1; at++) {
			current = current.next;
		}
		empty = current.next.value;
		current.next = current.next.next;
		return empty;
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, this.start);
	}

	@Override
	public void addBack(T item) {
		if (this.start == null) {
			this.addFront(item);
			return;
		}
		Node <T> last = null;
		for (Node<T> current = this.start; current != null; current = current.next) {
			last = current;
		}
		assert(last.next == null);
		last.next = new Node<T>(item, null);
	}

	@Override
	public void addIndex(int index, T item) {
		if (index == 0) {
			this.addFront(item);
			return;
		} else if (index == this.size()) {
			this.addBack(item);
			return;
		} else if (index > this.size() || (index < 0)) {
			throw new BadIndexError(index);
		}
		Node<T> current = this.start;
		for (int at = 0;  at < index-1; at++) {
			current = current.next;
		}
		current.next = new Node<T>(item, current.next); 
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return getIndex(0);
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return getIndex(this.size()-1);
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		if (index > this.size() - 1 || index < 0) {
			throw new BadIndexError(index);
		}
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				n.value = value;
			}
		}
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with a friend.
		 * @param value - the value to put in it.
		 * @param next - the friend of this node.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Alternate constructor; create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.next = null;
		}
	}

}
