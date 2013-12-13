package direstruts.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.model.UserBean;
import direstruts.model.DirestrutsBean;

public class bytopicAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
        
	@Override
	public String execute() throws Exception {
            setDirestrutsBean(new DirestrutsBean());
            getDirestrutsBean().setTipo(4);
            this.session.put("listBean", new direstruts.model.ListBean());
            return SUCCESS;
        }

	public DirestrutsBean getDirestrutsBean() {
		return (DirestrutsBean) session.get("direstrutsBean");
	}

	public void setDirestrutsBean(DirestrutsBean direstrutsBean) {
		this.session.put("direstrutsBean", direstrutsBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
