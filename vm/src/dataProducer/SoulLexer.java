// Generated from D:/workspace/soul_virtual_machine/src/dataProducer\Soul.g4 by ANTLR 4.5.1
package dataProducer;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SoulLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            ID = 10, NUMBER = 11, WS = 12, SL_COMMENT = 13;
    public static final String[] ruleNames = {
            "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
            "ID", "NUMBER", "WS", "SL_COMMENT"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\17`\b\1\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5" +
                    "\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\7\13\66\n\13" +
                    "\f\13\16\139\13\13\3\f\6\f<\n\f\r\f\16\f=\3\f\6\fA\n\f\r\f\16\fB\3\f\3" +
                    "\f\6\fG\n\f\r\f\16\fH\5\fK\n\f\3\r\6\rN\n\r\r\r\16\rO\3\r\3\r\3\16\3\16" +
                    "\3\16\3\16\7\16X\n\16\f\16\16\16[\13\16\3\16\3\16\3\16\3\16\3Y\2\17\3" +
                    "\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\3\2\6\5" +
                    "\2C\\aac|\6\2\62;C\\aac|\3\2\62;\5\2\13\f\17\17\"\"f\2\3\3\2\2\2\2\5\3" +
                    "\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2" +
                    "\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3" +
                    "\2\2\2\3\35\3\2\2\2\5\37\3\2\2\2\7!\3\2\2\2\t#\3\2\2\2\13(\3\2\2\2\r*" +
                    "\3\2\2\2\17-\3\2\2\2\21/\3\2\2\2\23\61\3\2\2\2\25\63\3\2\2\2\27J\3\2\2" +
                    "\2\31M\3\2\2\2\33S\3\2\2\2\35\36\7B\2\2\36\4\3\2\2\2\37 \7}\2\2 \6\3\2" +
                    "\2\2!\"\7\177\2\2\"\b\3\2\2\2#$\7t\2\2$%\7g\2\2%&\7n\2\2&\'\7{\2\2\'\n" +
                    "\3\2\2\2()\7=\2\2)\f\3\2\2\2*+\7k\2\2+,\7p\2\2,\16\3\2\2\2-.\7.\2\2.\20" +
                    "\3\2\2\2/\60\7\u0080\2\2\60\22\3\2\2\2\61\62\7\60\2\2\62\24\3\2\2\2\63" +
                    "\67\t\2\2\2\64\66\t\3\2\2\65\64\3\2\2\2\669\3\2\2\2\67\65\3\2\2\2\678" +
                    "\3\2\2\28\26\3\2\2\29\67\3\2\2\2:<\t\4\2\2;:\3\2\2\2<=\3\2\2\2=;\3\2\2" +
                    "\2=>\3\2\2\2>K\3\2\2\2?A\t\4\2\2@?\3\2\2\2AB\3\2\2\2B@\3\2\2\2BC\3\2\2" +
                    "\2CD\3\2\2\2DF\7\60\2\2EG\t\4\2\2FE\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2" +
                    "\2\2IK\3\2\2\2J;\3\2\2\2J@\3\2\2\2K\30\3\2\2\2LN\t\5\2\2ML\3\2\2\2NO\3" +
                    "\2\2\2OM\3\2\2\2OP\3\2\2\2PQ\3\2\2\2QR\b\r\2\2R\32\3\2\2\2ST\7\61\2\2" +
                    "TU\7\61\2\2UY\3\2\2\2VX\13\2\2\2WV\3\2\2\2X[\3\2\2\2YZ\3\2\2\2YW\3\2\2" +
                    "\2Z\\\3\2\2\2[Y\3\2\2\2\\]\7\f\2\2]^\3\2\2\2^_\b\16\2\2_\34\3\2\2\2\n" +
                    "\2\67=BHJOY\3\b\2\2";
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
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

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

    public SoulLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}