import java.util.*;

public class OldMaid{
	public static Deck deck=new Deck();
	public static Player players[]=new Player[4];

	public static void main(String args[]){
		initializePlayers();
		shuffleAndDeal();
		for(int round=0; round<100; round++){
			for(int i=0; i<4; i++) eliminateAndCount(i);
			if(players[0].numberOfPokers==0){
				System.out.println("You win the game!!!");
				break;
			}
			if(players[1].numberOfPokers==0 && players[2].numberOfPokers==0 && players[3].numberOfPokers==0){
				System.out.println("You lose the game.");
				break;
			}
			for(int i=0; i<4; i++) show(i);
			for(int i=0; i<4; i++){
				if(players[i].numberOfPokers==0) continue;
				int j=1;
				while(players[(i+j)%4].numberOfPokers==0) j++;
				if(i==0) showAndinput(i,(i+j)%4);
				else draw(i,(i+j)%4);
			}
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

	public static void draw(int i, int j){
		Random rand=new Random(System.currentTimeMillis());
		int num=players[j].numberOfPokers;
		int draw=rand.nextInt(num);
		arrange(i,j,draw);
	}

	public static void showAndinput(int i, int j){
		Scanner scn=new Scanner(System.in);
		int input=99;
		boolean check=false;
		/*
		System.out.println("Player 1 ("+players[i].numberOfPokers+" pokers)");
		players[i].showPoker();
		*/
		System.out.println("Next player have "+players[j].numberOfPokers+" pokers.");
		while(check==false){
			System.out.println("Please input a number between 1 - "+players[j].numberOfPokers);
			input=scn.nextInt();
			if(input>=1 && input<=players[j].numberOfPokers) check=true;
		}
		arrange(i,j,(input-1));
	}

	public static void arrange(int i, int j, int draw){
		players[i].numberOfPokers+=1;
		players[i].handPokers[players[i].numberOfPokers-1]=players[j].handPokers[draw];
		players[j].handPokers[draw]=new Poker();
		players[i].eliminate();
		players[i].sort();
		players[i].count();
		players[j].sort();
		players[j].count();
	}

	public static void show(int i){
		System.out.println("Player "+i+" ("+players[i].numberOfPokers+" pokers)");
		players[i].showPoker();
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
			if(handPokers[i].serialNumber==99){
				System.out.println();
				return;
			}
			System.out.print(handPokers[i].serialNumber+"\t");
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
		for(int i=0; i<numberOfPokers+1; i++){
			if(handPokers[i].serialNumber==99){
				numberOfPokers=i;
				//System.out.println("number : "+numberOfPokers);
				return;
			}
		}
	}
}