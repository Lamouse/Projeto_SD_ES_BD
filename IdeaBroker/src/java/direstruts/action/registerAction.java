package direstruts.action;

import com.opensymphony.xwork2.ActionSupport;
import direstruts.xmeta1.ExecuteCommands;
import java.rmi.Naming;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class registerAction extends ActionSupport {
    private String user;
    private String pass;
    private String token;
    private String fbid;

    @Override
    public String execute() throws Exception {
        //System.out.println("->"+fbid+" "+token);
        if(addPeople())
            return SUCCESS;
        return ERROR;
    }

    private boolean addPeople() {
        boolean cond = false;
        try{
            ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
            if(fbid.isEmpty()){
                if(srmi.addPessoa(user,pass))
                    cond = true;
            }
            else{
                if(srmi.addPessoa(user,pass,fbid,token))
                    cond = true;
            }
        }catch(Exception e) {
            System.err.println("Erro no RMI: " + e);
        }
        return cond;
    } 
        
    public String getUser() {
    	return user;
    }

    public void setUser(String aux) {
    	user = aux;
    }

    public String getPass() {
    	return pass;
    }

    public void setPass(String aux) {
    	pass = stringHexa(gerarHash(aux, "MD5"));
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
    
    public String getToken() {
    	return token;
    }

    public void setToken(String aux) {
    	token = aux;
    }
           
    public String getFbid() {
    	return fbid;
    }

    public void setFbid(String aux) {
    	fbid = aux;
    }
}
