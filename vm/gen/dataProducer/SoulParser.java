// Generated from D:/workspace/soul_virtual_machine/src/dataProducer\Soul.g4 by ANTLR 4.5.1
package dataProducer;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SoulParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, String=12, WS=13, SL_COMMENT=14;
	public static final int
		RULE_creater = 0, RULE_block = 1, RULE_stmt = 2, RULE_table = 3, RULE_field = 4;
	public static final String[] ruleNames = {
		"creater", "block", "stmt", "table", "field"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'{'", "'}'", "'inc'", "';'", "'m2o in'", "'in'", "'random'", 
		"','", "'&'", "'.'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"String", "WS", "SL_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Soul.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SoulParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CreaterContext extends ParserRuleContext {
		public TerminalNode String() { return getToken(SoulParser.String, 0); }
		public TableContext table() {
			return getRuleContext(TableContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public CreaterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creater; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterCreater(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitCreater(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitCreater(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreaterContext creater() throws RecognitionException {
		CreaterContext _localctx = new CreaterContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_creater);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			match(String);
			setState(11);
			match(T__0);
			setState(12);
			table();
			setState(13);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15);
			match(T__1);
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==String) {
				{
				{
				setState(16);
				stmt();
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(22);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	 
		public StmtContext() { }
		public void copyFrom(StmtContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ASSIGNEDLISTContext extends StmtContext {
		public List<TerminalNode> String() { return getTokens(SoulParser.String); }
		public TerminalNode String(int i) {
			return getToken(SoulParser.String, i);
		}
		public ASSIGNEDLISTContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterASSIGNEDLIST(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitASSIGNEDLIST(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitASSIGNEDLIST(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class INContext extends StmtContext {
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public INContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterIN(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitIN(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitIN(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RANDOMContext extends StmtContext {
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public RANDOMContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterRANDOM(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitRANDOM(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitRANDOM(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class M2OContext extends StmtContext {
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public M2OContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterM2O(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitM2O(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitM2O(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ASSIGNFIELDContext extends StmtContext {
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ASSIGNFIELDContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterASSIGNFIELD(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitASSIGNFIELD(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitASSIGNFIELD(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class INCContext extends StmtContext {
		public FieldContext field() {
			return getRuleContext(FieldContext.class,0);
		}
		public INCContext(StmtContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterINC(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitINC(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitINC(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stmt);
		int _la;
		try {
			setState(52);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				_localctx = new INCContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(24);
				field();
				setState(25);
				match(T__3);
				setState(26);
				match(T__4);
				}
				break;
			case 2:
				_localctx = new M2OContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(28);
				field();
				setState(29);
				match(T__5);
				setState(30);
				field();
				setState(31);
				match(T__4);
				}
				break;
			case 3:
				_localctx = new INContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(33);
				field();
				setState(34);
				match(T__6);
				setState(35);
				match(T__4);
				}
				break;
			case 4:
				_localctx = new RANDOMContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(37);
				field();
				setState(38);
				match(T__7);
				setState(39);
				match(T__4);
				}
				break;
			case 5:
				_localctx = new ASSIGNEDLISTContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(41);
				match(String);
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(42);
					match(T__8);
					setState(43);
					match(String);
					}
					}
					setState(48);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 6:
				_localctx = new ASSIGNFIELDContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(49);
				field();
				setState(50);
				block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TableContext extends ParserRuleContext {
		public List<TerminalNode> String() { return getTokens(SoulParser.String); }
		public TerminalNode String(int i) {
			return getToken(SoulParser.String, i);
		}
		public TableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_table; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitTable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitTable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TableContext table() throws RecognitionException {
		TableContext _localctx = new TableContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_table);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(String);
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(55);
				match(T__9);
				setState(56);
				match(String);
				}
				}
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FieldContext extends ParserRuleContext {
		public List<TerminalNode> String() { return getTokens(SoulParser.String); }
		public TerminalNode String(int i) {
			return getToken(SoulParser.String, i);
		}
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).enterField(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SoulListener ) ((SoulListener)listener).exitField(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SoulVisitor ) return ((SoulVisitor<? extends T>)visitor).visitField(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(String);
			setState(63);
			match(T__10);
			setState(64);
			match(String);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\20E\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\2\3\2\3\3\3\3\7\3\24\n\3\f\3"+
		"\16\3\27\13\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4/\n\4\f\4\16\4\62\13\4\3\4\3\4\3\4"+
		"\5\4\67\n\4\3\5\3\5\3\5\7\5<\n\5\f\5\16\5?\13\5\3\6\3\6\3\6\3\6\3\6\2"+
		"\2\7\2\4\6\b\n\2\2G\2\f\3\2\2\2\4\21\3\2\2\2\6\66\3\2\2\2\b8\3\2\2\2\n"+
		"@\3\2\2\2\f\r\7\16\2\2\r\16\7\3\2\2\16\17\5\b\5\2\17\20\5\4\3\2\20\3\3"+
		"\2\2\2\21\25\7\4\2\2\22\24\5\6\4\2\23\22\3\2\2\2\24\27\3\2\2\2\25\23\3"+
		"\2\2\2\25\26\3\2\2\2\26\30\3\2\2\2\27\25\3\2\2\2\30\31\7\5\2\2\31\5\3"+
		"\2\2\2\32\33\5\n\6\2\33\34\7\6\2\2\34\35\7\7\2\2\35\67\3\2\2\2\36\37\5"+
		"\n\6\2\37 \7\b\2\2 !\5\n\6\2!\"\7\7\2\2\"\67\3\2\2\2#$\5\n\6\2$%\7\t\2"+
		"\2%&\7\7\2\2&\67\3\2\2\2\'(\5\n\6\2()\7\n\2\2)*\7\7\2\2*\67\3\2\2\2+\60"+
		"\7\16\2\2,-\7\13\2\2-/\7\16\2\2.,\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2\60\61"+
		"\3\2\2\2\61\67\3\2\2\2\62\60\3\2\2\2\63\64\5\n\6\2\64\65\5\4\3\2\65\67"+
		"\3\2\2\2\66\32\3\2\2\2\66\36\3\2\2\2\66#\3\2\2\2\66\'\3\2\2\2\66+\3\2"+
		"\2\2\66\63\3\2\2\2\67\7\3\2\2\28=\7\16\2\29:\7\f\2\2:<\7\16\2\2;9\3\2"+
		"\2\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>\t\3\2\2\2?=\3\2\2\2@A\7\16\2\2AB\7"+
		"\r\2\2BC\7\16\2\2C\13\3\2\2\2\6\25\60\66=";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}