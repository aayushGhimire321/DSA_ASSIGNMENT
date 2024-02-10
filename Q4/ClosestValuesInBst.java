package Q4;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class ClosestValuesInBst {
    public List<Integer> closestValues(TreeNode root, double target, int x) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Double.compare(Math.abs(a - target), Math.abs(b - target)));

        inorderTraversal(root, pq);

        while (x-- > 0 && !pq.isEmpty()) {
            result.add(pq.poll());
        }

        return result;
    }

    private void inorderTraversal(TreeNode node, PriorityQueue<Integer> pq) {
        if (node == null) {
            return;
        }

        inorderTraversal(node.left, pq);
        pq.offer(node.val);
        inorderTraversal(node.right, pq);
    }

    public static void main(String[] args) {
        ClosestValuesInBst solution = new ClosestValuesInBst();

        // Create the binary search tree
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        // Target value
        double target = 3.8;

        // Number of closest values required
        int x = 2;

        // Find the closest values
        List<Integer> closest = solution.closestValues(root, target, x);

        // Output the closest values
        System.out.println("Closest values to " + target + " with x = " + x + ": " + closest);
    }
}
