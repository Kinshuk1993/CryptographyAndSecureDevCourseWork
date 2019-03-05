import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class KPT {
	
	public static void main(String[] args) {
		try {
			System.out.println(bruteForce("src/files/pt1.txt", "src/files/ct1.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String toText(String[] lines_pt){
		String decrypted_t="";
		//convert into text
		int len2 = lines_pt.length;
		for (int l = 1; l< len2; l++) {
			String s = lines_pt[l];
			int	c = Hex16.convert(s);
			int	c0 = c / 256;
			int	c1 = c % 256;
			decrypted_t+=(char)c0;
			
			if (c1 != 0)
				decrypted_t+=(char)c1;
		}
		return decrypted_t;
	}
	
	@SuppressWarnings("resource")
	public static String bruteForce(String pt_file, String ct_file) throws FileNotFoundException {
		//read the files into String array
		String ct = new Scanner(new File(ct_file)).useDelimiter("\\Z").next();
		String ct1 = ct.split(System.getProperty("line.separator"))[0];
		
		String pt = new Scanner(new File(pt_file)).useDelimiter("\\Z").next();
		
		int key=0;
		
		int len = ct.split(System.getProperty("line.separator")).length;
		//itterate through all possible keys
		for (int i = 0; i < 65536; i++) {
			
					key=i;
					int	c = Hex16.convert(ct1);
					int	p = Coder.decrypt(key, c);
					String decrypted = String.format("0x%04x", p);

					//check if we found a match
					if(decrypted.equals(pt)) break;
		
		}

		System.out.println("The key is:   "+key);
		
		String[] lines_ct = ct.split(System.getProperty("line.separator"));
		String decrypted="";
		
		//decrypt the cipher text
		for (int i = 0; i < len; i++) {
				String	s = lines_ct[i];
				int	c = Hex16.convert(s);
				int	p = Coder.decrypt(key, c);
				String	out = String.format("0x%04x", p);
				decrypted+="\n"+out;
			
		}
		
		String[] lines_pt = decrypted.split(System.getProperty("line.separator"));
		
		
		String decrypted_t = toText(lines_pt);
		

		
		
		return("Decrypted text:      "+ decrypted_t);
			
	}

}
