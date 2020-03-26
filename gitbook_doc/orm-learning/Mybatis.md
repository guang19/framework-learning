# Mybatis

### 什么是Mybatis?
**Mybatis是一款优秀的轻量级的半ORM框架。**
Mybatis最大的优点就是无需像JDBC一样采用硬编码的方式进行持久化操作，
它允许我们定制SQL和对象与数据库之间的高级映射关系，极大的提高了持久化操作的灵活性。


#### 为什么说Mybatis是半ORM框架?
与Hibernate不同，Hibernate属于全自动ORM框架，无需手写SQL，
且能够自动建立对象与数据库之间的映射关系，很方便。
但**无需手写SQL也就意味着SQL优化方面可能不如Mybatis那么出色。**

Mybatis则属于半自动ORM框架，因为Mybatis仅仅给我们省去了JDBC硬编码的形式，
但是在定义SQL和建立对象持久化关系方面，仍然给了我们很大自由，
**它相对Hibernate更加灵活，扩展性更强。**