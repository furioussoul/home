package vm;

/**
 * Created by szj on 2016/6/5.
 * 指令
 */
public class Instruction {

    public int seqNum; // 指令顺序计数器

    public String name; // 指令名称

    public String oprand1 = ""; //指令参数

    public String oprand2 = ""; //指令参数

    public String oprand3 = ""; //指令参数

    public String oprand4 = ""; //指令参数

    public String oprand5 = ""; //指令参数


    public Instruction(int seqNum, String name) {
        super();
        this.seqNum = seqNum;
        this.name = name;
    }

    public Instruction(int seqNum, String name, String oprand1) {
        this(seqNum, name);
        this.oprand1 = oprand1;
    }

    public Instruction(int seqNum, String name, String oprand1, String oprand2) {
        this(seqNum, name, oprand1);
        this.oprand2 = oprand2;
    }

    public String getName() {
        return name;
    }

    public String getOprand1() {
        return oprand1;
    }

    public void setOprand1(String oprand1) {
        this.oprand1 = oprand1;
    }

    public String getOprand2() {
        return oprand2;
    }

    public String toString() {
        return seqNum + " " + name + " " + oprand1 + " " + oprand2 + " " + oprand3 + " " + oprand4 + " " + oprand5;
    }
}
