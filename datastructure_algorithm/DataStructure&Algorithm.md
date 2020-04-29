<!-- TOC -->



<!-- /TOC -->

## 数据结构与算法

往往有人把数据结构与算法混为一谈，以为学习数据结构就是在学习算法，或者以为学习算法
就是在学习数据结构，这样的理解是不对的。

数据结构与算法属于两种不同的学科，数据结构主要讲的是数据存储的问题，而算法主要讲的
是数据处理的问题，这种学科的内容是不一样的。不过既然都涉及都数据层面，
且有些数据结构是随着某种算法的诞生而被发明出来的，所以前辈计算机科学家们把它们归为一门课程。

````text
如有错误之处，请多多指教
````

PS:部分的图片来源于网上，如有侵权，请联系俺，俺会立刻删除。

此外关于数据结构与算法,我推荐一个非常好的可视化网站: 
[数据结构与算法可视化](https://www.cs.usfca.edu/~galles/visualization/Algorithms.html)


## 数据结构

数据结构是计算机存储数据的方式，不同类型的数据，其存储数据的方式也各不相同。
我们的图片，音视频等内容以何种方式存储在磁盘上，就取决于这种类型的数据采用了什么样的数据结构。

数据结构按逻辑划分，大致可以分为线性数据结构和非线性数据结构(包括树，图等)。如果按具体的存储结构划分，
还可以分为顺序存储结构，散列存储结构，索引存储结构等等。


### 线性数据结构

**线性数据结构**是一个有序数据元素的集合，其中数据元素之间的关系是一对一的关系。
常用的线性结构有：**线性表，栈，队列，双队列，数组，串**等。


#### 线性表
线性表(list)是最基本的数据结构，它是由零个或多个数据元素组成的有序集合，
这个有序不是指的排序后的有序，而是指添加元素的顺序，假如2在1之前被放入了集合中，那么2肯定在1前面，不会因为大小原因而
改变他们在集合中的顺序。

链表的实现方式主要有数组和链表。

数组实现的线性表，访问速度是其最大的优点，但由于数组的容量是固定的，所以如果采用数组作为线性表的实现，
那么就涉及到扩容，调整元素位置等问题，这些操作是非常消耗性能的，因此数组实现的线性表适用于读多写少的环境。

链表可以分单向链表和双线链表。
单向链表的每个节点都保存了指向下一个节点的引用(称为next)，单向链表的尾节点的下一个节点
的引用为null。

双向链表的每个节点都保存了指向它前一个节点(previous)和后一个节点的引用(next),双向链表的首节点的
previous为尾节点，尾节点的next是首节点。

无论是单链表和双链表，因为每个节点都保存了其他节点的引用，所以是比较消耗内存的。

链表实现的线性表，增删改操作的性能是其最大的优点，但链表并不支持下标这种RandomAccess随机访问的方式，
所以链表实现的线性表适用于写多读少的环境。

链表:

![单向链表和双向链表](../img/datastructure_algorithm/单向链表和双向链表.png)


线性表实现源码(链式队列使用的是双向链表实现的):

- [ArrayList](https://github.com/guang19/framework-learning/blob/dev/datastructure_algorithm/src/main/java/com/github/anhTom2000/datastructure/list/ArrayList.java)

- [LinkedList](https://github.com/guang19/framework-learning/blob/dev/datastructure_algorithm/src/main/java/com/github/anhTom2000/datastructure/list/LinkedList.java)


#### 队列

队列（queue）是一种采用先进先出(FIFO)策略的抽象数据结构，即最先进队列的数据元素，
同样要最先出队列。队列跟我们排队买票一样，先来排队的肯定先买票，后来排队的的后买到票。

队列有两个重要的概念，一个叫队首，一个叫队尾，队首指向的是第一个元素，而队尾指向的是最后一个元素。
队列也只有两个主要的操作：入队(enqueue)操作 和 出队(dequeue)操作 。
入队操作就是将一个元素添加到队尾，出队操作就是取出队首的元素。

队列的底层实现可以用数组和链表，基于数组实现的队列叫作顺序队列，基于链表实现的队列叫作链式队列。

队列:

![简单队列](../img/datastructure_algorithm/简单队列.png)

对于普通的队列来说，其容量肯定是有限制的，当队列满了之后，如何能够再次使用呢？
这就是循环队列的作用了。

队列实现源码:

- [循环队列](https://github.com/guang19/framework-learning/blob/dev/datastructure_algorithm/src/main/java/com/github/anhTom2000/datastructure/queue/ArrayCircleQueue.java)

- [链式队列](https://github.com/guang19/framework-learning/blob/dev/datastructure_algorithm/src/main/java/com/github/anhTom2000/datastructure/list/LinkedList.java)

**PS:关于队列，可以参考: [掘金 - 队列](https://juejin.im/post/5d5fb74fe51d45620346b8d0) , 写得很详细。**

### 非线性数据结构
**非线性数据结构**中各个数据元素不再保持在一个线性序列中，每个数据元素可能与零个或者多个其他数据元素发生联系。
根据关系的不同，可分为层次结构和群结构。

常见的非线性数据结构主要有矩阵(多维数组)，广义表，树，图等。

#### 稀疏数组
当一个数组中大部分元素为0，或者为同一个值的数组时，可以使用稀疏数组来保存该数组。

处理方法：

1.记录数组有几行几列，有多少个不同的值。

2.把具有不同值的元素的行列及值记录在一个更小规模的数组中。实现程序的压缩。

如图所示: 有大部分的空间是无用的.

![稀疏数组](../img/datastructure_algorithm/稀疏数组.png)

那么我们可以使用稀疏数组进行压缩,稀疏数组的第一部分所记录的是原数组的行数和列数

第二部分记录原数组中的有效值的位置和值,经过压缩后只需要声明n*3的数组,n表示有效值的个数

![稀疏数组2](../img/datastructure_algorithm/稀疏数组2.png)


---


## 算法

### 排序算法

冒泡排序源码: [BubbleSort](https://github.com/guang19/framework-learning/blob/dev/datastructure_algorithm/src/main/java/com/github/anhTom2000/algorithm/sort/BubbleSort.java)
