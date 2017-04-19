import java.util.*;

interface Chess{
	public int maxChess=8;
}

interface Chessboard{
	public int e=4;
}

class Chessgame implements Chess, Chessboard{
	public int chessboard[][]=new int[e][e];

	public Chessgame(){
		chessboard=new int[][]{{1,1,2,2},{3,3,5,4},{5,4,6,6},{7,7,8,8}};
	}

	public void printArray(){
		System.out.println();
		for(int i=0; i<e; i++){
			for(int j=0; j<e; j++){
				if(chessboard[i][j]<10){
					System.out.print(" "+chessboard[i][j]+" ");
				}else{
					System.out.print(chessboard[i][j]+" ");
				}
			}
			System.out.println();
		}
	}

	public int checkInput(String requirement){
		Scanner scn=new Scanner(System.in);
		int check=0;
		String inputString;
		float inputInt;

		while(check==0){
			System.out.println(requirement);
			inputString=scn.next();
			inputInt=Float.parseFloat(inputString);
			if(inputInt>=1 && inputInt<=e && (int)inputInt==inputInt){
				check=1;
				return (int)inputInt;
			}else{
				System.out.println("Please input a number between 1-4!!!");
			}
		}

		return 999;
	}

	public int canGameGoOn(){
		int A1[]=new int[e*e];
		int A2[]=new int[e*e];
		int B1[]=new int[e*e];
		int B2[]=new int[e*e];
		int endgame[]=new int[e*e];
		int wingame[]=new int[e*e];
		int r1, r2, c1, c2;
		int k;

		for(int i=0; i<e*e; i++){
			A1[i]=99;
			A2[i]=99;
			B1[i]=99;
			B2[i]=99;
			endgame[i]=0;
			wingame[i]=0;
		}
		for(int i=0; i<e; i++){
		  	for(int j=0; j<e; j++){
			    k=chessboard[j][i];
			    if(A1[k]==99){
	          		B1[k]=j;
	          		A1[k]=i;
	    		}else{
		        	B2[k]=j;
		        	A2[k]=i;
		        }
		  	}
		}
		for(int l=1; l<=e*e/2; l++){
			r1=A1[l];
			r2=A2[l];
			c1=B1[l];
			c2=B2[l];

			if(r1!=99 && r2!=99 && c1!=99 && c2!=99){
				if(checkLine(r1,r2,c1,c2)){
					k=chessboard[c1][r1];
					A1[k]=99;
					B1[k]=99;
					A2[k]=99;
					B2[k]=99;
					return 1;
				}else if(checkCorner(r1,r2,c1,c2)){
					k=chessboard[c1][r1];
				 	A1[k]=99;
				 	B1[k]=99;
					A2[k]=99;
					B2[k]=99;
					return 1;
				}else{
					endgame[l]=1;
				}
			}else{
				endgame[l]=1;
				wingame[l]=1;
			}
		}

		int game=2;
		for(int l=1; l<=e*e/2; l++)
		  	if(wingame[l]!=1) game=0;

		if(game==2) return 2;
		else game=0;

		if(game==0){
			for(int l=1; l<=e*e/2; l++)
			  	if(endgame[l]!=1) return 1;
		}

		return 0;
	}

	public int gameOver(){
		int a=canGameGoOn();
		if(a==1) return 1;
		else if(a==0) System.out.println("There is nothing can be removed，game over QWQ");
		else if(a==2) System.out.println("You win the game!!!");
		return 0;
	}

	public boolean canTwoCoordinateBeRemoved(int r1, int r2, int c1, int c2){
		if(chessboard[c1][r1]==0 || chessboard[c2][r2]==0){
			System.out.println("Please don't input a number which had already be cancel~");
			return false;
		}else if(r1==r2 && c1==c2){
			System.out.println("Please input two different coordinate~");
			return false;
		}else if(chessboard[c1][r1]!=chessboard[c2][r2]){
			System.out.println("Only can remove same numbers~");
			return false;
		}else{
			return true;
		}
	}

	public boolean checkLine(int r1, int r2, int c1, int c2){
		int checkline=0;
		int m, n;
		if(r1==r2 || c1==c2){
			checkline=1;
			if(r1==r2){
				if(c1>c2){
					n=c1;
					c1=c2;
					c2=n;
				}
				if(c2>c1){
					for(int i=c1+1; i<c2 ; i++){
						if(chessboard[i][r1]!=0) checkline=0;
					}
				}	
			}
			if(c1==c2){
				if(r1>r2){
					m=r1;
					r1=r2;
					r2=m;
				}
				if(r2>r1){
					for(int i=r1+1; i<r2 ; i++){
						if(chessboard[c1][i]!=0) checkline=0;
					}
				}
			}

			if(checkline==1) return true;
			else return false;
		}
		return false;
	}

	public boolean checkCorner(int r1, int r2, int c1, int c2){
		int crr=1, ccr=1, crl=1, ccl=1; 
		int m, n;
		if(r1!=r2 && c1!=c2){
			//(c1>c2 && r1>r2) || (c2>c1 && r2>r1)
			if(r2>r1 && c2>c1){
				n=c1;
				c1=c2;
				c2=n;

				m=r1;
				r1=r2;
				r2=m;
			}
			if(c1>c2 && r1>r2){
				for(int i=r2+1; i<=r1; i++){
					if(chessboard[c2][i]!=0) crr=0;
				}
				for(int j=c2; j<c1; j++){
					if(chessboard[j][r1]!=0)	ccr=0;
				}
				for(int i=r2; i<r1; i++){
					if(chessboard[c1][i]!=0) crl=0;
				}
				for(int j=c2+1; j<=c1; j++){
					if(chessboard[j][r2]!=0)	ccl=0;
				}
			}
			//(r1>r2 && c2>c1) || (r2>r1 && c1>c2)
			if(r2>r1 && c1>c2){
				n=c1;
				c1=c2;
				c2=n;
				m=r1;
				r1=r2;
				r2=m;
			}
			if(r1>r2 && c2>c1){
				for(int i=r2; i<r1; i++){
					if(chessboard[c1][i]!=0) crl=0;
				}
				for(int j=c1; j<c2; j++){
					if(chessboard[j][r2]!=0) ccl=0;
				}
				for(int i=r2+1; i<=r1; i++){
					if(chessboard[c2][i]!=0) crr=0;
				}
				for(int j=c1+1; j<=c2; j++){
					if(chessboard[j][r1]!=0) ccr=0;
				}
			}
			if((crr==1 && ccr==1) || (crl==1 && ccl==1)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}

public class MatchingGame{
	public static Chessgame chessgame=new Chessgame();

	public static void main(String[] args) {
		System.out.println("Welcome to the game~");
		chessgame.printArray();

		int x1, x2, y1, y2;
		int r1, r2, c1, c2;
		int game=1;

		while(game==1){
			x1=chessgame.checkInput("Please input first coordinate of x:");
			y1=chessgame.checkInput("Please input first coordinate of y:");
			x2=chessgame.checkInput("Please input second coordinate of x:");
			y2=chessgame.checkInput("Please input second coordinate of y:");
			
			r1=x1-1;
			r2=x2-1;
			c1=y1-1;
			c2=y2-1;
			
			if(chessgame.canTwoCoordinateBeRemoved(r1,r2,c1,c2)){
				if(chessgame.checkLine(r1,r2,c1,c2) || chessgame.checkCorner(r1,r2,c1,c2)){
					chessgame.chessboard[c1][r1]=0;
					chessgame.chessboard[c2][r2]=0;
					System.out.println("Successful Remove");
				}else{
					System.out.println("There is something between, they can't be remove~");
				}
			}
			chessgame.printArray();
			game=chessgame.gameOver();
		}

		return;
	}
}