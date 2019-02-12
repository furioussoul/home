package vm;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szj on 2016/6/8.
 */
public class MyCompiler extends CymbolBaseVisitor {
    private int seqNum = 0;
    private int reAsnNum = 0;
    private int level = 0;
    private Map<String, String> paramRenameMap = null;
    private List<Instruction> insList = null;
    private Map<String, List<Instruction>> functionMap = new HashMap<>();
    private String funName = null;

    private MyCompiler addInstruction(Instruction inc) {
        insList.add(inc);
        return this;
    }

    public Map<String, List<Instruction>> getFunctionMap() {
        return this.functionMap;
    }

    @Override
    public Object visitProg(CymbolParser.ProgContext ctx) {
        return super.visitProg(ctx);
    }

    @Override
    public Object visitFunDecl(CymbolParser.FunDeclContext ctx) {
        reAsnNum = 0;
        seqNum = 0;
        this.paramRenameMap = new HashMap<>();
        this.insList = new ArrayList<>();
        funName = ctx.ID().getText();
        functionMap.put(funName, insList);
        return super.visitFunDecl(ctx);
    }

    @Override
    public Object visitBlock(CymbolParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }

    @Override
    public Object visitStmtList(CymbolParser.StmtListContext ctx) {
        return super.visitStmtList(ctx);
    }

    @Override
    public Object visitStmt(CymbolParser.StmtContext ctx) {
        super.visitStmt(ctx);
        if (ctx.RETURN() != null) {
            if (funName.equals("main")) {
                addInstruction(new Instruction(seqNum++, "halt"));
            } else {
                addInstruction(new Instruction(seqNum++, "ret"));
            }
        }
        return null;
    }

    @Override
    public Object visitIfStmt(CymbolParser.IfStmtContext ctx) {
        //if() ……;
        if (ctx.case2 == null) {
            visit(ctx.expr());//计算if（）中表达式
            Instruction in = new Instruction(seqNum++, "jz");//是否要进去if体
            addInstruction(in);
            visit(ctx.case1);
            in.setOprand1(seqNum + "");//if false 就 跳过{}
        } else { //if() ……; else ……;
            visit(ctx.expr());//计算if（）中表达式
            Instruction in = new Instruction(seqNum++, "jz");
            addInstruction(in);
            visit(ctx.case1);
            Instruction in2 = new Instruction(seqNum++, "jmp");
            addInstruction(in2);//执行完if()... 跳过else()...
            in.setOprand1(seqNum + "");//如果false 就跳过if()... 进入 else()...
            visit(ctx.case2);
            in2.setOprand1(seqNum + "");
        }
        return null;
    }

    @Override
    public Object visitCall(CymbolParser.CallContext ctx) {
        super.visitCall(ctx);
        int argsNum = 0;
        while ((ctx.exprList().expr(argsNum)) != null) {
            argsNum++;
        }
        addInstruction(new Instruction(seqNum++, "call", ctx.ID().getText(), argsNum + ""));
        return null;
    }

    @Override
    public Object visitNot(CymbolParser.NotContext ctx) {
        return super.visitNot(ctx);
    }

    @Override
    public Object visitNumber(CymbolParser.NumberContext ctx) {

        addInstruction(new Instruction(seqNum++, "ldc", ctx.getText()));
        return null;
    }

    @Override
    public Object visitParameterList(CymbolParser.ParameterListContext ctx) {
        int i = 0;
        TerminalNode node = ctx.ID(i++);
        while (node != null) {
            paramRenameMap.put(node.getText(), "t" + reAsnNum++);
            node = ctx.ID(i++);
        }
        return null;
    }

    @Override
    public Object visitMulDiv(CymbolParser.MulDivContext ctx) {
        visit(ctx.expr(0));
        visit(ctx.expr(1));
        if (ctx.op.getText().equalsIgnoreCase("*")) {
            addInstruction(new Instruction(seqNum++, "mul"));
        } else {
            addInstruction(new Instruction(seqNum++, "div"));
        }
        return null;
    }

    @Override
    public Object visitAddSub(CymbolParser.AddSubContext ctx) {
        visit(ctx.expr(0));
        visit(ctx.expr(1));
        if (ctx.op.getText().equalsIgnoreCase("+")) {
            addInstruction(new Instruction(seqNum++, "add"));
        } else {
            addInstruction(new Instruction(seqNum++, "sub"));
        }
        return null;
    }

    @Override
    public Object visitEqual(CymbolParser.EqualContext ctx) {
        return super.visitEqual(ctx);
    }

    @Override
    public Object visitVar(CymbolParser.VarContext ctx) {
        String var = paramRenameMap.get(ctx.ID().getText());
        if (var == null) {
            String temp = "t" + reAsnNum++;
            paramRenameMap.put(ctx.ID().getText(), temp);
            var = temp;
        }
        addInstruction(new Instruction(seqNum++, "ldv", var));
        return null;
    }

    @Override
    public Object visitAsn(CymbolParser.AsnContext ctx) {
        visit(ctx.expr());
        String var = paramRenameMap.get(ctx.ID().getText());
        if (null == var) {
            String temp = "t" + reAsnNum++;
            paramRenameMap.put(ctx.ID().getText(), temp);
            var = temp;
        }
        addInstruction(new Instruction(seqNum++, "asn", var));
        return null;
    }

    @Override
    public Object visitNegate(CymbolParser.NegateContext ctx) {
        visit(ctx.expr());//解释负号右边的表达式
        addInstruction(new Instruction(seqNum++, "ldc", "-1"))
                .addInstruction(new Instruction(seqNum++, "mul"));
        return null;
    }

    @Override
    public Object visitExprList(CymbolParser.ExprListContext ctx) {
        return super.visitExprList(ctx);
    }

    @Override
    public Object visitGtLt(CymbolParser.GtLtContext ctx) {
        visit(ctx.expr(0));
        visit(ctx.expr(1));
        if (ctx.op.getText().equals(">")) {
            addInstruction(new Instruction(seqNum++, "gt"));
        } else {
            addInstruction(new Instruction(seqNum++, "lt"));
        }
        return null;
    }

    @Override
    public Object visitWhileStmt(CymbolParser.WhileStmtContext ctx) {
        int whileBeginMarker = seqNum;
        visit(ctx.expr());
        Instruction in1 = new Instruction(seqNum++, "jz");
        addInstruction(in1);
        visit(ctx.stmt());
        Instruction in2 = new Instruction(seqNum++, "jmp");
        addInstruction(in2);
        in1.setOprand1(seqNum + "");//false 跳出循环
        in2.setOprand1(whileBeginMarker + "");//跳回循环头
        return null;
    }
}
