import java.util.*;

public class PickRed{
	public static Deck deck=new Deck();
	public static Table table=new Table();
	public static Player player[]=new Player[4];
	
	public static void main(String args[]){
		initializePlayers();
		shuffleAndDeal();

		for(int round=0; round<6; round++){
			System.out.println("Round = "+(round+1));
			for(int i=0; i<4; i++) player[i].sort();
			play(round);
		}

		showScores();
	}

	public static void initializePlayers(){
		for(int i=0; i<4; i++) player[i]=new Player();
	}

	public static void shuffleAndDeal(){
		deck.shuffle();
		deck.deal(player);
		deck.deal(table);
	}

	public static void play(int round){
		for(int i=0; i<4; i++){
			if(i==0){
				if(round!=0) showScores();
				System.out.println("Table");
				table.showPoker();
				player[0].showPoker(round,0);

				table.tablePokers[table.numberOfPokers].serialNumber=player[0].inputAndcheck();
				table.numberOfPokers++;
				System.out.println();
			}else{
				player[i].showPoker(round,i);

				table.tablePokers[table.numberOfPokers].serialNumber=player[i].selectPoker(table);
				table.numberOfPokers++;
			}
			
			table.tablePokers[table.numberOfPokers].serialNumber=deck.deckPokers[deck.orderOfPokers].serialNumber;
			table.numberOfPokers++;			
			deck.orderOfPokers++;

			System.out.println("Table < After play and draw >");
			table.showPoker();

			System.out.println("Table < After eliminate >");
			player[i].score+=table.eliminate();
			table.showPoker();
			System.out.println();
		}
	}

	public static void showScores(){
		System.out.println("Score");
		for(int i=0; i<4; i++){
			System.out.println("Player "+(i+1)+"   "+player[i].score);
		}
		System.out.println();
	}
}

class Poker{
	public int serialNumber;
	public String suit;
	public String rank;
	public int score;

	public Poker(){
		serialNumber=99;
		suit="NULL";
		rank="NULL";
		score=0;
	}

	public Poker(int ser){
		serialNumber=ser;
		suit=setSuit(ser);
		rank=setNumber(ser);
		score=setScore(ser);
	}

	public static String setSuit(int ord){
		if(ord/13==0) return "Club";
		else if(ord/13==1) return "Diamond";
		else if(ord/13==2) return "Heart";
		else return "Spades";
	}

	public static String setNumber(int ord){
		ord+=1;
		if(ord%13==1) return "A";
		else if(ord%13==11)	return "J";
		else if(ord%13==12) return "Q";
		else if(ord%13==0) return "K";
		else return Integer.toString(ord%13);
	}

	public static int setScore(int ord){
		if(ord==39) return 10;
		if(ord==13 || ord==26) return 20;
		if(ord/13==1 || ord/13==2){
			ord+=1;
			if(ord%13==9 || ord%13==11 || ord%13==12 || ord%13==0) return 10;
			else return (ord%13);
		}else{
			return 0;
		}
	}
}

class Deck{
	public int orderOfPokers;
	public Poker deckPokers[]=new Poker[52];

	public Deck(){
		orderOfPokers=28;
		for(int i=0; i<52; i++) deckPokers[i]=new Poker();
	}

	public void shuffle(){
		Random rand=new Random(System.currentTimeMillis());
		int n, temp;

		for(int i=0; i<52; i++) deckPokers[i].serialNumber=i;
		for(int i=0; i<52; i++){
			n=rand.nextInt(52);
			temp=deckPokers[i].serialNumber;
			deckPokers[i].serialNumber=deckPokers[n].serialNumber;
			deckPokers[n].serialNumber=temp;
		}
	}

	public void deal(Player players[]){
		int n=0;

		for(int i=0; i<24; i++){
			if(i%4==0) players[0].handPokers[n].serialNumber=deckPokers[i].serialNumber;
			else if(i%4==1) players[1].handPokers[n].serialNumber=deckPokers[i].serialNumber;
			else if(i%4==2) players[2].handPokers[n].serialNumber=deckPokers[i].serialNumber;
			else if(i%4==3){
				players[3].handPokers[n].serialNumber=deckPokers[i].serialNumber;
				n++;
			}
		}
	}

	public void deal(Table table){
		int n=0;

		for(int i=24; i<28; i++){
			table.tablePokers[n].serialNumber=deckPokers[i].serialNumber;
			n++;
		}
	}
}

class Table{
	public int numberOfPokers;
	public Poker tablePokers[]=new Poker[12];

	public Table(){
		numberOfPokers=4;
		for(int i=0; i<12; i++) tablePokers[i]=new Poker();
	}
	
	public void showPoker(){
		sort();
		count();
		System.out.println("Number = "+numberOfPokers);
		for(int i=0 ; i<numberOfPokers+1; i++){
			if(tablePokers[i].serialNumber==99){
				System.out.println();
				return;
			}
			tablePokers[i]=new Poker(tablePokers[i].serialNumber);
			System.out.println(tablePokers[i].serialNumber+"\t"+tablePokers[i].suit+"\t"+tablePokers[i].rank+"\t"+tablePokers[i].score);
		}
	}

	public void sort(){
		int temp;

		for(int i=0; i<numberOfPokers-1; i++){
			for(int j=i+1; j<numberOfPokers; j++){
				if(tablePokers[j].serialNumber<tablePokers[i].serialNumber){
					temp=tablePokers[i].serialNumber;
					tablePokers[i].serialNumber=tablePokers[j].serialNumber;
					tablePokers[j].serialNumber=temp;
				}
			}
		}
	}

	public void count(){
		for(int i=0; i<13; i++){
			if(tablePokers[i].serialNumber==99){
				numberOfPokers=i;
				return;
			}
		}
	}

	public void sortByNumber(){
		int temp;

		sort();
		for(int i=0; i<numberOfPokers-1; i++){
			for(int j=i+1; j<numberOfPokers; j++){
				if(tablePokers[j].serialNumber%13<tablePokers[i].serialNumber%13){
					temp=tablePokers[i].serialNumber;
					tablePokers[i].serialNumber=tablePokers[j].serialNumber;
					tablePokers[j].serialNumber=temp;
				}
			}
		}
		for(int i=numberOfPokers-1; i>0; i--){
			for(int j=i-1; j>=0; j--){
				if(tablePokers[j].serialNumber%13==tablePokers[i].serialNumber%13){
					if(tablePokers[j].score<tablePokers[i].score){
						temp=tablePokers[i].serialNumber;
						tablePokers[i].serialNumber=tablePokers[j].serialNumber;
						tablePokers[j].serialNumber=temp;
					}
				}
			}
		}

		return;
	}

	public int eliminate(){
		int scoreYouGet=0;

		sortByNumber();
		for(int i=0; i<numberOfPokers-1; i++){
			for(int j=i+1; j<numberOfPokers; j++){
				if(tablePokers[i].serialNumber!=99 && tablePokers[j].serialNumber!=99){
					int a=tablePokers[i].serialNumber%13;
					int b=tablePokers[j].serialNumber%13;

					if((a+b)==8){
						scoreYouGet+=setScore(tablePokers[i].serialNumber);
						scoreYouGet+=setScore(tablePokers[j].serialNumber);
						tablePokers[i].serialNumber=99;
						tablePokers[j].serialNumber=99;
						continue;
					}

					if((a==b) && (a==4 || a==9 || a==10 || a==11 || a==12)){
						scoreYouGet+=setScore(tablePokers[i].serialNumber);
						scoreYouGet+=setScore(tablePokers[j].serialNumber);
						tablePokers[i].serialNumber=99;
						tablePokers[j].serialNumber=99;
						continue;
					}
				}
			}
		}

		return scoreYouGet;
	}

	public static int setScore(int ord){
		if(ord==39) return 10;
		if(ord==13 || ord==26) return 20;
		if(ord/13==1 || ord/13==2){
			ord+=1;
			if(ord%13==9 || ord%13==11 || ord%13==12 || ord%13==0) return 10;
			else return (ord%13);
		}else{
			return 0;
		}
	}
}

class Player{
	public int numberOfPokers;
	public Poker handPokers[]=new Poker[6];
	public int score;

	public Player(){
		numberOfPokers=6;
		for(int i=0; i<6; i++) handPokers[i]=new Poker();
		score=0;
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

	public void showPoker(int round, int order){
		System.out.println("Player "+(order+1));
		sort();
		if(order!=0) return;

		System.out.println("Your pokers");
		for(int i=0 ; i<(6-round); i++){
			if(handPokers[i].serialNumber==99){
				System.out.println();
				return;
			}
			handPokers[i]=new Poker(handPokers[i].serialNumber);
			System.out.println(handPokers[i].serialNumber+"\t"+handPokers[i].suit+"\t"+handPokers[i].rank);
		}
	}
	
	public int inputAndcheck(){
		Scanner scn=new Scanner(System.in);
		int inputNumber;
		String inputSuit;
		String inputRank;
		boolean check=false;

		while(check==false){
			/////////////test/////////////
			System.out.print("Please input the serialNumber: ");
			inputNumber=scn.nextInt();
			///////////////////////////
			// System.out.print("Please input the suit of poker which you want to eliminate:");
			// inputSuit=scn.next();
			// System.out.println("Plesae input the rank of poker which you want to eliminate");
			// inputRank=scn.next();

			////////////test///////////
			for(int i=0; i<6; i++){
				if(handPokers[i].serialNumber==inputNumber){
					handPokers[i].serialNumber=99;
					return inputNumber;
				}
			}
			///////////////////////////
			// for(int i=0; i<6; i++){
			// 	if(handPokers[i].suit==inputSuit && handPokers[i].rank==inputRank){
			// 		return handPokers[i].serialNumber;
			// 	} 
			// }
		}
		
		return 999;
	}

	public int selectPoker(Table table){
		int score=0, max=0, maxj=0;
		int numberToPlay;

		sort();
		for(int i=0; i<table.numberOfPokers; i++){
			for(int j=0; j<numberOfPokers; j++){
				int a=table.tablePokers[i].serialNumber%13;
				int b=handPokers[j].serialNumber%13;

				if(table.tablePokers[i].serialNumber==99 || handPokers[j].serialNumber==99) break;

				if((a+b)==8){
					score=setScore(table.tablePokers[i].serialNumber)+setScore(handPokers[j].serialNumber);
					if(score>max){
						max=score;
						maxj=j;
					}
					continue;
				}

				if((a==b) && (a==4 || a==9 || a==10 || a==11 || a==12)){
					score=setScore(table.tablePokers[i].serialNumber)+setScore(handPokers[j].serialNumber);
					if(score>max){
						max=score;
						maxj=j;
					}
					continue;
				}
			}
		}
		numberToPlay=handPokers[maxj].serialNumber;
		handPokers[maxj].serialNumber=99;
		return numberToPlay;
	}

	public static int setScore(int ord){
		if(ord==39) return 10;
		if(ord==13 || ord==26) return 20;
		if(ord/13==1 || ord/13==2){
			ord+=1;
			if(ord%13==9 || ord%13==11 || ord%13==12 || ord%13==0) return 10;
			else return (ord%13);
		}else{
			return 0;
		}
	}
}

