import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class TMT1 {


	public static void main(String[] args) {
		try {
			
			buildTable(66, 1000, "src/files/pt3.txt", "src/files/table.txt");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void buildTable(int l, int n, String pt_block, String tableFile) throws IOException{
		
		@SuppressWarnings("resource")
		String pt_block1 = new Scanner(new File(pt_block)).useDelimiter("\\Z").next();
		PrintWriter writer = new PrintWriter("src/files/table.txt", "UTF-8");
		
		//writes n chains to file
		for (int i = 0; i < n; i++) {
			buildChain(pt_block1, l, writer);
			
		}
		writer.close();
	}
	
	//builds a chains of length l and writes it using the provided fileWriter
	public static void buildChain(String pt, int l, PrintWriter writer) throws IOException{
		
		//initial encrypion link
		int x = (int)Math.floor(Math.random()*65536);
		int x0 = x;
		//create l-1 more links
		for (int i = 0; i < l; i++) {
			x = Coder.encrypt(x, Hex16.convert(pt));
		}
		
		//write chain to file
		writer.println(x0 + "," + x);
		
	}
	
}
