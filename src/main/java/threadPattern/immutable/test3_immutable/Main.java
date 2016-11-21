package threadPattern.immutable.test3_immutable;

public class Main {
    public static void main(String[] args) {
        //����ʵ��
        Point p1 = new Point(0, 0);
        Point p2 = new Point(100, 0);
        Line line = new Line(p1, p2);

        //��ʾ
        System.out.println("line = " + line);

        //状态变化（并不会状态改变）
        p1.x = 150;//由于LINE构造器属性指向的是新建的对象，因此Point的改变不会影响Line
        p2.x = 150;
        p2.y = 250;

        //�ٶ���ʾ
        System.out.println("line = " + line);
    }
}
