package direstruts.action;

import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.model.UserBean;
import direstruts.xmeta1.ExecuteCommands;
import java.rmi.Naming;
import java.util.ArrayList;

public class seewatchlistAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private int id_user;
    private ArrayList lista;

    @Override
	public String execute() throws Exception {
        UserBean user = (UserBean) session.get("userBean");
        if(user==null)
            return "notfound";
        id_user = user.getId();
        searchwatchlist();
        return SUCCESS;
    }

    private void searchwatchlist(){
        try{
            ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
            lista = srmi.seeWatchlist(id_user);
        }catch(Exception e) {
            System.err.println("Erro no RMI: " + e);
        }
    }
        
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int aux) {
        id_user = aux;
    }

    public ArrayList getLista() {
        return lista;
    }

    public void setLista(ArrayList aux){
        lista = aux;
    }
    
    @Override
    public void setSession(Map<String, Object> session) {
            this.session = session;
    }
}
