import java.util.*;

class Card{
	private String suit;
	private String number;

	public Card(){}

	public Card(String su, String num){
		suit=su;
		number=num;
	}

	public void show(){
		System.out.println(suit+"\t"+number);
	}
}

public class Poker{
	static Card[] card=new Card[52];

	public static void main(String args[]){
		shuffleCards();
		for(int i=0; i<52; i++){
			card[i].show();
		}
	}

	public static void shuffleCards(){
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
		
		for(int i=0; i<52; i++){
			card[i]=new Card(setSuit(order[i]),setNumber(order[i]));
		}

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