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


    public ArrayList<Vertex> BreadthFirstSearch(int VFrom, int VTo)
    {
        // Узлы задаются позициями в списке vertex.
        // Возвращается список узлов -- путь из VFrom в VTo.
        // Список пустой, если пути нету.

        ArrayList<Vertex> path = new ArrayList<>();
        int [] predecessors = new int[max_vertex];

        if( BFS(VFrom, VTo, predecessors) )
        {
            int t = VTo;
            path.add(vertex[t]);
            while(t != VFrom)
            {
                path.add(vertex[predecessors[t]]);
                t = predecessors[t];
            }
            Collections.reverse(path);
        }

        return path;
    }

    private boolean BFS(int VFrom, int VTo, int [] predecessors)
    {
        ArrayDeque<Integer> nodesPath = new ArrayDeque<>();

        vertex[VFrom].Hit = true;
        nodesPath.addLast( VFrom );
        predecessors[VFrom] = VFrom;

        if(VFrom == VTo)
            return true;

        int v2;

        while( !nodesPath.isEmpty() )
        {
            int v1 = nodesPath.pop();

            while ((v2 = getIndexUnvisitedNode(v1)) != -1)
            {
                vertex[v2].Hit = true;
                nodesPath.addLast(v2);
                predecessors[v2] = v1;

                if(v2 == VTo)
                    return true;
            }
        }
        return false;
    }

    private int getIndexUnvisitedNode(int n)
    {
        for(int i=0; i<max_vertex; i++)
        {
            if(m_adjacency[n][i]==1 && vertex[i].Hit == false)
            {
                return i;
            }
        }
        return -1;
    }


    public ArrayList<Vertex> DepthFirstSearch(int VFrom, int VTo)
    {
        // Узлы задаются позициями в списке vertex.
        // Возвращается список узлов -- путь из VFrom в VTo.
        // Список пустой, если пути нету.
        Stack<Vertex> nodesPath = new Stack<>();
        recursiveDFS(VFrom, nodesPath, VTo);

        return new ArrayList<Vertex>(nodesPath);
    }

    private boolean recursiveDFS(int nodeFrom, Stack<Vertex> nodesPath, int nodeTo)
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
                if (recursiveDFS(i, nodesPath, nodeTo))
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