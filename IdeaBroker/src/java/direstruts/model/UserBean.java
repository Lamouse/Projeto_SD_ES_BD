package direstruts.model;

import direstruts.xmeta1.ExecuteCommands;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserBean {
	private String user;
	private String pass;
        private int id = -1;

	public String getUser() {
		return this.user;
	}

	public void setUser(String aux) {
		user = aux;
	}

	public String getPass() {
		return this.pass;
	}

	public void setPass(String aux) {
		pass = stringHexa(gerarHash(aux, "MD5"));
	}
        
        public int getId() {
            if(this.id == -1)
                searchId(); 
            return this.id;
	}

	public void setId(int aux) {
		id = aux;
	}
        
        private void searchId() {
            int aux = -1;
            try{
                ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                aux = srmi.hasUSer(this.user, this.pass);
            }catch(Exception e) {
                System.err.println("Erro no RMI: " + e);
            }
            //System.out.println("BBB " + this.user + " " + this.pass + " " + aux);
            this.id = aux;
        } 
        
        private String stringHexa(byte[] bytes) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
                int parteBaixa = bytes[i] & 0xf;
                if (parteAlta == 0) s.append('0');
                s.append(Integer.toHexString(parteAlta | parteBaixa));
            }
            return s.toString();
        }

        private byte[] gerarHash(String frase, String algoritmo) {
            try {
                MessageDigest md = MessageDigest.getInstance(algoritmo);
                md.update(frase.getBytes());
                return md.digest();
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        }
}
