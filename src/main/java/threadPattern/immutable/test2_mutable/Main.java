package threadPattern.immutable.test2_mutable;

public class Main {
    public static void main(String[] args) {
        // ����ʵ��
        UserInfo userinfo = new UserInfo("Alice", "Alaska");

        // ��ʾ
        System.out.println("userinfo = " + userinfo);

        //类属性为引用对象，当外部改变该对象时，不可变状态会被打破
        StringBuffer info = userinfo.getInfo();
        System.out.println("before: "+info);
        info.replace(12, 17, "Bobby");  // StringBuffer的replace改变内部System.arraycopy().，但其实例未改变；String的replace则是生成新实例（若前后内容有变化）
        System.out.println("after: "+info);

        //�ٶ���ʾ
        System.out.println("userinfo = " + userinfo);
    }
}
