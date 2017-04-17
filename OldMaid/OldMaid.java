import java.util.*;

public class OldMaid{
	public static Deck deck=new Deck();
	public static Player players[]=new Player[4];
	public static int price=1;

	public static void main(String args[]){
		initializePlayers();
		shuffleAndDeal();

		for(int round=0; round<100; round++){
			for(int i=0; i<4; i++) eliminateAndCount(i);

			if(players[0].numberOfPokers==0){
				do{
					for(int i=1; i<4; i++){
						if(players[i].numberOfPokers==0) continue;
						int j=1;
						while(players[(i+j)%4].numberOfPokers==0) j++;
						draw(i,(i+j)%4);
					}
					if(price==4){
						for(int k=0; k<4; k++){
							if(players[k].place==0){
								players[k].place=4;
								price++;
							}
						}
					}
				}while(price!=5);
				for(int i=0; i<4; i++) show(i);
				System.out.println("You got "+players[0].place+" place.");
				break;
			}
			if(players[1].numberOfPokers==0 && players[2].numberOfPokers==0 && players[3].numberOfPokers==0){
				for(int i=0; i<4; i++) show(i);
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
			players[i].shuffle(players[i].numberOfPokers);
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
		
		System.out.println("Your pokers :");
		players[i].sort();
		for(int k=0; k<15; k++) players[i].handPokers[k]=new Poker(players[i].handPokers[k].serialNumber);
		players[i].showPoker();
		
		System.out.println("Next player have "+players[j].numberOfPokers+" pokers.");
		while(check==false){
			System.out.print("Please input a number between 1 - "+players[j].numberOfPokers+" :");
			System.out.println();
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
		players[i].shuffle(players[i].numberOfPokers);
		players[j].shuffle(players[j].numberOfPokers);
		if(players[j].numberOfPokers==0){
			if(players[j].place==0){
				players[j].place=price;
				price++;
			}
		}
		if(players[i].numberOfPokers==0){
			if(players[i].place==0){
				players[i].place=price;
				price++;
			}
		}				
	}

	public static void show(int i){
		if(players[i].place!=0){
			System.out.println("Player "+(i+1)+" : "+players[i].place+" place");
		}else{
			System.out.println("Player "+(i+1)+" ("+players[i].numberOfPokers+" pokers)");
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

	Poker(int ser){
		serialNumber=ser;
		suit=setSuit(ser);
		rank=setNumber(ser);
	}

	public static String setSuit(int ord){
		if(ord==52 || ord==53) return "Joker";
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
		if(ord==52 || ord==53) return "Joker";
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
	int place;

	Player(){
		place=0;
		numberOfPokers=15;
		for(int i=0; i<15; i++) handPokers[i]=new Poker();
	}

	public void showPoker(){
		for(int i=0 ;i<14; i++){
			if(handPokers[i].serialNumber==99){
				System.out.println();
				return;
			}
			System.out.println(handPokers[i].serialNumber+"\t"+handPokers[i].suit+"\t"+handPokers[i].rank);
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

	public void shuffle(int num){
		Random rand=new Random(System.currentTimeMillis());
		int n, temp;

		for(int i=0; i<num; i++){
			n=rand.nextInt(num);
			temp=handPokers[i].serialNumber;
			handPokers[i].serialNumber=handPokers[n].serialNumber;
			handPokers[n].serialNumber=temp;
		}
	}
}