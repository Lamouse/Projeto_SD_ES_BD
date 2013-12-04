package direstruts.action;

import java.util.ArrayList;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.model.UserBean;
import direstruts.model.DirestrutsBean;
import direstruts.xmeta1.ExecuteCommands;
import java.rmi.Naming;

public class loginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
        private String token;
        private int fbid;

        /*private String buttonLogin;
        public void setButtonName(String buttonLogin) {
           this.buttonLogin = buttonLogin;
        }*/
        
	@Override
	public String execute() throws Exception {
            UserBean user = (UserBean) session.get("userBean");
            int aux;
            //System.out.println("->"+fbid);
            if(fbid==0){
                user.setId(-1);
                setUserBean(user);
                aux = getUserBean().getId();
            }else{
                aux = login_facebook(user);
            }
            if(aux != -1)
                return SUCCESS;
            session.remove("userBean");
            return ERROR;
        }
            
        private Integer tryParse(String text) {
            try {
                return new Integer(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        
        private int login_facebook(UserBean user) {
            int aux = -1;
            try{
                ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                ArrayList<String> lista = srmi.getDataUser1(fbid,token);
                if(lista.size() > 0){
                    user.setId(tryParse(lista.get(0)));
                    user.setUser(lista.get(1));
                    setUserBean(user);
                    aux=user.getId();
                }
            }catch(Exception e) {
                System.err.println("Erro no RMI: " + e);
            }
            return aux;
        }

	public UserBean getUserBean() {
		return (UserBean) session.get("userBean");
	}

	public void setUserBean(UserBean direstrutsBean) {
		this.session.put("userBean", direstrutsBean);
	}    

        public String getToken() {
            return token;
        }

        public void setToken(String aux) {
            token = aux;
        }

        public int getFbid() {
            return fbid;
        }

        public void setFbid(int aux) {
            fbid = aux;
        }

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
