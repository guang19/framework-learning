package com.github.guang19.designpattern.builder;

/**
 * @author guang19
 * @date 2020/5/20
 * @description 计算机产品
 * @since 1.0.0
 */
public  class Computer
{
    private String cpu;
    private String gpu;
    private String keyboard;

    //默认构造
    public Computer() {}

    //全参构造
    public Computer(String cpu, String gpu, String keyboard)
    {
        this.cpu = cpu;
        this.gpu = gpu;
        this.keyboard = keyboard;
    }

    //set and getter
    public String getCpu()
    {
        return cpu;
    }

    public void setCpu(String cpu)
    {
        this.cpu = cpu;
    }

    public String getGpu()
    {
        return gpu;
    }

    public void setGpu(String gpu)
    {
        this.gpu = gpu;
    }

    public String getKeyboard()
    {
        return keyboard;
    }

    public void setKeyboard(String keyboard)
    {
        this.keyboard = keyboard;
    }

    @Override
    public String toString()
    {
        return "Computer{" +
                "cpu='" + cpu + '\'' +
                ", gpu='" + gpu + '\'' +
                ", keyboard='" + keyboard + '\'' +
                '}';
    }
}
