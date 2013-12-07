package direstruts.websocket;

import java.io.BufferedReader;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.Hello_C_I;
import java.io.Serializable;

public class ChatWebSocketServlet extends WebSocketServlet {

    private final AtomicInteger sequence = new AtomicInteger(0);
    private final Set<ChatMessageInbound> connections =
                    new CopyOnWriteArraySet<ChatMessageInbound>();

    public ChatWebSocketServlet() {
        System.getProperties().put("java.security.policy", "policy.all");
        try {
            ExecuteCommands h = (ExecuteCommands) Naming.lookup("ServerRMI");
            HelloClient c = new HelloClient();
            h.subscribe((Hello_C_I) c);
        } catch (Exception e) {
            System.out.println("Exception in main: " + e);
        }
    }
    
    protected StreamInbound createWebSocketInbound(String subProtocol,
                    HttpServletRequest request) {
        return new ChatMessageInbound(sequence.incrementAndGet());
    }

    class HelloClient extends UnicastRemoteObject implements Hello_C_I {

        HelloClient() throws RemoteException {
            super();
        }

        public void print_on_client(String id, String s) throws RemoteException {
            for (ChatMessageInbound connection : connections) {
                if(id.equals(connection.getNickname())){
                    try {
                        CharBuffer buffer = CharBuffer.wrap(s);
                        connection.getWsOutbound().writeTextMessage(buffer);
                    } catch (IOException ignore) {}
                }
            }
        }
        
        public void print_all_client(String msg) throws RemoteException {
            for (ChatMessageInbound connection : connections) {
                try {
                    CharBuffer buffer = CharBuffer.wrap("all:"+msg);
                    connection.getWsOutbound().writeTextMessage(buffer);
                } catch (IOException ignore) {}
            }
        }
    }

    private final class ChatMessageInbound extends MessageInbound implements Serializable {
        private String nickname;

        public String getNickname() {
            return nickname;
        }

        private ChatMessageInbound(int id) {
                this.nickname = "Client" + id;
        }

        protected void onOpen(WsOutbound outbound) {
                connections.add(this);
        }

        protected void onClose(int status) {
                connections.remove(this);
        }

        protected void onTextMessage(CharBuffer message) throws IOException {
                String filteredMessage = filter(message.toString());
                nickname = filteredMessage;
        }

        public String filter(String message) {
                if (message == null)
                        return (null);
                // filter characters that are sensitive in HTML
                char content[] = new char[message.length()];
                message.getChars(0, message.length(), content, 0);
                StringBuilder result = new StringBuilder(content.length + 50);
                for (int i = 0; i < content.length; i++) {
                        switch (content[i]) {
                        case '<':
                                result.append("&lt;");
                                break;
                        case '>':
                                result.append("&gt;");
                                break;
                        case '&':
                                result.append("&amp;");
                                break;
                        case '"':
                                result.append("&quot;");
                                break;
                        default:
                                result.append(content[i]);
                        }
                }
                return (result.toString());
        }

        protected void onBinaryMessage(ByteBuffer message) throws IOException {
                throw new UnsupportedOperationException("Binary messages not supported.");
        }
    }
}