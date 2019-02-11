package ioc.util;

import annotation.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PathUtils {

    private PathUtils() {
    }

    public static String pathToPackage(String path) {
        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        return path.replaceAll("/", ".");
    }

    public static String packageToPath(String pkg) {
        return pkg.replace(".", File.separator);
    }

    public static String concat(Object... objs) {
        StringBuilder sb = new StringBuilder(30);
        for (int ix = 0; ix < objs.length; ++ix) {
            sb.append(objs[ix]);
        }

        return sb.toString();
    }

    public static String trimSuffix(String name) {
        int dotIndex = name.indexOf('.');
        if (-1 == dotIndex) {
            return name;
        }

        return name.substring(0, dotIndex);
    }

    public static String distillPathFromJarURL(String url) {
        int startPos = url.indexOf(':');
        int endPos = url.lastIndexOf('!');

        return url.substring(startPos + 1, endPos);
    }

    public static void main(String[] args) throws IOException {
        String s = "com.ly.base.training.lesson77.domain";
        PkgScanner scanner = new PkgScanner(s, Component.class);
        List<String> clazzUrlList = scanner.scan();
        if (clazzUrlList == null) {
            return;
        }
        for (String clazzUrl : clazzUrlList) {
            System.out.println(clazzUrl);
        }

    }
}
