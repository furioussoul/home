package utils;

import org.junit.Test;
import packageScanner.PkgScanner;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by szj on 2016/6/16.
 */
public class ConvertIntoTXT {

    private static final String INPUT_DIRECTORY = "D:\\workspace\\ZDCRM\\";
    private static final String OUTPUT_DIRECTORY = "D:\\out\\";
    private static final int LINE_NUMBER = 60;
    private static int counter = 0;
    @Test
    public void main() throws IOException {
        String packName = "com.kashuo.crm";
        PkgScanner scanner = new PkgScanner(packName, Cut.class);
        List<String> list =  scanner.scan();
        for(String s : list){
            convertFile(parsePath(s));
        }
        System.out.println(counter);
    }

    private String parsePath(String classPath){
        return INPUT_DIRECTORY + "src\\main\\java\\" + classPath.replace('.','\\') + ".java";
    }

    public void convertFile(String filePath) throws IOException {
        File file = new File(filePath);
        System.out.println("----converting : " + file.getName() + "----");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder buffer = new StringBuilder();
        String line = null;
        int i = 0;
        int j = 0;
        while ((line = reader.readLine()) != null) {
            i++;
            buffer.append(line + "\r\n");
            if(i == LINE_NUMBER){
                convertLines(file.getName().substring(
                        0, file.getName().lastIndexOf('.')) + ++j + ".txt", buffer.toString());
                i=0;
                buffer.setLength(0);
            }
        }
        reader.close();
        if(buffer.length() != 0){
            convertLines(file.getName().substring(
                    0, file.getName().lastIndexOf('.')) + ++j + ".txt", buffer.toString());
        }
//        File outPutFile = new File(OUTPUT_DIRECTORY + "\\" + file.getName().substring(0, file.getName().lastIndexOf('.')) + ".txt");
//        write(outPutFile, buffer.toString());
    }

    public void convertLines(String path, String content) throws FileNotFoundException {
        File outPutFile = new File(OUTPUT_DIRECTORY + "\\" + path);
        write(outPutFile, content);
        counter++;
    }

    private void write(File file, String context) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
        writer.print(context);
        writer.close();
    }

    public static class DirFilte implements FilenameFilter {
        private Pattern pattern;

        public DirFilte(String regex) {
            this.pattern = Pattern.compile(regex);
        }

        @Override
        public boolean accept(File dir, String name) {
            return pattern.matcher(name).matches();
        }
    }
}
