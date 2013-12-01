package direstruts.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;
import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.Mensagem;
import java.io.ByteArrayInputStream;
import java.rmi.Naming;

public class downloadAction extends ActionSupport{
    private String filename;
    private InputStream fileInputStream;
    private int ididea;

    public String execute() throws Exception {
        System.out.println(ididea);
        //fileInputStream = new FileInputStream(new File("C:\\downloadfile.txt"));
        if(searchfile())
            return SUCCESS;
        return ERROR;
    }

    private boolean searchfile() {
        boolean cond = false;
        try{
            ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
            Mensagem msg = srmi.retrieveAnexos(ididea,"");
            if(msg.getFile() != null){
                filename = msg.getFile().getName();
                fileInputStream = new ByteArrayInputStream(msg.getFile().getBytes());
                cond = true;
                System.out.println(filename);
            }
        }catch(Exception e) {
            System.err.println("Erro no RMI: " + e);
        }
        return cond;
    }

    public void setIdidea(int aux) {
        ididea = aux;
    }

    public int getIdidea() {
        return ididea;
    }

    public String getFilename() {
        return filename;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }
}