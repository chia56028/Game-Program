import java.util.*;

public class OldMaid{
	public static Deck deck=new Deck();
	public static Player players[]=new Player[4];

	public static void main(String args[]){
		initializePlayers();
		shuffleAndDeal();
		for(int round=0; round<3; round++){
			for(int i=0; i<4; i++) eliminateAndCount(i);
			showAndinput(0,1);
			for(int i=1; i<4; i++) drawAndArrange(i);
		}

	}

	public static void initializePlayers(){
		for(int i=0; i<4; i++) players[i]=new Player();
	}

	public static void shuffleAndDeal(){
		deck.shuffle();
		deck.deal(players);
	}

	public static void eliminateAndCount(int i){
			players[i].eliminate();
			players[i].sort();
			players[i].count();
	}

	public static void drawAndArrange(int i){

	}

	public static void showAndinput(int i, int j){
		Scanner scn=new Scanner(System.in);
		int input;
		boolean check=false;

		System.out.println("Player 1 ("+players[i].numberOfPokers+" pokers)");
		players[i].showPoker();
		System.out.println("Next player have "+players[j].numberOfPokers+" pokers.");
		while(check==false){
			System.out.println("Please input a number between 1 - "+players[j].numberOfPokers);
			input=scn.nextInt();
			if(input>=1 && input<=players[j].numberOfPokers) check=true;
		}
	}
}

class Poker{
	public int serialNumber;
	public String suit;
	public String rank;

	Poker(){
		serialNumber=99;
		suit="NULL";
		rank="NULL";
	}
}

class Deck{
	Poker deckPokers[]=new Poker[54];

	Deck(){
		for(int i=0; i<54; i++) deckPokers[i]=new Poker();
	}

	public void shuffle(){
		Random rand=new Random(System.currentTimeMillis());
		int n, temp;

		for(int i=0; i<54; i++) deckPokers[i].serialNumber=i;
		for(int i=0; i<54; i++){
			n=rand.nextInt(54);
			temp=deckPokers[i].serialNumber;
			deckPokers[i].serialNumber=deckPokers[n].serialNumber;
			deckPokers[n].serialNumber=temp;
		}
	}

	public void deal(Player players[]){
		int n=0;

		for(int i=0; i<54; i++){
			if(i%4==0) players[0].handPokers[n].serialNumber=deckPokers[i].serialNumber;
			else if(i%4==1) players[1].handPokers[n].serialNumber=deckPokers[i].serialNumber;
			else if(i%4==2) players[2].handPokers[n].serialNumber=deckPokers[i].serialNumber;
			else if(i%4==3){
				players[3].handPokers[n].serialNumber=deckPokers[i].serialNumber;
				n++;
			}
		}
	}

	///////////////////////////////
	public void showPoker(){
		for(int i=0 ;i<54; i++){
			System.out.println(deckPokers[i].serialNumber);
		}
	}
	///////////////////////////////
}

class Player{
	int numberOfPokers;
	Poker handPokers[]=new Poker[15];

	Player(){
		numberOfPokers=15;
		for(int i=0; i<15; i++) handPokers[i]=new Poker();
	}

	public void showPoker(){
		for(int i=0 ;i<14; i++){
			if(handPokers[i].serialNumber==99) return;
			System.out.println(handPokers[i].serialNumber);
		}
	}

	public void eliminate(){
		for(int i=0; i<numberOfPokers-1; i++){
			for(int j=i+1; j<numberOfPokers; j++){
				int a=handPokers[i].serialNumber;
				int b=handPokers[j].serialNumber;

				if(a!=99 && b!=99 && a!=52 && b!=52 && a!=53 && b!=53){
					if(a%13==b%13){
						handPokers[i].serialNumber=99;
						handPokers[j].serialNumber=99;
					}
				}
			}
		}
	}

	public void sort(){
		int temp;

		for(int i=0; i<numberOfPokers-1; i++){
			for(int j=i+1; j<numberOfPokers; j++){
				if(handPokers[j].serialNumber<handPokers[i].serialNumber){
					temp=handPokers[i].serialNumber;
					handPokers[i].serialNumber=handPokers[j].serialNumber;
					handPokers[j].serialNumber=temp;
				}
			}
		}
	}

	public void count(){
		for(int i=0; i<numberOfPokers; i++){
			if(handPokers[i].serialNumber==99){
				numberOfPokers=i;
				//System.out.println("number : "+numberOfPokers);
				return;
			}
		}
	}
}