// Name : Atul Anand
// Roll No. 2017284
// CSD

import java.util.Random;

public class lab5 {

	public static void main(String[] args) {
		Simulation S=new Simulation();					// creating object of simulation class
		S.run();
	}
}

class Simulation{					// Simulation class for insurance settlement system
	vehicle V[];					// array of objects of vehicle class to store registered vehicles

	Simulation(){						
		 V=new vehicle[6];			
	}
	
	public void run() {		
		V[0]=new nonEngine("Raju     ","Dual PR",2,0);			
		V[1]=new nonEngine("Vijay    ","2018 Ex",3,0);
		V[2]=new Engine("Ramprakash","Shine",2,1,20,12,2011);
		V[3]=new Engine("Uday     ","Pulsar",2,1,20,12,2019);
		V[4]=new Engine("Ajay   ","Creta Z",4,1,20,12,2012);
		V[5]=new Engine("Manpreet ","Grand",4,1,20,12,2018);
		
		displayRegisteredVehicles();						// calling method to print all registered vehicles

		System.out.println("Simulation has been started...");
		System.out.println();
		
		for(int i=0;i<6;i++){ 							//colliding each vehicle with all other vehicles
			for(int j=0;j<6;j++) {
				if(i!=j) {
					collide(V[i],V[j]);							
				}
			}
		}
	}
		
	public void collide(vehicle A, vehicle B) {
		System.out.println("vehicle1 "+A.getModel()+", "+A.getOwner()+" collided with "+"vehilce2 "+B.getModel()+", "+B.getOwner());
		A.setDamage();													//Assigning damage to both vehicles
		B.setDamage();
		System.out.println("Damage awarded to vehicle1 : Rs."+ A.getDamage());
		System.out.println("Damage awarded to vehicle2 : Rs."+ B.getDamage());
		A.settle(B);													//calling settle method
	}
	
	
	public void displayRegisteredVehicles() {									//printing all registered vehicles
		System.out.println("Model        Owner Name         Type of Vehicle       Type of Insurance policy    "
				+ "     Policy Validity Status");
		for(int i=0;i<6;i++) {
			System.out.println(V[i].getModel()+"         "+V[i].getOwner()+"           "+V[i].getVehicleType()+
					"           "+ V[i].getPolicyType()+"        "+V[i].getPolicyValidity());
		}
		System.out.println();
	}	
}

class vehicle{									//vehicle class
	protected String owner;
	protected String model;
	protected int wheels;
	protected int engine;   					// 0 for non-engined vehicles and 1 for engined vehicles.
	protected double damage;
	protected String vehicleType;
	protected double settled_damage;
	protected String policyType;
	protected String policyValidity;
	
	vehicle(String owner, String model, int wheels, int engine){			//vehicle constructor
		setOwner(owner);
		setModel(model);
		setWheels(wheels);
		setEngine(engine);
		damage=0;
		settled_damage=0;
	}
	
	public String getOwner() {												// setter and getters methods
		return owner;
	}

	public void setOwner(String owner) {
		this.owner=owner;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model=model;
	}
	
	public int getWheels() {
		return wheels;
	}
	
	public void setWheels(int wheels) {
		this.wheels=wheels;
	}
	
	public int getEngine() {
		return engine;
	}
	
	public void setEngine(int engine) {
		this.engine=engine;
	}
	
	public double getDamage() {
		return damage;
	}
	
	public void setDamage() {											
		Random r= new Random();											//randomly assigns damage to the vehicle
		damage=r.nextInt(10000)+1000;
	}
	
	public String getVehicleType() {
		return vehicleType;
	}
	
	public void setType(String vehicleType) {
		this.vehicleType=vehicleType;
	}
	
	public void setPolicyType(String policyType) {
		this.policyType=policyType;
	}
	
	public String getPolicyType() {
		return policyType;
	}
	
	public String getPolicyValidity() {
		return policyValidity;
	}
	
	public void setPolicyValidity(String policyValidity) {
		this.policyValidity=policyValidity;
	}
	
	public void settle(vehicle B) {										//settle method of vehicle class
		if(getPolicyValidity().compareTo(" Valid")==0){
			if(getVehicleType().compareTo("Two-Wheeler")==0) {
				setSettled_damage(10,getDamage());						// for two-wheeler with valid policy,default 10% is given
				B.setSettled_damage(50, B.getDamage());					// 50% settlement for oncoming vehicle in this case
			}
			else {
				setSettled_damage(60,getDamage());						// for four-wheeler with valid policy, 10%+50% is given
				B.setSettled_damage(80, B.getDamage());					// 80% settlement for oncoming vehicle in this case		
			}
			double one=getDamage()-getSettled_Damage();
			double two=B.getDamage()-B.getSettled_Damage();
			System.out.println("Settlement details:");
			System.out.println("       vehicle1 damage status after settlement : Rs."+one);
			System.out.println("       vehilce2 damage status after settlement : Rs."+two);
			System.out.println();
		}
		else{
			setSettled_damage(10,getDamage());						//for expired policy, default policy of 10% is given to self 
			B.setSettled_damage(0,getDamage());						// no damage settled for oncoming vehicle in this case
			double one=getDamage()-getSettled_Damage();
			double two=B.getDamage()-B.getSettled_Damage();
			System.out.println("Settlement details:");
			System.out.println("	vehicle1 damage status after settlement : Rs."+one);
			System.out.println("	vehilce2 damage status after settlement : Rs."+two);
			displayError();				
		}
	}
	
	public double getSettled_Damage() {
		return settled_damage;
	}

	public void setSettled_damage(double percent, double damage) {
		settled_damage=damage*percent/100;
	}
	
	public void displayError() {
		System.out.println("	Initiator does not have a valid policy.");
		System.out.println();
	}
}

class nonEngine extends vehicle{							// child class of vehicle class (inheritance)

	nonEngine(String o, String m, int w, int e) {
		super(o,m,w,e);										// calling base class constructor
		if(wheels==2) {
			setType("Bicycle    ");
		}
		else {
			setType("Rickshaw   ");
		}
		setPolicyType("Null Policy              ");
		setPolicyValidity("Not applicable");
	}
	
	
	public void settle(vehicle B) {									//overriding base class method (run-time polymorphism)
		System.out.println("Settlement Details : ");
		displayError();
	}
	
	public void displayError() {								//overriding base class method (run-time polymorphism)
		System.out.println("	Error. Initiator is not a engine-powered vehicle...");
		System.out.println();
	}
}

class Engine extends vehicle{								//child class of vehicle class (inheritance)
	private insurance ins;
	
	Engine(String o,  String m, int w, int e, int date, int month, int year ){
		super(o,m,w,e);											//calling base class constructor
		if (wheels==2){
			setType("Two-Wheeler");
			setPolicyType("Third Party Policy        ");
			ins=new insurance(1,date,month,year);
		}
		else {
			setType("Four-Wheeler");
			setPolicyType("Comprehensive policy      ");
			ins=new insurance(2,date,month,year);
		}
		
		if(ins.getPolicy()==true) {
			setPolicyValidity(" Valid");
		}
		else {
			setPolicyValidity("Invalid");
		}
	}
}

class insurance{										// insurance class of engine-powered vehicles only

	private int expiry[];	  // Date of expiry in the format of 3 indices of array in the order " date, month, year".
	private boolean policy;
	
	insurance(int p, int date,int month, int year){
		setExpiry(date,month,year);
		checkPolicy();
	}
	
	public int[] getExpiry() {
		return expiry;
	}

	public void setExpiry(int date, int month, int year) {
		expiry=new int[] {date, month, year};
	}
	
	public void setPolicy(boolean b) {
		policy=b;
	}
	
	public boolean getPolicy() {
		return policy;
	}

	public void checkPolicy() {										// 3 september 2018 is used as current date for checking policy
		if(expiry[2]>2018) {
			setPolicy(true);
		}
		else if(expiry[2]==2018){
			if(expiry[1]>9) {
				setPolicy(true);
			}
			else if(expiry[1]==9) {
				if(expiry[0]>=3) {
					setPolicy(true);
				}
				else {
					setPolicy(false);
				}
			}
			else {
				setPolicy(false);
			}
		}
		else {
			setPolicy(false);
		}
		
	}	
}