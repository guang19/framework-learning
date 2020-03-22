package com.github.guang19.entity;

import java.io.Serializable;

/**
 * @Description : TODO          测试实体类
 * @Author :    yangguang
 * @Date :      2019/12/22
 */
public class Person implements Serializable {

    //序列化ID
    private static final long serialVersionUID = 336571784061779072L;

    private Long pId;

    private String pName;

    private Integer age;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

  @Override
  public String toString()
  {
    return "Person{" +
      "pId=" + pId +
      ", pName='" + pName + '\'' +
      ", age=" + age +
      '}';
  }
}
