package schoolstuff;

public class War<E> {


	public static class Card {
		int number;
		int suit;
		public Card(int n, int p) {
			number = n;
			suit = p;
		}
	} 
	
	
	public static void main(String[] args) {

		LinkedList<Card> deck = new LinkedList<Card>();
		//j.number = 1;
		//j.suit = 1;
		//deck.add(j);
		
		//creating the deck
		for (int i = 0; i < 4; i++) {
			for (int k = 1; k < 14; k++) {
				deck.add(new Card(k, i));
			}
		}
		for (int i = 0; i < 52; i++) {
			//System.out.println(deck.get(i).number);
		}
		//shuffling
		System.out.println("shuffling...");
		for (int i = 0; i < deck.size(); i++) {
			int j = (int) (Math.random()*deck.size());
			Card temp = deck.get(i);
			deck.remove(i);
			deck.add(deck.get(j), i);
			deck.remove(j);
			deck.add(temp, j);
		} 


		System.out.println("done");
		
		//splitting deck
		LinkedList<Card> deck1 = new LinkedList<Card>();
		LinkedList<Card> deck2 = new LinkedList<Card>();
		

		
		
		for (int i = 0; i < deck.size(); i++) {
			System.out.println(deck.get(i).number);
			if (i % 2 == 1) {
				deck1.add(deck.get(i));
			} else {
				deck2.add(deck.get(i));
			}
		}

		//for (int i = 0; i < 52; i++) {
		//	System.out.println(deck.get(i).number + " " + deck.get(i).suit);
		//}
		
		while (deck1.size() > 0 || deck2.size() > 0) {
			
			String cardsuit1;
			String cardnum1;
			
			if (deck1.get(0).suit == 0) {
				cardsuit1 = "spades";
			} else if (deck1.get(0).suit == 1) {
				cardsuit1 = "heart";
			} else if (deck1.get(0).suit == 2) {
				cardsuit1 = "clubs";
			}else {
				cardsuit1 = "diamonds";
			}
			
			if (deck1.get(0).number == 11) {
				cardnum1 = "jack";
			} else if(deck1.get(0).number == 12) {
				cardnum1 = "queen";
			} else if (deck1.get(0).number == 13) {
				cardnum1 = "king";
			} else {
				cardnum1 = String.valueOf(deck1.get(0).number);
			}
			
			System.out.println("player 1 plays " + cardnum1 + " of " + cardsuit1);
			
			String cardsuit2;
			String cardnum2;
			
			if (deck2.get(0).suit == 0) {
				cardsuit2 = "spades";
			} else if (deck2.get(0).suit == 1) {
				cardsuit2 = "heart";
			} else if (deck2.get(0).suit == 2) {
				cardsuit2 = "clubs";
			}else {
				cardsuit2 = "diamonds";
			}
			
			if (deck2.get(0).number == 11) {
				cardnum2 = "jack";
			} else if(deck2.get(0).number == 12) {
				cardnum2 = "queen";
			} else if (deck2.get(0).number == 13) {
				cardnum2 = "king";
			} else {
				cardnum2 = String.valueOf(deck2.get(0).number);
			}
			
			System.out.println("player 2 plays " + cardnum2 + " of " + cardsuit2);
			
			if (deck1.get(0).number > deck2.get(0).number) {
				System.out.println("player 1 wins");
				deck1.add(deck1.get(0));
				deck1.add(deck2.get(0));
				deck1.remove(0);
				deck2.remove(0);
			} else if (deck2.get(0).number > deck1.get(0).number) {
				System.out.println("player 2 wins");
				deck2.add(deck1.get(0));
				deck2.add(deck2.get(0));
				deck1.remove(0);
				deck2.remove(0);
			} else if (deck1.get(0).number == deck2.get(0).number){
				
				System.out.println("draw");
				
				String cardsuit11, cardsuit12, cardsuit21, cardsuit22;
				if (deck1.get(1).suit == 0) {
					cardsuit11 = "spades";
				} else if (deck1.get(1).suit == 1) {
					cardsuit11 = "heart";
				} else if (deck1.get(1).suit == 2) {
					cardsuit11 = "clubs";
				}else {
					cardsuit11 = "diamonds";
				}
				if (deck1.get(2).suit == 0) {
					cardsuit12 = "spades";
				} else if (deck1.get(2).suit == 1) {
					cardsuit12 = "heart";
				} else if (deck1.get(2).suit == 2) {
					cardsuit12 = "clubs";
				}else {
					cardsuit12 = "diamonds";
				}
				
				if (deck2.get(1).suit == 0) {
					cardsuit21 = "spades";
				} else if (deck2.get(1).suit == 1) {
					cardsuit21 = "heart";
				} else if (deck2.get(1).suit == 2) {
					cardsuit21 = "clubs";
				}else {
					cardsuit21 = "diamonds";
				}
				if (deck2.get(2).suit == 0) {
					cardsuit22 = "spades";
				} else if (deck2.get(2).suit == 1) {
					cardsuit22 = "heart";
				} else if (deck2.get(2).suit == 2) {
					cardsuit22 = "clubs";
				}else {
					cardsuit22 = "diamonds";
				}
				String cardnum11, cardnum12, cardnum21, cardnum22;	
				
				if (deck1.get(1).number == 11) {
					cardnum11 = "jack";
				} else if(deck1.get(1).number == 12) {
					cardnum11 = "queen";
				} else if (deck1.get(1).number == 13) {
					cardnum11 = "king";
				} else {
					cardnum11 = String.valueOf(deck1.get(0).number);
				}
				
				if (deck1.get(2).number == 11) {
					cardnum12 = "jack";
				} else if(deck1.get(2).number == 12) {
					cardnum12 = "queen";
				} else if (deck1.get(2).number == 13) {
					cardnum12 = "king";
				} else {
					cardnum12 = String.valueOf(deck1.get(2).number);
				}
				
				if (deck2.get(1).number == 11) {
					cardnum21 = "jack";
				} else if(deck2.get(1).number == 12) {
					cardnum21 = "queen";
				} else if (deck2.get(1).number == 13) {
					cardnum21 = "king";
				} else {
					cardnum21 = String.valueOf(deck2.get(0).number);
				}
				
				if (deck2.get(2).number == 11) {
					cardnum22 = "jack";
				} else if(deck2.get(2).number == 12) {
					cardnum22 = "queen";
				} else if (deck2.get(2).number == 13) {
					cardnum22 = "king";
				} else {
					cardnum22 = String.valueOf(deck2.get(2).number);
				}
				
				System.out.println("player1 also plays " + cardnum11 + " of " + cardsuit11 + " and " + cardnum12 + " of " + cardsuit12);
				System.out.println("player2 also plays " + cardnum21 + " of " + cardsuit21 + " and " + cardnum22 + " of " + cardsuit2);
				int total1, total2;
				total1 = deck1.get(0).number + deck1.get(1).number + deck1.get(2).number;
				total2 = deck1.get(0).number + deck1.get(1).number + deck1.get(2).number;
				if (total1 > total2) {
					System.out.println("player 1 wins draw");
				} else {
					System.out.println("player 2 wins draw");
				}
			} 
			
		}
		
	}

	
}
