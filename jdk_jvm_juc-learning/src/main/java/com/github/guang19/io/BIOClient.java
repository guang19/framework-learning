package com.github.guang19.io;

import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author yangguang
 * @date 2020/2/22
 * @description <p>BIO测试客户端</p>
 */
public class BIOClient
{
    public static void main(String[] args)
    {
        Socket socket = null;
        try
        {
            socket = new Socket("127.0.0.1", 8080);
            socket.getOutputStream().write("hello server".getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch (Exception e)
            {
            }
        }
    }
}
