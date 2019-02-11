package utils;

import org.junit.Test;

import java.io.*;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by szj on 2016/6/24.
 */
public class FileScanner {

    private final static String ROOT = "D:\\data\\data";
    private final static Stack<File> STACK = new Stack<>();
    private final String DATE = "10100311.000";
    private final String OUTPUT = "D:\\tq.txt";
    private final String seq = "50349";
    StringBuilder buffer = new StringBuilder();
    File outPutFile = new File(OUTPUT);

    @Test
    public void main(){
        scan(ROOT);
    }

    private void scan(String root){
        STACK.push(new File(root));
        while(STACK.size() != 0){
            File file = STACK.pop();
            if(file.isDirectory()){
                File[] files = scanDirectory(file);
                for(File f : files){
                    STACK.push(f);
                }
            }else{
                scanFile2(file);
            }
        }
        try{
            write(outPutFile, buffer.toString());
        }catch (IOException e){
            System.out.print(e.getMessage());
        }
    }

    private File[] scanDirectory(File directory){
        File[] files = directory.listFiles();
        return files == null ? new File[0] : files;
    }

    private void scanFile(File file) {
        try{
            if(file.getName().equals(DATE)){
                InputStreamReader  reader = new InputStreamReader (new FileInputStream(file), "gbk");
                while (reader.ready()) {
                    buffer.append((char) reader.read());
                }
            }
        }catch (IOException e){
            System.out.print(e.getMessage());
        }
    }

    private void scanFile2(File file){

        try {
            InputStreamReader  isr = new InputStreamReader (new FileInputStream(file), "gbk");
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while(line != null){
                if(line.contains(seq)){
                    buffer.append(line).append("\r\n");
                }
                line = br.readLine();
            }
        }catch (IOException e){
            System.out.print(e.getMessage());
        }
    }

    private void write(File file, String context) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
        writer.print(context);
        writer.close();
    }
}
