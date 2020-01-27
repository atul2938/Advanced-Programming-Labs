//Name : Atul Anand
//Roll No : 2017284
//CSD

import java.io.*;
import java.util.*;

// Assumption : All the paths provided should be without any space an any place(before, between, after)
// All the products, categories and paths are case sensitive.
public class lab8 {

	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException {				//driver method
		Amacon A=new Amacon();								// creating an object of Amacon class
		A.run();											// starting application
	}
}

class Amacon implements Serializable{						// Amacon class (software application)
	private static final long serialVersionUID = 1L;
	private static int revenue;								// revenue of Amacon  
	protected database Database;							// it contains a database object for storing its data 
	
	Amacon() throws ClassNotFoundException{
		setRevenue(0);
		
		try {
			ObjectInputStream input=new ObjectInputStream(new FileInputStream("save.txt"));
			Database=(database) input.readObject();
			input.close();
		}
		catch(IOException E){
			Database=new database();
		}
	}
	
	public int getRevenue() {										// setters and getters method
		return revenue;
	}

	public static void setRevenue(int r) {
		revenue+=r;
	}
	
	void run() throws FileNotFoundException, IOException, ClassNotFoundException{					// method providing user-driven menu and handling all the tasks 
		Scanner in=new Scanner(System.in);
		System.out.println("Welcome to Amacon");
		administrator A=null;
		while(true) {
			System.out.println("Please select one of the options :");
			System.out.println("1. I am an administrator");
			System.out.println("2. I am a customer");
			System.out.println("3. Exit Amacon");
			int option=in.nextInt();
			
			if(option==1) {
				if(A==null) {
					A=new administrator();
				}
				
				while(true) {
					System.out.println("Please select one of the following options");
					System.out.println("a. Insert product/category");
					System.out.println("b. Delete product/category");
					System.out.println("c. Search product");
					System.out.println("d. Modify product");
					System.out.println("e. Exit as Administrator");
					char c=in.next().charAt(0);
					
					if(c=='a') {
						System.out.print("Enter path where you want to add product : ");
						String path=in.next();
						System.out.print("Enter product name: ");
						String product=in.next();
						System.out.print("Enter its price : ");
						int price=in.nextInt();
						System.out.print("Enter its number of units : ");
						int units=in.nextInt();
						A.getDatabase().insert(path, product,price, units);
					}
					else if(c=='b') {
						System.out.print("Enter path to product/category: ");
						String path=in.next();
						A.getDatabase().delete(path);
					}
					else if(c=='c') {
						System.out.print("Enter product to search: ");
						String product=in.next();
						product x=A.getDatabase().search(product);
						if(x!=null) {
							System.out.println("path to the product :"+x.getPath()+">"+x.getName());
							System.out.println("Price : Rs."+x.getPrice()+", Quantity : "+x.getUnits());
						}
						
					}
					else if(c=='d') {
						System.out.print("Enter name of the product : ");
						String product=in.next();
						System.out.print("Enter 1 to modify price and 2 to modify number of units in stock : ");
						int x=in.nextInt();
						if(x==1) {
							System.out.print("Enter its new price : ");
							int price=in.nextInt();
							A.getDatabase().modify(1,product,price);
						}
						else {
							System.out.print("Enter new number of stocks : ");
							int stocks=in.nextInt();
							A.getDatabase().modify(2,product,stocks);
						}	
					}
					else {
						ObjectOutputStream output = null;
						try {
							output = new ObjectOutputStream(new FileOutputStream("save.txt"));
							output.writeObject(this.Database);
						}
						finally {
							output.close();
						}
						break;
					}
					System.out.println();	
				}
			}
			else if(option==2) {
				System.out.print("Enter username : ");
				String s=in.next();
				boolean b=Database.checkDuplicacy(s);
				customer C;
				if(b==true) {
					System.out.println("Welcome back "+s);
					C=Database.getCustomer(s);
					
				}
				else {
					C=new customer(s);							//creating customer class object
					Database.addCustomer(C);
				}
				while(true) {
					System.out.println("Please select one of the following options");
					System.out.println("a. Add funds");
					System.out.println("b. Add product to the cart");
					System.out.println("c. Check-out cart");
					System.out.println("d. Exit as Customer");
					char c=in.next().charAt(0);
					if(c=='a') {
						System.out.print("Enter the amount to add : ");
						int f=in.nextInt();
						C.addFunds(f);
					}
					else if(c=='b') {
						System.out.print("Enter the name of product you want to add to cart : ");
						String p=in.next();
						System.out.print("Enter its quantity : ");
						int q=in.nextInt();
						product x=Database.search(p);
						C.addtoCart(x,q,Database);
						
					}
					else if(c=='c') {
						C.checkout(Database);
					}
					else {
						 break;
					}
				}
			}
			else {
				exitStatus();							//calling exitStatus method before termination						
			
				ObjectOutputStream output = null;
				try {
					output = new ObjectOutputStream(new FileOutputStream("save.txt"));
					output.writeObject(Database);
				}
				finally {
					output.close();
				}
				break;
			}	
		}
		in.close();
	}
	
	private void exitStatus() {									//method to print Amacon revenue on exit
		System.out.println("Total revenue generated by Amacon : Rs."+getRevenue());
	}
	
	public void forceClose() {
		return;
	}	
}

class database implements Serializable{
	private static final long serialVersionUID = 1L;
	private Collection<product> products;				// (Collection Framewrok) for storing products
	private Collection<customer> customers;
	
	// Arraylist -----> for searching by index.
	database(){
		products=new ArrayList<product>();							
		customers=new ArrayList<customer>();
	}
	
	public Collection<product> getProducts(){
		return products;
	}

	public Collection<customer> getCustomers(){
		return customers;
	}
	
	public void addCustomer(customer c) {
		customers.add(c);
	}
	
	public customer getCustomer(String s) {
		Collection<customer> c=getCustomers();
		for(int i=0;i<c.size();i++) {
			customer x=((ArrayList<customer>) customers).get(i);
			if(x.getUsername().equals(s)) {
				return x;
			}
		}
		return null;
	}

	public boolean checkDuplicacy(String S) {
		Collection<customer> c=getCustomers();
		for(int i=0;i<c.size();i++) {
			customer x=((ArrayList<customer>) customers).get(i);
			if(x.getUsername().equals(S)) {
				return true;
			}
		}
		return false;
	}
	
	public void insert(String path, String name, int price, int units) {
		try {																	//handling custom exceptions in insertion
			Collection<product> p=getProducts();
			for(int i=0;i<p.size();i++) {
				product x=((ArrayList<product>) products).get(i);
				if(x.getName().equals(name)) {
					throw new myCustomException("Exception : Product already exist..");
				}
			}
			products.add(new product(price,units,path,name));
		}
		catch(myCustomException E) {
			System.out.println(E.getMessage());
			System.out.println();
		}
	}
	
	public void delete(String path) {
		int pathLength=path.length();
		int size=products.size();
		int[] deletion=new int[size];
		int x=0;
		for(int i=0;i<size;i++) {
			product p=((ArrayList<product>) products).get(i);
			String tempPath=p.getPath()+">"+p.getName();
			if(tempPath.equals(path)) {
				deletion[x]=i;
				x++;
			}
			else if(p.getPath().equals(path)) {
				deletion[x]=i;
				x++;
			}
			else if(p.getPath().length()>=pathLength) {
				if(p.getPath().substring(0, pathLength-1).equals(path)) {
					deletion[x]=i;
					x++;
				}
			}		
		}
		try{
			if(x>0) {												//handling custom exceptions in deletion
				for(int i=x-1;i>=0;i--) {													
					products.remove(i);
				}
			}
			else {
				throw new myCustomException("Exception : Invalid Path Specified for deletion...");
			}
		}
		catch(myCustomException E) {
			System.out.println(E.getMessage());
			System.out.println();
		}
	}
	
	public product search(String productName) {
		product ref=null;
		boolean found=false;
		for(int i=0;i<products.size();i++) {
			product x=((ArrayList<product>) products).get(i);
			if(x.getName().equals(productName)) {
				found=true;
				ref=x;
				break;
			}
		}
		try {															//handling custom exceptions in search
			if(found==false) {																	
				throw new myCustomException("Exception : The product doesn't exist...");
			}
		}
		catch(myCustomException E) {
			System.out.println(E.getMessage());
			System.out.println();
		}
		return ref;
	}
	
	public void modify(int choice, String p , int n) {
		product x=search(p);
		if(choice==1) {
			x.setPrice(n);
		}
		else {
			x.setUnits(n);
		}
	}
	
	public int sale(product p, int quantity, int remaining_funds_with_customer) {
		int total_price=0;
		try {																			//handling custom exceptions in sales
			for(int i=0;i<products.size();i++) {
				product x=((ArrayList<product>) products).get(i);
				
				if(x.getName().equals(p.getName())) {
					total_price=x.getPrice()*quantity;
					if(x.getUnits()<quantity) {
						throw new myCustomException("Exception : Stock Unavailable");
					}
					else if(total_price>remaining_funds_with_customer) {
						throw new myCustomException("Exception : Insufficient funds");
					}
					else {
						x.setUnits(x.getUnits()-quantity);
						Amacon.setRevenue(total_price);
					}
				}
			}
		}
		catch(myCustomException E) {
			System.out.println(E.getMessage());
			System.out.println();
			return 0;
		}
		
		return total_price;
	}
}

class product implements Serializable{										//product class for storing data of a product
	private static final long serialVersionUID = 1L;
	private int price;
	private int units;
	private String path;
	private String name;
	
	product(int p, int u,String path, String n){							//constructor
		setPrice(p);
		setUnits(u);
		setName(n);
		setPath(path);
	}
	
	public void setPath(String path) {
		this.path=path;
	}

	public String getPath() {
		return path;
	}
	
	public void setName(String n) {
		name=n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPrice(int p) {
		price=p;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setUnits(int u) {
		units=u;
	}
	
	public int getUnits() {
		return units;
	}
}

class administrator extends Amacon{								// administrator inheriting Amacon for accessing its database
	private static final long serialVersionUID = 1L;

	administrator() throws ClassNotFoundException{
		super();
	}
	
	public database getDatabase() {
		return Database;
	}
}

class customer implements Serializable{							// customer class for storing customer data
	private static final long serialVersionUID = 1L;
	private int funds;														
	private product[] cart;
	private int[] quantity;
	private int counter;
	private String username;
	
	customer(String s){
		addFunds(0);
		cart=new product[20];
		quantity=new int[20];
		counter=0;
		setUsername(s);
	}
	
	public void setUsername(String s){							//setters and getter methods
		username=s;
	}

	public String getUsername() {
		return username;
	}

	public void addFunds(int f) {									
		funds+=f;
	}
	
	public int getFunds() {
		return funds;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void addtoCart(product p, int q, database d) {
		try {
			Collection<product> products=d.getProducts(); 
			for(int i=0;i<products.size();i++) {
				product x=((ArrayList<product>) products).get(i);
				
				if(x.getName().equals(p.getName())) {
					if(x.getUnits()==0) {
						throw new myCustomException("Exception : Product Out of Stock...");
					}
				}
				
			}
			cart[counter]=p;
			quantity[counter]=q;
			counter++;
		}
		catch(myCustomException E){
			System.out.println(E.getMessage());
			System.out.println();
		}
	}
	
	public void checkout(database d) {
		int tempPaid=0;
		for(int i=0;i<counter;i++) {
			int paid=d.sale(cart[i],quantity[i],this.getFunds());
			tempPaid+=paid;
			this.addFunds(-paid);
		}
		if(tempPaid==0) {
			System.out.println("Checkout Failed...");
			System.out.println();
		}
		else {
			System.out.println("Partial or Full Checkout Completed...");
			System.out.println();
			reset();
		}
	}
	
	public void reset() {
		counter=0;
	}
}