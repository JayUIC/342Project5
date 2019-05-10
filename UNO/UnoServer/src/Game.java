import java.util.ArrayList;
import java.util.Collections;

public class Game
{
    private Deck deck;
    int numPlayers;
    ArrayList<Player> players;
    int direction = 0; //clockwise
    int turn = 0;
    String color;
    boolean turn_rejected;

    String prevRank;


    public Game()
    {
        players = new ArrayList<>();
        deck = new Deck();
        deck.shuffle();
        numPlayers = 0;
        color = "null";
        turn_rejected = false;
        turn = 0;
        prevRank = "";

    }



    public void addPlayer(int id)
    {
        players.add(new Player(id));
        numPlayers++;
        players.get(players.size() - 1).addToHand(deck.deal(7));
    }

    public void dealTo(int id)
    {
        players.get(id).addToHand(deck.deal(7));
    }

    public String returnSerializedHand(int id)
    {
        ArrayList<Card> hand = players.get(id).getHand();

        String serialized = "";

        for(int i = 0; i<hand.size(); i++)
        {
            serialized += hand.get(i).getColor() + ";" + hand.get(i).getRank();
        }

        return serialized;
    }

    public void draw(int id, int numCards)
    {
        players.get(id).addToHand(deck.deal(numCards));
    }

    public int getTurn()
    {
        return turn;
    }


    public int getNextPlayer()
    {
        if(turn == 0)
        {
            if(direction == 0) return 1;

            else return 3;
        }

        else if(turn == 3)
        {
            if(direction == 0) return 0;

            else return 2;
        }

        else if(direction == 0) return direction + 1;

        else return direction - 1;
    }

    public int getNumCardsByID(int id)
    {
        return players.get(id).getHand().size();
    }

    public void play(int id, String color, String rank)
    {
        Card c = deck.getCard(color, rank);


        turn_rejected = false;
        if(color.equals("null")) color = c.color;

        if(color.equals("null") || c.getColor().equals(color) || c.getRank().equals("w") || c.getRank().equals("w4") || c.getRank().equals(prevRank))
        {
            prevRank = c.getRank();
            color = c.getColor();
            if(c.getRank().equals("d2")) draw(getNextPlayer(), 2);

            else if(c.getRank().equals("r")) {
                if(direction == 0) direction = 1;

                else direction = 0;
            }

            else if(c.getRank().equals("s")) {
                turn = getNextPlayer();
            }

            else if(c.getRank().equals("w")) {
                color = "null";
            }

            else if(c.getRank().equals("w4")) {
                color = "null";
                draw(getNextPlayer(), 4);
            }

            turn = getNextPlayer();
        }

        else turn_rejected = true;


    }



    class Player
    {
        private ArrayList<Card> hand;
        private int player_id;

        public Player(int id)
        {

            player_id = id;
            hand = new ArrayList<>();
        }

        public void addToHand(ArrayList<Card> toDeal)
        {
            hand.addAll(toDeal);

        }

        public void draw(Card c)
        {
            hand.remove(c);
        }

        public ArrayList<Card> getHand()
        {
            return hand;
        }

    }

    class Deck
    {
        private ArrayList<Card> cards;
        private int size;

        public Deck()
        {
            cards = new ArrayList<>();
            size = 108;
            String suit, rank;
            for(int i = 0; i<4; i++)
            {
                for(int j = 0; j<25; j++)
                {
                    if(i == 0) suit = "red";
                    else if(i == 1) suit = "green";
                    else if(i == 2) suit = "blue";
                    else if(i == 3) suit = "yellow";
                    else suit = "null";

                    if(j == 0) rank = "0";

                    else if(j == 1 || j == 24) rank = "1";

                    else if(j == 2 || j == 3) rank = "2";

                    else if(j == 4 || j == 5) rank = "3";

                    else if(j == 6 || j == 7) rank = "4";

                    else if(j == 8 || j == 9) rank = "5";

                    else if(j == 10 || j == 11) rank = "6";

                    else if(j == 12 || j == 13) rank = "7";

                    else if(j == 14 || j == 15) rank = "8";

                    else if(j == 16 || j == 17) rank = "9";

                    else if(j == 18 || j == 19) rank = "d2";

                    else if(j == 20 || j == 21) rank = "skip";

                    else if(j == 22 || j == 23) rank = "reverse";


                    else {

                        rank = "null";
                    }

                    cards.add(new Card(suit, rank));


                }
            }

            for(int i = 0; i<4; i++)
                cards.add(new Card("null", "w"));
            for(int i = 0; i<4; i++)
                cards.add(new Card("null", "w4"));
        };

        public ArrayList<Card> deal(int numCards)
        {
            ArrayList<Card> dealing = new ArrayList<>();
            for(int i = 0; i<numCards; i++)
            {
                dealing.add(cards.get(cards.size() - 1));
                cards.remove(cards.size() - 1);
            }

            return dealing;
        }

        public Card getCard(String color, String rank)
        {
            return cards.get(cards.indexOf(new Card(color, rank)));

        }



        public void shuffle()
        {
            Collections.shuffle(cards);
        }

        public void addToBottom(Card c)
        {
            cards.add(0, c);
        }

    }

    class Card
    {
        private String color;
        private String rank;

       public Card(String color, String rank)
       {
           this.color = color;
           this.rank = rank;

       }

       public String getColor()
       {
           return color;
       }

       public String getRank()
       {
           return rank;
       }


    }




}
