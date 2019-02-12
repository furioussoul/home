// Generated from D:/workspace/soul_virtual_machine/src/dataProducer\Soul.g4 by ANTLR 4.5.1
package dataProducer;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SoulLexer extends Lexer {
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, String = 12, WS = 13, SL_COMMENT = 14;
    public static final String[] ruleNames = {
            "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
            "T__9", "T__10", "String", "WS", "SL_COMMENT"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\20d\b\1\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3" +
                    "\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\t" +
                    "\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\7\rE\n\r\f\r\16\rH" +
                    "\13\r\3\r\6\rK\n\r\r\r\16\rL\5\rO\n\r\3\16\6\16R\n\16\r\16\16\16S\3\16" +
                    "\3\16\3\17\3\17\3\17\3\17\7\17\\\n\17\f\17\16\17_\13\17\3\17\3\17\3\17" +
                    "\3\17\3]\2\20\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16" +
                    "\33\17\35\20\3\2\6\4\2C\\c|\5\2\62;C\\c|\3\2\62;\5\2\13\f\17\17\"\"h\2" +
                    "\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2" +
                    "\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2" +
                    "\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\3\37\3\2\2\2\5!\3\2\2\2\7#\3\2\2" +
                    "\2\t%\3\2\2\2\13)\3\2\2\2\r+\3\2\2\2\17\62\3\2\2\2\21\65\3\2\2\2\23<\3" +
                    "\2\2\2\25>\3\2\2\2\27@\3\2\2\2\31N\3\2\2\2\33Q\3\2\2\2\35W\3\2\2\2\37" +
                    " \7?\2\2 \4\3\2\2\2!\"\7}\2\2\"\6\3\2\2\2#$\7\177\2\2$\b\3\2\2\2%&\7k" +
                    "\2\2&\'\7p\2\2\'(\7e\2\2(\n\3\2\2\2)*\7=\2\2*\f\3\2\2\2+,\7o\2\2,-\7\64" +
                    "\2\2-.\7q\2\2./\7\"\2\2/\60\7k\2\2\60\61\7p\2\2\61\16\3\2\2\2\62\63\7" +
                    "k\2\2\63\64\7p\2\2\64\20\3\2\2\2\65\66\7t\2\2\66\67\7c\2\2\678\7p\2\2" +
                    "89\7f\2\29:\7q\2\2:;\7o\2\2;\22\3\2\2\2<=\7.\2\2=\24\3\2\2\2>?\7(\2\2" +
                    "?\26\3\2\2\2@A\7\60\2\2A\30\3\2\2\2BF\t\2\2\2CE\t\3\2\2DC\3\2\2\2EH\3" +
                    "\2\2\2FD\3\2\2\2FG\3\2\2\2GO\3\2\2\2HF\3\2\2\2IK\t\4\2\2JI\3\2\2\2KL\3" +
                    "\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2NB\3\2\2\2NJ\3\2\2\2O\32\3\2\2\2P" +
                    "R\t\5\2\2QP\3\2\2\2RS\3\2\2\2SQ\3\2\2\2ST\3\2\2\2TU\3\2\2\2UV\b\16\2\2" +
                    "V\34\3\2\2\2WX\7\61\2\2XY\7\61\2\2Y]\3\2\2\2Z\\\13\2\2\2[Z\3\2\2\2\\_" +
                    "\3\2\2\2]^\3\2\2\2][\3\2\2\2^`\3\2\2\2_]\3\2\2\2`a\7\f\2\2ab\3\2\2\2b" +
                    "c\b\17\2\2c\36\3\2\2\2\b\2FLNS]\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
            null, "'='", "'{'", "'}'", "'inc'", "';'", "'m2o in'", "'in'", "'random'",
            "','", "'&'", "'.'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, null, null,
            "String", "WS", "SL_COMMENT"
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