//Name : Atul Anand
//Roll No. : 2017284
// AP Lab 1

import java.util.Scanner;

public class lab1 {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		fibonacciClass X=new fibonacciClass();
		System.out.print("Enter the value of max_Size : ");
		int max_size=in.nextInt();
		X.generateSequence(max_size);
		X.displaySequence();
		in.close();
	}
}

class fibonacciClass{
	static int []fibo;
	private int max_size;
	private int curr_size;
	
	fibonacciClass(){		// default constructor
		max_size=100;
		fibo=new int[max_size];
		curr_size=0;
	}
	
	public int getcurr_size(){
		return curr_size;
	}
	
	public void generateSequence(int N){ // method to generate Sequence
		if(N==0){
			return;
		}
		if(N==1){
			fibo[0]=0;
			curr_size=1;
		}
		else if(N==2){
			fibo[0]=0;
			fibo[1]=1;
			curr_size=2;
		}
		else if(N<=max_size){
	
			fibo[0]=0;
			fibo[1]=1;
			curr_size=2;
			for(int i=2;i<N;i++){
				fibo[i]=fibo[i-1]+fibo[i-2];
				curr_size+=1;
			}
			
		}
		else{
			fibo[0]=0;
			fibo[1]=1;
			curr_size=2;
			for(int i=2;i<max_size;i++){
				fibo[i]=fibo[i-1]+fibo[i-2];
				curr_size+=1;
			}
		}
	}

	public void displaySequence(){			//method to display sequence
		int size=getcurr_size();			//calling getter method
		System.out.println("Fibonacci Sequence is given below: ");
		for(int i=0;i<size;i++){
			System.out.print(fibo[i]+", ");
		}
		System.out.println();
	}
}