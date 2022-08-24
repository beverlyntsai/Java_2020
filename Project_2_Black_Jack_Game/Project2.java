/************************************************************************************************************************************
Project 2
Beverlyn Tsai
8/02/2020


*************************************************************************************************************************************/
import java.util.Scanner;
public class Project2
{
	public static void main( String [] args )
	{

		/*
		Deck myDeck= new Deck(1, true);
		myDeck.printDeck(52);
		*/
		int playerTotalPoints;
		int dealerTotalPoints;
		boolean isGameOver=false;
		boolean isPlayerStay= false;
		boolean isDealerStay=false;
		boolean isDealerDealtCard=false;
		String playerName="player";

		int choice=1;
		Scanner input=new Scanner(System.in);
		System.out.print("***************************************\nWelcome to play simple Blackjack! \n***************************************\nPlease enter your name:");
		playerName=input.nextLine();

		while(!isGameOver){
			isDealerDealtCard=false;
			System.out.println("********************************************\nThe cards are shuffled!\n********************************************\n");
			Deck deck= new Deck(1,true); //One deck card with shuffle
			
			//Create 2 player objects
			Player player= new Player(playerName);
			Player dealer= new Player("Dealer");

			//System.out.println("Now the player is dealt with 2 cards!\n********************************************\n");

			//player is dealt two cards
			player.addCard(deck.dealNextCard());			
			player.addCard(deck.dealNextCard());
			player.printHand(true);
			playerTotalPoints=player.getSumFromHandCards();
			if(playerTotalPoints==21 ) isGameOver=true;


			if(!isGameOver){
				//System.out.println("********************************************\nNow the dealer is dealt with 2 cards!\n********************************************\n");
				isDealerDealtCard=true;
				dealer.addCard(deck.dealNextCard());
				dealer.addCard(deck.dealNextCard());
				dealer.printHand(false);
				dealerTotalPoints=dealer.getSumFromHandCards();
				if(dealerTotalPoints==21 ) isGameOver=true;
			}



			//initial flags
			isPlayerStay= false;
			isDealerStay=false;
			choice=1;


			if(!isGameOver){
				//player play first
				while(!isPlayerStay){
				
					System.out.print("\n*********************************\nPlayer play first.\n*********************************\n");
					System.out.print("Hit or Stay? Enter 1=Hit, 2=Stay :");
					choice=input.nextInt();
					System.out.println();

					if(choice==1){ //Hit
						//add next card
						player.addCard(deck.dealNextCard());
						player.printHand(true); //*******************

					}else{//Stay
						System.out.print("*********************************\nIt is dealer's turn.\n*********************************\n");
						isPlayerStay=true;
					}	

					//If player get total points over 21, game over!
					if(player.getSumFromHandCards()>=21) {
						isPlayerStay=true;
						isGameOver=true;
					}


				}//while
			}// if isGameOver=false



			if(!isGameOver){
				//check dealer's choice
				while(!isDealerStay){

					if(dealer.getSumFromHandCards()<17){
						System.out.println("*******************\nThe Dealer will hit!\n*******************\n");
						dealer.addCard(deck.dealNextCard());
						//After the deal deals with a new card, recalculate the total points
						//and decide the dealer is to hit or to stay
						isDealerStay= !dealer.isToHit();
						dealer.printHand(false); //*******************

					}else{
						System.out.println("The Dealer will stay!\n");
						isDealerStay=true;

					}
	
					if(dealer.getSumFromHandCards()>=21){
						isDealerStay=true;
						isGameOver=true;
					}
				}//while

			}//if isGameOver=false



			//System.out.println();	

			//input.close();//*******************


			System.out.println("*******************\nGame Over!\n*******************\n");
			//print final hands
			player.printHand(true);
			if(isDealerDealtCard){
				dealer.printHand(true);
			}else{
				System.out.println("*******************\nDealer is not dealt any card!\n*******************\n");
			}

			//Get the total points of the player and dealer
			playerTotalPoints=player.getSumFromHandCards();
			dealerTotalPoints=dealer.getSumFromHandCards();

			if(playerTotalPoints > dealerTotalPoints && playerTotalPoints<=21 || dealerTotalPoints>21){
				System.out.println("*******************\nPlayer wins!\n*******************\n");

			}else if(playerTotalPoints==dealerTotalPoints && playerTotalPoints<=21 && dealerTotalPoints<=21){
					System.out.println("*******************\nYou tie!\n*******************\n");
			}else{

				System.out.println("*******************\nDealer wins!\n*******************\n");
			}




			System.out.print("Do you want to play one more round? Enter 1=Yes, 2=No :");
			choice=input.nextInt();
			if(choice==2){
				isGameOver=true;
			}else{
				isGameOver=false;
			}


		}//while


	
	}//main

}

class Player
{
	private int MAX_NUM=10;
	private String name;
	private Card[] hand= new Card[MAX_NUM];
	private int numCardsInHand;

	//Constructor
	public Player(String playerName){
		name=playerName;
		emptyHand();
	}

	public void emptyHand(){
		
		for(int i=0;i<MAX_NUM;i++){
			hand[i]=null;
		}
		numCardsInHand=0;

	}

	public void addCard(Card card){
		
		if(numCardsInHand==10){
			System.err.printf("%s's hand already has maximum 10 cards!"+ "You cannot add another card!\n", name);
			System.exit(1);

		}

		hand[numCardsInHand]=card;
		numCardsInHand++;

	}

	public boolean isToHit(){
		
		if(getSumFromHandCards()<17)
			
			return true;

		return false;

	}


	public int getSumFromHandCards(){

		int handSum=0;
		int cardFaceNumber;
		int numberOfAcesCard=0;
		
		for(int i=0; i<numCardsInHand; i++){
			
			cardFaceNumber=hand[i].getNumber();


 			if(cardFaceNumber==1){

				numberOfAcesCard++;
				handSum+=11;
			}else if(cardFaceNumber>10){

				handSum+=10;

			}else{
				handSum+=cardFaceNumber;
			}

		}

		//check if aces should be counted as 1 or 11
		//If sum>21, we should change some or all aces to 1
		while(handSum>21 && numberOfAcesCard >0){
			handSum-=10; //Change 11 to 1 by deducting 10
			numberOfAcesCard--;

		}
		

		return handSum;
	}

	
	public void printHand(boolean showFirstCard){

		System.out.printf("This is %s's cards in hand:\n", name);
		for(int i=0;i<numCardsInHand;i++){

			if(i==0 && !showFirstCard){
				System.out.println(" [Card face down]");
			}else{
				System.out.printf(" %s\n", hand[i].toString());
			}
		}
		
		if(showFirstCard){
			System.out.println("The point total is "+getSumFromHandCards()+"\n");
		}
		
	}



}

class Deck
{

	private Card[] myCards;
	private int numCardsLeft; //The number of cards left in the deck

	//Constructor
	Deck(){
		this(1,false);
	}

	//Constructor
	public Deck(int numberDecks, boolean shuffle){
		
		this.numCardsLeft=numberDecks*52;
		this.myCards= new Card[this.numCardsLeft];

		//initial card index
		int c=0;


		for(int i=0;i<numberDecks;i++){

			for(int j=0;j<4;j++){

				for(int n=1;n<=13;n++){


					//add a new card to the deck
					this.myCards[c]= new Card(Suit.values()[j],n);
					c++;
				}

			}


		}

		if(shuffle){
			shuffleCards();
		}

	}




	public void shuffleCards(){

		Card temp;
		for(int i=0;i<numCardsLeft;i++){
			int index=(int)(Math.random()*numCardsLeft);
			
			//do swap
			temp=myCards[i];
			myCards[i]=myCards[index];
			myCards[index]=temp;
		}
		
	}

	
	public Card dealNextCard(){
		
		//get the top card from index 0
		Card top=myCards[0];

		//Shift the other cards to one cell left(one index less)
		for(int i =1;i<numCardsLeft;i++){		
			myCards[i-1]=myCards[i];
		}
		myCards[numCardsLeft-1]=null;
		
		//decrease the total number of cards left by one
		numCardsLeft--;

		return top;

	}

	//For debug purpose to print out all cards to see how it shuffles
	public void printDeck(int number){
		
		for(int i=0;i<number;i++){

			System.out.printf("% 3d/%d %s\n",i+1, numCardsLeft,myCards[i].toString());
		}

	}


}


class Card
{
	
	//One of the 4 suits for this card
	private Suit mySuit;

	//The number is the card number displays when "hit"
	private int myNumber;

	//Constructor
	Card(){
	
	}

	//Constructor
	public Card(Suit aSuit, int aNumber){
		this.mySuit = aSuit;

		if(aNumber>=1 && aNumber<=13){
			this.myNumber= aNumber;
		}else{
			System.out.print("Wrong number!");
			System.exit(1);
		}
	
	}

	public int getNumber(){
		return myNumber;
	}	


	//Override toString method
	//All class has toString and override it
	public String toString(){

		String numStr="Error";
	
		switch(this.myNumber){

			case 2:
				numStr="2";
				break;
			case 3:
				numStr="3";
				break;

			case 4:
				numStr="4";
				break;

			case 5:
				numStr="5";
				break;

			case 6:
				numStr="6";
				break;

			case 7:
				numStr="7";
				break;

			case 8:
				numStr="8";
				break;

			case 9:
				numStr="9";
				break;

			case 10:
				numStr="10";
				break;

			case 11:
				numStr="J";
				break;

			case 12:
				numStr="Q";

				break;
			case 13:
				numStr="K";
				break;

			case 1:
				numStr="A";
				break;

		}

		return numStr+"-"+mySuit.toString();


	}


}

enum Suit
{
	C,
	D,
	S,
	H,

}

