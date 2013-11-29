package direstruts.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.model.UserBean;
import direstruts.model.UpdatepriceBean;

public class changepriceAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
        
	@Override
	public String execute() throws Exception {
            UserBean user = (UserBean) session.get("userBean");
            getUpdatepriceBean().setId_user(user.getId());
            
            return SUCCESS;
        }

	public UpdatepriceBean getUpdatepriceBean() {
		return (UpdatepriceBean) session.get("updatepriceBean");
	}

	public void setUpdatepriceBean(UpdatepriceBean updatepriceBean) {
		this.session.put("updatepriceBean", updatepriceBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
