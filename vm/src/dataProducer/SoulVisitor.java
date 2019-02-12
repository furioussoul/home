// Generated from D:/workspace/soul_virtual_machine/src/dataProducer\Soul.g4 by ANTLR 4.5.1
package dataProducer;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SoulParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface SoulVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link SoulParser#prog}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitProg(SoulParser.ProgContext ctx);

    /**
     * Visit a parse tree produced by {@link SoulParser#describtion}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDescribtion(SoulParser.DescribtionContext ctx);

    /**
     * Visit a parse tree produced by {@link SoulParser#block}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlock(SoulParser.BlockContext ctx);

    /**
     * Visit a parse tree produced by {@link SoulParser#stmtlist}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStmtlist(SoulParser.StmtlistContext ctx);

    /**
     * Visit a parse tree produced by the {@code FIELD_RELY}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFIELD_RELY(SoulParser.FIELD_RELYContext ctx);

    /**
     * Visit a parse tree produced by the {@code FIELD_RANGE}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFIELD_RANGE(SoulParser.FIELD_RANGEContext ctx);

    /**
     * Visit a parse tree produced by the {@code IDS}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIDS(SoulParser.IDSContext ctx);

    /**
     * Visit a parse tree produced by the {@code FIELD_BLOCK}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFIELD_BLOCK(SoulParser.FIELD_BLOCKContext ctx);

    /**
     * Visit a parse tree produced by {@link SoulParser#range}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRange(SoulParser.RangeContext ctx);

    /**
     * Visit a parse tree produced by {@link SoulParser#field}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitField(SoulParser.FieldContext ctx);
}