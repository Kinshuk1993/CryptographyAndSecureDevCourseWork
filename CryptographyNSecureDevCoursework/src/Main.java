import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// KNOWN PLAIN TEXT ATTACK
		// plain text file path
		String plainTextFilePath = "src/Input_Files/pt1.txt";
		// cipher text file path
		String cypherTextFilePath = "src/Input_Files/ct1.txt";
		System.out.println("KPT Output:");
		// call method to get decrypted text using brute force attack
		String finalPlainTextOutput = KPT.bruteForceAttack(plainTextFilePath, cypherTextFilePath);
		// print the final decrypted text
		System.out.println(finalPlainTextOutput + "\r\n");
		
		// CIPHER TEXT ONLY ATTACK
		String cipherTextFilePath = "src/Input_Files/ct2.txt";
		// decrypt cipher text and print the key and result
		System.out.println("CTO Output:" + "\r\n" + CTO.bruteForceAttack(cipherTextFilePath, 0));
		//count number of cipher blocks needed for decryption
		System.out.println("Number of blocks needed to decrypt the given ciphertext: " + CTO.countNumberOfBlocksNeeded(cipherTextFilePath) + "\r\n");
	}

}
