package RestAssuredMaven.RestAssuredMaven;

import java.io.IOException;
import java.util.ArrayList;

public class DataDrivenTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
	DataDrivenExcelUtility d=new DataDrivenExcelUtility();
	ArrayList data=d.getData("Add Profile","testdata");
	
	System.out.println(data.get(0));
	System.out.println(data.get(1));
	System.out.println(data.get(2));
	System.out.println(data.get(3));

	
		
	}

}
