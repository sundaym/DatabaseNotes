package com.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jpa_customer")
public class Customer {

    /**
     * IDENTITY, 采用ID自增的方式自增主键, Oracle不支持
     * AUTO, JPA自动选择合适的策略, 默认选项
     * SEQUENCE, 通过序列产生主键, 通过@SequenceGenerator指定序列名, MySQL不支持
     * TABLE, 通过表产生主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // JPA自动选择一个最适合底层数据库的主键生成策略
    private Integer id;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
