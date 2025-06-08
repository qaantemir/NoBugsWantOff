package java_express.myObjects;

public class StudentGroup {
    private String groupName;
    private int groupCount;

    public StudentGroup(String groupName, int groupCount) {
        this.groupName = groupName;
        this.groupCount = groupCount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(int groupCount) {
        if (groupCount >= 0) this.groupCount = groupCount;
        else System.out.println("Нельзя установить количество меньше или равно нулю");

    }

    @Override
    public String toString() {
        return "StudentGroup{" +
                "groupName='" + groupName + '\'' +
                ", groupCount=" + groupCount +
                '}';
    }

    public void printInfo(){
        System.out.println(this.toString());
    }
}
