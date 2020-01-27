//Name : Atul Anand
//Roll No. : 2017284
//CSD

import java.util.Random;

// Assumption : A player can win the same prize again provided it is found at a new location.
public class lab6 {

	public static void main(String[] args) throws CloneNotSupportedException {			//driver method
		ballon_buster b=new ballon_buster();						// creating object of class ballon_buster
		b.run();													
	}
}

class ballon_buster{											// the simulation class
	private prize board[][]; 										
	static String [] S= {"dog","cat","rabbit","kitkat", "snickers","fivestar","pen","pencil","eraser"};		//Set of prizes
	
	ballon_buster(){
		board=new prize[10][10];
	}
	
	public void setBoard() {							// its assigns prizes to 50% of locations
		int c=0;
		Random r=new Random();
		board[0][r.nextInt(10)]=new prize(S[0]);
		board[1][r.nextInt(10)]=new prize(S[1]);
		board[2][r.nextInt(10)]=new prize(S[2]);
		board[3][r.nextInt(10)]=new prize(S[3]);
		board[4][r.nextInt(10)]=new prize(S[4]);
		board[5][r.nextInt(10)]=new prize(S[5]);
		board[6][r.nextInt(10)]=new prize(S[6]);
		board[7][r.nextInt(10)]=new prize(S[7]);
		board[8][r.nextInt(10)]=new prize(S[8]);
		
		c=c+9;
		
		while(c<50){
			int x=r.nextInt(10);						// randomly assigning prizes to random locations
			int y=r.nextInt(10);

			int p=r.nextInt(9);
			if(board[x][y]==null) {
				board[x][y]=new prize(S[p]);
				c+=1;
			}	
		}		
	}
	
	public void resetBoard() {					// utililty function to set everything to null after a player uses all of its chances
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				board[i][j]=null;
			}
		}
	}
	
	public void run() throws CloneNotSupportedException {			//random simulation method
		Random r=new Random();
		int N=r.nextInt(5)+1;
		System.out.println(N);
		
		String names[]=new String[N];
		for(int i=0;i<N;i++) {
			names[i]="Player"+(i+1);
			System.out.print(names[i]+" ");
		}
		System.out.println();
		
		for(int n=0;n<N;n++) {
			this.setBoard();
			player p=new player(names[n],S);
			System.out.println("--"+names[n]+" started playing--");
			p.displayPrizes();
			
			int chance=0;
			int alloted_chances=p.getChances();
			
			int targeted_positions[][]=new int[10][10];
			
			while(chance<alloted_chances) {
				int x=r.nextInt(10);
				int y=r.nextInt(10);
				
				if(targeted_positions[x][y]==0) {
					targeted_positions[x][y]=1;
					System.out.println("Choose a coordinate");
					System.out.println(x+"     "+ y);
					
					int g=r.nextInt(5);
					prize guess=p.getPrizes()[g];

					if(board[x][y]!=null) {
						System.out.println("Guess the prize");
						System.out.println(g);
						boolean b=this.equals(board[x][y],guess);
						if(b==true) {
							p.setPrizes_won((prize)board[x][y].clone());			//calling clone method
							int type=guess.getType();
							if(type==1) {
								p.setPoints_double();
								p.setPoints(10);
							}
							else if(type==2) {
								p.setPoints(20);
							}
							else {
								p.setPoints_percent();
								p.setPoints(5);
							}
							System.out.println("You won "+guess.getName());
		
						}
						else {
							System.out.println("Sorry, you guessed wrong, it was "+board[x][y].getName());
							int diff=Math.abs(guess.getWeight()-board[x][y].getWeight());
							p.setPoints(-diff);
						}
						p.displayPoints();
						
					}
					else {
						System.out.println("Sorry, no prize here");
						p.setPoints_halved();
						p.displayPoints();
					}
					chance+=1;
				}	
			}
			p.summary();
		}
		
	}
	
	public boolean equals(Object x, Object y) {									// method for object comparision
		if(x instanceof prize && y instanceof prize) {
			if(((prize) y).getName().compareTo(((prize) x).getName())==0){
				return true;
			}
		}

		return false;
	}
	
	public void displayBoard() {
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				System.out.println(board[i][j]);
			}
		}
	}	
}

class prize implements Cloneable{									// prize implenting the interface Cloneable 
	private String name;
	private int weight;
	private int type;
	
	prize(String n){
		setName(n);
		setType();
	}
	
	public void setName(String n) {									// setters, getters and utility methods
		name=n;														
	}

	public String getName() {
		return name;
	}
	
	public void setWeight(int w) {
		weight=w;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setType() {
		if(this.getName().equals("dog") || this.getName().equals("cat") || this.getName().equals("rabbit")) {
			type=1;
			if(this.getName().equals("dog")) {
				this.setWeight(50);
			}
			else if(this.getName().equals("cat")) {
				this.setWeight(40);
			}
			else {
				this.setWeight(20);
			}
		}
		else if(this.getName().equals("kitkat") || this.getName().equals("snickers") || this.getName().equals("fivestar")) {
			type=2;
			if(this.getName().equals("kitkat")) {
				this.setWeight(10);
			}
			else if(this.getName().equals("snickers")) {
				this.setWeight(5);
			}
			else {
				this.setWeight(7);
			}
		}
		else {
			type=3;
			if(this.getName().equals("pen")){
				weight=2;
			}
			else if(this.getName().equals("pencil")) {
				weight=1;
			}
			else {
				weight=3;
			}
		}
	}

	public int getType() {
		return type;
	}
	
	public Object clone() throws CloneNotSupportedException{ 					// object cloning method (overriding object class method)
		return super.clone(); 													// polymorphism
	} 
}


class person{
	protected String name;
	
	person(String n){
		setName(n);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String n) {
		name=n;
	}
}

class player extends person{									//player inheriting the class person
	private prize prizes[];
	private int chances;
	private prize prizes_won[];
	private int points;
	private int counter;
	
	player(String n, String S[]){
		super(n);
		prizes=new prize[5];
		prizes_won=new prize[5];
		points=0;
		setCounter(0);
		setChances(10);
		setPrizes(S);
	}
	
	public void setCounter(int x) {
		counter=x;
	}

	public int getCounter() {
		return counter;
	}
	
	public void setPoints(int x) {
		points+=x;
		if(points<0) {
			points=0;
		}
	}
	
	public int getPoints(){
		return points;
	}
	
	public void setPoints_halved() {
		points=points/2;
	}
	
	public void setPoints_double() {
		points=points*2;
	}
	
	public void setPoints_percent() {
		points+=points*0.1;
	}
	
	public prize[] getPrizes() {
		return prizes;
	}
	
	public void setPrizes(String S[]) {
		Random r=new Random();
		int i=0;
		while(i<5) {
			int x=r.nextInt(9);
			int flag=1;
			prize p=new prize(S[x]);
			for(int j=0;j<i;j++) {
				if(this.getPrizes()[j].getName().compareTo(S[x])==0) {
					flag=0;
				}
			}
			if(flag==1) {
				this.getPrizes()[i]=p;
				i+=1;
			}
		}
	}
	
	public int getChances() {
		return chances;
	}
	
	public void setChances(int c) {
		chances=c;
	}
	
	public prize[] getPrizes_won() {
		return prizes_won;
	}
	
	public void setPrizes_won(prize x) {
		prizes_won[counter]=x;
		counter+=1;
	}
	
	public void displayPrizes() {
		for(int i=0;i<5;i++) {
			System.out.print(this.getPrizes()[i].getName()+" ");
		}
		System.out.println();
	}
	
	public void displayPoints() {
		System.out.println(this.getPoints()+" points");
	}
	
	public void summary() {
		System.out.println("--Summary of "+this.getName()+"--");
		if(this.getCounter()==0) {
			System.out.print("You won nothing. ");
		}
		else {
			System.out.print("You have won ");
			for(int i=0;i<counter;i++) {
				System.out.print(prizes_won[i].getName());
				if(i!=counter-1) {
					System.out.print(", ");
				}
				else {
					System.out.print(". ");
				}
			}
		}
		System.out.println("Total points won = "+this.getPoints());
		System.out.println();	
	}
}