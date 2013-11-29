package direstruts.action;

import java.io.File;
import com.opensymphony.xwork2.ActionSupport;
import direstruts.model.UserBean;
import direstruts.xmeta1.ExecuteCommands;
import direstruts.xmeta1.NamedByteArray;
import java.rmi.Naming;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class createideaAction extends ActionSupport {
        private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
        private String topic;
        private String ideiaText;
        private int invest;
        private int iduser;
        
	@Override
	public String execute() throws Exception {            
            DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String dataString = dateFormat.format(new Date());
            if(addIdea(iduser,dataString))
                return SUCCESS;
            return ERROR;
        }
        
        private boolean addIdea(int id, String data) {
            NamedByteArray aux = null;
            boolean cond = false;
            
            if(!(fileUploadFileName==null)){
                aux = new NamedByteArray(fileUpload,fileUploadFileName);
                System.out.print("BITCH PLEASE!");
            }                 
            try{
                ExecuteCommands srmi = (ExecuteCommands)Naming.lookup("rmi://localhost:1099/ServerRMI");
                if(srmi.addNewIdeia(ideiaText,topic,iduser,invest,data,aux))
                    cond = true;
            }catch(Exception e) {
                System.err.println("Erro no RMI: " + e);
            }
            return true;
        }
        
        public String getTopic() {
            return topic;
        }
        
        public void setTopic(String aux) {
            topic = aux;
        }
        
        public String getIdeiaText() {
            return ideiaText;
        }
        
        public void setIdeiaText(String aux) {
            ideiaText = aux;
        }
        
        public int getInvest() {
            return invest;
        }
        
        public void setInvest(int aux) {
            invest = aux;
        }   
        
        public int getIduser() {
            return iduser;
        }
        
        public void setIduser(int aux) {
            iduser = aux;
        }
                
        public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}
        

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}
        
        public String display() {
		return NONE;
	}
}
