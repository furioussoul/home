// Generated from D:/workspace/soul_virtual_machine\Cymbol.g4 by ANTLR 4.5.1
package vm;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CymbolLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, MUL = 11, DIV = 12, ADD = 13, SUB = 14, NOT = 15, IF = 16, GT = 17, LT = 18,
            RETURN = 19, ID = 20, NUMBER = 21, WS = 22, SL_COMMENT = 23;
    public static final String[] ruleNames = {
            "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
            "T__9", "MUL", "DIV", "ADD", "SUB", "NOT", "IF", "GT", "LT", "RETURN",
            "ID", "NUMBER", "WS", "SL_COMMENT"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\31\u0085\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2" +
                    "\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3" +
                    "\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3" +
                    "\17\3\17\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3" +
                    "\24\3\24\3\24\3\24\3\25\3\25\7\25h\n\25\f\25\16\25k\13\25\3\26\6\26n\n" +
                    "\26\r\26\16\26o\3\27\6\27s\n\27\r\27\16\27t\3\27\3\27\3\30\3\30\3\30\3" +
                    "\30\7\30}\n\30\f\30\16\30\u0080\13\30\3\30\3\30\3\30\3\30\3~\2\31\3\3" +
                    "\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21" +
                    "!\22#\23%\24\'\25)\26+\27-\30/\31\3\2\6\4\2C\\c|\5\2\62;C\\c|\3\2\62;" +
                    "\5\2\13\f\17\17\"\"\u0088\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2" +
                    "\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2" +
                    "\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3" +
                    "\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2" +
                    "\2\2\2-\3\2\2\2\2/\3\2\2\2\3\61\3\2\2\2\5\63\3\2\2\2\7\65\3\2\2\2\t\67" +
                    "\3\2\2\2\139\3\2\2\2\r;\3\2\2\2\17=\3\2\2\2\21B\3\2\2\2\23H\3\2\2\2\25" +
                    "J\3\2\2\2\27M\3\2\2\2\31O\3\2\2\2\33Q\3\2\2\2\35S\3\2\2\2\37U\3\2\2\2" +
                    "!W\3\2\2\2#Z\3\2\2\2%\\\3\2\2\2\'^\3\2\2\2)e\3\2\2\2+m\3\2\2\2-r\3\2\2" +
                    "\2/x\3\2\2\2\61\62\7*\2\2\62\4\3\2\2\2\63\64\7+\2\2\64\6\3\2\2\2\65\66" +
                    "\7.\2\2\66\b\3\2\2\2\678\7}\2\28\n\3\2\2\29:\7\177\2\2:\f\3\2\2\2;<\7" +
                    "=\2\2<\16\3\2\2\2=>\7g\2\2>?\7n\2\2?@\7u\2\2@A\7g\2\2A\20\3\2\2\2BC\7" +
                    "y\2\2CD\7j\2\2DE\7k\2\2EF\7n\2\2FG\7g\2\2G\22\3\2\2\2HI\7?\2\2I\24\3\2" +
                    "\2\2JK\7?\2\2KL\7?\2\2L\26\3\2\2\2MN\7,\2\2N\30\3\2\2\2OP\7\61\2\2P\32" +
                    "\3\2\2\2QR\7-\2\2R\34\3\2\2\2ST\7/\2\2T\36\3\2\2\2UV\7#\2\2V \3\2\2\2" +
                    "WX\7k\2\2XY\7h\2\2Y\"\3\2\2\2Z[\7@\2\2[$\3\2\2\2\\]\7>\2\2]&\3\2\2\2^" +
                    "_\7t\2\2_`\7g\2\2`a\7v\2\2ab\7w\2\2bc\7t\2\2cd\7p\2\2d(\3\2\2\2ei\t\2" +
                    "\2\2fh\t\3\2\2gf\3\2\2\2hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2j*\3\2\2\2ki\3\2" +
                    "\2\2ln\t\4\2\2ml\3\2\2\2no\3\2\2\2om\3\2\2\2op\3\2\2\2p,\3\2\2\2qs\t\5" +
                    "\2\2rq\3\2\2\2st\3\2\2\2tr\3\2\2\2tu\3\2\2\2uv\3\2\2\2vw\b\27\2\2w.\3" +
                    "\2\2\2xy\7\61\2\2yz\7\61\2\2z~\3\2\2\2{}\13\2\2\2|{\3\2\2\2}\u0080\3\2" +
                    "\2\2~\177\3\2\2\2~|\3\2\2\2\177\u0081\3\2\2\2\u0080~\3\2\2\2\u0081\u0082" +
                    "\7\f\2\2\u0082\u0083\3\2\2\2\u0083\u0084\b\30\2\2\u0084\60\3\2\2\2\7\2" +
                    "iot~\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
            null, "'('", "')'", "','", "'{'", "'}'", "';'", "'else'", "'while'", "'='",
            "'=='", "'*'", "'/'", "'+'", "'-'", "'!'", "'if'", "'>'", "'<'", "'return'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, null, "MUL",
            "DIV", "ADD", "SUB", "NOT", "IF", "GT", "LT", "RETURN", "ID", "NUMBER",
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

    public CymbolLexer(CharStream input) {
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
        return "Cymbol.g4";
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