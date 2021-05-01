package heig.smtp;

public class SmptClient implements ISmtpClient {

   private String SMTPaddress;
   private int SMTPport;
   

   private static final String CHARACTER_ENCODING = "UTF-8";


   public SMTPClient(String address, int port){
        this.SMTPaddress = address;
        this.SMTPport = port;
    }


    public void sendEmails(List<Email> emails){
        try {
            Socket socket = new Socket(SMTPaddress, SMTPport);
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), CHARACTER_ENCODING));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARACTER_ENCODING));

            if(socket == null || printWriter == null || bufferedReader == null){
                throw new IOException("Erreur de connexion.. ");
            }


            printWriter.close();
            bufferedReader.close();
            socket.close();

        } catch (IOException e) {

         System.out.println(e);
        }
    }



}
