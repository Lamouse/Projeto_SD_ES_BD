package direstruts.action;

import java.util.Map;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.Mensagem;
import java.rmi.Naming;

public class addwatchlistAction extends ActionSupport {
    private int id_user;
    private int id_ideia;

    @Override
	public String execute() throws Exception {

        if(user==null)
            return "notfound";
        setId_user(user.getId());  
        return SUCCESS;
    }
        
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int aux) {
        id_user = aux;
    }

    public int getId_ideia() {
        return id_ideia;
    }

    public void setId_ideia(int aux) {
        id_ideia = aux;
    }
}
