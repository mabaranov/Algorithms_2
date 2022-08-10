package com.company;

import java.util.*;

class Vertex
{
    public int Value;
    public boolean Hit;
    public Vertex(int val)
    {
        Value = val;
        Hit = false;
    }
}

class SimpleGraph
{
    Vertex [] vertex;
    int [][] m_adjacency;
    int max_vertex;

    public SimpleGraph(int size)
    {
        max_vertex = size;
        m_adjacency = new int [size][size];
        vertex = new Vertex[size];
    }

    public ArrayList<Vertex> DepthFirstSearch(int VFrom, int VTo)
    {
        // Узлы задаются позициями в списке vertex.
        // Возвращается список узлов -- путь из VFrom в VTo.
        // Список пустой, если пути нету.
        Stack<Vertex> nodesPath = new Stack<>();
        searchNextNode(VFrom, nodesPath, VTo);

        return new ArrayList<Vertex>(nodesPath);
    }

    private boolean searchNextNode(int nodeFrom, Stack<Vertex> nodesPath, int nodeTo)
    {
        vertex[nodeFrom].Hit = true;
        nodesPath.push(vertex[nodeFrom]);
        for(int i=0; i<max_vertex; i++)
        {
            if (m_adjacency[nodeFrom][i] == 1 && i == nodeTo) {
                nodesPath.push(vertex[i]);
                vertex[i].Hit = true;
                return true;
            }
        }
        for(int i=0; i<max_vertex; i++)
        {
            if (m_adjacency[nodeFrom][i] == 1 && vertex[i].Hit == false)
            {
                if (searchNextNode(i, nodesPath, nodeTo))
                    return true;
            }
        }

        nodesPath.pop();
        if(nodesPath.empty())
            return false;

        return false;
    }

    public void AddVertex(int value)
    {
        // ваш код добавления новой вершины
        // с значением value
        // в незанятую позицию vertex
        for(int i=0; i < max_vertex; i++)
        {
            if (vertex[i] == null) {
                vertex[i] = new Vertex(value);
                break;
            }
        }
    }

    // здесь и далее, параметры v -- индекс вершины
    // в списке  vertex
    public void RemoveVertex(int v)
    {
        // ваш код удаления вершины со всеми её рёбрами
        vertex[v] = null;
        for(int i=0; i<max_vertex; i++)
        {
            m_adjacency[i][v] = 0;
            m_adjacency[v][i] = 0;
        }
    }

    public boolean IsEdge(int v1, int v2)
    {
        // true если есть ребро между вершинами v1 и v2
        return (m_adjacency[v1][v2] == 1);
    }

    public void AddEdge(int v1, int v2)
    {
        // добавление ребра между вершинами v1 и v2
        m_adjacency[v1][v2] = 1;
        m_adjacency[v2][v1] = 1;
    }

    public void RemoveEdge(int v1, int v2)
    {
        // удаление ребра между вершинами v1 и v2
        m_adjacency[v1][v2] = 0;
        m_adjacency[v2][v1] = 0;
    }
}