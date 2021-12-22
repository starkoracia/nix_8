package ua.com.alevel.util;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {
    private int dist[];
    private Set<Integer> integerSet;
    private PriorityQueue<Node> pq;
    private int vertices;
    List<List<Node>> listListNode;

    public Graph(int vertices) {
        this.vertices = vertices;
        dist = new int[vertices];
        integerSet = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(vertices, new Node());
    }

    public void dijkstra(List<List<Node>> listListNode, int startNode) {
        this.listListNode = listListNode;

        for (int i = 0; i < vertices; i++)
            dist[i] = Integer.MAX_VALUE;

        pq.add(new Node(startNode, 0));

        dist[startNode] = 0;
        while (integerSet.size() != vertices) {
            if (pq.isEmpty()) {
                return;
            }
            int u = pq.remove().getIndex();
            integerSet.add(u);
            e_Neighbours(u);
        }
    }

    private void e_Neighbours(int u) {
        int edgeDistance = -1;
        int newDistance = -1;

        for (int i = 0; i < listListNode.get(u).size(); i++) {
            Node vertex = listListNode.get(u).get(i);

            if (!integerSet.contains(vertex.getIndex())) {
                edgeDistance = vertex.getCost();
                newDistance = dist[u] + edgeDistance;

                if (newDistance < dist[vertex.getIndex()])
                    dist[vertex.getIndex()] = newDistance;

                pq.add(new Node(vertex.getIndex(), dist[vertex.getIndex()]));
            }
        }
    }

    public int[] getDist() {
        return dist;
    }
}
