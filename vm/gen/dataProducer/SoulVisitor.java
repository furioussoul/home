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
     * Visit a parse tree produced by {@link SoulParser#creater}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCreater(SoulParser.CreaterContext ctx);

    /**
     * Visit a parse tree produced by {@link SoulParser#block}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBlock(SoulParser.BlockContext ctx);

    /**
     * Visit a parse tree produced by the {@code INC}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitINC(SoulParser.INCContext ctx);

    /**
     * Visit a parse tree produced by the {@code M2O}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitM2O(SoulParser.M2OContext ctx);

    /**
     * Visit a parse tree produced by the {@code IN}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitIN(SoulParser.INContext ctx);

    /**
     * Visit a parse tree produced by the {@code RANDOM}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitRANDOM(SoulParser.RANDOMContext ctx);

    /**
     * Visit a parse tree produced by the {@code ASSIGNEDLIST}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitASSIGNEDLIST(SoulParser.ASSIGNEDLISTContext ctx);

    /**
     * Visit a parse tree produced by the {@code ASSIGNFIELD}
     * labeled alternative in {@link SoulParser#stmt}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitASSIGNFIELD(SoulParser.ASSIGNFIELDContext ctx);

    /**
     * Visit a parse tree produced by {@link SoulParser#table}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitTable(SoulParser.TableContext ctx);

    /**
     * Visit a parse tree produced by {@link SoulParser#field}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitField(SoulParser.FieldContext ctx);
}