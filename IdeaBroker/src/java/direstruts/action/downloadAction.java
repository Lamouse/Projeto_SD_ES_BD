package direstruts.action;

import com.opensymphony.xwork2.ActionSupport;

public class downloadAction extends ActionSupport {
    private int ididea;

    @Override
    public String execute() throws Exception {
        download();
        return SUCCESS;
    }

    public int getIdidea() {
    	return ididea;
    }

    public void setIdidea(int aux) {
    	ididea = aux;
    }
    
    private void download() {
        //do something
    }
}
