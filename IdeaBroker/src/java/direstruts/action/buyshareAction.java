package direstruts.action;

import java.util.Map;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.model.UserBean;
import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.Mensagem;
import java.rmi.Naming;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class buyshareAction extends ActionSupport {
        private double price;
        private double newprice;
        private int nr;
        private int iduser;
        private int idideia;
        
	@Override
	public String execute() throws Exception {
            System.out.println("NR:" + nr + " NP:" + newprice + " P:" + price + " IDI:" + idideia + " IDU:" + iduser);
            if(iduser != 0){
                if(buyshare())
                    return SUCCESS;
            }
            else{
                buyallshare();
                return SUCCESS;
            }
            return ERROR;
        }
        
        private void buyallshare() {
            try{
                ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                srmi.buy_all(idideia);
            }catch(Exception e) {
                System.err.println("Erro no RMI: " + e);
            }
        }
        
        private boolean buyshare() {
            boolean aux = false;
            DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String dataString = dateFormat.format(new Date());
            try{
                ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                if(srmi.addPendingTransaction(iduser,idideia,nr,newprice,price,dataString))
                    aux = true;
            }catch(Exception e) {
                System.err.println("Erro no RMI: " + e);
            }
            return aux;
        }
        
        public double getPrice() {
            return price;
        }

        public void setPrice(double number) {
            this.price = number;
        }

        public double getNewprice() {
            return newprice;
        }

        public void setNewprice(double number) {
            this.newprice = number;
        }

        public int getNr() {
            return nr;
        }

        public void setNr(int aux) {
            nr = aux;
        }
        
        public int getIduser() {
            return iduser;
        }

        public void setIduser(int aux) {
            iduser = aux;
        }
        
        public int getIdideia() {
            return idideia;
        }

        public void setIdideia(int aux) {
            idideia = aux;
        }
}
