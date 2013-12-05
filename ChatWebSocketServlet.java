package direstruts.action;

import direstruts.xmeta1.ExecuteCommands;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;

import javax.servlet.http.HttpServletRequest;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatWebSocketServlet extends WebSocketServlet {
    private final AtomicInteger sequence = new AtomicInteger(0);
    private final Set<ChatMessageInbound> connections = new CopyOnWriteArraySet<ChatMessageInbound>();
    private String username;
    
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        try {
            return new ChatMessageInbound(username, sequence.incrementAndGet());
        } catch (RemoteException ex) {
            Logger.getLogger(ChatWebSocketServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ChatWebSocketServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ChatWebSocketServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private final class ChatMessageInbound extends MessageInbound {
        private final String nickname;
        private String username;
        private ChatMessageInbound(String username, int id) throws RemoteException, NotBoundException, MalformedURLException {
            this.nickname = "Client" + id;
            this.username = username;
            ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
            broadcast(srmi.popUserDataOffline(1));
        }

        @Override
        protected void onOpen(WsOutbound outbound) {
            connections.add(this);
            broadcast(username + " just joined the chat room");
        }

        @Override
        protected void onClose(int status) {
            connections.remove(this);
            broadcast(username + " just left the chat room");
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            // never trust the client
            String filteredMessage = filter(message.toString());
            broadcast("&lt;" + username + "&gt; " + filteredMessage);
        }

        private void broadcast(String message) { // send message to all
            for (ChatMessageInbound connection : connections) {
                try {
                    CharBuffer buffer = CharBuffer.wrap(message);
                    connection.getWsOutbound().writeTextMessage(buffer);
                } catch (IOException ignore) {}
            }
        }

        public String filter(String message) {
            if (message == null)
                return (null);
            // filter characters that are sensitive in HTML
            char content[] = new char[message.length()];
            message.getChars(0, message.length(), content, 0);
            StringBuilder result = new StringBuilder(content.length + 50);
            if(message.startsWith("username")) {
                String aux = this.username = message.split(" ")[1];
                broadcast(username + " has just changed his username to " + aux);
            }
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

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            throw new UnsupportedOperationException("Binary messages not supported.");
        }
    }
}