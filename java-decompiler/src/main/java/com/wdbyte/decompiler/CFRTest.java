package com.wdbyte.decompiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.util.getopt.OptionsImpl;

/**
 * CFR Test
 *
 * @author https://github.com/niumoo
 * @date 2021/05/15
 */
public class CFRTest {

    public static void main(String[] args) throws IOException {
        Long time = cfr("decompiler.jar", "./cfr_output_jar");
        System.out.println(String.format("decompiler time: %dms", time));
    }

    public static Long cfr(String source, String targetPath) throws IOException {
        Long start = System.currentTimeMillis();
        // source jar
        List<String> files = new ArrayList<>();
        files.add(source);
        // target dir
        HashMap<String, String> outputMap = new HashMap<>();
        outputMap.put("outputdir", targetPath);

        OptionsImpl options = new OptionsImpl(outputMap);
        CfrDriver cfrDriver = new CfrDriver.Builder().withBuiltOptions(options).build();
        cfrDriver.analyse(files);
        Long end = System.currentTimeMillis();
        return (end - start);
    }

}
