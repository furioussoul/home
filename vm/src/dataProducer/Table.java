package dataProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szj on 2016/7/11.
 */
public class Table {
    public static List<String> tables = new ArrayList<>();

    public static void add(String table){
        tables.add(table);
    }
}
