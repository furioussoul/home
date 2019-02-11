// Generated from D:/workspace/soul_virtual_machine/src/dataProducer\Soul.g4 by ANTLR 4.5.1
package dataProducer;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SoulParser}.
 */
public interface SoulListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SoulParser#creater}.
	 * @param ctx the parse tree
	 */
	void enterCreater(SoulParser.CreaterContext ctx);
	/**
	 * Exit a parse tree produced by {@link SoulParser#creater}.
	 * @param ctx the parse tree
	 */
	void exitCreater(SoulParser.CreaterContext ctx);
	/**
	 * Enter a parse tree produced by {@link SoulParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(SoulParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link SoulParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(SoulParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code INC}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterINC(SoulParser.INCContext ctx);
	/**
	 * Exit a parse tree produced by the {@code INC}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitINC(SoulParser.INCContext ctx);
	/**
	 * Enter a parse tree produced by the {@code M2O}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterM2O(SoulParser.M2OContext ctx);
	/**
	 * Exit a parse tree produced by the {@code M2O}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitM2O(SoulParser.M2OContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IN}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterIN(SoulParser.INContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IN}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitIN(SoulParser.INContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RANDOM}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterRANDOM(SoulParser.RANDOMContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RANDOM}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitRANDOM(SoulParser.RANDOMContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ASSIGNEDLIST}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterASSIGNEDLIST(SoulParser.ASSIGNEDLISTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGNEDLIST}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitASSIGNEDLIST(SoulParser.ASSIGNEDLISTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ASSIGNFIELD}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterASSIGNFIELD(SoulParser.ASSIGNFIELDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ASSIGNFIELD}
	 * labeled alternative in {@link SoulParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitASSIGNFIELD(SoulParser.ASSIGNFIELDContext ctx);
	/**
	 * Enter a parse tree produced by {@link SoulParser#table}.
	 * @param ctx the parse tree
	 */
	void enterTable(SoulParser.TableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SoulParser#table}.
	 * @param ctx the parse tree
	 */
	void exitTable(SoulParser.TableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SoulParser#field}.
	 * @param ctx the parse tree
	 */
	void enterField(SoulParser.FieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link SoulParser#field}.
	 * @param ctx the parse tree
	 */
	void exitField(SoulParser.FieldContext ctx);
}