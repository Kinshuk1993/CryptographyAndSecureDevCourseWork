import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TMT2 {
	// variable to store the length of the chain in chain table
	static int lengthOfChain = 66;

	public static void timeMemoryTradeOffAttack() throws IOException {
		// variable to store the plain text hex file path
		String plainTextHexFilePath = "src/Input_Files/Plain_Text_TMT.txt";
		// variable to store the chain table file path
		String chainTableFilePath = "src/Input_Files/Chain_Table.txt";
		// variable to store the cipher text hex file path
		String cipherTextHexFilePath = "src/Input_Files/Cipher_Text_TMT.txt";

		// reads the file into the associative array.
		Table chainTableData = loadChainTable(chainTableFilePath);

		// read the contents of the cipher text file hex in a single go
		@SuppressWarnings("resource")
		String cipherTextHexString = new Scanner(new File(cipherTextHexFilePath)).useDelimiter("\\Z").next();
		// read first index values of cipher text hex file
		String cipherTextHexFirst = cipherTextHexString.split("\r\n")[0];
		// convert into integer value
		int cipherTextHexIntVal = Hex16.convert(cipherTextHexFirst);

		// read the contents of the plain text file hex in a single go
		@SuppressWarnings("resource")
		String plainTextHecString = new Scanner(new File(plainTextHexFilePath)).useDelimiter("\\Z").next();
		// convert plain text hex file into integer representation
		int plainTextHexIntVal = Hex16.convert(plainTextHecString);

		// find the key using the integer representation of the cipher and the plain
		// text
		int keyFromChainTable = findKeyInChainTable(chainTableData, cipherTextHexIntVal, plainTextHexIntVal);

		// decrypt the content of hex file using the obtained key
		String decrypted = decryptToText(cipherTextHexString, keyFromChainTable);

		// print the key obtained and the decrypted text
		System.out.println("The Key is: " + keyFromChainTable + "\r\n" + "The decrypted Text is: " + decrypted);
	}

	public static String decryptToText(String cipherTextHexString, int keyFromChainTable) {
		// string array to hold the cipher text string representations
		String[] hexForCipherTextArray = cipherTextHexString.split("\r\n");
		// call function to get the normal text hex values for the corresponding cipher
		// text hex
		String normalTextHex = KPT.decryptCipherHexToNormalTextHex(hexForCipherTextArray.length, hexForCipherTextArray,
				keyFromChainTable, "TMT");
		// call method to decrypt the normal text hex to readable English words
		String decryptedText = KPT.hexToEnglishLetter(normalTextHex, "TMT");
		// return the decrypted text
		return decryptedText;
	}

	public static Table loadChainTable(String tablePath) throws IOException {
		// prepare reader for reading the file
		BufferedReader fileReader = new BufferedReader(new FileReader(tablePath));
		// new table class instance
		Table chainTable = new Table();
		// till next line is present
		while (fileReader.ready()) {
			// read in new chain link from the chain table file
			String eachChainLink = fileReader.readLine();
			// add the contents of the file as a map [1] -> [0]
			chainTable.add(Integer.parseInt(eachChainLink.split(",")[1]),
					Integer.parseInt(eachChainLink.split(",")[0]));
		}
		// close the reader to avoid memory leaks
		fileReader.close();
		// return the table populated
		return chainTable;
	}

	public static int findKeyInChainTable(Table findKeyInChainTable, int cipherTextHexIntVal, int plainTextHexIntVal) {
		// loop to find the key in the chain table created
		for (int counterForFindingKey = 1; counterForFindingKey < lengthOfChain; counterForFindingKey++) {
			// check if the key is found or not
			if (findKeyInChainTable.find(cipherTextHexIntVal) != -1) {
				// if key is found then capture the key and run a loop to encrypt the key
				int key = findKeyInChainTable.find(cipherTextHexIntVal);
				// loop to encrypt the key till the position of the key found
				for (int counterForKeyEncryption = 0; counterForKeyEncryption < lengthOfChain
						- counterForFindingKey; counterForKeyEncryption++) {
					// encrypt key found
					key = Coder.encrypt(key, plainTextHexIntVal);
				}
				// return key found
				return key;
			}
			// encrypt the cipher text integer representation
			cipherTextHexIntVal = Coder.encrypt(cipherTextHexIntVal, plainTextHexIntVal);
		}
		// if no key is found, return -1
		return -1;
	}
}
