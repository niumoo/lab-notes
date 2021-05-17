package com.wdbyte.decompiler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.jboss.windup.decompiler.api.DecompilationFailure;
import org.jboss.windup.decompiler.api.DecompilationListener;
import org.jboss.windup.decompiler.api.DecompilationResult;
import org.jboss.windup.decompiler.api.Decompiler;
import org.jboss.windup.decompiler.procyon.ProcyonDecompiler;

/**
 * Procyon 反编译测试
 *
 *  @author https://github.com/niumoo
 * @date 2021/05/15
 */
public class ProcyonTest {
    public static void main(String[] args) throws IOException {
        Long time = procyon("decompiler.jar", "procyon_output_jar");
        System.out.println(String.format("decompiler time: %dms", time));
    }
    public static Long procyon(String source,String targetPath) throws IOException {
        long start = System.currentTimeMillis();
        Path outDir = Paths.get(targetPath);
        Path archive = Paths.get(source);
        Decompiler dec = new ProcyonDecompiler();
        DecompilationResult res = dec.decompileArchive(archive, outDir, new DecompilationListener() {
            public void decompilationProcessComplete() {
                System.out.println("decompilationProcessComplete");
            }
            public void decompilationFailed(List<String> inputPath, String message) {
                System.out.println("decompilationFailed");
            }
            public void fileDecompiled(List<String> inputPath, String outputPath) {
            }
            public boolean isCancelled() {
                return false;
            }
        });

        if (!res.getFailures().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed decompilation of " + res.getFailures().size() + " classes: ");
            Iterator failureIterator = res.getFailures().iterator();
            while (failureIterator.hasNext()) {
                DecompilationFailure dex = (DecompilationFailure)failureIterator.next();
                sb.append(System.lineSeparator() + "    ").append(dex.getMessage());
            }
            System.out.println(sb.toString());
        }
        System.out.println("Compilation results: " + res.getDecompiledFiles().size() + " succeeded, " + res.getFailures().size() + " failed.");
        dec.close();
        Long end = System.currentTimeMillis();
        return end - start;
    }
}
