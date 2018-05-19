package BBST;

public class BBSTDriver {
    public static void main(String[] args) throws Exception {
        BBST<Integer> bbst = new BBST<Integer>();
        bbst.add(8);
        bbst.add(16);
        bbst.add(10);
        bbst.add(13);
        bbst.add(15);
        bbst.add(14);
        bbst.add(7);
        bbst.add(6);
        bbst.add(5);
        bbst.add(4);
        bbst.add(3);
        bbst.add(2);
        bbst.add(1);

        System.out.println(bbst.toString());
    }
}

