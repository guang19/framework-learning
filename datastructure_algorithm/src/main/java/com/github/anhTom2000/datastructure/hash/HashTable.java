package com.github.anhTom2000.datastructure.hash;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author guang19
 * @date 2020/5/4
 * @description hash表实现(拉链法)
 * @since 1.0.0
 */
public class HashTable<K,V>
{
    static class Node<K,V> implements Serializable
    {

        //key
        K key;

        //value
        V val;

        int hash;

        //下一个节点的引用
        Node<K,V> next;

        public Node() {}

        public Node(K key , V val)
        {
            this.key = key;
            this.val = val;
        }
        public Node(K key , V val , int hash,Node<K,V> next)
        {
            this.key = key;
            this.val = val;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }
            Node<?, ?> node = (Node<?, ?>) o;
            return hash == node.hash &&
                    key.equals(node.key) &&
                    val.equals(node.val);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(key, val, hash);
        }

        @Override
        public String toString()
        {
            return "Node{" +
                    "key=" + key +
                    ", val=" + val +
                    ", hash=" + hash +
                    ", next=" + next +
                    '}';
        }
    }

    //hash表
    private Node<K,V>[] table;

    //负载因子， 当 size >= threshold =  loadFactor * capacity时，扩容
    private final float loadFactor = 0.75f;

    //扩容阈值
    int threshold;

    //元素数量
    private int size;

    //默认容量: 16
    private static final int DEFAULT_CAPACITY = 1 << 4;

    //最大容量为 Integer.MAX_VALUE 的一半
    private static final int MAX_CAPACITY = 1 << 30;

    //默认构造
    public HashTable() {}

    //初始化容量构造
    @SuppressWarnings("unchecked")
    public HashTable(int capacity)
    {
        assert capacity > 0;
        if (capacity >= MAX_CAPACITY)
        {
            this.table = new Node[capacity];
            this.threshold = Integer.MAX_VALUE;
        }
        else
        {
            //保证capacity为2的幂, 借用了 HashMap的实现
            capacity = -1 >>> Integer.numberOfLeadingZeros(capacity - 1);
            capacity = capacity <= 0 ? 1 : capacity >= MAX_CAPACITY ? MAX_CAPACITY : capacity + 1;
            this.table = new Node[capacity];
            this.threshold = (int)(capacity * loadFactor);
        }
    }

    /**
     * 添加键值对
     * @param k
     * @param v
     */
    public void put(K k , V v)
    {
        assert  k != null && v != null;
        putVal(k,v);
    }

    private void putVal(K key , V value)
    {
        if (table == null || table.length == 0)
        {
            //初始化
            resize();
        }
        int hash = hash(key);
        Node<K,V>[] tab = table;
        int len = tab.length;
        Node<K, V> newNode = new Node<>(key, value, hash,null);
        Node<K,V> temp;
        int index;
        if ((temp = tab[index = (hash & (len - 1))]) == null)
        {
            tab[index] = newNode;
        }
        else
        {
            //hash冲突，将新节点添加至链表尾
            if (temp.key == newNode.key || temp.key.equals(newNode.key))
            {
                newNode.next = temp.next;
                temp.next = null;
                tab[index] = newNode;
                return;
            }
            //hash冲突，将新节点添加至链表尾
            while (temp.next != null)
            {
                //key相同，就覆盖
                if (temp.next.key.equals(newNode.key))
                {
                    newNode.next = temp.next.next;
                    temp.next.next = null;
                    temp.next = newNode;
                    return;
                }
                temp = temp.next;
            }
            temp.next = newNode;
        }
        ++size;
        if(size > threshold)
        {
            resize();
        }
    }

    /**
     * 根据key获取key对应的value
     * @param key
     * @return
     */
    public V get(K key)
    {
        Node<K,V> t;
        return (t = getNode(key)) == null ? null : t.val;
    }

    private Node<K,V> getNode(K key)
    {
        final Node<K,V>[] tab = table;
        int hash = hash(key);
        Node<K,V> temp = tab[hash & (tab.length - 1)];
        while (temp != null)
        {
            if (temp.hash == hash && (temp.key == key || temp.key.equals(key)))
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    /**
     * 移除key对应的键值对
     * @return
     */
    public V remove(K key)
    {
        return removeNode(key);
    }

    private V removeNode(K key)
    {
        final Node<K,V>[] tab = table;
        int hash = hash(key);
        int index = hash & (tab.length - 1);
        Node<K,V> temp = tab[index];
        Node<K,V> next;
        V val;
        //如果该桶的第一个节点就是要删除的值
        if (temp.hash == hash && (temp.key == key || temp.key.equals(key)))
        {
            val = temp.val;
            next = temp.next;
            temp.next = null;
            tab[index] = next;
            --size;
            return val;
        }
        //否则遍历该桶的所有节点
        while (temp.next != null)
        {
            if (temp.next.hash == hash && (temp.next.key == key || temp.next.key.equals(key)))
            {
                val = temp.next.val;
                next = temp.next.next;
                temp.next.next = null;
                temp.next = next;
                --size;
                return val;
            }
        }
        return null;
    }

    //hash函数，借用了 HashMap的实现
    private int hash(Object key)
    {
        int h;
        return key == null ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


    //扩容和初始化table函数
    private void resize()
    {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length , newCap;

        if (oldCap == 0)
        {
            table = new Node[DEFAULT_CAPACITY];
            threshold = (int)(DEFAULT_CAPACITY * loadFactor);
        }
        else
        {
            Node<K,V>[] newTab;
            //如果容量达到了最大且threshold已经达到了最大,直接抛出异常
            if (oldCap == MAX_CAPACITY)
            {
                throw new OutOfMemoryError("hash table there no enough space");
            }
            else if ((newCap = oldCap << 1) >= MAX_CAPACITY)
            {
                newCap = MAX_CAPACITY;
                threshold = Integer.MAX_VALUE;
            }
            else if(newCap > DEFAULT_CAPACITY)
            {
                threshold = (int)(loadFactor * newCap);
            }
            //遍历原来的 table ， 将原table中的节点全部赋予新table
            newTab = new Node[newCap];
            Node<K,V> temp, newTemp , next;
            int index;
            for (int i = 0 ; i < oldCap; ++i)
            {
                if ((temp = oldTab[i]) != null)
                {
                    //遍历这个桶，将这个桶的所有Node都移动到新Table的桶中
                    while (temp != null)
                    {
                        //保存当前桶的下一个引用
                        next = temp.next;
                        temp.next = null;
                        //遍历新桶的引用
                        if((newTemp = newTab[index = (temp.hash & (newCap - 1))]) != null)
                        {
                            //遍历新桶的当前节点的下一个节点，直到最后，将node添加进去
                            while (newTemp.next != null)
                            {
                                newTemp = newTemp.next;
                            }
                            newTemp.next = temp;
                        }
                        else
                        {
                            //将当前桶的节点赋予新桶节点
                            newTab[index] = temp;
                        }
                        temp = next;
                    }
                }
            }
        }

    }

    //获取元素数量
    public int size()
    {
        return size;
    }

    @Override
    public String toString()
    {
        StringJoiner tableStr = new StringJoiner(",","{","}");
        final Node<K,V>[] tab = table;
        if(tab != null)
        {
            int len = tab.length;
            Node<K,V> temp;
            for (int i = 0 ; i < len ; ++i)
            {
                temp = tab[i];
                while (temp != null)
                {
                    tableStr.add(temp.key + "=" + temp.val);
                    temp = temp.next;
                }
            }
        }
        return tableStr.toString();
    }

    public static void main(String[] args)
    {
        HashTable<Integer,String> table1 = new HashTable<>(16);
        table1.put(1,"a");
        table1.put(2,"b");
        table1.put(3,"c");
        table1.put(4,"d");
        table1.put(5,"e");
        table1.put(6,"f");
        table1.put(7,"g");
        table1.put(8,"h");
        table1.put(9,"i");
        table1.put(10,"j");
        table1.put(11,"k");
        table1.put(12,"h");
        table1.put(13,"l");
        System.out.println(table1.get(13));
        System.out.println(table1.remove(13));
        System.out.println(table1);
        System.out.println(table1.size());
    }

}
