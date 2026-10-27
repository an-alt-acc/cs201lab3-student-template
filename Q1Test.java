import java.util.*;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3){
          int[][] traversals = {
               Arrays.stream(traversal1.split("-")).mapToInt(Integer::parseInt).toArray(),
               Arrays.stream(traversal2.split("-")).mapToInt(Integer::parseInt).toArray(),
               Arrays.stream(traversal3.split("-")).mapToInt(Integer::parseInt).toArray(),
          };
          if (!(traversals[0].length == traversals[1].length && traversals[1].length == traversals[2].length)) return "Invalid traversals";

          String[] out = new String[3];

          int[] inorder = null;
          int inorderIndex = 0;
          outer: for (int[] traversal : traversals) {
               for (int j = 0; j < traversal.length - 1; j++) {
                    if (traversal[j] >= traversal[j+1]) {
                         inorderIndex++;
                         continue outer;
                    }
               }
               inorder = traversal;
               out[inorderIndex] = "Inorder";
               break;
          }
          if (inorder == null) return "Invalid traversals";

          int[] traversalA = traversals[inorderIndex == 0 ? 2 : 0];
          int[] traversalB = traversals[inorderIndex == 1 ? 2 : 1];
          int[] preorder = null;
          int[] postorder = null;
          if (traversalA[0] == traversalB[traversalB.length - 1]) {
               preorder = traversalA;
               postorder = traversalB;
               out[inorderIndex == 0 ? 2 : 0] = "Preorder";
               out[inorderIndex == 1 ? 2 : 1] = "Postorder";
          } else if (traversalA[traversalB.length - 1] == traversalB[0]) {
               preorder = traversalB;
               postorder = traversalA;
               out[inorderIndex == 1 ? 2 : 1] = "Preorder";
               out[inorderIndex == 0 ? 2 : 0] = "Postorder";
          }
          if (preorder == null) return "Invalid traversals";

          if (!verifyInner(inorder, preorder, postorder)) return "Invalid traversals";
          return String.format("Traversal 1 - %s, Traversal 2 - %s, Traversal 3 - %s%n", out[0], out[1], out[2]);

     }
     private static boolean verifyInner(int[] inorder, int[] preorder, int[] postorder) {
          if (inorder.length == 0) return true;
          if (inorder.length == 1) {
               return inorder[0] == preorder[0] && preorder[0] == postorder[0];
          }

          int root = preorder[0];
          int inorderRootElemIndex = -1;
          for (int j = 0; j < inorder.length; j++) {
               if (inorder[j] == root) {
                    inorderRootElemIndex = j;
                    break;
               }
          }
          if (inorderRootElemIndex == -1) return false;

          int leftBranchLength = inorderRootElemIndex;
          int rightBranchLength = inorder.length - 1 - leftBranchLength;
          
          int[] preorderLeftBranch = Arrays.copyOfRange(preorder, 1, 1 + leftBranchLength);
          int[] preorderRightBranch = Arrays.copyOfRange(preorder, 1 + leftBranchLength, 1 + leftBranchLength + rightBranchLength);
          int[] postorderLeftBranch = Arrays.copyOfRange(postorder, 0, leftBranchLength);
          int[] postorderRightBranch = Arrays.copyOfRange(postorder, leftBranchLength, leftBranchLength + rightBranchLength);
          int[] inorderLeftBranch = Arrays.copyOfRange(inorder, 0, leftBranchLength);
          int[] inorderRightBranch = Arrays.copyOfRange(inorder, inorderRootElemIndex + 1, inorderRootElemIndex + rightBranchLength + 1);

          System.out.println(Arrays.deepToString(new int[][] {inorderLeftBranch, preorderLeftBranch, postorderLeftBranch}));
          System.out.println(Arrays.deepToString(new int[][] {inorderRightBranch, preorderRightBranch, postorderRightBranch}));
          System.out.println();

          if (preorderLeftBranch.length != 0 && preorderLeftBranch[0] >= root) return false;
          if (preorderRightBranch.length != 0 && preorderRightBranch[0] <= root) return false;

          boolean leftBranchResult = verifyInner(inorderLeftBranch, preorderLeftBranch, postorderLeftBranch);
          boolean rightBranchResult = verifyInner(inorderRightBranch, preorderRightBranch, postorderRightBranch);
          return leftBranchResult && rightBranchResult;
     }
}
