package BBST;

import java.util.ArrayList;

public class BBST<T extends Comparable> {
    private class Node implements Comparable {
        public Node left = null;
        public Node right = null;
        public Node parent = null;
        public long leftWeight = 0;
        public long rightWeight = 0;
        public T data = null;
        public boolean clean = false;

        Node(T data) {
            this.data = data;
        }

        @Override
        public int compareTo(Object o) {
            Integer beforeOrAfter = null;
            try {
                beforeOrAfter = this.data.compareTo(((Node) o).data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return beforeOrAfter;
        }
    }
    public Node root = null;
    public ArrayList<Node> unorderedNodeList = new ArrayList<Node>();

    private void weightDistribute(Node current) {
        if (current != null) {
            if (current.left != null) {
                current.leftWeight = current.left.rightWeight + current.left.leftWeight + 1;
            }
            if (current.right != null) {
                current.rightWeight = current.right.rightWeight + current.right.leftWeight + 1;
            }
            weightDistribute(current.parent);
        }
    }
    //The pattern needs to rebalance/compensate upon addition of a new node
    //rotate then add
//    There are multiple types of self balancing binary search trees. implement aAVL, then red and black trees
//    This will be a weight balanced tree from the weight at the root, you can tell the levels of each node.

    public void add(T data) {
        if(root == null){
            root = new Node(data);
        }
        else{

        }
    }

    @Override
    public String toString() {
        String aggregate = "";
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }

        while (current != null) {
            if (current.left != null && !current.left.clean) {
                current = current.left;
            } else {
                if (!current.clean) {
                    aggregate = aggregate.concat(current.toString() + "\n");
                    current.clean = true;
                }
                if (current.right != null && !current.right.clean) {
                    current = current.right;
                } else if ((current.right == null || current.right.clean) && (current.left == null || current.left.clean)) {
                    current = current.parent;
                }
            }

        }
        for (Node node : unorderedNodeList) {
            node.clean = false;
        }

        return aggregate;
    }
}
