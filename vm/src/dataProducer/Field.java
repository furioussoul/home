package dataProducer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szj on 2016/7/11.
 */
public class Field {

    public static Map<String, Field> fieldsValue = new HashMap<>();
    public static Map<String, Field> fieldsRange = new HashMap<>();
    public String tableName;
    public String filedName;
    public List<String> fieldValues;
    public String range;

    public Field() {
    }
    public Field(String tableName, String filedName, String range) {
        this.tableName = tableName;
        this.filedName = filedName;
        this.range = range;
    }
    public Field(String tableName, String filedName, List<String> fieldValues) {
        this.tableName = tableName;
        this.filedName = filedName;
        this.fieldValues = fieldValues;
    }

    public static void addFieldValue(Field field) {
        fieldsValue.put(field.tableName, field);
    }

    public static Field getFieldValue(String tableName) {
        return fieldsValue.get(tableName);
    }

    public static void addFieldRange(Field field) {
        fieldsValue.put(field.tableName, field);
    }

    public static Field getFieldRange(String tableName) {
        return fieldsValue.get(tableName);
    }
}
