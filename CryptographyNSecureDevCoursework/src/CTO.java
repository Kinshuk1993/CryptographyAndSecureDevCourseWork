import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CTO {

	public static int countNumberOfBlocksNeeded(String cipherFilePath) throws FileNotFoundException {
		// Loop to check how many blocks are needed to decrypt
		for (int counterForBlocks = 63; counterForBlocks > 1; counterForBlocks--) {
			// get decrypted text with cut blocks as counter value
			String decryptedText = bruteForceAttack(cipherFilePath, counterForBlocks);
			// check if the decryption is done or not
			if (!decryptedText.equals("No decrypted text using CTO")) {
				// if decrypted text is found and is in English, return the number of blocks
				// needed
				return 64 - counterForBlocks;
			}
		}
		// if nothing found, return number of blocks as zero
		return 0;
	}

	public static String bruteForceAttack(String cipherFilePath, int blocksNeeded) throws FileNotFoundException {
		// read cipher file contents all in one read till end of file
		@SuppressWarnings("resource")
		String cipherTextHexValues = new Scanner(new File(cipherFilePath)).useDelimiter("\\Z").next();
		// array to store cipher text hex values
		String[] cipherTextHexArray = cipherTextHexValues.split("\r\n");
		// length of the cipher text file
		int cipherTextHexLength = cipherTextHexValues.split("\r\n").length;

		// number of blocks of ciphers to be used
		if (blocksNeeded != 0) { // if not zero
			cipherTextHexLength = cipherTextHexLength - blocksNeeded;
		}
		// return the key and the decrypted text
		return findKeyAndDecrypt(cipherTextHexLength, cipherTextHexArray);
	}

	public static String findKeyAndDecrypt(int cipherTextHexLength, String[] cipherTextHexArray) {
		// Iterate through all possible keys
		for (int counterForKey = 0; counterForKey < 65536; counterForKey++) {
			// call KPT method to cipher text to normal text hex
			String normalTextHex = KPT.decryptCipherHexToNormalTextHex(cipherTextHexLength, cipherTextHexArray,
					counterForKey, "CTO");
			// call KPT method to convert the normal text hex to readable text
			String decryptedText = KPT.hexToEnglishLetter(normalTextHex, "CTO");
			// check if the decrypted text is in English or not
			if (checkIfDecryptedTextEnglish(decryptedText)) {
				// if English, return and exit
				return "The Key is: " + counterForKey + "\r\n" + "The decrypted Text is: " + decryptedText;
			}
		}
		// if not English, return not found
		return "No decrypted text using CTO";
	}

	public static boolean checkIfDecryptedTextEnglish(String text) {
		// check if the text is in English or not
		boolean isEnglishText = ((text.indexOf("The ") != -1 || text.indexOf("the ") != -1 || text.indexOf(" the") != -1
				|| text.indexOf("and ") != -1 || text.indexOf(" and") != -1 || text.indexOf("That ") != -1
				|| text.indexOf(" that") != -1 || text.indexOf("that ") != -1 || text.indexOf("have ") != -1)
				&& (text.indexOf(" ") != -1)) ? true : false;
		// return final result
		return isEnglishText;
	}

}
