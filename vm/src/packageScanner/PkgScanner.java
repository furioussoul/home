package packageScanner;

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
	/**
	 * ����
	 */
	private String pkgName;

	/**
	 * ����Ӧ��·����
	 */
	private String pkgPath;

	/**
	 * ע���class����
	 */
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

	/**
	 * ִ��ɨ�����.
	 *
	 * @return
	 * @throws IOException
	 */
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

	public void setAnnocation(Class an) {
		this.anClazz = an;
	}

	private List<String> loadResource() throws IOException {
		List<String> list = null;

		Enumeration<URL> urls = cl.getResources(pkgPath);//���������ȡ������Ӧ�����ļ��İ����ڵ�·��url
		while (urls.hasMoreElements()) {
			URL u = urls.nextElement();//���ļ���url��������java�ļ���			
			ResourceType type = determineType(u);//�ж���jar����file��java���ļ�

			switch (type) {
			case JAR:
				String path = PathUtils.distillPathFromJarURL(u.getPath());
				list = scanJar(path);
				break;

			case FILE:
				list = scanFile(java.net.URLDecoder.decode(u.getPath(), "utf-8"), pkgName);//�����java�ļ�
				break;
			}
		}

		return list;
	}

	/**
	 * ����URL�ж���JAR�������ļ�Ŀ¼
	 * 
	 * @param url
	 * @return
	 */
	private ResourceType determineType(URL url) {
		if (url.getProtocol().equals(ResourceType.FILE.getTypeString())) {
			return ResourceType.FILE;
		}

		if (url.getProtocol().equals(ResourceType.JAR.getTypeString())) {
			return ResourceType.JAR;
		}

		throw new IllegalArgumentException("��֧�ָ�����:" + url.getProtocol());
	}

	/**
	 * ɨ��JAR�ļ�
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
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

	/**
	 * ɨ���ļ�Ŀ¼�µ���
	 * ��ʵɨ���ǰ����Ӧ�����ļ��İ�
	 * @param path
	 * @return
	 */
	private List<String> scanFile(String path, String basePkg) {
		File f = new File(path);

		List<String> classNameList = new ArrayList<>(10);

		// �õ�Ŀ¼�������ļ�(Ŀ¼)
		File[] files = f.listFiles();
		if (null != files) {
			int LEN = files.length;

			for (int ix = 0; ix < LEN; ++ix) {
				File file = files[ix];

				// �ж��Ƿ���һ��Ŀ¼
				if (file.isDirectory()) {
					// �ݹ����Ŀ¼, com.furious.cn�������ݽ�
					List<String> list = scanFile(file.getAbsolutePath(),
							PathUtils.concat(basePkg, ".", file.getName()));
					classNameList.addAll(list);

				} else if (file.getName().endsWith(ResourceType.CLASS_FILE.getTypeString())) {
					// �������.class��β
					String className = PathUtils.trimSuffix(file.getName());
					// �����������"$"����������
					if (-1 != className.lastIndexOf("$")) {
						continue;
					}

					// �����ļ�������list
					String result = PathUtils.concat(basePkg, ".", className);
					classNameList.add(result);
				}
			}
		}

		return classNameList;
	}

	/**
	 * ���ǵ�û��ָ��ע�����
	 * 
	 * @param classList
	 * @return
	 */
	private List<String> filterComponents(List<String> classList) {
		List<String> newList = new ArrayList<>(20);
		
		if(classList == null){
			return null;
		}
		
		for(String name : classList){
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
