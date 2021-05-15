package com.wdbyte.lab.rmi;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author niulang
 * @date 2021/04/04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -5176281163098592499L;
    private String name;
    private Integer age;
}
