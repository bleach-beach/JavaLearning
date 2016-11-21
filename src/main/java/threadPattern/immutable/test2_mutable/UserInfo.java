package threadPattern.immutable.test2_mutable;

//通过构造器赋值属性为引用对象，若该对象内部属性改变，则会打破该类状态不可变性
public final class UserInfo {
    private final StringBuffer info;
    public UserInfo(String name, String address) {
        this.info = new StringBuffer("<info name=\"" + name + "\" address=\"" + address + "\" />");
    }
    public StringBuffer getInfo() {
        return info;
    }
    public String toString() {
        return "[ UserInfo: " + info + " ]";
    }
}
