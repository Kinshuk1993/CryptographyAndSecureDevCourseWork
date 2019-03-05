import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class CTO {
	
	public static void main(String[] args) {
		try {
			//run brute force and experiment
			System.out.println(bruteForce("src/files/ct2.txt", 0).get(0));
			System.out.println("Number of blocks needed:  " + experiment("src/files/ct2.txt"));
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//check how many blocks are needed to decrypt with only one match
	public static int experiment(String ct_path) throws FileNotFoundException{
		for (int i = 63; i > 1 ; i--) {
			ArrayList<String> r = bruteForce(ct_path, i);
			if ( r.size() == 1  && !r.get(0).equals("-")){
				return 64-i;
			}
		}
		
		return 0;
	}
	
	
	@SuppressWarnings("resource")
	public static ArrayList<String> bruteForce(String ct_path, int cutBlocks) throws FileNotFoundException {
		ArrayList<String> ans = new ArrayList<String>();
		//read the file into String array
		String ct = new Scanner(new File(ct_path)).useDelimiter("\\Z").next();
		String[] lines = ct.split(System.getProperty("line.separator"));
		int len = ct.split(System.getProperty("line.separator")).length;
		
		//set how many blocks will be used
		if (cutBlocks!=0) len=len-cutBlocks;

		//itterate through all possible keys
		for (int key = 0; key < 65536; key++) {
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
			
			if (checkIfEnglish(pt_t)){
				ans.add("Key:    " + key + "\n" + pt_t + "\n");
			}
		
		}
		if (ans.size()==0) ans.add("-");
		return ans;
	}
	
	
	public static boolean checkIfEnglish(String text){
		ArrayList<String> wordList = new ArrayList<String>();
		String[] split = text.split(" ");
		for (String string : split) {
			wordList.add(string);
		}
		
		//looks for the most common three letter english words.
		if(wordList.contains("the") || wordList.contains("and") || wordList.contains("that") || wordList.contains("have")){
			return true;
		}
		else return false;
		
	}
	
}
