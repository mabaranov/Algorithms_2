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

    ////////////////
    private int getNewFromIndex(int vFrom) {
        for (int i = 0; i < vertex.length; i++){
            if (vertex[i] != null && IsEdge(vFrom, i) && vertex[i].Hit == false) {
                return i;
            }
        }
        return -1;
    }

    private int getVertexIndex(Vertex v){
        for (int i = 0; i < vertex.length; i++){
            if (vertex[i] != null && vertex[i].Value == v.Value) return i;
        }
        return -1;
    }
    public ArrayList<Vertex> BreadthFirstSearch(int VFrom, int VTo) {
        // Узлы задаются позициями в списке vertex.
        // Возвращается список узлов -- путь из VFrom в VTo.
        // Список пустой, если пути нету.
        Queue<Vertex> vertexList = new LinkedList<>();
        int[] parents = new int[vertex.length];
        ArrayList<Vertex> path = new ArrayList<>();
        for (Vertex vert : vertex){
            if (vert != null) vert.Hit = false;
        }
        vertex[VFrom].Hit = true;
        parents[VFrom] = VFrom;
        path = bfs(VFrom, VFrom, VTo, vertexList, parents, path);
        return path;
    }

    private ArrayList<Vertex> bfs(int start, int vFrom, int vTo, Queue<Vertex> vertexList, int[] parents, ArrayList<Vertex> path){
        int nearVertexIndex = getNewFromIndex(vFrom); // ближайшая непосещённая вершина
        if (nearVertexIndex == vTo) {
            parents[vTo] = vFrom;
            return getPath(start, vTo, parents, path);
        }
        if (nearVertexIndex >= 0) { // если такая вершина есть, но не равно искомой
            vertex[nearVertexIndex].Hit = true;
            parents[nearVertexIndex] = vFrom;
            vertexList.add(vertex[nearVertexIndex]);
            return bfs(start, vFrom, vTo, vertexList, parents, path);
        }
        if (vertexList.isEmpty()) {
            return new ArrayList<>(); // путь не найден
        }
        Vertex newFromVertex = vertexList.remove(); //если нет непосещённых вершин
        int newFromIndex = getVertexIndex(newFromVertex);
        return bfs(start, newFromIndex, vTo, vertexList, parents, path);
    }

    private ArrayList<Vertex> getPath(int start, int vTo, int[] parents, ArrayList<Vertex> path){
        Vertex v = vertex[vTo];
        while (v != vertex[start]){
            path.add(v);
            v = vertex[parents[vTo]];
            vTo = parents[vTo];
        }
        path.add(vertex[start]);
        Collections.reverse(path);
        return path;
    }
    ////////////////



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