package ua.com.alevel.binarytree.tree;

import lombok.Getter;

public class Tree {
    @Getter
    private TreeNode rootNode;
    private static int count;

    public Tree() {
        this.rootNode = null;
    }

    public TreeNode findByValue(int value) {
        if (rootNode == null) {
            return null;
        }
        TreeNode currentNode = rootNode;
        while (value != currentNode.getValue()) {
            if (value < currentNode.getValue()) {
                currentNode = currentNode.getLeftChild();
            } else {
                currentNode = currentNode.getRightChild();
            }
            if (currentNode == null) {
                return null;
            }
        }
        return currentNode;
    }

    public void insertNode(int value) {
        TreeNode newNode = new TreeNode(value);
        if (rootNode == null) {
            rootNode = newNode;
            return;
        }
        TreeNode currentNode = rootNode;
        while (value != currentNode.getValue()) {
            if (value < currentNode.getValue()) {
                TreeNode leftChild = currentNode.getLeftChild();
                if (leftChild == null) {
                    currentNode.setLeftChild(newNode);
                    return;
                }
                currentNode = leftChild;
            } else {
                TreeNode rightChild = currentNode.getRightChild();
                if (rightChild == null) {
                    currentNode.setRightChild(newNode);
                    return;
                }
                currentNode = rightChild;
            }
        }
    }

    public boolean deleteNode(int value) {
        if (rootNode == null) {
            return false;
        }
        boolean isLeftChild = true;
        TreeNode currentNode = rootNode;
        TreeNode parentNode = rootNode;
        while (value != currentNode.getValue()) {
            parentNode = currentNode;
            if (value < currentNode.getValue()) {
                currentNode = currentNode.getLeftChild();
                isLeftChild = true;
            } else {
                currentNode = currentNode.getRightChild();
                isLeftChild = false;
            }
            if (currentNode == null) {
                return false;
            }
        }

        if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {
            if (currentNode == rootNode) {
                rootNode = null;
            } else if (isLeftChild) {
                parentNode.setLeftChild(null);
            } else {
                parentNode.setRightChild(null);
            }
        } else if (currentNode.getLeftChild() == null) {
            if (currentNode == rootNode) {
                rootNode = rootNode.getRightChild();
            } else if (isLeftChild) {
                parentNode.setLeftChild(currentNode.getRightChild());
            } else {
                parentNode.setRightChild(currentNode.getRightChild());
            }
        } else if (currentNode.getRightChild() == null) {
            if (currentNode == rootNode) {
                rootNode = rootNode.getLeftChild();
            } else if (isLeftChild) {
                parentNode.setLeftChild(currentNode.getLeftChild());
            } else {
                parentNode.setRightChild(currentNode.getLeftChild());
            }
        } else {
            TreeNode heir = findHeir(currentNode);
            if (currentNode == rootNode) {
                rootNode = heir;
                heir.setLeftChild(rootNode.getLeftChild());
            } else if (isLeftChild) {
                parentNode.setLeftChild(heir);
                heir.setLeftChild(currentNode.getLeftChild());
            } else {
                parentNode.setRightChild(heir);
                heir.setLeftChild(currentNode.getLeftChild());
            }
        }
        return true;
    }

    private TreeNode findHeir(TreeNode node) {
        TreeNode parentNode = node;
        TreeNode heirNode = node;
        TreeNode currentNode = node.getRightChild();
        while (currentNode != null) {
            parentNode = heirNode;
            heirNode = currentNode;
            currentNode = heirNode.getLeftChild();
        }
        if (heirNode != node.getRightChild()) {
            parentNode.setLeftChild(heirNode.getRightChild());
            heirNode.setRightChild(node.getRightChild());
        }
        return heirNode;
    }

    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        int Left = maxDepth(root.getLeftChild());
        int Right = maxDepth(root.getRightChild());
        return Math.max(Left, Right) + 1;
    }

}
