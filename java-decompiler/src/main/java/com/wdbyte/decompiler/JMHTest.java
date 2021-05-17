package com.wdbyte.decompiler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 微基准测试。
 * @author https://github.com/niumoo
 * @date 2021/05/15
 */
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Thread)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class JMHTest {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(JMHTest.class.getSimpleName())
            .build();
        new Runner(opt).run();
    }

    @Benchmark
    public Long cfr() throws IOException {
        return CFRTest.cfr("python2java4common-1.0.0-20180706.084921-1.jar", "./output_cfr");
    }

    @Benchmark
    public Long procyon() throws IOException {
        return ProcyonTest.procyon("python2java4common-1.0.0-20180706.084921-1.jar", "./output_procyon");
    }

    @Benchmark
    public Long fernflower() throws IOException {
        return FernflowerTest.fernflower("python2java4common-1.0.0-20180706.084921-1.jar", "./output_fernflower");
    }

    @Benchmark
    public Long jdcore() throws Exception {
        JDCoreDecompiler jdCoreDecompiler = new JDCoreDecompiler();
        return jdCoreDecompiler.decompiler("python2java4common-1.0.0-20180706.084921-1.jar", "./output_jdcore");
    }

}
