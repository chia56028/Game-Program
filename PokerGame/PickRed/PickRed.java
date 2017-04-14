import java.util.*;

public class Pickred{
	static Pokers pokercards[]=new Pokers[52];
	static SortPokers sortPokers=new SortPokers();
	static Players players[]=new Players[4];
	static Table table;


	public static void main(String args[]){
		initializePokers();
		initializePlayers(4);
		table=new Table(pokercards);

		for(int i=0; i<6; i++){
			showTable(i);
			inputPokers();

			System.out.println("\n");
		}
	}

	public static void initializePokers(){
		int order[]=Cards.shuffleCards();

		for(int i=0; i<52; i++){
			pokercards[i]=new Pokers(order[i]);
		}
	}

	public static void initializePlayers(int numberOfPeople){
		for(int i=0; i<numberOfPeople; i++){
			players[i]=new Players(i,pokercards);
		}
	}

	public static void showTable(int round){
		sort(round);
		table.showRoundAndScore(round);
		for(int j=0; j<4; j++){
			players[j].showPlayerAndScore();
		}
		System.out.println("\n");
		table.showPokersOnTable();
		players[0].showPlayerAndPokers();
	}

	public static void inputPokers(){
		players[0].inputAndcheck();
	}

	public static void sort(int round){
		sortPokers=new SortPokers(table);
		sortPokers.sorting(sortPokers);
		sortPokers=new SortPokers(round,players[0]);
		sortPokers.sorting(sortPokers);
	}
}

class Cards{
	protected int serialNumber;

	Cards(){}

	public static int[] shuffleCards(){
		Random rand=new Random(System.currentTimeMillis());
		int order[]=new int[52];
		int n, temp;

		for(int i=0; i<52; i++) order[i]=i;
		for(int i=0; i<52; i++){
			n=rand.nextInt(52);
			temp=order[i];
			order[i]=order[n];
			order[n]=temp;
		}
		
		return order;
	}

	public void showPoker(){
		System.out.println(serialNumber);
	}
	
}

class Pokers extends Cards{
	public String suit;
	public String number;

	Pokers(){}

	Pokers(int ser){
		serialNumber=ser;
		suit=setSuit(ser);
		number=setNumber(ser);
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

	public void showPoker(){
		System.out.println(suit+"\t"+number);
	}

}

class SortPokers{
	int numberOfPokers;
	Pokers pokerToSort[]=new Pokers[14];

	SortPokers(){}

	SortPokers(Table table){
		numberOfPokers=table.pokercardsOnTable;
		pokerToSort=table.pokercards;
	}

	SortPokers(int round,Players player){
		numberOfPokers=(6-round);
		pokerToSort=player.pokercards;
	}

	public void sorting(SortPokers sorting){
		int tem;
		String temp;

		for(int i=0; i<sorting.numberOfPokers; i++){
			for(int j=1; j<sorting.numberOfPokers-i; j++){
				if(sorting.pokerToSort[j-1].serialNumber>sorting.pokerToSort[j].serialNumber){
					tem=sorting.pokerToSort[j-1].serialNumber;
					sorting.pokerToSort[j-1].serialNumber=sorting.pokerToSort[j].serialNumber;
					sorting.pokerToSort[j].serialNumber=tem;

					temp=sorting.pokerToSort[j-1].suit;
					sorting.pokerToSort[j-1].suit=sorting.pokerToSort[j].suit;
					sorting.pokerToSort[j].suit=temp;

					temp=sorting.pokerToSort[j-1].number;
					sorting.pokerToSort[j-1].number=sorting.pokerToSort[j].number;
					sorting.pokerToSort[j].number=temp;
				}
			}
		}
	}
}

class Table{
	int pokercardsOnTable;
	Pokers pokercards[]=new Pokers[14];

	Table(Pokers pokers[]){
		pokercardsOnTable=4;

		int n=0;
		for(int i=24; i<28; i++){
			pokercards[n]=pokers[i];
			n++;
		}
	}

	public void showRoundAndScore(int round){
		System.out.println("Round : "+(round+1));
		System.out.print("Score : ");
	}

	public void showPokersOnTable(){
		System.out.println("The pokers on table :");
		for(int i=0; i<pokercardsOnTable; i++){
			System.out.println(pokercards[i].suit+"\t"+pokercards[i].number);
		}
		System.out.println();
	}

	public Pokers canThePokersBeMated(Pokers input){


		return input;
	}

	public Pokers[] canThePokersBeMated(Pokers input[]){
		

		return input;
	}

}

class Players{
	int order;
	String name;
	int score;
	public Pokers pokercards[]=new Pokers[6];

	Players(){}

	Players(int ord, Pokers pokers[]){
		order=ord;									// 0,1,2,3...
		name="Player "+Integer.toString(ord+1);
		score=0;

		int n=0;
		for(int i=0; i<24; i++){
			if(i%4==ord){
				pokercards[n]=pokers[i];
				n++;
			}
		}
	}

	public void showPlayerAndPokers(){
		System.out.println("The pokers of "+name+" :");
		for(int i=0; i<6; i++){
			System.out.println(pokercards[i].suit+"\t"+pokercards[i].number);
		}
	}

	public void showPlayerAndScore(){
		System.out.print(name+" = "+score+"\t");
	}

	public Pokers inputAndcheck(){
		Pokers input=new Pokers();
		Scanner scn=new Scanner(System.in);
		boolean check=false;
		while(check==false){
			System.out.print("Please input the suit :");
			String su=scn.next();
			System.out.print("Please input the number :");
			String num=scn.next();
			check=checkInput(su,num);
			if(check==true){
				input.suit=su;
				input.number=num;
			}
		}
		return input;
	}

	public boolean checkInput(String suit, String number){
		for(int i=0; i<6; i++){
			if(suit.equals(pokercards[i].suit) && number.equals(pokercards[i].number)){
				return true;
			}
		}
		System.out.println("You don't have this card.");
		return false;
	}
}
