package direstruts.action;

import com.opensymphony.xwork2.ActionSupport;
import direstruts.xmeta1.ExecuteCommands;
import java.rmi.Naming;

public class createtopicAction extends ActionSupport {
        private String topic;
        
	@Override
	public String execute() throws Exception {
            String aux = ERROR;
            if(addTopic())
                aux = SUCCESS;
            topic="";
            return aux;
        }
        
        public void setTopic(String aux) {
            topic = aux;
        }
        
        public String getTopic() {
            return topic;
        }
        
        private boolean addTopic() {
            boolean aux = false;
            if(topic.isEmpty())
                return false;
            try{
                ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                if(srmi.addTopic(topic))
                    aux = true;
            }catch(Exception e) {
                System.err.println("Erro no RMI: " + e);
            }
            return aux;
        }
}
