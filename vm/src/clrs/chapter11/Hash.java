package clrs.chapter11;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szj on 2016/6/27.
 */
public class Hash {

    private Node[] list = new Node[100];

    private int hash(Object key){
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public void put(Object key, Object val){
        Node node = new Node();
        node.key = key;
        node.val = val;
        int hashCode = hash(key);
        list[hashCode] = node;
    }

    private class Node{
        Object key;
        Object val;
    }

    private class T{
        Integer age;
        String name;
        public T(int age, String name){
            this.age = age;
            this.name = name;
        }
    }

    @Test
    public void main(){
        System.out.print(new T(1,"1").hashCode()>>>16);
        Hash h = new Hash();
        h.put("1",new T(1,"1"));
        h.put("2",new T(2,"2"));
        h.put("3",new T(3,"3"));
        h.put("4",new T(4,"4"));
    }
}
