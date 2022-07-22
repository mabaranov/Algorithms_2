package com.company;

import java.util.*;

class aBST
{
    public Integer Tree []; // массив ключей

    public aBST(int depth)
    {
        // правильно рассчитайте размер массива для дерева глубины depth:
        int treeSize = (int) (Math.pow(2, depth + 1) - 1);
        Tree = new Integer[ treeSize ];
        for(int i=0; i<treeSize; i++)
            Tree[i] = null;
    }

    public Integer FindKeyIndex(int key)
    {
        // ищем в массиве индекс ключа

        // Пустое дерево

        for(int i=0; i<Tree.length; )
        {
            if(i==0 && Tree[i] == null) // пустое дерево
                return i;

            if(Tree[i] == null) // не нашли ключ, нашли место
                return -i;
            if(Tree[i] == key) // нашли ключ
                return i;

            if(Tree[i] > key)
                i = 2 * i + 1;
            else
                i = 2 * i + 2;
        }

        return null; // не нашли ключ, не нашли место
    }

    public int AddKey(int key)
    {
        // добавляем ключ в массив
        Integer foundIndex = FindKeyIndex(key);

        if(foundIndex == null)
            return -1;

        if(foundIndex==0 && Tree[foundIndex]==null)
            Tree[foundIndex] = key;

        if(foundIndex < 0)
        {
            foundIndex *= -1;
            Tree[foundIndex] = key;
        }

        return foundIndex;
        // индекс добавленного/существующего ключа или -1 если не удалось
    }
}