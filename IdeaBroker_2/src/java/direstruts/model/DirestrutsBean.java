package direstruts.model;

import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.Mensagem;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DirestrutsBean {
	private ArrayList list = new ArrayList();
	private int tipo;
	private int id_user;
	private String data;             

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int number) {
		this.id_user = number;
	}
	
	public ArrayList<String> getList() {
		return list;
	}
        
        public void setList(String aux) {
            list.add(aux);
        }
        
        public int getTipo() {
            return tipo;
	}
        
        public void setTipo(int number) {
            this.tipo = number;
            if(this.tipo == 3){
                try{
                    ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                    if(!srmi.addTopic((String) this.list.get(0)))
                        this.tipo = -3;
                }catch(Exception e) {
                    System.err.println("Erro no RMI: " + e);
                    this.tipo = -3;
                }
            }
            else if(this.tipo == 4){
                try{
                    ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                    Mensagem msg = srmi.retrieveTopicos();
                    int i, length=msg.getListSize()/2;
                    for(i=0;i<length;i++){
                        Map map = new HashMap();
                        map.put("one", msg.popList());
                        map.put("two", msg.popList());
                        list.add(map);
                    }
                }catch(Exception e) {
                    System.err.println("Erro no RMI: " + e);
                    this.tipo = -3;
                }
            }
            else if(this.tipo == 10){
                try{
                    ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                    Mensagem msg = srmi.retrieveUserData(this.id_user);
                    list.add(msg.popList());
                    String hist = "";
                    int length = msg.getListSize()/2;
                    for(int i=0;i<length;i++){
                        hist += msg.popList() + "\t" + msg.popList();
                    }
                    list.add(hist);
                }catch(Exception e) {
                    System.err.println("Erro no RMI: " + e);
                }
            }
            else if(this.tipo == 11){
                try{
                    ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                    Mensagem msg = srmi.retrieveIdeiasUser(this.id_user);
                    int i, length = msg.getListSize()/6;
                    for(i=0;i<length;i++) {
                        Map map = new HashMap();
                        map.put("one", msg.popList());
                        map.put("two", msg.popList());
                        map.put("three", msg.popList());
                        map.put("four", msg.popList());
                        map.put("five", msg.popList());
                        map.put("six", msg.popList());
                        list.add(map);
                    }
                }catch(Exception e) {
                    System.err.println("Erro no RMI: " + e);
                }
            }
            else if(this.tipo == 19){
               try{
                    ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                    list = srmi.retrieveHoF();
                }catch(Exception e) {
                    System.err.println("Erro no RMI: " + e);
                } 
            }
	}
        
        public void setData(String aux) {
            this.data = aux;
        }
        
        public String getData() {
            return this.data;
        }
}
