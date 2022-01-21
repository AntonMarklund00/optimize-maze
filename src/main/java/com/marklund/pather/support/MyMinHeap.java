package com.marklund.pather.support;

import com.marklund.pather.dao.Node;

import java.lang.reflect.Array;

public class MyMinHeap<T extends Node> {
    private class InnerNode {
        private final double key;
        private final T value;

        public InnerNode(double key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    private InnerNode[] nodes;
    private int size;
    private int arraySize;

    public MyMinHeap(int size){
        arraySize = size;
        nodes = (InnerNode[]) Array.newInstance(InnerNode.class, arraySize);
    }

    public void insert(T value, double valueKey) {
        if (isFull())
            resizeArray();

        nodes[size++] = new InnerNode(valueKey, value);

        bubbleUp();
    }

    private void resizeArray() {
        InnerNode[] newItems = (InnerNode[]) Array.newInstance(Node.class, arraySize * 2);

        if (arraySize >= 0) System.arraycopy(nodes, 0, newItems, 0, arraySize);

        arraySize = arraySize * 2;
        nodes = newItems;

    }

    public boolean contains(T vertex) {
        for (int i = 0; i < size; i++) {
            if (nodes[i].value == vertex)
                return true;
        }
        return false;
    }

    public T remove() {
        if (isEmpty())
            throw new IllegalStateException();

        var root = nodes[0].value;
        nodes[0] = nodes[--size];

        bubbleDown();

        return root;
    }

    private void bubbleDown() {
        var index = 0;
        while (index <= size && !isValidParent(index)) {
            var largerChildIndex = smallerChildIndex(index);
            swap(index, largerChildIndex);
            index = largerChildIndex;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int smallerChildIndex(int index) {
        if (!hasLeftChild(index))
            return index;

        if (!hasRightChild(index))
            return leftChildIndex(index);

        return (leftChild(index).key < rightChild(index).key) ?
                leftChildIndex(index) :
                rightChildIndex(index);
    }

    private boolean hasLeftChild(int index) {
        return leftChildIndex(index) <= size;
    }

    private boolean hasRightChild(int index) {
        return rightChildIndex(index) <= size;
    }

    private boolean isValidParent(int index) {
        if (!hasLeftChild(index))
            return true;

        var isValid = nodes[index].key <= leftChild(index).key;

        if (hasRightChild(index))
            isValid &= nodes[index].key <= rightChild(index).key;

        return isValid;
    }

    private InnerNode rightChild(int index) {
        return nodes[rightChildIndex(index)];
    }

    private InnerNode leftChild(int index) {
        return nodes[leftChildIndex(index)];
    }

    private int leftChildIndex(int index) {
        return index * 2 + 1;
    }

    private int rightChildIndex(int index) {
        return index * 2 + 2;
    }

    public boolean isFull() {
        return size == nodes.length;
    }

    private void bubbleUp() {
        var index = size - 1;
        while (index > 0 && nodes[index].key < nodes[parent(index)].key) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private void swap(int first, int second) {
        var temp = nodes[first];
        nodes[first] = nodes[second];
        nodes[second] = temp;
    }

    public int getSize() {
        return size;
    }
}
