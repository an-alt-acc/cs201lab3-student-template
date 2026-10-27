import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
          String[][] traversals = {traversal1.split("-"), traversal2.split("-"), traversal3.split("-")};
          if (!(traversals[0].length == traversals[1].length && traversals[1].length == traversals[2].length)) return "Invalid traversals";
          String[] result = verifyInner(traversals);
          if (result == null) return "Invalid traversals";
          return String.format("Traversal 1 - %s, Traversal 2 - %s, Traversal 3 - %s", result[0], result[1], result[2]);

     }
     private static String[] verifyInner(String[][] traversals) {
          System.out.println(Arrays.deepToString(traversals));
          if (traversals[0].length == 0) return new String[]{};
          if (traversals[0].length == 1 && traversals[0][0].equals(traversals[1][0]) && traversals[1][0].equals(traversals[2][0])) return new String[]{};
          if (traversals[0].length == 2) {
               if (Arrays.deepEquals(traversals[0], traversals[1]) && Arrays.deepEquals(traversals[0], new String[] {traversals[2][1], traversals[2][0]})) return new String[]{};
               if (Arrays.deepEquals(traversals[0], traversals[2]) && Arrays.deepEquals(traversals[0], new String[] {traversals[1][1], traversals[1][0]})) return new String[]{};
               if (Arrays.deepEquals(traversals[2], traversals[1]) && Arrays.deepEquals(traversals[1], new String[] {traversals[0][1], traversals[0][0]})) return new String[]{};
               return null;
          }

          List<String> roots = new ArrayList<>();
          List<Integer> preorderIndices = new ArrayList<>();
          List<Integer> postorderIndices = new ArrayList<>();
          for (int i = 0; i <= 2; i++) {
               for (int j = 0; j <= 2; j++) {
                    if (traversals[i][0].equals(traversals[j][traversals[j].length - 1])) {
                         roots.add(traversals[i][0]);
                         preorderIndices.add(i);
                         postorderIndices.add(j);
                    }
               }
          }
          if (roots.isEmpty()) return null;

          for (int i = 0; i < roots.size(); i++) {
               String root = roots.get(i);
               int preorderIndex = preorderIndices.get(i);
               int postorderIndex = postorderIndices.get(i);

               Set<Integer> indices = new HashSet<>(Set.of(0, 1, 2));
               indices.remove(preorderIndex);
               indices.remove(postorderIndex);
               int inorderIndex = indices.iterator().next();

               int inorderRootElemIndex = Arrays.asList(traversals[inorderIndex]).indexOf(root);
               System.out.printf("%s @ [%d][%d]%n", root, inorderIndex, inorderRootElemIndex);
               if (inorderRootElemIndex == -1) continue;

               int leftBranchLength = inorderRootElemIndex;
               int rightBranchLength = traversals[inorderIndex].length - 1 - leftBranchLength;
               
               String[] preorderLeftBranch = Arrays.copyOfRange(traversals[preorderIndex], 1, 1 + leftBranchLength);
               String[] preorderRightBranch = Arrays.copyOfRange(traversals[preorderIndex], 1 + leftBranchLength, 1 + leftBranchLength + rightBranchLength);
               String[] postorderLeftBranch = Arrays.copyOfRange(traversals[postorderIndex], 0, leftBranchLength);
               String[] postorderRightBranch = Arrays.copyOfRange(traversals[postorderIndex], leftBranchLength, leftBranchLength + rightBranchLength);
               String[] inorderLeftBranch = Arrays.copyOfRange(traversals[inorderIndex], 0, leftBranchLength);
               String[] inorderRightBranch = Arrays.copyOfRange(traversals[inorderIndex], inorderRootElemIndex + 1, inorderRootElemIndex + rightBranchLength + 1);

               String[] leftBranchResult = verifyInner(new String[][] {preorderLeftBranch, postorderLeftBranch, inorderLeftBranch});
               String[] rightBranchResult = verifyInner(new String[][] {preorderRightBranch, postorderRightBranch, inorderRightBranch});
               if (leftBranchResult == null || (leftBranchResult.length != 0 && !Arrays.deepEquals(leftBranchResult, new String[] {"Preorder", "Postorder", "Inorder"}))) continue;
               if (rightBranchResult == null || (rightBranchResult.length != 0 && !Arrays.deepEquals(rightBranchResult, new String[] {"Preorder", "Postorder", "Inorder"}))) continue;

               String traversal1Type = preorderIndex == 0 ? "Preorder" : inorderIndex == 0 ? "Inorder" : "Postorder";
               String traversal2Type = preorderIndex == 1 ? "Preorder" : inorderIndex == 1 ? "Inorder" : "Postorder";
               String traversal3Type = preorderIndex == 2 ? "Preorder" : inorderIndex == 2 ? "Inorder" : "Postorder";
               return new String[] {traversal1Type, traversal2Type, traversal3Type};
          }
          return null;
     }
}
