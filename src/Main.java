
public class Main {
    public static  int Suites_Run = 0 ;
    public static int Suites_Passed = 0 ;

    public static int Tests_Run = 0 ;
    public static int Tests_Passed = 0 ;

    public static void start_suite(String name)
    {
        Suites_Run ++ ;
        System.out.print("\n" + name +"\n\n") ;
    }

    public static void end_suite()
    {
        if (Tests_Run == Tests_Passed)
        {
            Suites_Passed ++ ;
            System.out.print("All tests have passed\n" + "*\n");
        }
        else
        {
            System.out.print("Some Tests Failed : " + Tests_Passed + "/" + Tests_Run + "\n*\n");
        }
        Tests_Run = 0;
        Tests_Passed = 0 ;
    }

    public static void assertEquals(String got , String expected)
    {
        Tests_Run ++ ;
        if (got.equals(expected))
        {
            Tests_Passed ++ ;
            System.out.print("Test "+ Tests_Run + " Passed\n");
        }
        else
        {
            System.out.print("Test " + Tests_Run + " Failed\n" + "You got : " + got + " You expected : " + expected + "\n");
        }
    }
    public static void assertEquals(boolean got , boolean expected)
    {
        Tests_Run ++ ;
        if (got == expected)
        {
            Tests_Passed ++ ;
            System.out.print("Test "+ Tests_Run + " Passed\n");
        }
        else
        {
            System.out.print("Test " + Tests_Run + " Failed\n" + "You got : " + got + " You expected : " + expected + "\n");
        }
    }

    public static void assertEquals(Integer got , Integer expected)
    {
        Tests_Run ++ ;
        if (got == expected)
        {
            Tests_Passed ++ ;
            System.out.print("Test "+ Tests_Run + " Passed\n");
        }
        else
        {
            System.out.print("Test " + Tests_Run + " Failed\n" + "You got : " + got + " You expected : " + expected + "\n");
        }
    }

    public static void assertEquals(Object got , Object expected)
    {
        Tests_Run ++ ;
        if (got == null && expected == null)
        {
            Tests_Passed ++ ;
            System.out.print("Test "+ Tests_Run + " Passed\n");
        }
        else
        if (got != null && got.equals(expected))
        {
            Tests_Passed ++ ;
            System.out.print("Test "+ Tests_Run + " Passed\n");
        }
        else
        {
            System.out.print("Test " + Tests_Run + " Failed\n" + "You got : " + got + " You expected : " + expected + "\n");
        }
    }

    public static void assertEquals(BinaryNode<Integer> got, BinaryNode<Integer> expected) {
        Tests_Run ++ ;
        if (got == null && expected == null)
        {
            Tests_Passed ++ ;
            System.out.print("Test "+ Tests_Run + " Passed\n");
        }
        else
        if (got != null && got.data.equals(expected.data))
        {
            Tests_Passed ++ ;
            System.out.print("Test "+ Tests_Run + " Passed\n");
        }
        else
        {
            System.out.print("Test " + Tests_Run + " Failed\n" + "You got : " + got + " You expected : " + expected + "\n");
        }
    }

    public static void main(String[] args) {
        start_suite("BinaryNode");
        BinaryNode<Integer> node = new BinaryNode<>(5);
        assertEquals(node.data, 5);
        assertEquals(node.left, null);
        assertEquals(node.right, null);
        assertEquals(node.toString(), "[N]<-[5]->[N]");

        BinaryNode<Integer> left = new BinaryNode<>(3);
        BinaryNode<Integer> right = new BinaryNode<>(7);
        BinaryNode<Integer> node2 = new BinaryNode<>(5, left, right);
        assertEquals(node2.data, 5);
        assertEquals(node2.left, left);
        assertEquals(node2.right, right);
        assertEquals(node2.toString(), "[3]<-[5]->[7]");

        end_suite();

        start_suite("BinarySearchTree");
        BST<Integer> tree = new BST<>();

        StringBuilder sb = new StringBuilder();
        sb.append("[");


        int NUM_ELEMENTS = 200;
        java.util.Random rand = new java.util.Random(100);
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            int rnd = rand.nextInt(100);
            tree.insert(rnd);
            sb.append(rnd);
            if (i < NUM_ELEMENTS - 1) {
                sb.append(",");
            }
        }
        System.out.println(sb.toString() + "]");
        System.out.println(tree.toString());

        tree.delete(50);
        System.out.println("------------------------------------------------------------------");
        System.out.println(tree);

        tree.delete(63);
        System.out.println("------------------------------------------------------------------");
        System.out.println(tree);

        tree.delete(63);

        tree.delete(53);
        System.out.println("------------------------------------------------------------------");
        System.out.println(tree);

        tree.delete(15);
        System.out.println("------------------------------------------------------------------");
        System.out.println(tree);

        tree.delete(10);
        System.out.println("------------------------------------------------------------------");
        System.out.println(tree);

        tree.delete(55);
        System.out.println("------------------------------------------------------------------");
        System.out.println(tree);

        tree.delete(52);
        System.out.println("------------------------------------------------------------------");
        System.out.println(tree);

        BST<Integer> tree2 = new BST<>();

        tree2.insert(50);
        tree2.insert(51);
        tree2.delete(50);
        tree2.delete(51);

        assertEquals(tree.getHeight(), 10);

        BST<Integer> tree3 = new BST<>();

        int count = 0;

        rand = new java.util.Random(100);
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            int rnd = rand.nextInt(100);
            if (!tree3.contains(rnd))
                count++;
            tree3.insert(rnd);
        }

        System.out.println(tree3);
        assertEquals(tree3.printSearchPath(12), "15 -> 13 -> 0 -> 6 -> 8 -> 11 -> 12");
        assertEquals(tree3.printSearchPath(200), "15 -> 50 -> 74 -> 88 -> 91 -> 92 -> 95 -> 98 -> 99 -> Null");

        assertEquals(tree3.getNumLeaves(), 30);

        BST<Integer> supBalancedTree = tree3.extractBiggestSuperficiallyBalancedSubTree();

        System.out.println(supBalancedTree);

        assertEquals(tree3.isSuperficiallyBalanced(), false);
        assertEquals(supBalancedTree.isSuperficiallyBalanced(), true);

        assertEquals(tree3.findMax(), new BinaryNode<>(99));

    }
}
