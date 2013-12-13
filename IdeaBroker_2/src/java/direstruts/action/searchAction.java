package direstruts.action;

import com.opensymphony.xwork2.ActionSupport;
import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.Mensagem;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class searchAction extends ActionSupport {
    private String type;
    private int id;
    private ArrayList lista = new ArrayList();
    private String nome;

    @Override
    public String execute() throws Exception {
        nome = null;
        lista.clear();
        if("Topic".equals(type))
            searchTopic();
        else
            searchIdea();
        return SUCCESS;
    }
    
    private void searchTopic() {
        try{
            ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
            Mensagem msg = srmi.retrieveIdeiasTopico(id);
            nome = msg.popList();
            int i,length=msg.getListSize()/3;
            for(i=0;i<length;i++){
                Map map = new HashMap();
                map.put("one", msg.popList());
                map.put("two", msg.popList());
                map.put("three", msg.popList());
                lista.add(map);
            }
        }catch(Exception e) {
            System.err.println("Erro no RMI: " + e);
        }
    }
    
    private void searchIdea() {
        try{
            ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
            Mensagem msg = srmi.retrieveIdeiasSearch(id);
            int i,length=msg.getListSize()/3;
            for(i=0;i<length;i++){
                Map map = new HashMap();
                map.put("one", msg.popList());
                map.put("two", msg.popList());
                map.put("three", msg.popList());
                lista.add(map);
            }
        }catch(Exception e) {
            System.err.println("Erro no RMI: " + e);
        }
    }
    
    public ArrayList getLista() {
        return lista;
    }
    
    public void setLista(ArrayList aux) {
        lista = aux;
    }
    
    public String getNome() {
        return nome;
    }
    
    private void setNome(String aux) {
        nome = aux;
    }

    public void setType(String aux) {
            type = aux;
    }

    public String getType() {
            return type;
    }

    public void setId(int aux) {
            id = aux;
    }

    public int getId() {
            return id;
    }
}
