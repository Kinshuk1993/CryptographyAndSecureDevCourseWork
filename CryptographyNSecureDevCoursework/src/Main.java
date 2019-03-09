import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// KNOWN PLAIN TEXT ATTACK
		// plain text file path
		String plainTextFilePath = "src/Input_Files/Plain_Text_KPT.txt";
		// cipher text file path
		String cypherTextFilePath = "src/Input_Files/Cipher_Text_KPT.txt";
		System.out.println("KPT Output:");
		// call method to get decrypted text using brute force attack
		String finalPlainTextOutput = KPT.bruteForceAttack(plainTextFilePath, cypherTextFilePath);
		// print the final decrypted text
		System.out.println(finalPlainTextOutput + "\r\n");

		// CIPHER TEXT ONLY ATTACK
		String cipherTextFilePath = "src/Input_Files/Cipher_Text_CTO.txt";
		// decrypt cipher text and print the key and result
		System.out.println("CTO Output:" + "\r\n" + CTO.bruteForceAttack(cipherTextFilePath, 0));
		// count number of cipher blocks needed for decryption
		System.out.println("Number of blocks needed to decrypt the given ciphertext: "
				+ CTO.countNumberOfBlocksNeeded(cipherTextFilePath) + "\r\n");

		// TIME MEMORY TRADE OFF ATTACK
		System.out.println("TMT Output:");
		// variable to store the plain text hex file path
		String plainTextHexFilePath = "src/Input_Files/Plain_Text_TMT.txt";
		// variable to store the chain table file path
		String chainTableFilePath = "src/Input_Files/Chain_Table.txt";
		// build the chain table
		TMT1.createChainTable(66, 1000, plainTextHexFilePath, chainTableFilePath);
		// perform decryption using TMT
		TMT2.timeMemoryTradeOffAttack();
	}
}
