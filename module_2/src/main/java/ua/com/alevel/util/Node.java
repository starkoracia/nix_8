package ua.com.alevel.util;

import java.util.Comparator;

public class Node implements Comparator<Node> {
    private int index;
    private int cost;

    public Node()
    {
    }

    public Node(int index, int cost)
    {
        this.index = index;
        this.cost = cost;
    }

    @Override
    public int compare(Node node1, Node node2)
    {
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        return 0;
    }

    public int getIndex() {
        return index;
    }

    public int getCost() {
        return cost;
    }
}
