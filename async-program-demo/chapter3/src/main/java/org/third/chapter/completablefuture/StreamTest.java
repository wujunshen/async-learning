package org.third.chapter.completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

  public static void useStream(List<Person> personList) {
    List<String> nameList =
        personList.stream()
            // 1.过滤大于等于10的
            .filter(person -> person.getAge() >= 10)
            // 2.使用map映射元素
            .map(Person::getName)
            // 3.收集映射后元素
            .collect(Collectors.toList());

    nameList.forEach(name -> log.info("{}", name));
  }

  public static void noStream(List<Person> personList) {
    List<String> nameList = new ArrayList<>();

    for (Person person : personList) {
      if (person.age >= 10) {
        nameList.add(person.getName());
      }
    }

    for (String name : nameList) {
      log.info("{}", name);
    }
  }

  public static void main(String[] args) {
    List<Person> personList = makeList();

    noStream(personList);
  }

  @Data
  static class Person {
    private String name;
    private int age;
  }
}
