package ioc.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PkgScanner {

    private String pkgName;

    private String pkgPath;

    private Class anClazz;

    private ClassLoader cl;

    public PkgScanner(String pkgName) {
        this.pkgName = pkgName;
        this.pkgPath = PathUtils.packageToPath(pkgName);

        cl = Thread.currentThread().getContextClassLoader();
    }

    public PkgScanner(String pkgName, Class anClazz) {
        this(pkgName);

        this.anClazz = anClazz;
    }

    public List<String> scan() throws IOException {
        List<String> list = loadResource();
        if (null != this.anClazz) {
            list = filterComponents(list);
        }

        return list;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
        this.pkgPath = PathUtils.packageToPath(pkgName);
    }

    public void setAnnotation(Class an) {
        this.anClazz = an;
    }

    private List<String> loadResource() throws IOException {
        List<String> list = null;

        Enumeration<URL> urls = cl.getResources(pkgPath);
        while (urls.hasMoreElements()) {
            URL u = urls.nextElement();
            ResourceType type = determineType(u);

            switch (type) {
                case JAR:
                    String path = PathUtils.distillPathFromJarURL(u.getPath());
                    list = scanJar(path);
                    break;

                case FILE:
                    list = scanFile(java.net.URLDecoder.decode(u.getPath(), "utf-8"), pkgName);
                    break;
            }
        }

        return list;
    }

    private ResourceType determineType(URL url) {
        if (url.getProtocol().equals(ResourceType.FILE.getTypeString())) {
            return ResourceType.FILE;
        }

        if (url.getProtocol().equals(ResourceType.JAR.getTypeString())) {
            return ResourceType.JAR;
        }

        throw new IllegalArgumentException(url.getProtocol());
    }

    private List<String> scanJar(String path) throws IOException {
        JarFile jar = new JarFile(path);

        List<String> classNameList = new ArrayList<>(20);

        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();

            if ((name.startsWith(pkgPath)) && (name.endsWith(ResourceType.CLASS_FILE.getTypeString()))) {
                name = PathUtils.trimSuffix(name);
                name = PathUtils.pathToPackage(name);

                classNameList.add(name);
            }
        }

        return classNameList;
    }

    private List<String> scanFile(String path, String basePkg) {
        File f = new File(path);

        List<String> classNameList = new ArrayList<>(10);

        File[] files = f.listFiles();
        if (null != files) {
            int LEN = files.length;

            for (int ix = 0; ix < LEN; ++ix) {
                File file = files[ix];

                if (file.isDirectory()) {
                    List<String> list = scanFile(file.getAbsolutePath(),
                            PathUtils.concat(basePkg, ".", file.getName()));
                    classNameList.addAll(list);

                } else if (file.getName().endsWith(ResourceType.CLASS_FILE.getTypeString())) {
                    String className = PathUtils.trimSuffix(file.getName());
                    if (-1 != className.lastIndexOf("$")) {
                        continue;
                    }

                    String result = PathUtils.concat(basePkg, ".", className);
                    classNameList.add(result);
                }
            }
        }

        return classNameList;
    }

    private List<String> filterComponents(List<String> classList) {
        List<String> newList = new ArrayList<>(20);

        if (classList == null) {
            return null;
        }

        for (String name : classList) {
            try {
                Class clazz = Class.forName(name);
                Annotation an = clazz.getAnnotation(this.anClazz);
                if (null != an) {
                    newList.add(name);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return newList;
    }
}
