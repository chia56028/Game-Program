import java.util.*;

class Card{
	protected String suit;
	protected String number;

	public Card(){}

	public void show(){
		System.out.println(suit+"\t"+number);
	}
}

class Poker extends Card{
	public Poker(String su, String num){
		suit=su;
		number=num;
	}

	public static int[] shuffleCards(){
		int order[]=new int[52];
		for(int i=0; i<52; i++) order[i]=i;

		Random rand=new Random(System.currentTimeMillis());
		int n, temp;
		for(int i=0; i<52; i++){
			n=rand.nextInt(52);
			temp=order[i];
			order[i]=order[n];
			order[n]=temp;
		}
		
		return order;
	}

	public static String setSuit(int ord){
		if(ord/13==0){
			return "Club";
		}else if(ord/13==1){
			return "Diamond";
		}else if(ord/13==2){
			return "Heart";
		}else{
			return "Spades";
		}
	}

	public static String setNumber(int ord){
		ord+=1;
		if(ord%13==1){
			return "A";
		}else if(ord%13==11){
			return "J";
		}else if(ord%13==12){
			return "Q";
		}else if(ord%13==0){
			return "K";
		}else{
			return Integer.toString(ord%13);
		}
	}
}

class PokerOnTable extends Card{

	public static void printThePojerOnTable(int quantity){
		for(int i=0; i<quantity; i++){
			// System.out.println(pokercard[i].suit+"\t"+pokercard[i].number)
		}
	}
}

class Player{
	int order;
	String name;
	int score;
	Card pokercard[]=new Card[6];

	Player(int o, Card po[]){
		order=o;
		name="Player "+Integer.toString(o);
		score=0;
		int n=0;
		for(int i=0; i<24; i++){
			if(i%4==o){
				pokercard[n]=po[i];
				n++;
			}
		}
	}

	public void show(){
		System.out.println("Player = "+name);
		System.out.println("Order = "+order+1);
		System.out.println("Score = "+score);
		System.out.println("PokerCard = ");
		for(int i=0; i<6; i++){
			System.out.println(pokercard[i].suit+"\t"+pokercard[i].number);
		}
	}

	public void showScore(){
		System.out.println(name+":"+score);
	}
}

public class PickRed{
	static Poker[] poker=new Poker[52];
	static PokerOnTable[] pokerOnTable=new PokerOnTable[14];
	static Player[] player=new Player[4];

	public static void main(String args[]){
		initializeCards();
		initializeForFourPlayers();

		int round=1;

		for(int i=0; i<6; i++){

		}
	}

	public static void initializeCards(){
		int order[]=new int[52];
		order=Poker.shuffleCards();

		for(int i=0; i<52; i++){
			poker[i]=new Poker(Poker.setSuit(order[i]),Poker.setNumber(order[i]));
		}

		for(int i=0; i<52; i++){
			poker[i].show();
		}
	}

	public static void initializeCardsOnTable(){
		
	}

	public static void initializeForFourPlayers(){
		//order = i (0,1,2,3)
		for(int i=0; i<4; i++){
			player[i]=new Player(i,poker);
		}
	}

	public static void inputTheCardAndCheck(){
		Scanner scn=new Scanner(System.in);
		System.out.println("Please input the suit:");
		String inputSuit=scn.next();

		System.out.println("Please input the number:");
		String inputNumber=scn.next();

	}

	public static void game(){

	}
}