/*
Program:MatchingGame
Author:ChingYu-Chia
Date:2017/03
*/ 

//#define DEBUG
#include <iostream>
#include <stdlib.h>
#include <time.h>
#include <string>
using namespace std;

void establishAnArray();
bool theGameEndAtFirstPrint();
int  canGameGoOn();
int  gameOver();
int  checkInput(string);
void printArray();
bool canTwoCoordinateBeRemoved(int, int, int, int);
bool checkLine(int, int, int, int);
bool checkCorner(int, int, int, int);

int e=6;					//e=edge
int arr2d[6][6];

int main(int argc, char** argv) {
	establishAnArray();	
	cout<<"Welcome to the game~"<<endl;
	printArray();
	
	int x1, x2, y1, y2;
	int r1, r2, c1, c2;
	int game=1;

	while(game==1){
		//輸入座標 
		x1=checkInput("請輸入第一組座標x值:");
		y1=checkInput("請輸入第一組座標y值:");
		x2=checkInput("請輸入第二組座標x值:");
		y2=checkInput("請輸入第二組座標y值:");
		
		//轉換成陣列欄位 
		r1=x1-1, r2=x2-1, c1=y1-1, c2=y2-1;
		
		if(canTwoCoordinateBeRemoved(r1,r2,c1,c2)){
			if(checkLine(r1,r2,c1,c2) || checkCorner(r1,r2,c1,c2)){
				arr2d[c1][r1]=0;
				arr2d[c2][r2]=0;
				cout<<"Successful Remove"<<endl;
			}else{
				cout<<"中間有太多的障礙物無法消除喔~"<<endl; 
			}
		}
		printArray();
		game=gameOver();
	}
	system("pause");
	return 0;
}

void establishAnArray(){
	//建立一個陣列並打亂 
	int arr[e*e];
	for(int i=0; i<e*e; i++){
		arr[i]=i%(e*e/2)+1;
	}
	do{
		srand(time(NULL));
		int m, n;
		for(int i=0; i<e*e; i++){
			m=rand()%e*e;
			n=arr[i];
			arr[i]=arr[m];
			arr[m]=n;
		}
		//放入二維陣列
		int k=0;
		for(int i=0; i<e; i++){
			for(int j=0; j<e; j++){
				arr2d[j][i]=arr[k];
				k+=1;
			}
		}
	}while(theGameEndAtFirstPrint());
}

bool theGameEndAtFirstPrint(){
	if(canGameGoOn()==1){
		return false;
	}else{
		return true;
	}
}

int canGameGoOn(){
	int A1[e*e], A2[e*e], B1[e*e], B2[e*e], endgame[e*e], wingame[e*e];
	int r1, r2, c1, c2;
	int k;
	for(int i=0; i<e*e; i++){
		A1[i]=99, A2[i]=99, B1[i]=99, B2[i]=99, endgame[i]=0, wingame[i]=0;
	}
	for(int i=0; i<e; i++){
	  	for(int j=0; j<e; j++){
			k=arr2d[j][i];
			if(A1[k]==99){
          			B1[k]=j, A1[k]=i;
    			}else{
	        		B2[k]=j, A2[k]=i;
	    		}
	  	}
	}
	for(int l=1; l<=e*e/2; l++){
		r1=A1[l], r2=A2[l], c1=B1[l], c2=B2[l];
		if(r1!=99 && r2!=99 && c1!=99 && c2!=99){
			if(checkLine(r1,r2,c1,c2)){
				k=arr2d[c1][r1];
				A1[k]=99, B1[k]=99;
				A2[k]=99, B2[k]=99;
				#ifdef DEBUG
				cout<<"l="<<l<<", CheckLine=1"<<endl;
				#endif
				return 1;
			}else if(checkCorner(r1,r2,c1,c2)){
				k=arr2d[c1][r1];
			 	A1[k]=99, B1[k]=99;
				A2[k]=99, B2[k]=99;
				#ifdef DEBUG
				cout<<"l="<<l<<", CheckCorner=1"<<endl;
				#endif
				return 1;
			}else{
				endgame[l]=1;
			}
		}else{
			endgame[l]=1;
			wingame[l]=1;
		}
		#ifdef DEBUG
		cout<<l<<", eg="<<endgame[l]<<", wg="<<wingame[l]<<endl;
		#endif
	}
	//判斷遊戲輸贏 
	int game;
	game=2;
	for(int l=1; l<=e*e/2; l++){
	  	if(wingame[l]!=1) game=0;
	}
	if(game==2){
		return 2;
	}else{
		game=0;
	}
	if(game==0){
		for(int l=1; l<=e*e/2; l++){
		  	if(endgame[l]!=1) return 1;
		}
	}
	return 0;
}

int gameOver(){
	int a=canGameGoOn();
	if(a==1){
		return 1;
	}else if(a==0){
		cout<<"There is nothing to remove, game over QwQ"<<endl;
	}else if(a==2){
		cout<<"You win the game!!!"<<endl;
	}
	return 0;
}

void printArray(){
	cout<<endl;
	for(int i=0; i<e; i++){
		for(int j=0; j<e; j++){
			if(arr2d[i][j]<10){
				cout<<" "<<arr2d[i][j]<<" ";
			}else{
				cout<<arr2d[i][j]<<" ";	
			}
		}
		cout<<endl;
	}
}

int checkInput(string requirement){
	int check=0;
	string InputString;
	float InputInt;
	while(check==0){
		cout<<requirement;
		getline(cin,InputString);
		InputInt=atof(InputString.c_str());
		if(InputInt>=1 && InputInt<=e && (int)InputInt==InputInt){
			check=1;
			return InputInt;
		}else{
			cout<<"只能輸入介於1-6間的整數喔!!!"<<endl;
		}
	}
}

bool canTwoCoordinateBeRemoved(int r1, int r2, int c1, int c2){
	if(arr2d[c1][r1]==0 || arr2d[c2][r2]==0){
		cout<<"請勿重複輸入數字已被消掉的座標~"<<endl;
		return false;
	}else if(r1==r2 && c1==c2){
		cout<<"請輸入兩不同座標~"<<endl;
		return false;
	}else if(arr2d[c1][r1]!=arr2d[c2][r2]){
		cout<<"只能消除相同的數字喔~"<<endl;
		return false;
	}else{
		return true;
	}
}

//兩數字相臨 or 在同一直線上 
bool checkLine(int r1, int r2, int c1, int c2){
	int checkline=0;
	int m, n;
	if(r1==r2 || c1==c2){
		checkline=1;
		if(r1==r2){
			if(c1>c2){
				n=c1, c1=c2, c2=n;
			}
			if(c2>c1){
				for(int i=c1+1; i<c2 ; i++){
					if(arr2d[i][r1]!=0)	checkline=0;
				}
			}	
		}
		if(c1==c2){
			if(r1>r2){
				m=r1, r1=r2, r2=m;
			}
			if(r2>r1){
				for(int i=r1+1; i<r2 ; i++){
					if(arr2d[c1][i]!=0)	checkline=0;
				}
			}
		}
		#ifdef DEBUG
		//cout<<"checkline="<<checkline<<endl;
		#endif
		if(checkline==1){
			return true;
		}else{
			return false;
		}
	}
	return false;
}

//轉一個彎
bool checkCorner(int r1, int r2, int c1, int c2){
	int crr=1, ccr=1, crl=1, ccl=1; 
	int m, n;
	if(r1!=r2 && c1!=c2){
		//(c1>c2 && r1>r2) || (c2>c1 && r2>r1)
		if(r2>r1 && c2>c1){
			n=c1, c1=c2, c2=n;
			m=r1, r1=r2, r2=m;
		}
		if(c1>c2 && r1>r2){
			for(int i=r2+1; i<=r1; i++){
				if(arr2d[c2][i]!=0) crr=0;
			}
			for(int j=c2; j<c1; j++){
				if(arr2d[j][r1]!=0)	ccr=0;
			}
			for(int i=r2; i<r1; i++){
				if(arr2d[c1][i]!=0) crl=0;
			}
			for(int j=c2+1; j<=c1; j++){
				if(arr2d[j][r2]!=0)	ccl=0;
			}
		}
		//(r1>r2 && c2>c1) || (r2>r1 && c1>c2)
		if(r2>r1 && c1>c2){
			n=c1, c1=c2, c2=n;
			m=r1, r1=r2, r2=m;
		}
		if(r1>r2 && c2>c1){
			for(int i=r2; i<r1; i++){
				if(arr2d[c1][i]!=0) crl=0;
			}
			for(int j=c1; j<c2; j++){
				if(arr2d[j][r2]!=0) ccl=0;
			}
			for(int i=r2+1; i<=r1; i++){
				if(arr2d[c2][i]!=0) crr=0;
			}
			for(int j=c1+1; j<=c2; j++){
				if(arr2d[j][r1]!=0) ccr=0;
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
