package dataProducer;

import utils.CheckedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szj on 2016/7/10.
 */
public class MyCompiler extends SoulBaseMonitor {

    @Override
    public Object visitDescribtion(SoulParser.DescribtionContext ctx) {
        if (ctx.ID().getText().equals("table")) {
            for (SoulParser.StmtContext stmtContext : ctx.block().stmtlist().stmt()) {
                String array = stmtContext.getText();
                array = array.substring(0, array.lastIndexOf(";"));
                String[] arrays = array.split(",");
                for (String a : arrays) {
                    Table.add(a);
                }
            }
        } else if (ctx.ID().getText().equals("cascading")) {
            for (SoulParser.StmtContext stmtContext : ctx.block().stmtlist().stmt()) {
                String[] arrays = stmtContext.getText().split("rely");
                if (arrays == null || arrays.length != 2) {
                    throw new CheckedException("语法错误");
                }
                Cascading.cascadeTable(arrays[0].split("\\.")[0], arrays[1].split("\\.")[0]);
            }
        } else if (ctx.ID().getText().equals("field")) {
            super.visitDescribtion(ctx);
        } else {
            throw new UnsupportedOperationException("标记:" + ctx.ID().getText() + " 不支持");
        }
        return null;
    }

    @Override
    public Object visitFIELD_RANGE(SoulParser.FIELD_RANGEContext ctx) {
        String[] arrays = ctx.getText().split("in");
        if (arrays == null || arrays.length != 2) {
            throw new CheckedException("语法错误");
        }
        String[] tableAndField = arrays[0].split("\\.");
        if (tableAndField == null || tableAndField.length != 2) {
            throw new CheckedException("语法错误");
        }
        Field.addFieldRange(new Field(tableAndField[0], tableAndField[1], arrays[1]));
        return null;
    }

    @Override
    public Object visitFIELD_BLOCK(SoulParser.FIELD_BLOCKContext ctx) {
        String[] tableAndField = ctx.field().getText().split("\\.");
        if (tableAndField == null || tableAndField.length != 2) {
            throw new CheckedException("语法错误");
        }
        String array = ctx.block().stmtlist().stmt(0).getText();
        array = array.substring(0, array.lastIndexOf(";"));
        String[] arrays = array.split(",");

        List<String> fieldValues = new ArrayList<>();
        for (String a : arrays) {
            fieldValues.add(a);
        }
        Field.addFieldValue(new Field(tableAndField[0], tableAndField[1], fieldValues));
        return null;
    }
}
