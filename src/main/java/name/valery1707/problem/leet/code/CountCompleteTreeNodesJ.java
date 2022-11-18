package name.valery1707.problem.leet.code;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * <h1>222. Count Complete Tree Nodes</h1>
 * Given the {@code root} of a <b>complete</b> binary tree, return the number of the nodes in the tree.
 * <p>
 * According to <a href="http://en.wikipedia.org/wiki/Binary_tree#Types_of_binary_trees">Wikipedia</a>, every level, except possibly the last,
 * is completely filled in a complete binary tree, and all nodes in the last level are as far left as possible.
 * It can have between {@code 1} and {@code 2^h} nodes inclusive at the last level {@code h}.
 * <p>
 * Design an algorithm that runs in less than {@code O(n)} time complexity.
 *
 * <h3>Constraints</h3>
 * <ul>
 * <li>The number of nodes in the tree is in the range {@code [0, 5 * 10^4]}</li>
 * <li>{@code 0 <= Node.val <= 5 * 10^4}</li>
 * <li>The tree is guaranteed to be <b>complete</b></li>
 * </ul>
 */
public interface CountCompleteTreeNodesJ {

    int countNodes(TreeNode root);

    enum Implementation implements CountCompleteTreeNodesJ {
        /**
         * Very slow on big trees.
         */
        naiveRecurse {
            @Override
            public int countNodes(TreeNode root) {
                return root == null ? 0 : 1 + countNodes(root.left) + countNodes(root.right);
            }
        },
        heightCalculation {
            @Override
            public int countNodes(TreeNode root) {
                if (root == null) {
                    return 0;
                }

                int hL = 0;
                var curr = root;
                while (curr != null) {
                    hL++;
                    curr = curr.left;
                }

                int hR = 0;
                curr = root;
                while (curr != null) {
                    hR++;
                    curr = curr.right;
                }

                if (hL == hR) {
                    //If height only left corner equals with height only right corner - then we are known the count of elements
                    return (int) Math.pow(2.0, hL) - 1;
                } else {
                    //Else we need go deeper
                    return 1 + countNodes(root.left) + countNodes(root.right);
                }
            }
        },
        /**
         * Same as {@link #heightCalculation} but shorter by using lambdas.
         * Interesting that it runs faster too.
         */
        heightCalculationLambda {
            @Override
            public int countNodes(TreeNode root) {
                if (root == null) {
                    return 0;
                }
                int hL = height(root, it -> it.left);
                int hR = height(root, it -> it.right);
                if (hL == hR) {
                    return (int) Math.pow(2.0, hL) - 1;
                } else {
                    return 1 + countNodes(root.left) + countNodes(root.right);
                }
            }

            private int height(TreeNode node, Function<TreeNode, TreeNode> mapper) {
                int h = 0;
                while (node != null) {
                    h++;
                    node = mapper.apply(node);
                }
                return h;
            }
        },
    }

    @Nullable
    static TreeNode build(int height, int lastLineMod) {
        var size = (int) Math.pow(2, height) - 1 + lastLineMod;
        var items = IntStream.rangeClosed(1, size).toArray();
        return build(items);
    }

    @Nullable
    static TreeNode build(int[] source) {
        List<TreeNode> nodes = new ArrayList<>(source.length);
        int last = -1;
        for (int item : source) {
            var node = new TreeNode(item);
            if (last >= 0) {
                var parent = nodes.get(last);
                if (parent.left == null) {
                    parent.left = node;
                } else {
                    parent.right = node;
                    last++;
                }
            } else {
                last++;
            }
            nodes.add(node);
        }
        return nodes.isEmpty() ? null : nodes.get(0);
    }

    /**
     * Definition for a binary tree node.
     */
    class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}

        TreeNode(int val) {this.val = val;}

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

    }

}
