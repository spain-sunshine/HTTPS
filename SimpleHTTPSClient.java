import javax.net.ssl.*;
import java.io.*;
import java.net.*;

public class SimpleHTTPSClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8443;

        try {
            // 加载信任库
            System.setProperty("javax.net.ssl.trustStore", "path_to_truststore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "password");

            SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) ssf.createSocket(host, port);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 发送一个简单的GET请求
            String request = "GET / HTTP/1.1\r\n" +
                             "Host: 127.0.0.1\r\n" +
                             "\r\n";
            out.write(request);
            out.flush();

            // 接收响应
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
