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
          @SuppressWarnings("unchecked")
          List<Integer>[] traversals = (List<Integer>[]) new List[] {
               Arrays.stream(traversal1.split("-")).map(Integer::parseInt).toList(),
               Arrays.stream(traversal2.split("-")).map(Integer::parseInt).toList(),
               Arrays.stream(traversal3.split("-")).map(Integer::parseInt).toList(),
          };
          if (!(traversals[0].size() == traversals[1].size() && traversals[1].size() == traversals[2].size())) return "Invalid traversals";

          String[] out = new String[3];

          List<Integer> inorder = null;
          int inorderIndex = 0;
          outer: for (List<Integer> traversal : traversals) {
               for (int j = 0; j < traversal.size() - 1; j++) {
                    if (traversal.get(j) >= traversal.get(j+1)) {
                         inorderIndex++;
                         continue outer;
                    }
               }
               inorder = traversal;
               out[inorderIndex] = "Inorder";
               break;
          }
          if (inorder == null) return "Invalid traversals";

          List<Integer> traversalA = traversals[inorderIndex == 0 ? 2 : 0];
          List<Integer> traversalB = traversals[inorderIndex == 1 ? 2 : 1];
          List<Integer> preorder = null;
          List<Integer> postorder = null;
          if (traversalA.get(0) == traversalB.get(traversalB.size() - 1)) {
               preorder = traversalA;
               postorder = traversalB;
               out[inorderIndex == 0 ? 2 : 0] = "Preorder";
               out[inorderIndex == 1 ? 2 : 1] = "Postorder";
          } else if (traversalA.get(traversalA.size() - 1) == traversalB.get(0)) {
               preorder = traversalB;
               postorder = traversalA;
               out[inorderIndex == 1 ? 2 : 1] = "Preorder";
               out[inorderIndex == 0 ? 2 : 0] = "Postorder";
          }
          if (preorder == null) return "Invalid traversals";

          if (!verifyInner(inorder, preorder, postorder)) return "Invalid traversals";
          return String.format("Traversal 1 - %s, Traversal 2 - %s, Traversal 3 - %s", out[0], out[1], out[2]);

     }
     private static boolean verifyInner(List<Integer> inorder, List<Integer> preorder, List<Integer> postorder) {
          if (inorder.isEmpty()) return true;
          if (inorder.size() == 1) {
               return inorder.get(0) == preorder.get(0) && preorder.get(0) == postorder.get(0);
          }

          int root = preorder.get(0);
          int inorderRootElemIndex = Collections.binarySearch(inorder, root);
          if (inorderRootElemIndex < 0) return false;

          int leftBranchLength = inorderRootElemIndex;
          int rightBranchLength = inorder.size() - 1 - leftBranchLength;
          
          List<Integer> preorderLeftBranch = preorder.subList(1, 1 + leftBranchLength);
          List<Integer> preorderRightBranch = preorder.subList(1 + leftBranchLength, 1 + leftBranchLength + rightBranchLength);
          List<Integer> postorderLeftBranch = postorder.subList(0, leftBranchLength);
          List<Integer> postorderRightBranch = postorder.subList(leftBranchLength, leftBranchLength + rightBranchLength);
          List<Integer> inorderLeftBranch = inorder.subList(0, leftBranchLength);
          List<Integer> inorderRightBranch = inorder.subList(inorderRootElemIndex + 1, inorderRootElemIndex + rightBranchLength + 1);
          if (!preorderLeftBranch.isEmpty() && preorderLeftBranch.get(0) >= root) return false;
          if (!preorderRightBranch.isEmpty() && preorderRightBranch.get(0) <= root) return false;

          boolean leftBranchResult = verifyInner(inorderLeftBranch, preorderLeftBranch, postorderLeftBranch);
          boolean rightBranchResult = verifyInner(inorderRightBranch, preorderRightBranch, postorderRightBranch);
          return leftBranchResult && rightBranchResult;
     }
}
