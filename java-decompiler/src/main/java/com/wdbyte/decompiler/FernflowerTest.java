package com.wdbyte.decompiler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.jboss.windup.decompiler.api.DecompilationFailure;
import org.jboss.windup.decompiler.api.DecompilationListener;
import org.jboss.windup.decompiler.api.DecompilationResult;
import org.jboss.windup.decompiler.fernflower.FernflowerDecompiler;

/**
 * @author darcy
 * @Date 2021/05/16
 */
public class FernflowerTest {

    public static void main(String[] args) {
        Long time = fernflower("decompiler.jar", "fernflower_output_jar");
        System.out.println(String.format("decompiler time: %dms", time));
    }

    public static Long fernflower(String source, String targetPath) {
        long start = System.currentTimeMillis();
        Path outDir = Paths.get(targetPath);
        Path archive = Paths.get(source);
        FernflowerDecompiler decompiler = new FernflowerDecompiler();
        DecompilationResult res = decompiler.decompileArchive(archive, outDir, new DecompilationListener() {
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
        decompiler.close();
        Long end = System.currentTimeMillis();
        return end - start;
    }
}
