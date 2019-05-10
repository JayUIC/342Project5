import java.util.ArrayList;
import java.util.Collections;

public class Game
{
    private Deck deck;
    int numPlayers;
    ArrayList<Player> players;


    public Game()
    {
        deck = new Deck();
        deck.shuffle();
        numPlayers = 0;

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

    class Player
    {
        private ArrayList<Card> hand;
        private int player_id;

        public Player(int id)
        {
            player_id = id;
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
                for(int j = 0; j<25; i++)
                {
                    if(i == 0) suit = "Red";
                    else if(i == 1) suit = "Green";
                    else if(i == 2) suit = "Blue";
                    else if(i == 3) suit = "Yellow";
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

                    else if(j == 20 || j == 21) rank = "s";

                    else if(j == 22 || j == 23) rank = "r";


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
                dealing.add(cards.get(cards.size()));
                dealing.remove(cards.size());
            }

            return dealing;
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
