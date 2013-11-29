package direstruts.model;

import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.Mensagem;
import java.rmi.Naming;


public class UpdatepriceBean {
	private int id_user;
	private int id_idea;
        private double price;

	public int getId_user() {
            return id_user;
	}

	public void setId_user(int number) {
            this.id_user = number;
            
            try{
                ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                srmi.changeSharePrice(this.id_user, this.id_idea, this.price);
            }catch(Exception e) {
                System.err.println("Erro no RMI: " + e);
            }
	}
	
	public int getId_idea() {
            return id_idea;
	}

	public void setId_idea(int number) {
            this.id_idea = number;
	}
        
        public double getPrice() {
            return price;
	}

        public void setPrice(double number) {
            this.price = number;
	}
}
