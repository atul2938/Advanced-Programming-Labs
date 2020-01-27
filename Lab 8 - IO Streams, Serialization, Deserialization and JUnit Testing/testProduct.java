import org.junit.*;
import static org.junit.Assert.*;

public class testProduct {
	private Amacon X;

	@Before
	public void initialize() throws ClassNotFoundException {
		X=new Amacon();
	}
	
	@Test(expected=myCustomException.class)
	public void testInsert(){
		X.Database.insert("electronics>phone", "jio",1000,1);
		X.Database.insert("electronics>phone", "jio",1000,1);
	}
	
	@Test(expected=myCustomException.class)
	public void testDelete(){
		X.Database.delete("electronic>phone>Oppo");
		
	}
	
	@Test(expected=myCustomException.class)
	public void testSearch(){
		X.Database.search("redmi");	
	}
	
	@Test(expected=myCustomException.class)
	public void testCart(){
		customer c= new customer("xyz");
		X.Database.insert("electronics>machine", "cutter",1000,0);
		product x=X.Database.search("cutter");
		c.addtoCart(x, 5, X.Database);
		
	}
	
	@Test(expected=myCustomException.class)
	public void testFunds(){
		customer c= new customer("abc");
		X.Database.insert("Books>novel", "ubn2", 500, 2);
		product x=X.Database.search("ubn2");
		c.addtoCart(x, 2, X.Database);
		c.checkout(X.Database);
	}
}