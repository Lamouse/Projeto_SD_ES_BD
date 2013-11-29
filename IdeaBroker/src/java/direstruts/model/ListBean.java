package direstruts.model;

import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.Mensagem;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListBean {
	private ArrayList list = new ArrayList();
        private int tipo;
        private int iduser;
	
	public ArrayList<String> getList() {
            return list;
	}
        
        public void setList(String aux) {
            list.add(aux);
        }
        
        public void setTipo(int number) {
            this.tipo = number;
            if(this.tipo == 19){
               try{
                    ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                    list = srmi.retrieveHoF();
                }catch(Exception e) {
                    System.err.println("Erro no RMI: " + e);
                } 
            }
            else if(this.tipo == 11){
                try{
                    ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                    Mensagem msg = srmi.retrieveIdeiasUser(this.iduser);
                    list.clear();
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
            else if(this.tipo == 5){
                try{
                    ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                    Mensagem msg = srmi.retrieveIdeias(this.iduser);
                    list.clear();
                    int i, length = msg.getListSize()/2;
                    for(i=0;i<length;i++) {
                        Map map = new HashMap();
                        map.put("one", msg.popList());
                        map.put("two", msg.popList());
                        list.add(map);
                    }
                }catch(Exception e) {
                    System.err.println("Erro no RMI: " + e);
                }
            }
	}
        
        public int getTipo() {
            return tipo;
        }
        
        public void setIduser(int aux) {
            iduser = aux;
        }
        
        public int getIduser() {
            return iduser;
        }
}
