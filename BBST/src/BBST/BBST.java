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
//    the max level of a given sub tree is log(n) of that subtree where n is the weight as measuring the number of nodes.
    //blalala
    //I guess I'll actually do something about this.

    //Amortized overhead or per operation overhead?
    public void add(T data) {
        Node newNode = new Node(data);
        if (root == null) {
            root = newNode;
        } else {
            walk(root, newNode);
            //rotate(newNode);
        }
    }

    //determine left and right weight and if they are out of balance at a given node,
    // perform a rotate specific to that unbalance
    private void reverseWalk(Node current) {
        if(current == root)
        {

        }
        else if(current == current.parent.left)
        {
            ++current.parent.leftWeight;
            if(current.parent.leftWeight - current.parent.rightWeight > 1)
            {
                //some kind of rotation
            }
            reverseWalk(current.parent);
        }
        else if(current == current.parent.right)
        {
            ++current.parent.rightWeight;
            if(Math.abs(current.parent.rightWeight - current.parent.leftWeight) > 1)
            {
                //some kind of rotation
            }
            reverseWalk(current.parent);
        }
        else
        {
            System.out.println("ERROR: Reverse Walk Failed to Find a Path To Root!\n\n");
        }
    }

    //simple walk
    private void walk(Node curr, Node newNode) {
        if (newNode.compareTo(curr) > 0) {
            if (curr.right == null) {
                ++curr.rightWeight;
                curr.right = newNode;
                newNode.parent = curr;
                reverseWalk(curr);
            } else {
                if(curr.left == null)
                {
                    if (newNode.compareTo(curr.right) < 0) {
                        --curr.rightWeight;
                        newNode.left = curr;
                        ++newNode.leftWeight;
                        newNode.parent = curr.parent;
                        curr.parent = newNode;
                        newNode.right = curr.right;
                        curr.right.parent = newNode;
                        curr.right = null;
                        ++newNode.rightWeight;
                        if(root == curr)
                        {
                            root = newNode;
                        }
                        else
                        {
                            newNode.parent.left = newNode;
                            reverseWalk(newNode);
                        }
                    }
                    else
                    {
                        curr.right.left = curr;
                        curr.right.parent = curr.parent;
                        curr.parent.right = curr.right;
                        curr.parent = curr.right;
                        curr.right.right = newNode;
                        newNode.parent = curr.right;
                        --curr.rightWeight;
                        ++curr.right.rightWeight;
                        ++curr.right.leftWeight;
                        if(root == curr)
                        {
                            root = curr.right;
                            curr.right = null;
                        }
                        else
                        {
                            curr.right = null;
                            reverseWalk(newNode);
                        }
                    }
                }
                else {
                    walk(curr.right, newNode);
                }
            }
        } else {
            if (curr.left == null) {
                curr.left = newNode;
                ++curr.leftWeight;
                newNode.parent = curr;
                reverseWalk(curr);
            } else {
                if(curr.right == null)
                {
                    if (newNode.compareTo(curr.left) > 0) {
                        --curr.leftWeight;
                        newNode.right = curr;
                        ++newNode.rightWeight;
                        newNode.parent = curr.parent;
                        curr.parent = newNode;
                        newNode.left = curr.left;
                        curr.left.parent = newNode;
                        curr.left = null;
                        ++newNode.leftWeight;
                        if(root == curr)
                        {
                            root = newNode;
                        }
                        else
                        {
                            newNode.parent.right = newNode;
                            reverseWalk(newNode);
                        }
                    }
                    else
                    {
                        curr.left.right = curr;
                        curr.left.parent = curr.parent;
                        curr.parent.left = curr.left;
                        curr.parent = curr.left;
                        curr.left.left = newNode;
                        newNode.parent = curr.left;
                        --curr.leftWeight;
                        ++curr.left.leftWeight;
                        ++curr.left.rightWeight;
                        if(root == curr)
                        {
                            root = curr.left;
                            curr.left = null;
                        }
                        else
                        {
                            curr.left = null;
                            reverseWalk(newNode);
                        }
                    }
                }
                else {
                    walk(curr.left, newNode);
                }
            }
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
