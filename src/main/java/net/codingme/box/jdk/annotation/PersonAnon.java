package net.codingme.box.jdk.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * 自定义注解
 *
 * @interface 意味着实现了 java.lang.annotation.Annotation 接口
 * @Documented 类和方法的Annotation在缺省情况下是不出现在javadoc中的。<br/>
 *             如果使用@Documented修饰该Annotation， 则表示它可以出现在javadoc中。
 *             定义Annotation时，@Documented可有可无；若没有定义，则Annotation不会出现在javadoc中。
 * @Target 定义注解可以使用的位置。<br/>
 *         若有@Target，则该Annotation只能用于它所指定的地方；若没有@Target，则该Annotation可以用于任何地方。
 * @Retention 可有可无。若没有@Retention，则默认是RetentionPolicy.CLASS。
 *
 * @Author niujinpeng
 * @Date 2019/4/1 13:16
 */
@Documented
@Target(value = {ElementType.TYPE, ElementType.FIELD, ElementType.METHOD}) // 可用位置
@Retention(RetentionPolicy.RUNTIME) // RetentionPolicy.SOURCE OR RetentionPolicy.CLASS OR RetentionPolicy.RUNTIME
public @interface PersonAnon {

}
