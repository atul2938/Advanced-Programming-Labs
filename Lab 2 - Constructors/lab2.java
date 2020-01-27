//Name : Atul Anand
//Roll No. : 2017284
//AP Lab 2

public class lab2 {

	public static void main(String[] args) {
		Box box_1=new Box();			//object with no parameters
		Box box_2=new Box(5);			//object with one number as parameter
		Box box_3=new Box(2,4,3);		//object with three numbers as parameters
		box_1.displayVolume();
		box_2.displayVolume();
		box_3.displayVolume();
	}
}


class Box{
	private double length;
	private double breadth;
	private double height;
	
	Box(){							//default constructor - constuctor 1
		length=0;
		breadth=0;
		height=0;
	}
	
	Box(double num){				// contructor 2				
		length=num;
		breadth=num;
		height=num;
	}
	
	Box(double length, double breadth, double height){		// constructor 3
		this.length=length;
		this.breadth=breadth;
		this.height=height;
	}
	
	public double getVolume() {								//method to get Volume
		return length*breadth*height;
	}
	
	public void displayVolume() {							//method to print value
		System.out.println("Volume of box : "+getVolume());
	}	
}