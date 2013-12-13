package direstruts.action;

import com.opensymphony.xwork2.ActionSupport;
import direstruts.xmeta1.ExecuteCommands;
import java.rmi.Naming;


public class deleteideaAction extends ActionSupport {
    private int ididea;
    private int iduser;
        
    @Override
    public String execute() throws Exception {            
        if(deleteidea())
            return SUCCESS;
        return ERROR;
    }

    private boolean deleteidea() {
        boolean cond = false;

        try{
            ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
            if(srmi.removeIdeia(iduser, ididea))
                cond = true;
        }catch(Exception e) {
            System.err.println("Erro no RMI: " + e);
        }
        return cond;
    }

    public int getIdidea() {
        return ididea;
    }

    public void setIdidea(int aux) {
        ididea = aux;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int aux) {
        iduser = aux;
    }
}
