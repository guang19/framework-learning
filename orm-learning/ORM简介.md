# ORM(Object Relational Mapping)

### 什么是ORM?

Object Relational Mapping : 对象关系映射。
它是一种解决数据库与简单对象(entity)之间关系映射的技术。

简单理解: ORM通过简单对象(entity) 与 数据库之间的映射关系，将描述对象持久化到数据库中。

#### JDBC的缺点

- 频繁创建数据库连接,浪费连接资源,不易维护

- SQL语句存在硬编码，不易维护

- 结果集处理过程繁琐