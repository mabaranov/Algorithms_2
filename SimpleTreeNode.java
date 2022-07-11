package com.company;

import java.util.*;

public class SimpleTreeNode<T>
{
    public T NodeValue; // значение в узле
    public SimpleTreeNode<T> Parent; // родитель или null для корня
    public List<SimpleTreeNode<T>> Children; // список дочерних узлов или null

    public SimpleTreeNode(T val, SimpleTreeNode<T> parent)
    {
        NodeValue = val;
        Parent = parent;
        Children = null;
    }
}

class SimpleTree<T>
{
    public SimpleTreeNode<T> Root; // корень, может быть null

    public SimpleTree(SimpleTreeNode<T> root)
    {
        Root = root;
    }

    public void AddChild(SimpleTreeNode<T> ParentNode, SimpleTreeNode<T> NewChild)
    {
        // ваш код добавления нового дочернего узла существующему ParentNode
        if ( Root == null ) {
            Root = NewChild;
            return;
        }

        if( ParentNode.Children == null ) {
            ParentNode.Children = new ArrayList<>();
        }

        ParentNode.Children.add(NewChild);
        NewChild.Parent = ParentNode;
    }

    public void DeleteNode(SimpleTreeNode<T> NodeToDelete)
    {
        // ваш код удаления существующего узла NodeToDelete
        if(NodeToDelete == Root)
            return;

        NodeToDelete.Parent.Children.remove(NodeToDelete);
        if(NodeToDelete.Parent.Children.isEmpty())
            NodeToDelete.Parent.Children = null;
    }

    public List<SimpleTreeNode<T>> GetAllNodes()
    {
        // ваш код выдачи всех узлов дерева в определённом порядке
        ArrayList<SimpleTreeNode<T>> allNodes = new ArrayList<>();

        if( Root == null )
            return allNodes;

        return recursiveNodes(Root, allNodes);
    }

    private List<SimpleTreeNode<T>> recursiveNodes(SimpleTreeNode<T> node, List<SimpleTreeNode<T>> allNodes)
    {
        allNodes.add(node);

        if( node.Children == null )
            return allNodes;

        for(SimpleTreeNode<T> element : node.Children) {
            recursiveNodes(element, allNodes);
        }

        return allNodes;
    }

    public List<SimpleTreeNode<T>> FindNodesByValue(T val)
    {
        // ваш код поиска узлов по значению
        ArrayList<SimpleTreeNode<T>> foundNodes = new ArrayList<SimpleTreeNode<T>>();

        if( Root == null ) return foundNodes;

        return recursiveFind(Root, foundNodes, val);
    }

    private List<SimpleTreeNode<T>> recursiveFind(SimpleTreeNode<T> node, List<SimpleTreeNode<T>> foundNodes, T val)
    {
        if( node.NodeValue == val )
            foundNodes.add(node);

        if( node.Children == null )
            return foundNodes;

        for(SimpleTreeNode<T> element : node.Children) {
           recursiveFind(element, foundNodes, val);
        }

        return foundNodes;
    }

    public void MoveNode(SimpleTreeNode<T> OriginalNode, SimpleTreeNode<T> NewParent)
    {
        // ваш код перемещения узла вместе с его поддеревом --
        // в качестве дочернего для узла NewParent
        DeleteNode(OriginalNode);
        AddChild(NewParent, OriginalNode);
    }

    public int Count()
    {
        // количество всех узлов в дереве
        if( Root == null ) return 0;

        return recursiveCount(Root, false);
    }

    public int LeafCount()
    {
        // количество листьев в дереве
        if( Root == null ) return 0;

        return recursiveCount(Root, true);
    }

    private int recursiveCount(SimpleTreeNode<T> node, boolean countOnlyLeaf)
    {
        if( node.Children == null )
            return 1;

        int count = 0;
        for(SimpleTreeNode<T> element: node.Children) {
            count += recursiveCount(element, countOnlyLeaf);
        }

        return countOnlyLeaf ? count : count+1 ;
    }
}