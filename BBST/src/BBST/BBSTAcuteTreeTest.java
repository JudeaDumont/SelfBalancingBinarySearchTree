package BBST;

public class BBSTAcuteTreeTest {
    public static void main(String[] args) throws Exception {
        //Plan to get this test passing next.
        //This test does not currently pass.
        BBST<Integer> bbst = new BBST<Integer>();
        bbst.add(32);
        bbst.add(0);
        bbst.add(31);
        bbst.add(1);
        bbst.add(30);
        bbst.add(2);
        bbst.add(29);
        bbst.add(3);
        bbst.add(28);
        bbst.add(4);
        bbst.add(27);
        bbst.add(5);
        bbst.add(16);
        bbst.add(15);
        bbst.add(17);
        bbst.add(14);
        bbst.add(18);
        bbst.add(13);
        bbst.add(19);
        bbst.add(12);
        bbst.add(6);
        bbst.add(11);
        bbst.add(7);
        bbst.add(10);
        bbst.add(8);
        bbst.add(9);
        //25 nodes

        System.out.println(bbst.toString());
    }
}

