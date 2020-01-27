import org.junit.*;
import static org.junit.Assert.*;

public class testDatabase {
	
	Amacon amacon;
	@Before
	public void pre() throws ClassNotFoundException {
		amacon=new Amacon();
		amacon.Database.insert("Electronic", "sanyo", 200, 1);
		customer c=new customer("c1");
		amacon.Database.addCustomer(c);
		c.addFunds(1000);
		product p=amacon.Database.search("sanyo");
		c.addtoCart(p, 1, amacon.Database);
		
	}
	
	@Test(expected=myCustomException.class)
	public void testSerialDatabase() throws ClassNotFoundException{
		Amacon A=new administrator();
		A.Database.addCustomer(new customer("abc"));
		A.Database.insert("new location>new dir", "name", 500, 2);
		Amacon B=new administrator();
		B.Database.insert("new location>new dir", "name", 500, 2);
		
	}
	
	@Test
	public void testISerialCustomer(){
		customer c=amacon.Database.getCustomer("c1");
		assertEquals(1, c.getCounter());
	}
}
