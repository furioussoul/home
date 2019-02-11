package dataProducer;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

/**
 * Created by szj on 2016/7/10.
 */
public class Test {
    public static void main(String[] arg0) throws IOException, InterruptedException {
        String[] tests = {
//                "D:\\workspace\\soul_virtual_machine\\src\\dataProducer\\p.soul"
                "F:\\workspace\\my_virtual_machine\\src\\dataProducer\\p.soul"
        };

        for (String s : tests) {
            SoulLexer lexer = new SoulLexer(new ANTLRFileStream(s));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SoulParser parser = new SoulParser(tokens);
            MyCompiler compiler = new MyCompiler();
            compiler.visit(parser.prog());
            SequenceResolver sequenceResolver = new SequenceResolver();
            sequenceResolver.getResidueFromTables();
            for(String seq : sequenceResolver.sequence){
                System.out.println(seq + "  ");
            }
        }
    }
}
