package net.codingme.box.lab;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * LomBok使用
 *
 * @Author niujinpeng
 * @Date 2018/12/10 11:05
 */
@Slf4j(topic = "Lombok")
public class LombokTest {

    public static void main(String[] args) {
        Person person1 = new Person();
        person1.setName("Darcy");
        person1.setAge(22);
        person1.setTeacher(true);
        log.info(person1.toString());

        Person person2 = new Person("Darcy", 22, true);
        log.info(person2.toString());

        boolean equals = person1.equals(person2);
        log.info("Equals:" + equals);

        log.info(person1.hashCode() + " and " + person2.hashCode());
    }

}

/**
 * @Getter/@Setter 为属性生成 get 和 set 方法。
 * @ToString 生成 toString 方法，输出各个属性值。
 * @EqualsAndHashCode 生成 equals 和 hashCode 方法。
 * @NoArgsConstructor 生成无惨构造器。
 * @AllArgsConstructor 生成全参数构造器。
 * @Data 是一个方便注解，它捆绑了 @ToString @Getter/@Setter  @EqualsAndHashCode 以及 @RequiredArgsConstructor.
 */
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
class Person {
    private String name;
    private Integer age;
    private boolean isTeacher;
}