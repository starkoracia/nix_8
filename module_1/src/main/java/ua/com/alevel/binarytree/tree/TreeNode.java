package ua.com.alevel.binarytree.tree;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class TreeNode implements Comparable<TreeNode> {
    private int value;
    private TreeNode leftChild;
    private TreeNode rightChild;

    public TreeNode(int value) {
        this.value = value;
    }

    public void printNode() {
        System.out.println(" Выбранный узел имеет значение :" + value);
    }

    @Override
    public int compareTo(TreeNode node) {
        return Integer.compare(value, node.value);
    }
}
