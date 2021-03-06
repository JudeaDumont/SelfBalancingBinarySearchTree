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
    //  The pattern needs to re-balance/compensate upon addition of a new node
    //  rotate then add
    //    There are multiple types of self balancing binary search trees. implement aAVL, then red and black trees
    //    This will be a weight balanced tree from the weight at the root, you can tell the levels of each node.
    //    the max level of a given sub tree is log(n) of that subtree where n is the weight as measuring the number of nodes.


    //Amortized overhead or per operation overhead?
    public void add(T data) {
        Node newNode = new Node(data);
        if (root == null) {
            root = newNode;
        } else {
            walk(root, newNode);
        }
    }

    private void reverseWalk(Node current) {
        if(current == root)
        {
        }
        else if(current == current.parent.left)
        {
            ++current.parent.leftWeight;
            reverseWalk(current.parent);
        }
        else if(current == current.parent.right)
        {
            ++current.parent.rightWeight;
            reverseWalk(current.parent);
        }
        else
        {
            System.out.println("ERROR: Reverse Walk Failed to Find a Path To Root!\n\n");
        }
    }

    private void walk(Node curr, Node newNode) {
        if (newNode.compareTo(curr) > 0) {
            if (curr.right == null) {
                curr.right = newNode;
                ++curr.rightWeight;
                newNode.parent = curr;
                reverseWalk(curr);
            } else {
                if(curr.left == null)
                {
                    if (newNode.compareTo(curr.right) < 0) {
                        newNode.left = curr;
                        ++newNode.leftWeight;
                        newNode.parent = curr.parent;
                        curr.parent = newNode;
                        newNode.right = curr.right;
                        ++newNode.rightWeight;
                        curr.right.parent = newNode;
                        curr.right = null;
                        --curr.rightWeight;
                        if(root == curr)
                        {
                            root = newNode;
                        }
                        else
                        {
                            if(newNode.parent.left == curr)
                            {
                                newNode.parent.left = newNode;
                            }
                            else
                            {
                                newNode.parent.right = newNode;
                            }
                            reverseWalk(newNode);
                        }
                    }
                    else
                    {
                        curr.right.left = curr;
                        ++curr.right.leftWeight;
                        --curr.right.rightWeight;
                        curr.right.parent = curr.parent;
                        if (curr.parent != null) {
                            if (curr.parent.right == curr) {
                                curr.parent.right = curr.right;
                                curr.parent = curr.right;
                            } else {
                                curr.parent.left = curr.right;
                                curr.parent = curr.right;
                            }
                        }
                        curr.right.right = newNode;
                        ++curr.right.rightWeight;
                        newNode.parent = curr.right;
                        if(root == curr)
                        {
                            root = curr.right;
                            curr.right = null;
                            --curr.rightWeight;
                            ++root.rightWeight;
                        }
                        else
                        {
                            curr.right = null;
                            --curr.rightWeight;
                            reverseWalk(newNode);
                        }
                    }
                }
                else {
                    walk(curr.right, newNode);
                }
            }
        } else if(newNode.compareTo(curr) < 0) {
            if (curr.left == null) {
                curr.left = newNode;
                ++curr.leftWeight;
                newNode.parent = curr;
                reverseWalk(curr);
            } else {
                if(curr.right == null)
                {
                    if (newNode.compareTo(curr.left) > 0) {
                        newNode.right = curr;
                        ++newNode.rightWeight;
                        newNode.parent = curr.parent;
                        curr.parent = newNode;
                        newNode.left = curr.left;
                        ++newNode.leftWeight;
                        curr.left.parent = newNode;
                        curr.left = null;
                        --curr.leftWeight;
                        if(root == curr)
                        {
                            root = newNode;
                        }
                        else
                        {
                            if(newNode.parent.left == curr)
                            {
                                newNode.parent.left = newNode;
                            }
                            else
                            {
                                newNode.parent.right = newNode;
                            }
                            reverseWalk(newNode);
                        }
                    }
                    else
                    {
                        curr.left.right = curr;
                        ++curr.left.rightWeight;
                        --curr.left.leftWeight;
                        curr.left.parent = curr.parent;
                        if (curr.parent != null) {
                            if (curr.parent.right == curr) {
                                curr.parent.right = curr.left;
                                curr.parent = curr.left;
                            } else {
                                curr.parent.left = curr.left;
                                curr.parent = curr.left;
                            }
                        }
                        curr.left.left = newNode;
                        ++curr.left.leftWeight;
                        newNode.parent = curr.left;
                        if(root == curr)
                        {
                            root = curr.left;
                            curr.left = null;
                            --curr.leftWeight;
                            ++root.leftWeight;
                        }
                        else
                        {
                            curr.left = null;
                            --curr.leftWeight;
                            reverseWalk(newNode);
                        }
                    }
                }
                else {
                    walk(curr.left, newNode);
                }
            }
        }
        else
        {
            // don't do anything, the tree already contains that data, do a count?
            return;
        }
        balance();
    }


    //    Determine left and right weight and if they are out of balance at a given node,
    //    Perform a rotate specific to that unbalance
    private void balance() {
        balance(root);
    }

    private void balance(Node curr) {
        if(curr.rightWeight - curr.leftWeight > 3)
        {
            //get the left most node of the right subtree for rotating
            Node temp = curr.right.left;
            while(temp.left!=null)
            {
                temp = temp.left;
            }
            Node parentTemp = curr.parent;
            curr.parent = temp.parent;
            curr.parent.left = curr;
            Node rightTemp = curr.right;
            curr.right = temp;
            temp.parent = curr;
            if(curr == root)
            {
                root = rightTemp;
            }
            else
            {
                parentTemp.right = rightTemp;
            }
            rightTemp.parent = parentTemp;
            curr.rightWeight = curr.right.rightWeight + 1;
            curr.parent.leftWeight += curr.leftWeight + 1;
            Node weightWalkNode = curr.parent;
            while(weightWalkNode.parent != parentTemp)
            {
                if(weightWalkNode.parent.left == weightWalkNode)
                {
                    weightWalkNode.parent.leftWeight += curr.leftWeight + 1;
                }
                else
                {
                    weightWalkNode.parent.rightWeight += curr.rightWeight + 1;
                }
                weightWalkNode = weightWalkNode.parent;
            }
        }
        else if(curr.leftWeight - curr.rightWeight > 3)
        {
            //get the right most node of the left sub tree for rotating
            Node temp = curr.left.right;
            while(temp.right!=null)
            {
                temp = temp.right;
            }
            Node parentTemp = curr.parent;
            curr.parent = temp.parent;
            curr.parent.right = curr;
            Node leftTemp = curr.left;
            curr.left = temp;
            temp.parent = curr;
            if(curr == root)
            {
                root = leftTemp;
            }
            else
            {
                parentTemp.left = leftTemp;
            }
            leftTemp.parent = parentTemp;
            curr.leftWeight = curr.left.leftWeight + 1;
            curr.parent.rightWeight += curr.rightWeight + 1;
            Node weightWalkNode = curr.parent;
            while(weightWalkNode.parent != parentTemp)
            {
                if(weightWalkNode.parent.right == weightWalkNode)
                {
                    weightWalkNode.parent.rightWeight += curr.rightWeight + 1;
                }
                else
                {
                    weightWalkNode.parent.leftWeight += curr.leftWeight + 1;
                }
                weightWalkNode = weightWalkNode.parent;
            }
        }
        if(curr.right!=null)
        {
            balance(curr.right);
        }
        if(curr.left!=null)
        {
            balance(curr.left);
        }
    }

    @Override
    public String toString() {
        //todo: Missing leaves can cause irrelevant output
        ArrayList<Node> list = new ArrayList<Node>();
        list.add(root);
        StringBuilder s = new StringBuilder();
        int levelAdd = 4;
        int nextLevelAt = 2;
        for(int i = 0; i != list.size(); ++i)
        {
            Node curr = list.get(i);
            if(i == 0)
            {
                s.append(curr.data).append("\n");
            }
            else if(i == nextLevelAt)
            {
                s.append(curr.data).append("\n");
                nextLevelAt += levelAdd;
                levelAdd*=2;
            }
            else
            {
                s.append(curr.data).append(" ");
            }

            Node left = curr.left;
            Node right = curr.right;
            if(left != null)
            {
                list.add(left);
            }
            if(right != null)
            {
                list.add(right);
            }
        }
        return s.toString();
    }
}
