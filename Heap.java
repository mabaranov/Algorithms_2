package com.company;

import java.util.*;

class Heap
{
    public int [] HeapArray; // хранит неотрицательные числа-ключи
    private int count;

    public Heap() { HeapArray = null; }

    public void MakeHeap(int[] a, int depth)
    {
        // создаём массив кучи HeapArray из заданного
        // размер массива выбираем на основе глубины depth
        // ...
        int treeSize = (int) (Math.pow(2, depth + 1) - 1);
        HeapArray = new int[treeSize];

        for(int element : a)
            Add(element);
    }

    public int GetMax()
    {
        // вернуть значение корня и перестроить кучу
        if(HeapArray == null || count == 0)
            return -1; // если куча пуста

        int root = HeapArray[0];

        HeapArray[0] = HeapArray[count-1];
        HeapArray[count-1] = 0;
        count -= 1;
        rebuildTree();

        return root;
    }

    public boolean Add(int key)
    {
        // добавляем новый элемент key в кучу и перестраиваем её
        if(count == HeapArray.length)
            return false; // если куча вся заполнена

        int indexAddKey = count;
        HeapArray[indexAddKey] = key;
        int indexParent = (indexAddKey - 1 ) / 2;

        while(indexAddKey>0 && indexParent>=0)
        {
            if(HeapArray[indexAddKey] > HeapArray[indexParent])
            {
                int temp = HeapArray[indexAddKey];
                HeapArray[indexAddKey] = HeapArray[indexParent];
                HeapArray[indexParent] = temp;
            }

            indexAddKey = indexParent;
            indexParent = (indexAddKey - 1) / 2;
        }

        count += 1;
        return true;
    }

    private void rebuildTree()
    {
        for(int i=0; i<HeapArray.length; )
        {
            int indexLeftChild = 2 * i + 1;
            int indexRightChild = 2 * i + 2;
            int indexGreatestChild = i;

            if((indexLeftChild < count) && (HeapArray[indexLeftChild] > HeapArray[indexGreatestChild]))
                indexGreatestChild = indexLeftChild;

            if((indexRightChild < count) && (HeapArray[indexRightChild] > HeapArray[indexGreatestChild]))
                indexGreatestChild = indexRightChild;

            if(indexGreatestChild == i)
                break;

            int tmp = HeapArray[i];
            HeapArray[i] = HeapArray[indexGreatestChild];
            HeapArray[indexGreatestChild] = tmp;
            i = indexGreatestChild;
        }
    }
}