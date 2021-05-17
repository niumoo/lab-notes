package com.wdbyte.decompiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jd.core.v1.ClassFileToJavaSourceDecompiler;
import org.jd.core.v1.api.loader.Loader;
import org.jd.core.v1.api.printer.Printer;

/**
 * @author https://github.com/niumoo
 * @date 2021/05/15
 */
public class JDCoreTest {

    public static void main(String[] args) throws Exception {
        JDCoreDecompiler jdCoreDecompiler = new JDCoreDecompiler();
        Long time = jdCoreDecompiler.decompiler("decompiler.jar","jd_output_jar");
        System.out.println(String.format("decompiler time: %dms", time));
    }
}


class JDCoreDecompiler{

    private ClassFileToJavaSourceDecompiler decompiler = new ClassFileToJavaSourceDecompiler();
    // 存放字节码
    private HashMap<String,byte[]> classByteMap = new HashMap<>();

    /**
     * 注意：没有考虑一个 Java 类编译出多个 Class 文件的情况。
     *
     * @param source
     * @param target
     * @return
     * @throws Exception
     */
    public Long decompiler(String source,String target) throws Exception {
        long start = System.currentTimeMillis();
        // 解压
        archive(source);
        for (String className : classByteMap.keySet()) {
            String path = StringUtils.substringBeforeLast(className, "/");
            String name = StringUtils.substringAfterLast(className, "/");
            if (StringUtils.contains(name, "$")) {
                name = StringUtils.substringAfterLast(name, "$");
            }
            name = StringUtils.replace(name, ".class", ".java");
            decompiler.decompile(loader, printer, className);
            String context = printer.toString();
            Path targetPath = Paths.get(target + "/" + path + "/" + name);
            if (!Files.exists(Paths.get(target + "/" + path))) {
                Files.createDirectories(Paths.get(target + "/" + path));
            }
            Files.deleteIfExists(targetPath);
            Files.createFile(targetPath);
            Files.write(targetPath, context.getBytes());
        }
        return System.currentTimeMillis() - start;
    }
    private void archive(String path) throws IOException {
        try (ZipFile archive = new JarFile(new File(path))) {
            Enumeration<? extends ZipEntry> entries = archive.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory()) {
                    String name = entry.getName();
                    if (name.endsWith(".class")) {
                        byte[] bytes = null;
                        try (InputStream stream = archive.getInputStream(entry)) {
                            bytes = IOUtils.toByteArray(stream);
                        }
                        classByteMap.put(name, bytes);
                    }
                }
            }
        }
    }

    private Loader loader = new Loader() {
        @Override
        public byte[] load(String internalName) {
            return classByteMap.get(internalName);
        }
        @Override
        public boolean canLoad(String internalName) {
            return classByteMap.containsKey(internalName);
        }
    };

    private Printer printer = new Printer() {
        protected static final String TAB = "  ";
        protected static final String NEWLINE = "\n";
        protected int indentationCount = 0;
        protected StringBuilder sb = new StringBuilder();
        @Override public String toString() {
            String toString = sb.toString();
            sb = new StringBuilder();
            return toString;
        }
        @Override public void start(int maxLineNumber, int majorVersion, int minorVersion) {}
        @Override public void end() {}
        @Override public void printText(String text) { sb.append(text); }
        @Override public void printNumericConstant(String constant) { sb.append(constant); }
        @Override public void printStringConstant(String constant, String ownerInternalName) { sb.append(constant); }
        @Override public void printKeyword(String keyword) { sb.append(keyword); }
        @Override public void printDeclaration(int type, String internalTypeName, String name, String descriptor) { sb.append(name); }
        @Override public void printReference(int type, String internalTypeName, String name, String descriptor, String ownerInternalName) { sb.append(name); }
        @Override public void indent() { this.indentationCount++; }
        @Override public void unindent() { this.indentationCount--; }
        @Override public void startLine(int lineNumber) { for (int i=0; i<indentationCount; i++) sb.append(TAB); }
        @Override public void endLine() { sb.append(NEWLINE); }
        @Override public void extraLine(int count) { while (count-- > 0) sb.append(NEWLINE); }
        @Override public void startMarker(int type) {}
        @Override public void endMarker(int type) {}
    };
}
