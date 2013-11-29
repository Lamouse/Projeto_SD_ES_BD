package direstruts.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.model.UserBean;
import direstruts.model.DirestrutsBean;

public class loginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;

        /*private String buttonLogin;
        public void setButtonName(String buttonLogin) {
           this.buttonLogin = buttonLogin;
        }*/
        
	@Override
	public String execute() throws Exception {
            UserBean user = (UserBean) session.get("userBean");
            //session.remove("userBean");
            //UserBean user = new UserBean();
            //user.setPass(user_old.getPass());
            //user.setUser(user_old.getUser());
            //System.out.println("AAA " + user.getUser() + " " + user.getPass());
            user.setId(-1);
            setUserBean(user);
            
            int aux = getUserBean().getId();
            //System.out.println("CCC " + aux);
            if(aux != -1)
                return SUCCESS;
            session.remove("userBean");
            return ERROR;
        }

	public UserBean getUserBean() {
		return (UserBean) session.get("userBean");
	}

	public void setUserBean(UserBean direstrutsBean) {
		this.session.put("userBean", direstrutsBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
