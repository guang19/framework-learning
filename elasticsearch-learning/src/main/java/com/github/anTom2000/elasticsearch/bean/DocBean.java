package com.github.anTom2000.elasticsearch.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @Description : TODO   测试实体类
 * @Author : Weleness
 * @Date : 2020/05/31
 */

@Document(indexName = "docbean",type = "com/github/anTom2000/elasticsearch/bean",shards = 1,replicas = 0)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DocBean implements Serializable {

    private static final long serialVersionUID = 2979206148986646100L;

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Keyword)
    private String password;
    @Field(type = FieldType.Integer)
    private Integer age;
    @Field(type = FieldType.Keyword)
    private String content;

}
