package projectometa2teste;

import java.io.*;

/**
 *
 * @author João Silva
 */
public class NamedByteArray implements Serializable {
    private String name;
    private byte [] bytes;

    public NamedByteArray(String name) {
        this.name = name;
        try {
            File myFile = new File(name);
            bytes  = new byte [(int)myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(bytes,0,bytes.length);
            bis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void saveToFile(String newNome) {
        try{
            File copy = new File(newNome);
            FileOutputStream fos = new FileOutputStream(copy);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(this.getBytes(), 0, this.getBytes().length);
            bos.flush();
            bos.close();
        } catch(IOException err) {
            System.err.println("Não conseguiu escrever no ficheiro: " + err);
        }
    }
}
