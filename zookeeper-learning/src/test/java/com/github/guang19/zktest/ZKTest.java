package com.github.guang19.zktest;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author yangguang
 * @Date 2020/1/29
 * @Description TODO        测试Curator操作ZK服务器
 */
public class ZKTest
{
    private final String host = "127.0.0.1:2182";

    private RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,5);

    private final CuratorFramework client =  CuratorFrameworkFactory.builder().connectString(host).sessionTimeoutMs(20000).connectionTimeoutMs(20000).retryPolicy(retryPolicy).build();

    @Before
    public void start()
    {
        client.start();
    }

    @Test
    public void create1() throws Exception
    {
//        client.create().forPath("/test1");
        //带数据的节点
        client.create().forPath("/test2","data".getBytes("UTF-8"));

        //指定节点模式创建
//        client.create().withMode(CreateMode.EPHEMERAL).forPath("/test3","临时节点".getBytes("UTF-8"));

        //如果父节点不存在就连带创建父节点
        client.create().creatingParentsIfNeeded().forPath("/test3/test3_1","test3的子节点".getBytes("UTF-8"));
    }

    @Test
    public void delete1() throws Exception
    {
        //只能删除叶子节点
//        client.delete().forPath("/test1");

        //强制删除
//        client.delete().guaranteed().forPath("/test3");

        //可以递归删除子节点
        client.delete().deletingChildrenIfNeeded().forPath("/test3");
    }

    @Test
    public void get1() throws Exception
    {
        System.out.println(new String(client.getData().forPath("/"),"UTF-8"));
        System.out.println(new String(client.getData().forPath("/test2"),"UTF-8"));
    }

    @Test
    public void set1() throws Exception
    {
//        client.setData().forPath("/test2","新值".getBytes("UTF-8"));

        client.setData().withVersion(0).forPath("/test2","第三个版本的新值".getBytes("UTF-8"));
    }

    @Test
    public void test01() throws Exception
    {
        System.out.println(client.checkExists().forPath("/test3"));
    }



    @Test
    public void watch() throws Exception
    {
//        client.create().forPath("/watch/watch_children","watch的子节点没有改变的数据".getBytes("UTF-8"));

        NodeCache nodeCache = new NodeCache(client,"/watch/watch_children");
        nodeCache.start(true);

        nodeCache.getListenable().addListener(()->{
//            System.out.println("node path : " + nodeCache.get());
            System.out.println("node data : " + nodeCache.getCurrentData());
            System.out.println(new String(nodeCache.getCurrentData().getData(),"UTF-8"));
        });


        client.setData().forPath("/watch/watch_children","watch的子节点改变后的数据".getBytes("UTF-8"));
//        client.delete().forPath("/watch");

        TimeUnit.SECONDS.sleep(3);
        client.close();
    }

    //分布式锁测试
    @Test
    public void zkLock() throws Exception
    {
        //线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,5,2000,TimeUnit.SECONDS,new LinkedBlockingQueue<>(5),Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardOldestPolicy());
        InterProcessLock zkLock = new InterProcessMutex(client,"/zkLock");

        for (int i = 0 ; i < 10; ++i)
        {
            threadPoolExecutor.execute(()->{
                try
                {
                    zkLock.acquire();
                    System.out.println("thread: " + Thread.currentThread().getName() + "  has gotten lock");
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (Throwable e)
                {
                }
                finally {
                    try
                    {
                        zkLock.release();
                        System.out.println("thread : " + Thread.currentThread().getName() + " has released lock");
                    }
                    catch (Throwable e)
                    {
                    }
                }
            });
        }
        TimeUnit.SECONDS.sleep(20);
        threadPoolExecutor.shutdown();
        client.close();
    }

}
