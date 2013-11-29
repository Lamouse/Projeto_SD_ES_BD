package direstruts.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.model.UserBean;
import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.Mensagem;
import java.rmi.Naming;

public class userdetailsAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
        private int id_user;
	private String money;
        private String hist;

        @Override
	public String execute() throws Exception {
            UserBean user = (UserBean) session.get("userBean");
            if(user==null)
                return "notfound";
            System.err.println(user);
            setId_user(user.getId());  
            return SUCCESS;
        }
        
	public int getId_user() {
            return id_user;
	}

	public void setId_user(int number) {
            this.id_user = number;

            try{
                ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                Mensagem msg = srmi.retrieveUserData(this.id_user);
                money = msg.popList();
                int length = msg.getListSize()/2;
                hist = "";
                for(int i=0;i<length;i++){
                    hist += msg.popList() + "\t" + msg.popList();
                }
            }catch(Exception e) {
                System.err.println("Erro no RMI: " + e);
            }
	}
        
        public String getMoney() {
            return money;
	}

        public void setMoney(String aux) {
            this.money = aux;
	}

	public String getHist() {
            return hist;
	}

        public void setHist(String aux) {
            this.hist = aux;
	}

        @Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
