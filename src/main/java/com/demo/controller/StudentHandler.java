package com.demo.controller;

import com.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import java.sql.ClientInfoStatus;
import java.util.List;
import java.util.Set;

@RestController
public class StudentHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/set")
    //RequestBody将客户端的数据转换成Java对象
    public void set(@RequestBody Student student) {


        System.out.println(student + "前端获取到的数据");

        //  opsForValue,意思是将k-v合成一个对象
        //往redis里存数据，redis会往key前面加序列化的字符串
        redisTemplate.opsForValue().set("student", student);
    }

    @GetMapping("/get/{key}")
    public Student get(@PathVariable("key") String key) {
        //反序列化操作
        return (Student) redisTemplate.opsForValue().get(key);
    }

    @DeleteMapping("/delete/{key}")
    public boolean delete(@PathVariable("key") String key) {
        redisTemplate.delete(key);
        return redisTemplate.hasKey(key);
    }


    @GetMapping("/string")
    public String stringTest() {
        redisTemplate.opsForValue().set("str", "Hello world");
        String str = (String) redisTemplate.opsForValue().get("str");
        return str;

    }

    @GetMapping("/list")
    public List<String> listTest() {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush("list", "hello");
        listOperations.leftPush("list", "world");
        listOperations.leftPush("list", "java");
        List<String> list = listOperations.range("list", 0, 2);
        return list;
        //先进后出
    }

    @GetMapping("/set1")
    public Set<String> setTest() {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        setOperations.add("set", "hello");
        setOperations.add("set", "hello");
        setOperations.add("set", "world");
        setOperations.add("set", "world");
        setOperations.add("set", "java");
        setOperations.add("set", "java");
        Set<String> set = setOperations.members("set");
        return set;
    }

    @GetMapping("/zset")
    public Set<String> zsetTest() {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("zset", "hello", 1);
        zSetOperations.add("zset", "world", 2);
        zSetOperations.add("zset", "java", 3);
        Set<String> set = zSetOperations.range("zset", 0, 2);
        return set;
    }

    @GetMapping("/hash")
    public void hashTest(){
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        hashOperations.put("key","hashkey","hello");
        System.out.println(hashOperations.get("key","hashkey"));
    }
}