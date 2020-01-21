package org.chapter5.rxjava;

import io.reactivex.Flowable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamTest {
  public static List<Person> makeList() {
    List<Person> personList = new ArrayList<>();
    Person p1 = new Person();
    p1.setAge(10);
    p1.setName("zlx");
    personList.add(p1);

    p1 = new Person();
    p1.setAge(12);
    p1.setName("jiaduo");
    personList.add(p1);

    p1 = new Person();
    p1.setAge(5);
    p1.setName("ruoran");
    personList.add(p1);
    return personList;
  }

  public static void main(String[] args) {
    // 1.创建person列表
    List<Person> personList = makeList();

    // 2.执行过滤与输出
    // 2.1转换列表为Flowable流对象
    Flowable.fromArray(personList.toArray(new Person[0]))
        // 2.2过滤
        .filter(person -> person.getAge() >= 10)
        // 2.3.映射转换
        .map(Person::getName)
        // 2.4订阅输出
        .subscribe(name -> log.info("{}", name));

    Flowable<Person> source = Flowable.fromArray(personList.toArray(new Person[0]));
    Flowable<Person> filterSource = source.filter(person -> person.getAge() >= 10);
    Flowable<String> nameSource = filterSource.map(Person::getName);
    nameSource.subscribe(name -> log.info("{}", name));
  }

  @Data
  static class Person {
    private String name;
    private int age;
  }
}
