<!-- TOC -->

   * [AQS(AbstractQueuedSynchronizer)](#aqsabstractqueuedsynchronizer)
       * [AQS概述](#aqs概述)
       * [AQS的两种共享资源的访问方式](#aqs的两种共享资源的访问方式)
       * [lock,tryLock和lockInterruptibly区别](#locktrylock和lockinterruptibly区别)
       * [CountDownLatch](#countdownlatch)
       * [Semaphore](#semaphore)
       * [CycliBarrier](#cyclibarrier)
       * [ReentrantReadWriteLock如何区分读写锁的?](#reentrantreadwritelock如何区分读写锁的)

<!-- /TOC -->

# AQS(AbstractQueuedSynchronizer)

````text
AQS是Doug Lea大师为JDK编写的一套基于API层面的抽象队列同步器.
AbstractQueuedSynchronizer,抽象队列同步器.
Lock,CountDownLatch等等这些并发工具都是基于AQS来实现的。
由此可以看出Doug Lea大师的功力已经臻至化境
````
#### AQS概述

**AQS的核心思想是如果被请求的资源空闲，那么就将当前请求资源的线程设置为有效的工作线程;
如果请求的资源被其他线程所占有， 那么就使用CLH线程阻塞队列来提供阻塞线程并唤线程分配资源的机制。
在CLH队列中，每个请求资源的线程都会被封装成队列中的一个节点。**

**在AQS内部有一个int类型的state表示线程同步状态，
当线程lock获取到锁后，该state计数就加1,unlock就减1，
这就是为什么解锁次数要对应加锁次数的原因。**

**AQS主要实现技术为:CLH队列(Craig,Landin and Hagersten)，
自旋CAS，park(阻塞线程)以及unparkSuccessor(唤醒阻塞队列中的后继线程)。**
     
#### AQS的两种共享资源的访问方式
>AQS定义了两种共享资源方式.

1. 独占式(Exclusive): **同一时间只有一个线程可以访问共享资源,也就是独占锁。**
如:Synchronized,ReentrantLock。
**对于独占式锁的实现,在AQS中对应tryAcquire获取锁和tryRelease释放锁。**
         
* 共享式(Share): **同一时间允许多个线程同时访问共享资源,也就是共享锁。**
CountDownLatch,Semaphore,ReentrantReadWriteLock的ReadLock都是共享锁。
**对于共享式锁的实现,在AQS中对应tryAcquireShare获取锁和tryReleaseShare释放锁。**

#### lock,tryLock和lockInterruptibly区别

**PS: AQS中的锁计数指的是 state 变量。**

- lock: 如果线程获取到了锁或线程已经拥有了锁就更改锁计数，
否则线程就加入阻塞队列并一直CAS自旋获取。

- tryLock: 线程尝试获取锁，如果线程获取到了锁或线程已经拥有了锁就更改锁计数，否则返回false。

- lockInterruptibly: 如果线程在获取锁之前被设置了中断状态，那么当线程获取锁时就会响应中断状态，
抛出InterruptedException异常。如果获取不到就加入阻塞队列并自旋获取，并且阻塞自旋期间还会响应中断，
也就是说在阻塞自旋期间可能抛出InterruptedException异常。
**所以lockInterruptibly优先响应中断，而不是优先获取锁。** 
如果线程获取到了锁或线程已经拥有了锁才更改锁计数。

#### CountDownLatch
>CountDownLatch允许count个线程阻塞在一个地方，直至所有线程的任务都执行完毕。

CountDownLatch是共享锁的一种实现,**它默认构造AQS的state为count。
当线程使用countDown方法时,其实使用了tryReleaseShared方法以CAS的操作来减少state,
直至state为0就代表所有的线程都调用了countDown方法。**
当调用await方法的时候，如果state不为0，
就代表仍然有线程没有调用countDown方法，那么就把已经调用过countDown的线程都放入阻塞队列Park,
并自旋CAS判断state == 0，直至最后一个线程调用了countDown，使得state == 0，
于是阻塞的线程便判断成功，全部往下执行。

#### Semaphore
>Semaphore允许一次性最多(不是同时)permits个线程执行任务。

Semaphore与CountDownLatch一样，也是共享锁的一种实现。
**它默认构造AQS的state为permits。
当执行任务的线程数量超出permits,那么多余的线程将会被放入阻塞队列Park,并自旋判断state是否大于0。
只有当state大于0的时候，阻塞的线程才有机会继续执行,此时先前执行任务的线程继续执行release方法，
release方法使得state的变量会加1，那么自旋的线程便会判断成功。**

如此，**每次只有不超过permits个的线程能自旋成功，便限制了执行任务线程的数量。**
所以这也是我为什么说它可能不是permits个线程同时执行，
因为只要state>0,线程就有机会执行.


#### CycliBarrier
CycliBarrier的功能与CountDownLatch相似，但是**CountDownLatch的实现是基于AQS的，
而CycliBarrier是基于ReentrantLock(ReentrantLock也属于AQS同步器)和Condition的。**

CountDownLatch虽然可以令多个线程阻塞在同一代码处，但只能await一次就不能使用了。
而CycliBarrier有Generation代的概念，一个代，就代表CycliBarrier的一个循环，
这也是CycliBarrier支持重复await的原因。 

#### ReentrantReadWriteLock如何区分读写锁的?

**Sync既有写锁，又有读锁，因此一个state不够用，
所以使用state的高16为表示读锁，低位16表示写锁.**

````text

 ReentrantReadWriteLock部分源码:

 static final int SHARED_SHIFT   = 16;
 static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
 static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
 static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

 /** Returns the number of shared holds represented in count. */
 static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
 /** Returns the number of exclusive holds represented in count. */
 static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }

````

```text
剩下的就读源码吧。
```

其实吧，在我读了几遍源码后,才发现jdk的源码不算特别不难阅读。

但是像我在读SpringBoot的源码时，我就只能分析个大概。

主要是Jdk的源码之间并没有什么耦合性，你看一个jdk的类，不像Spring的源码那样绕来绕去，
各种设计模式搞得你头晕。
所以我建议阅读源码可以从jdk的源码开始，前提是你需要一定的基础才能看得懂。
比如我这个版本(11)就发现AQS的部分源码与之前版本的源码不同。
这个版本的AQS使用了: VarHandle 这个类来设置Node类内部的属性，
而之前都是直接使用构造方法来构造Node的。
并且AQS使用的是LockSupport来阻塞线程的，LockSupport仍然使用的是Unsafe类来进行操作的,
这些都属于java与c/c++交互的类,所以你如果没有基础，会诧异,jdk还有这种东西呀 ^-^...    