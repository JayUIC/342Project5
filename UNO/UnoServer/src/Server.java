import java.io.*;
import java.net.*;
import java.util.*;

public class Server
{
    private ServerSocket server;
    private volatile ArrayList<Client> clients;
    private volatile Game game;






    public Server(int port)
    {
        try
        {
            server = new ServerSocket(port);
            clients = new ArrayList<>();
            game = new Game();
            ClientListener listener = new ClientListener();
            System.out.println("Listening on 8080");
            listener.start();


        }

        catch (IOException e)
        {
            System.out.println(e);
        }

    }

    class ClientListener extends Thread
    {

        @Override
        public void run()
        {
            while(true)
            {
                try
                {

                    Socket client = server.accept();
                    ClientHandler handler = new ClientHandler(client);
                    handler.start();

                }

                catch (IOException e )
                {
                    System.out.println(e);
                }

            }
        }
    }

    class ClientHandler extends Thread
    {
        private Socket client;
        private String command;
        private String[] params;
        private String delim;
        private String input;
        private DataInputStream dis;
        private DataOutputStream dos;
        private PrintWriter pw;
        private Scanner sc;
        boolean player1_ready, player2_ready, player3_ready, player4_ready;
        private int ClientID;



        public ClientHandler(Socket client)
        {
            this.client = client;
            input = "";
            command = "";
            delim = ";";
            try
            {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                pw = new PrintWriter(client.getOutputStream(), true);
                sc = new Scanner(client.getInputStream());
                clients.add(new Client(client, dis, dos, pw, sc));
                game.addPlayer(clients.size());
                ClientID = clients.size() - 1;
                clients.get(clients.size() - 1).setID(ClientID);

            }

            catch (IOException e)
            {
                System.out.println(e);
            }

        }

        @Override
        public void run()
        {
            try
            {

                System.out.println("connected");
                String toWrite = "message;Welcome, player " + clients.size() + "! Waiting for other players to join....";

                //dos.writeUTF(toWrite);
                System.out.println("player joined;" + clients.size());
                pw.println("player joined;" + clients.size());


                //dos.writeUTF("start game\n");

               // pw.println("start game");

                for(int i = 0; i<(clients.size() - 1); i++)
                {
                   // clients.get(i).getDos().writeUTF("player joined;" + clients.size());
                    clients.get(i).getPw().println("player joined;" + clients.size());
                    //(new PrintWriter(clients.get(i).getDos())).println("player joined;" + clients.size());
                }

                if(clients.size() == 4)
                {
                    for(int i = 0; i<clients.size(); i++)
                    {
                        clients.get(i).getPw().println("start game");


                    }
                }

                while(!command.equals("disconnect"))
                {

                    input = dis.readUTF();
                    params = input.split(delim);
                    command = params[0];
                    params = Arrays.copyOfRange(params, 1, params.length);


                    if(command.equals("hand"))
                    {
                        if(ClientID == 0) player1_ready = true;
                        else if(ClientID ==1) player2_ready = true;
                        else if(ClientID == 2) player3_ready = true;
                        else if(ClientID == 3) player4_ready = true;

                        if(player1_ready && player2_ready && player3_ready && player4_ready)
                        {
                            for(int i = 0; i<clients.size(); i++)
                            {
                                if(clients.get(i).getID() == game.getTurn()) clients.get(i).getPw().println("your turn");
                                break;
                            }
                        }
                        //game.dealTo(clients.size() - 1);
                    }

                    if(command.equals("turn"))
                    {
                        if(ClientID == game.getTurn())
                        {
                            if(params[0].equals("play"))
                            {



                                game.play(ClientID, params[1], params[2]);
                            }

                            else
                            {

                                game.draw(ClientID, 1);
                            }

                            clients.get(ClientID).getPw().println("send hand;" + game.returnSerializedHand(ClientID));
                        }

                        else clients.get(ClientID).getPw().println("message;Wait...It's not your turn!");

                        for(int i = 0; i<clients.size(); i++)
                        {

                            if(ClientID != clients.get(i).getID()) clients.get(i).getPw().println("all cards;" + game.getNumCardsByID(ClientID));
                        }
                    }

                }
            }



                catch (IOException e)
                {
                    System.out.println(e);
                }


            }
        }




    class Client
    {
        private int id;
        private Socket sock;
        private DataInputStream dis;
        private DataOutputStream dos;
        private PrintWriter pw;
        private Scanner sc;

        public Client(Socket sock, DataInputStream dis, DataOutputStream dos, PrintWriter pw, Scanner sc)
        {
            this.sock = sock;
            this.dis = dis;
            this.dos = dos;
            this.pw = pw;
            this.sc = sc;

        }

        public Socket getSock()
        {
            return sock;
        }

        public DataInputStream getDis()
        {
            return dis;
        }

        public DataOutputStream getDos()
        {
            return dos;
        }

        public void setID(int id)
        {
            this.id = id;
        }

        public int getID()
        {
            return id;
        }



        public PrintWriter getPw() {return pw;}
    }


}