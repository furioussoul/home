package dataProducer;

import java.util.*;

/**
 * Created by szj on 2016/7/10.
 */
public class Cascading {
    public static Map<String, List<String>> casCacheTable = new HashMap<>();

    public static void cascadeTable(String relier, String reliered) {
        if (ifExistsReliereds(relier)) {
            casCacheTable.get(relier).add(reliered);
        } else {
            List<String> reliereds = new ArrayList<String>();
            reliereds.add(reliered);
            casCacheTable.put(relier, reliereds);
        }
    }

    public static boolean ifExistsReliereds(String relier) {
        return casCacheTable.containsKey(relier);
    }

    public static void deCascade(List<String> reliereds) {

        for (String reliered : reliereds) {
            Iterator<Map.Entry<String, List<String>>> iterator = casCacheTable.entrySet().iterator();
            while (iterator.hasNext()) {
                List<String> tempList = iterator.next().getValue();
                if (tempList.contains(reliered)) {
                    tempList.remove(reliered);
                }
            }
        }
    }

    public static List<String> getReliersWithoutReliereds() {
        List<String> reliersWithoutReliereds = new ArrayList<>();
        List<String> deleteBuffer = new ArrayList<>();
        Iterator<Map.Entry<String, List<String>>> iterator = casCacheTable.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            if (entry.getValue().isEmpty()) {
                reliersWithoutReliereds.add(entry.getKey());
                deleteBuffer.add(entry.getKey());
            }
        }
        for(String s : deleteBuffer){
            casCacheTable.remove(s);
        }
        return reliersWithoutReliereds;
    }
}
