package dataProducer;

import java.util.*;

public class SequenceResolver {

    public List<String> sequence = new ArrayList<>();
    public List<String> deCasCache = new ArrayList<>();

    //如果这个方法调用结束sequence还是空的话，可能有循环依赖了
    public void getResidueFromTables() {
        List<String> cache = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : Cascading.casCacheTable.entrySet()) {
            cache.add(entry.getKey());
        }
        for (String table : Table.tables) {
            if(!cache.contains(table)){
                sequence.add(table);
                deCasCache.add(table);
            }
        }
        while(!Cascading.casCacheTable.isEmpty()){
            getResidueFromCascading();
        }
    }

    public void getResidueFromCascading(){
        Cascading.deCascade(deCasCache);
        List<String> relierrsWithoutReliereds = Cascading.getReliersWithoutReliereds();
        sequence.addAll(relierrsWithoutReliereds);
        deCasCache = new ArrayList<>();
        deCasCache.addAll(relierrsWithoutReliereds);
    }
}
