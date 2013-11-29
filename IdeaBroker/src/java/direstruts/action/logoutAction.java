package direstruts.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;

public class logoutAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;

        @Override
	public String execute() throws Exception {
            session.clear();
            return SUCCESS;
        }

    @Override
    public void setSession(Map<String, Object> session) {
            this.session = session;
    }
}
