package direstruts.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.model.UserBean;
import direstruts.model.ListBean;

public class halloffameAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
        
	@Override
	public String execute() throws Exception {
            setListBean(new ListBean());
            getListBean().setTipo(19);
            return SUCCESS;
        }

	public ListBean getListBean() {
		return (ListBean) session.get("listBean");
	}

	public void setListBean(ListBean listBean) {
		this.session.put("listBean", listBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
