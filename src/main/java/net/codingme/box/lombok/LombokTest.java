package net.codingme.box.lombok;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * LomBok使用
 *
 * @Author niujinpeng
 * @Date 2018/12/10 11:05
 */
@Slf4j
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

//@Getter
//@Setter
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class Person {
    private String name;
    private Integer age;
    private boolean isTeacher;
}