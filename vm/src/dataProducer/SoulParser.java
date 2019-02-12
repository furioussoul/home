// Generated from D:/workspace/soul_virtual_machine/src/dataProducer\Soul.g4 by ANTLR 4.5.1
package dataProducer;

import org.antlr.v4.runtime.dfa.DFA;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SoulParser extends Parser {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            ID = 10, NUMBER = 11, WS = 12, SL_COMMENT = 13;
    public static final int
            RULE_prog = 0, RULE_describtion = 1, RULE_block = 2, RULE_stmtlist = 3,
            RULE_stmt = 4, RULE_range = 5, RULE_field = 6;
    public static final String[] ruleNames = {
            "prog", "describtion", "block", "stmtlist", "stmt", "range", "field"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\17E\4\2\t\2\4\3\t" +
                    "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\7\2\22\n\2\f\2\16\2\25" +
                    "\13\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\7\5 \n\5\f\5\16\5#\13\5\3\6" +
                    "\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\62\n\6\f\6\16\6\65" +
                    "\13\6\3\6\3\6\3\6\3\6\5\6;\n\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\2\2" +
                    "\t\2\4\6\b\n\f\16\2\2C\2\23\3\2\2\2\4\26\3\2\2\2\6\32\3\2\2\2\b!\3\2\2" +
                    "\2\n:\3\2\2\2\f<\3\2\2\2\16@\3\2\2\2\20\22\5\4\3\2\21\20\3\2\2\2\22\25" +
                    "\3\2\2\2\23\21\3\2\2\2\23\24\3\2\2\2\24\3\3\2\2\2\25\23\3\2\2\2\26\27" +
                    "\7\3\2\2\27\30\7\f\2\2\30\31\5\6\4\2\31\5\3\2\2\2\32\33\7\4\2\2\33\34" +
                    "\5\b\5\2\34\35\7\5\2\2\35\7\3\2\2\2\36 \5\n\6\2\37\36\3\2\2\2 #\3\2\2" +
                    "\2!\37\3\2\2\2!\"\3\2\2\2\"\t\3\2\2\2#!\3\2\2\2$%\5\16\b\2%&\7\6\2\2&" +
                    "\'\5\16\b\2\'(\7\7\2\2(;\3\2\2\2)*\5\16\b\2*+\7\b\2\2+,\5\f\7\2,-\7\7" +
                    "\2\2-;\3\2\2\2.\63\7\f\2\2/\60\7\t\2\2\60\62\7\f\2\2\61/\3\2\2\2\62\65" +
                    "\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\66\3\2\2\2\65\63\3\2\2\2\66;\7" +
                    "\7\2\2\678\5\16\b\289\5\6\4\29;\3\2\2\2:$\3\2\2\2:)\3\2\2\2:.\3\2\2\2" +
                    ":\67\3\2\2\2;\13\3\2\2\2<=\7\r\2\2=>\7\n\2\2>?\7\r\2\2?\r\3\2\2\2@A\7" +
                    "\f\2\2AB\7\13\2\2BC\7\f\2\2C\17\3\2\2\2\6\23!\63:";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
            null, "'@'", "'{'", "'}'", "'rely'", "';'", "'in'", "','", "'~'", "'.'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, "ID", "NUMBER",
            "WS", "SL_COMMENT"
    };
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    static {
        RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION);
    }

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

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public SoulParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String getGrammarFileName() {
        return "Soul.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public final ProgContext prog() throws RecognitionException {
        ProgContext _localctx = new ProgContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_prog);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(17);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__0) {
                    {
                        {
                            setState(14);
                            describtion();
                        }
                    }
                    setState(19);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final DescribtionContext describtion() throws RecognitionException {
        DescribtionContext _localctx = new DescribtionContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_describtion);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(20);
                match(T__0);
                setState(21);
                match(ID);
                setState(22);
                block();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final BlockContext block() throws RecognitionException {
        BlockContext _localctx = new BlockContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_block);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(24);
                match(T__1);
                setState(25);
                stmtlist();
                setState(26);
                match(T__2);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final StmtlistContext stmtlist() throws RecognitionException {
        StmtlistContext _localctx = new StmtlistContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_stmtlist);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(31);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == ID) {
                    {
                        {
                            setState(28);
                            stmt();
                        }
                    }
                    setState(33);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final StmtContext stmt() throws RecognitionException {
        StmtContext _localctx = new StmtContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_stmt);
        int _la;
        try {
            setState(56);
            switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                case 1:
                    _localctx = new FIELD_RELYContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(34);
                    field();
                    setState(35);
                    match(T__3);
                    setState(36);
                    field();
                    setState(37);
                    match(T__4);
                }
                break;
                case 2:
                    _localctx = new FIELD_RANGEContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(39);
                    field();
                    setState(40);
                    match(T__5);
                    setState(41);
                    range();
                    setState(42);
                    match(T__4);
                }
                break;
                case 3:
                    _localctx = new IDSContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(44);
                    match(ID);
                    setState(49);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    while (_la == T__6) {
                        {
                            {
                                setState(45);
                                match(T__6);
                                setState(46);
                                match(ID);
                            }
                        }
                        setState(51);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    }
                    setState(52);
                    match(T__4);
                }
                break;
                case 4:
                    _localctx = new FIELD_BLOCKContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(53);
                    field();
                    setState(54);
                    block();
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final RangeContext range() throws RecognitionException {
        RangeContext _localctx = new RangeContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_range);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(58);
                match(NUMBER);
                setState(59);
                match(T__7);
                setState(60);
                match(NUMBER);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final FieldContext field() throws RecognitionException {
        FieldContext _localctx = new FieldContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_field);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(62);
                match(ID);
                setState(63);
                match(T__8);
                setState(64);
                match(ID);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ProgContext extends ParserRuleContext {
        public ProgContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<DescribtionContext> describtion() {
            return getRuleContexts(DescribtionContext.class);
        }

        public DescribtionContext describtion(int i) {
            return getRuleContext(DescribtionContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_prog;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitProg(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class DescribtionContext extends ParserRuleContext {
        public DescribtionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(SoulParser.ID, 0);
        }

        public BlockContext block() {
            return getRuleContext(BlockContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_describtion;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitDescribtion(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class BlockContext extends ParserRuleContext {
        public BlockContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StmtlistContext stmtlist() {
            return getRuleContext(StmtlistContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_block;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitBlock(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class StmtlistContext extends ParserRuleContext {
        public StmtlistContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<StmtContext> stmt() {
            return getRuleContexts(StmtContext.class);
        }

        public StmtContext stmt(int i) {
            return getRuleContext(StmtContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_stmtlist;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitStmtlist(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class StmtContext extends ParserRuleContext {
        public StmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StmtContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_stmt;
        }

        public void copyFrom(StmtContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FIELD_RANGEContext extends StmtContext {
        public FIELD_RANGEContext(StmtContext ctx) {
            copyFrom(ctx);
        }

        public FieldContext field() {
            return getRuleContext(FieldContext.class, 0);
        }

        public RangeContext range() {
            return getRuleContext(RangeContext.class, 0);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitFIELD_RANGE(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FIELD_BLOCKContext extends StmtContext {
        public FIELD_BLOCKContext(StmtContext ctx) {
            copyFrom(ctx);
        }

        public FieldContext field() {
            return getRuleContext(FieldContext.class, 0);
        }

        public BlockContext block() {
            return getRuleContext(BlockContext.class, 0);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitFIELD_BLOCK(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FIELD_RELYContext extends StmtContext {
        public FIELD_RELYContext(StmtContext ctx) {
            copyFrom(ctx);
        }

        public List<FieldContext> field() {
            return getRuleContexts(FieldContext.class);
        }

        public FieldContext field(int i) {
            return getRuleContext(FieldContext.class, i);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitFIELD_RELY(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class IDSContext extends StmtContext {
        public IDSContext(StmtContext ctx) {
            copyFrom(ctx);
        }

        public List<TerminalNode> ID() {
            return getTokens(SoulParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(SoulParser.ID, i);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitIDS(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class RangeContext extends ParserRuleContext {
        public RangeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> NUMBER() {
            return getTokens(SoulParser.NUMBER);
        }

        public TerminalNode NUMBER(int i) {
            return getToken(SoulParser.NUMBER, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_range;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitRange(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class FieldContext extends ParserRuleContext {
        public FieldContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> ID() {
            return getTokens(SoulParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(SoulParser.ID, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_field;
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof SoulVisitor) return ((SoulVisitor<? extends T>) visitor).visitField(this);
            else return visitor.visitChildren(this);
        }
    }
}