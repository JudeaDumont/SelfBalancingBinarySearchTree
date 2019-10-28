package BBST;

public class BBSTRightHeavyTest {
    public static void main(String[] args) throws Exception {
        BBST<Integer> bbst = new BBST<Integer>();
        bbst.add(0);
        bbst.add(1);
        bbst.add(2);
        bbst.add(3);
        bbst.add(4);
        bbst.add(5);
        bbst.add(6);


        System.out.println(bbst.toString());
    }
}

