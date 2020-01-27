import java.util.Scanner;

public class lab3 {

	public static void main(String[] args) throws NumberFormatException{
		Scanner in=new Scanner(System.in);
		System.out.print("Enter the number of shapes : ");
		int n=in.nextInt();
		in.nextLine();
		System.out.println();
		String S=in.nextLine();
		String[] Arr=S.split(" ");
		int size=Arr.length;
		double[] pointsOfA=new double[size];
		for(int j=0;j<size;j++) {
			pointsOfA[j]=Double.parseDouble(Arr[j]);
		}
		geometry X=new geometry(pointsOfA);
		int c=98;
		int count=0;
		int[] r=new int[n-1];
		for(int i=0;i<n-1;i++) {
			String s=in.nextLine();
			String[] arr=s.split(" ");
			double[] points=new double[Arr.length];
			for(int j=0;j<arr.length;j++) {
				points[j]=Double.parseDouble(arr[j]);
			}
			
			boolean b=X.cross(points);
			geometry Y=new geometry(points);
			int result=Y.encircle(X.Apoints);
			r[count]=result;
			count+=1;
			System.out.println("a cross "+(char) c+" :"+b);
			c+=1;
		}
		c=98;
		for(int i=0;i<n-1;i++) {
			System.out.println("a encircles"+(char) c+" :"+r[i] );
		}
	}
}

class geometry{
	double[] Apoints;
	int size;

	geometry(double[] p){
		boolean b=checkValidity(p);
		if(b==true) {
			error();
		}
		else {
			Apoints=p;
			size=p.length;
		}
	}
	
	int encircle(double[] B) {
		boolean b=checkValidity(B);
		if(b==true) {
			return 1;
		}
		else {
			double x=getAvg(Apoints,1);
			double y=getAvg(Apoints,2);
			double[] vertexOfX=new double[B.length/2];
			double[] vertexOfY=new double[B.length/2];
			for(int i=0;i<B.length;i+=2) {
				vertexOfX[i]=B[i];
				vertexOfY[i]=B[i+1];
			}
			
			int numV=B.length/2;
			int crosses=1;
			for(int i=0,j=numV-1;i<numV;j=i++) {
				if( ( (vertexOfY[i]>=y) != (vertexOfY[j]>=y) ) && ( x<= (vertexOfX[j]-vertexOfX[i])*(y-vertexOfY[i]) / (vertexOfY[j]-vertexOfY[i]) + vertexOfX[i])) {
					crosses=-crosses;
					System.out.println("Here");
				}
			}
			if(crosses==1) {
				return 1;
			}
			
			return 0;
		}
	}
	
	double getAvg(double[] B,int t) {
		double avg=0;
		double sum=0;
		if(t==1) {
			
			for(int i=0;i<B.length;i+=1) {
				sum=sum+B[i];
			}
		}
		else {
			for(int i=1;i<B.length;i+=1) {
				sum=sum+B[i];
			}
		}
		avg=sum/(B.length/2);
		return avg;
	}
	
	boolean cross(double[] B) {
		boolean b=checkValidity(B);
		if(b==true) {
			return b;
		}
		else {
			double[] vertexOfX=new double[B.length/2];
			double[] vertexOfY=new double[B.length/2];
			for(int i=0;i<B.length;i+=2) {
				vertexOfX[i]=B[i];
				vertexOfY[i]=B[i+1];
			}
			for(int s=0;s<size-1;s+=2) {
				int numV=B.length/2;
				int crosses=1;
				double x=Apoints[s];
				double y=Apoints[s+1];
				for(int i=0,j=numV-1;i<numV;j=i++) {
					if( ( (vertexOfY[i]>=y) != (vertexOfY[j]>=y) ) && ( x<= (vertexOfX[j]-vertexOfX[i])*(y-vertexOfY[i]) / (vertexOfY[j]-vertexOfY[i]) + vertexOfX[i])) {
						crosses=-crosses;
					}
				}
				if(crosses==1) {
					return true;
				}
			}
			
			return false;
		}
	}
	
	boolean checkValidity(double[] P) {
		int size=P.length;
		if(size%2!=0 || size<6) {
			return false;
		}
		return checkSameLine(P);
	}
	
	boolean checkSameLine(double[] P) {
		int size=P.length;
		for(int i=0;i<=size-6;i+=2) {
				double x1=P[i];
				double y1=P[i+1];
				double x2=P[i+2];
				double y2=P[i+3];
				double x3=P[i+4];
				double y3=P[i+5];
				double a=y2-y1;
				double b=x2-x1;
				double c=y3-y1;
				double d=x3-x1;
				if(a/b==c/d) {
					System.out.println("There");
					return true;
				}
		}
		return false;
	}
	
	void error() {
		System.out.println("Invalid Shape points");
	}
}

//Reference :https://stackoverflow.com/questions/217578/how-can-i-determine-whether-a-2d-point-is-within-a-polygon