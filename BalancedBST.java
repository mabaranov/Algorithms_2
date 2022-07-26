package com.company;

import java.util.*;

class BSTNode
{
    public int NodeKey; // ключ узла
    public BSTNode Parent; // родитель или null для корня
    public BSTNode LeftChild; // левый потомок
    public BSTNode RightChild; // правый потомок
    public int     Level; // глубина узла

    public BSTNode(int key, BSTNode parent)
    {
        NodeKey = key;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

class BalancedBST
{
    public BSTNode Root; // корень дерева

    public BalancedBST()
    {
        Root = null;
    }

    public void GenerateTree(int[] a)
    {
        // создаём дерево с нуля из неотсортированного массива a
        // ...
        Arrays.sort(a);
        recursiveGenerate(null, a, -1);
    }

    private BSTNode recursiveGenerate(BSTNode parent, int[] sortedArray, int level)
    {
        if(sortedArray.length == 0)
            return null;

        int middle = sortedArray.length / 2;
        BSTNode node = new BSTNode(sortedArray[middle], parent);

        if (Root == null)
        {
            Root = node;
            Root.Level = 1;
        }

        if (node.Parent != null)
            node.Level = node.Parent.Level + 1;

        //node.Level = level + 1;

        int [] leftSubArray = new int[middle];
        System.arraycopy(sortedArray, 0, leftSubArray, 0, middle);
        node.LeftChild = recursiveGenerate(node, leftSubArray, node.Level);

        int [] rightSubArray = new int[middle];
        System.arraycopy(sortedArray, middle+1, rightSubArray, 0, middle);
        node.RightChild = recursiveGenerate(node, rightSubArray, node.Level);

        return node;
    }

    public boolean IsBalanced(BSTNode root_node)
    {
        int leftDepth; // для высоты левого поддерева
        int rightDepth; // для высоты правого поддерева

        if (root_node == null)
            return true;

        leftDepth = recursiveDepth(root_node.LeftChild);
        rightDepth = recursiveDepth(root_node.RightChild);

        if (Math.abs(leftDepth - rightDepth) <= 1 && IsBalanced(root_node.LeftChild) && IsBalanced(root_node.RightChild))
            return true;

        return false; // сбалансировано ли дерево с корнем root_node
    }

    private int recursiveDepth(BSTNode node)
    {
        if (node == null)
            return 0;

        return 1 + Math.max(recursiveDepth(node.LeftChild), recursiveDepth(node.RightChild));
    }
}