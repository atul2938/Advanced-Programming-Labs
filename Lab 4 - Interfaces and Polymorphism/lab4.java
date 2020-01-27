// Name : Atul Anand
// Roll No. : 2017284
// AP Lab 4

import java.util.Scanner;

public class lab4 {
	
	public static void main(String[] args) {		// main method	
		Spotify_App S=new Spotify_App();
		S.run();
	}
}

class Spotify_App {							// This is the class handling the whole program.
	artist artists[];
	customer customers[];
	song songs[];
	private int a_counter;
	private int c_counter;
	private int s_counter;
	
	Spotify_App(){							//constructor
		artists=new artist[100];			//array storing objects of type artist. current limit is set to 100 but could be increased easily. 	
		customers=new customer[1000];		//array storing objects of type customer. current limit is 900 as its use is started from 100 
		songs=new song[1000];				//array storing objects of type song. songs capacity is set to 1000.
		a_counter=1;
		c_counter=100;
		s_counter=1;
	}
	
	public void run() {						//driver method for all the queries
		Scanner in=new Scanner(System.in);				
		int q=in.nextInt();
		for(int Q=0;Q<q;Q++) {
			int type=in.nextInt();
		
			// Errors are check at queries of every type for invalid inputs.	
			if(type==1) {	
				char c=in.next().charAt(0);
				String name=in.next();
				if(c=='A') {
					
					artists[a_counter]=new artist(name,a_counter);
					artists[a_counter].displayInfo();;
					a_counter++;
				}
				else if(c=='C') {
					customers[c_counter]=new customer(name,c_counter);
					customers[c_counter].displayInfo();;
					c_counter++;
					
				}
				else {
					displayError();
				}
				
			}
			else if(type==2) {
				int id=in.nextInt();
				if(id<100 || id>=c_counter) {
					displayError();
				}
				else {
					int new_plan=in.nextInt();
					customers[id].changeSubscription(new_plan);
				}
			}
			else if(type==3) {
				int artist_id=in.nextInt();
				if(artist_id<1 || artist_id>=a_counter) {
					displayError();
				}
				else {
					int n=in.nextInt();
					int start=s_counter;
					for(int i=0;i<n;i++) {
						String song=in.next();
						songs[s_counter]=new song(song,s_counter,artist_id);
						s_counter++;
					}
					int end=s_counter;
					displayRecentSongs(start,end);
				}
			}
			else if(type==4) {
				int artist_id=in.nextInt();
				if(artist_id<1 || artist_id>=a_counter) {
					displayError();
				}
				else {
					artists[artist_id].payRoyalties();
				}
			}
			
			// Royalties are counted for an artist when either a customer or other artist downloads or play his/her songs.
			else if(type==5) {
				int id=in.nextInt();
				
				if(id>=100 && id<c_counter) {
					int n=in.nextInt();
					for(int i=0;i<n;i++) {
						int song_id=in.nextInt();
						char c=in.next().charAt(0);
						if(c=='D') {
							if(customers[id].getDownloaded()<customers[id].getDownloadLimit()) {
								customers[id].setDownloaded();
								artists[songs[song_id].getArtistID()].addRoyalties(20);
							}
							else {
								System.out.println("Subscription Limit Reached. Upgrade your plan....");
							}
						}
						else if(c=='P') {
							if(customers[id].getPlayed()<customers[id].getPlayLimit()) {
								customers[id].setPlayed();
								artists[songs[song_id].getArtistID()].addRoyalties(5);
							}
							else {
								System.out.println("Subscription Limit Reached. Upgrade your plan....");
							}
						}
						else {
							displayError();
						}
					}
					
					// sorting based on user preference in O(N).
					int[] order=new int[n];
					for(int i=0;i<n;i++) {
						order[i]=in.nextInt();
					}
					for(int i=0;i<n;i++) {
						if(order[i]<1 || order[i]>=s_counter) {
							displayError();
						}
						else {
							System.out.print(songs[order[i]].getName()+" ");
						}
					}
					System.out.println();
					
				}
				else if(id>=1  && id<a_counter){
					int n=in.nextInt();
					for(int i=0;i<n;i++) {
						int song_id=in.nextInt();
						if(song_id<1 || song_id>=s_counter) {
							displayError();
						}
						else {
							char c=in.next().charAt(0);
							if(c=='D') {
								if(id!=songs[song_id].getArtistID()) {
									artists[songs[song_id].getArtistID()].addRoyalties(20);
								}
							}
							else if(c=='P') {
								if(id!=songs[song_id].getArtistID()) {
									artists[songs[song_id].getArtistID()].addRoyalties(5);
								}
							}
							else {
								displayError();
							}
						}
					}
					// sorting based on user preference in O(N).
					int[] order=new int[n];
					for(int i=0;i<n;i++) {
						order[i]=in.nextInt();
					}
					for(int i=0;i<n;i++) {
						if(order[i]<1 || order[i]>=s_counter) {
							displayError();
						}
						else {
							System.out.print(songs[order[i]].getName()+" ");
						}
					}
					System.out.println();
					
				}
				else {
					displayError();
				}
			}
			else if(type==6) {
				int id=in.nextInt();
				if(id<100 || id>=c_counter) {
					displayError();
				}
				else {
					customers[id].displayCharges();
				}
				
				
			}
			else if(type==7) {
				int id=in.nextInt();
				if(id>=100 && id<c_counter) {
					customers[id].displayFullInfo();
				}
				else if(id>=1 && id<a_counter) {
					artists[id].displayFullInfo(this);
				}
				else {
					displayError();
				}
			}
			else {
				displayError();
			}	
		}
		in.close();
	}
	
	public void displayRecentSongs(int start,int end) {		// it display all the songs added with names and IDs in a query.
		for(int i=start;i<end;i++) {
			System.out.print(songs[i].getName()+" "+songs[i].getID()+" ");
		}
		System.out.println();
	}
	
	public String artistWiseSongs(int id) {		// it return storing all the songs of an artist based on his id. 
		String S="";
		for(int i=1;i<s_counter;i++) {
			if(songs[i].getArtistID()==id) {
				S=S+songs[i].getName()+", ";
			}
		}
		if(S.length()<2) {
			return "";
		}
		return S.substring(0, S.length()-2);
	}
	
	public void displayError() {
		System.out.println("Invalid input... Try again");
	}
}


interface output{					// interface output
	public void displayInfo();
	public void displayError();
}


class artist implements output{		// artist class for artists. It implements interface output.
	private String name;
	private int royalties;
	private int ID;
	
	artist(String name, int ID){	//constructor
		this.name=name;
		this.ID=ID;
		royalties=0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getRoyalties() {
		return royalties;
	}
	
	public void resetRoyalties() {
		royalties=0;
	}
	
	public void addRoyalties(int r) {
		royalties+=r;
	}
	
	public void payRoyalties() {
		System.out.println(getRoyalties());
		resetRoyalties();
	}
	
	public void displayInfo() {
		System.out.println(getName()+" "+getID());
	}
	
	public void displayFullInfo(Spotify_App S) {			// this method takes another class object as a parameter.
		System.out.println(getName()+" "+getID()+": "+S.artistWiseSongs(ID));
	}
	
	public void displayError() {
		System.out.println("Invalid input... Try again");
	}
}

class customer implements output{		// customer class for customers. It implements interface output.
	private String name;
	private int subscription;
	private int ID;
	private int played;
	private int downloaded;
	private int playLimit;
	private int downloadLimit;
	
	customer(String name, int ID){		//customer
		this.name=name;
		this.ID=ID;
		subscription=1;
		played=0;
		downloaded=0;
		playLimit=1;
		downloadLimit=0;
	}	
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getSupscription() {
		return subscription;
	}
	
	public int getPlayed() {
		return played;
		
	}
	
	public int getDownloaded() {
		return downloaded;
	}
	
	public int getPlayLimit() {
		return playLimit;
	}
	
	public int getDownloadLimit() {
		return downloadLimit;
	}
	
	public void setPlayed() {
		played+=1;
	}
	
	public void setDownloaded() {
		downloaded+=1;
	}
	
	public void changeSubscription(int p) {			// method to change a subscription of a customer.
		subscription=p;
		if(subscription==1) {
			playLimit=1;
			downloadLimit=0;
		}
		else if(subscription==2) {
			playLimit=5;
			downloadLimit=2;
		}
		else if(subscription==3) {
			playLimit=10;
			downloadLimit=5;
		}
		else if(subscription==4) {
			playLimit=Integer.MAX_VALUE;
			downloadLimit=Integer.MAX_VALUE;
		}
		else {
			displayError();
		}
	}
	
	public void displayInfo() {
		System.out.println(getName()+" "+getID());
	}
	
	public void displayFullInfo() {
		System.out.println(getName()+" "+getID()+" "+getSupscription());
	}
	
	public void displayError() {
		System.out.println("Invalid input... Try again");
	}
	
	public void displayCharges() {			// it displays charges based upon the subscription for a customer.
		if(subscription==1) {
			System.out.println("0");
		}
		else if(subscription==2) {
			System.out.println("100");
		}
		else if(subscription==3) {
			System.out.println("500");
		}
		else {
			System.out.println("1000");
		}
	}
}

class song{						//song class for implementing the songs for the Spotify App.
	private String name;
	private int ID;
	private int artist_ID;
	
	song(String name, int ID,int author_ID){
		this.name=name;
		this.ID=ID;
		this.artist_ID=author_ID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getArtistID() {
		return artist_ID;
	}	
}