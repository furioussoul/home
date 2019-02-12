package dataProducer;

/**
 * Created by szj on 2016/7/14.
 */
public class Controller {
    private String preSql() {
        SequenceResolver resolver = new SequenceResolver();
        for (String seq : resolver.sequence) {
            Field field = Field.getFieldValue(seq);
            if (field == null) {
                field = Field.getFieldRange(seq);
                if (field == null) {
                    throw new RuntimeException("字段定义了却没有定义值");
                }
                return SqlFactory.createSql(field);
            } else {
                Field field0 = Field.getFieldRange(seq);
                if (field != null) {
                    field.range = field0.range;
                }
                return SqlFactory.createSql(field);
            }
        }
        return "";
    }

    private String createSql() {
        preSql();
        DataBaseMonitor.getPro("user");
        return "";
    }

    private void doCreateObj2DB() {
        DataBaseMonitor.create();
    }
}
