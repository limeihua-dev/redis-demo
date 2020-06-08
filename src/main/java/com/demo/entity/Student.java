package com.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    //如果不实现序列化接,是不能存入到redis里面的
    private Integer id;
    private String name;
    private Double score;
    private Data birthday;
}

