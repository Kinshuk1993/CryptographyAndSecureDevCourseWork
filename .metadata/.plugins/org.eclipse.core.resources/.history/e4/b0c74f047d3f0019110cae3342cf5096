import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class TMT2 {
	

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			
			String ct_file="src/files/ct3.txt";
			String pt_file="src/files/pt3.txt";
			String table_file="src/files/table.txt";
			
			//reads the file into the associative array.
			Table table = readTableFile(table_file);
			
			String ct = new Scanner(new File(ct_file)).useDelimiter("\\Z").next();
			String ct_block1 = ct.split(System.getProperty("line.separator"))[0];
			int ct_block1_int = Hex16.convert(ct_block1);

			String pt_block1 = new Scanner(new File(pt_file)).useDelimiter("\\Z").next();
			int pt_block1_int = Hex16.convert(pt_block1);
			
			//searches for the key.
			int key = findKey(table, ct_block1_int, pt_block1_int);
			
			//decrypts using the key.
			String decrypted = decryptToText(ct, key);
			
			System.out.println("Key:  " + key + "\n" + "Decrypted:     " + decrypted);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static String decryptToText(String ct, int key){
		int len = ct.split(System.getProperty("line.separator")).length;
		String[] lines = ct.split(System.getProperty("line.separator"));
		String pt="";
		
		//decrypt the cipher text
		for (int i = 0; i < len; i++) {
				String	s = lines[i];
				int	c = Hex16.convert(s);
				int	p = Coder.decrypt(key, c);
				String	out = String.format("0x%04x", p);
				pt+="\n"+out;
			
		}
		String pt_t="";
		
		String[] lines_pt = pt.split(System.getProperty("line.separator"));
		int len2 = pt.split(System.getProperty("line.separator")).length;
		
		//convert into text
		for (int l = 1; l< len2; l++) {
			String s = lines_pt[l];
			int	c = Hex16.convert(s);
			int	c0 = c / 256;
			int	c1 = c % 256;
			pt_t+=(char)c0;
			
			if (c1 != 0)
				pt_t+=(char)c1;
		}
	
		return pt_t;
	}
	
	public static int findKey(Table tab, int ct, int pt){
		int l = 66;
			
		for (int i = 1; i < l; i++) {
			if (tab.find(ct) != -1){
				int key = tab.find(ct);

				
				for (int j = 0; j < l-i; j++) {
					key = Coder.encrypt(key, pt);
				}
				return key;
				
			}
			ct=Coder.encrypt(ct, pt);
			
		}
			
		return -1;
		
	}
	
	public static Table readTableFile(String tablePath) throws IOException{
		
		BufferedReader in = new BufferedReader(new FileReader(tablePath));
		
		Table tab = new Table();
		while (in.ready()) {
			String line = in.readLine();
			tab.add(Integer.parseInt(line.split(",")[1]), Integer.parseInt(line.split(",")[0]));
			//now its stored y --> x, so y is searchable. 
		}
		in.close();
		return tab;
	}


	
	
	
	
}
