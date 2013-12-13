package direstruts.action;

import com.opensymphony.xwork2.ActionSupport;
import direstruts.xmeta1.ExecuteCommands;
import java.rmi.Naming;

public class removewatchlistAction extends ActionSupport {
    private int id_user;
    private int id_ideia;

    @Override
	public String execute() throws Exception {
        if(removewatchlist())
            return SUCCESS;
        return ERROR;
    }

    private boolean removewatchlist(){
        boolean cond = false;
        try{
            ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
            if(srmi.removeWatchlist(id_ideia,id_user))
                cond = true;
        }catch(Exception e) {
            System.err.println("Erro no RMI: " + e);
        }
        return cond;
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
