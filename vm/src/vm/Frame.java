package vm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by szj on 2016/6/5.
 * 帧，由帧维护指令序列，运算栈
 */
public class Frame {

    private String functionName; // 函数名

    private int ir = 0;  //指令指针

    private List<Instruction> inslist; // 指令序列

    private Stack<ActiveOperand> stack = new Stack<ActiveOperand>();  //运算栈

    private Map<String, ActiveOperand> localMap = new HashMap<String, ActiveOperand>(); // 局部变量

    public Frame(String functionName) {
        super();
        this.functionName = functionName;
    }

    public void setInslist(List<Instruction> inslist) {
        this.inslist = inslist;
    }

    public void setIr(int ir) {
        this.ir = ir;
    }

    // 指令位置往后移一位
    public void incIr() {
        this.ir += 1;
    }

    //指令是否运行完毕
    public boolean isNotEndOfInstructions() {
        return ir < this.inslist.size();
    }

    public Instruction getInstruction() {
        return this.inslist.get(ir);
    }

    //栈
    public void pushActiveOperand(ActiveOperand ao) {
        this.stack.push(ao);
    }

    public ActiveOperand popActiveOperand() {
        return this.stack.pop();
    }

    public ActiveOperand peekActiveOperand() {
        return this.stack.peek();
    }

    //取局部变量
    public ActiveOperand getActiveOperand(String variable) {
        return this.localMap.get(variable);
    }

    //设置局部变量
    public void putActiveOperand(String variable, ActiveOperand ao) {
        this.localMap.put(variable, ao);
    }
}