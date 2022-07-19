package com.company;

import java.util.ArrayList;

class BSTNode<T>
{
    public int NodeKey; // ключ узла
    public T NodeValue; // значение в узле
    public BSTNode<T> Parent; // родитель или null для корня
    public BSTNode<T> LeftChild; // левый потомок
    public BSTNode<T> RightChild; // правый потомок

    public BSTNode(int key, T val, BSTNode<T> parent)
    {
        NodeKey = key;
        NodeValue = val;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

// промежуточный результат поиска
class BSTFind<T>
{
    // null если в дереве вообще нету узлов
    public BSTNode<T> Node;

    // true если узел найден
    public boolean NodeHasKey;

    // true, если родительскому узлу надо добавить новый левым
    public boolean ToLeft;

    public BSTFind() { Node = null; }
}

class BST<T>
{
    BSTNode<T> Root; // корень дерева, или null

    public BST(BSTNode<T> node)
    {
        Root = node;
    }

    public BSTFind<T> FindNodeByKey(int key)
    {
        // ищем в дереве узел и сопутствующую информацию по ключу
        if (Root == null)
            return new BSTFind<>();


        BSTFind<T> foundNode = new BSTFind<>();
        BSTNode<T> tmpNode = Root;

        while(key != tmpNode.NodeKey)
        {
            if (key < tmpNode.NodeKey) {
                if (tmpNode.LeftChild != null) {
                    tmpNode = tmpNode.LeftChild;
                } else {
                    foundNode.ToLeft = true;
                    break;
                }
            } else {
                if (tmpNode.RightChild != null) {
                    tmpNode = tmpNode.RightChild;
                } else {
                    foundNode.ToLeft = false;
                    break;
                }
            }
        }

        foundNode.Node = tmpNode;
        foundNode.NodeHasKey = (key == tmpNode.NodeKey);
        return foundNode;
    }

    public boolean AddKeyValue(int key, T val)
    {
        // добавляем ключ-значение в дерево
        BSTFind<T> found = FindNodeByKey(key);

        if (found.NodeHasKey)
            return false; // если ключ уже есть

        BSTNode<T> addNode = new BSTNode<>(key, val, found.Node);

        if (found.Node == null) {// дерево пустое
            Root = addNode;
            return true;
        }

        if (found.ToLeft)
            found.Node.LeftChild = addNode;
        else
            found.Node.RightChild = addNode;

        return true;
    }

    public BSTNode<T> FinMinMax(BSTNode<T> FromNode, boolean FindMax)
    {
        // ищем максимальный/минимальный ключ в поддереве
        if (FromNode == null)
            return null;

        BSTNode<T> current = FromNode;

        if (FindMax)
            while (current.RightChild != null)
                current = current.RightChild;
        else
            while (current.LeftChild != null)
                current = current.LeftChild;

        return current;
    }

    public boolean DeleteNodeByKey(int key)
    {
        // удаляем узел по ключу
        BSTFind<T> found = FindNodeByKey(key);

        // не нашли
        if (!found.NodeHasKey)
            return false;

        BSTNode<T> deleteNode = found.Node;
        BSTNode<T> parentDeleteNode = deleteNode.Parent;

        boolean isLeftChild = true;
        if (parentDeleteNode != null)
            isLeftChild = (parentDeleteNode.LeftChild == deleteNode);


        // ищем узел которым надо заменить
        BSTNode<T> replaceNode = deleteNode;
        if (replaceNode.RightChild != null) {
            replaceNode = replaceNode.RightChild;
            while (replaceNode.LeftChild != null)
                replaceNode = replaceNode.LeftChild;
        }

        // не нашли чем заменить, просто удалим
        if (deleteNode == replaceNode)
        {
            if (parentDeleteNode == null)
                Root = null;
            else
                if (isLeftChild)
                    parentDeleteNode.LeftChild = null;
                else
                    parentDeleteNode.RightChild = null;

            return true;
        }

        //  нашли чем заменить,
        //      на старом месте - удаляем
        BSTNode<T> parentNodeForReplace = replaceNode.Parent;
        if (parentNodeForReplace != null)
        {
            if (parentNodeForReplace.LeftChild == replaceNode)
                parentNodeForReplace.LeftChild = null;
            else
                parentNodeForReplace.RightChild = null;
        }

        //      и "исправляем" родителя
        replaceNode.Parent = parentDeleteNode;

        //      на новом месте "пропишем"
        if (parentDeleteNode == null)
            Root = replaceNode;
        else
            if (isLeftChild)
                parentDeleteNode.LeftChild = replaceNode;
            else
                parentDeleteNode.RightChild = replaceNode;


        // ... ссылки на детей
        BSTNode<T> deleteNodeLeftChild = deleteNode.LeftChild;
        BSTNode<T> deleteNodeRightChild = deleteNode.RightChild;
        replaceNode.LeftChild = deleteNodeLeftChild;
        replaceNode.RightChild = deleteNodeRightChild;

        // ... у детей ссылку на родителя
        if (deleteNodeLeftChild != null)
            deleteNodeLeftChild.Parent = replaceNode;
        if (deleteNodeRightChild != null)
            deleteNodeRightChild.Parent = replaceNode;

        return true;
    }


    public int Count()
    {
        if (Root == null)
            return 0;

        return 1 + recursiveCountNode(Root); // количество узлов в дереве
    }

    private int recursiveCountNode(BSTNode<T> node)
    {
        int counter = 0;
        BSTNode<T> nodeLeft = node.LeftChild;
        BSTNode<T> nodeRight = node.RightChild;

        if (nodeLeft == null && nodeRight == null) {
            return counter;
        } else {
            if (nodeLeft != null)
                counter += 1 + recursiveCountNode(nodeLeft);
            if (nodeRight != null)
                counter += 1 + recursiveCountNode(nodeRight);
        }
        return counter;
    }

    public ArrayList<BSTNode> WideAllNodes()
    {
        ArrayList<BSTNode> allNodes = new ArrayList<>();

        if(Root == null)
            return allNodes;

        ArrayList<BSTNode> currentNodesLevel = new ArrayList<>();
        currentNodesLevel.add(Root);

        while( !currentNodesLevel.isEmpty() )
        {
            ArrayList<BSTNode> nextNodesLevel = new ArrayList<>();
            for(BSTNode node : currentNodesLevel)
            {
                allNodes.add(node);

                if(node.LeftChild != null)
                    nextNodesLevel.add(node.LeftChild);
                if(node.RightChild != null)
                    nextNodesLevel.add(node.RightChild);
            }
            currentNodesLevel = nextNodesLevel;
        }

        return allNodes;
    }

    public ArrayList<BSTNode> DeepAllNodes(int order)
    {
        ArrayList<BSTNode> allNodes = new ArrayList<>();

        if(Root == null)
            return allNodes;

        switch (order)
        {
            case 0:
                recursiveDeepInOrder(Root, allNodes);
                break;
            case 1:
                recursiveDeepPostOrder(Root, allNodes);
                break;
            case 2:
                recursiveDeepPreOrder(Root, allNodes);
                break;
            default: return null;
        }

        return allNodes;
    }

    private void recursiveDeepInOrder(BSTNode node, ArrayList<BSTNode> nodes)
    {
        if(node.LeftChild != null)
            recursiveDeepInOrder(node.LeftChild, nodes);

        nodes.add(node);

        if(node.RightChild != null)
            recursiveDeepInOrder(node.RightChild, nodes);

    }

    private void recursiveDeepPostOrder(BSTNode node, ArrayList<BSTNode> nodes)
    {
        if(node.LeftChild != null)
            recursiveDeepPostOrder(node.LeftChild, nodes);

        if(node.RightChild != null)
            recursiveDeepPostOrder(node.RightChild, nodes);

        nodes.add(node);
    }

    private void recursiveDeepPreOrder(BSTNode node, ArrayList<BSTNode> nodes)
    {
        nodes.add(node);

        if(node.LeftChild != null)
            recursiveDeepPreOrder(node.LeftChild, nodes);

        if(node.RightChild != null)
            recursiveDeepPreOrder(node.RightChild, nodes);
    }

}