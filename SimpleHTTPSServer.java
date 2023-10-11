import javax.net.ssl.*;
import java.io.*;
import java.net.*;

public class SimpleHTTPSServer {

    public static void main(String[] args) {
        int port = 8443;
        try {
            // 加载密钥库和信任库
            System.setProperty("javax.net.ssl.keyStore", "path_to_keystore.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "password");

            SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(port);

            System.out.println("HTTPS服务器启动在 127.0.0.1:" + port);

            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                System.out.println("接受来自 " + clientSocket.getInetAddress() + " 的加密连接");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                // 从客户端接收数据
                String line;
                while ((line = in.readLine()) != null && !line.isEmpty()) {
                    System.out.println(line);
                }

                // 发送一个简单的HTTP响应
                String response = "HTTP/1.1 200 OK\r\n" +
                                  "Content-Type: text/plain\r\n" +
                                  "\r\n" +
                                  "Hello, HTTPS!";
                out.write(response);
                out.flush();

                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
