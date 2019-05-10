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
                clients.add(new Client(client, dis, dos));
                game.addPlayer(clients.size());
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

                dos.writeUTF(toWrite);

                for(int i = 0; i<(clients.size() - 1); i++)
                {
                    clients.get(i).getDos().writeUTF("player joined;" + clients.size());
                }

                if(clients.size() == 4)
                {
                    for(int i = 0; i<clients.size(); i++)
                    {
                        clients.get(i).getDos().writeUTF("start game");
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
                        String toRet = "send hand;" + game.returnSerializedHand(clients.size() - 1);

                        //game.dealTo(clients.size() - 1);
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
        private Socket sock;
        private DataInputStream dis;
        private DataOutputStream dos;

        public Client(Socket sock, DataInputStream dis, DataOutputStream dos)
        {
            this.sock = sock;
            this.dis = dis;
            this.dos = dos;
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
    }


}