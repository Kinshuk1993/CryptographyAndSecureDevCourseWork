import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KPT {

	// variable to store the final key obtained
	private static int finalKey = 0;

	public static String bruteForceAttack(String plainTextFile, String cipherTextFile) throws FileNotFoundException {
		// read the Cipher text file contents in a single go
		@SuppressWarnings("resource")
		String cipheFileContent = new Scanner(new File(cipherTextFile)).useDelimiter("\\Z").next();
		// take the length of the cipher file (number of lines in the file)
		int cipherTextFileLength = cipheFileContent.split("\r\n").length;
		// split the contents of the cipher file given
		String[] hexForCipherTextArray = cipheFileContent.split("\r\n");
		// read first index of first line of cipher text file
		String eachCipherTextLine = cipheFileContent.split("\r\n")[0];
		// read the Plain text file contents in a single read
		@SuppressWarnings("resource")
		String plainTextFileContent = new Scanner(new File(plainTextFile)).useDelimiter("\\Z").next();

		// call method to get the key
		getFinalKey(eachCipherTextLine, plainTextFileContent);
		// print final key obtained
		System.out.println("The key is:   " + KPT.finalKey);

		// call method to convert the cipher text hex into normal text hex values
		String decryptedHex = decryptCipherHexToNormalTextHex(cipherTextFileLength, hexForCipherTextArray, 0, "KPT");

		// call method to convert each hex to readable string
		String finalOutputString = hexToEnglishLetter(decryptedHex, "kpt");
		// return the decrypted sentence
		return ("The decrypted Text is: " + finalOutputString);
	}

	public static void getFinalKey(String eachCipherTextLine, String plainTextFileContent) {
		// iterate through all possible keys (65536 is the max number of possible keys)
		for (int counterForKey = 0; counterForKey < 65536; counterForKey++) {
			// change the value of final key for every iteration
			KPT.finalKey = counterForKey;
			// use given Hex16 class
			int eachCipherInt = Hex16.convert(eachCipherTextLine);
			// get the decrypted integer value for the normal hex integer
			int decryptedInt = Coder.decrypt(KPT.finalKey, eachCipherInt);
			// format the hex output for decrypted text
			String decrypted = String.format("0x%04x", decryptedInt);
			// check if we found a match between the decrypted text hex and the given plain
			// text hex value given
			if (decrypted.equals(plainTextFileContent)) {
				// break out of the loop as the key is found
				break;
			}
		}
	}

	public static String decryptCipherHexToNormalTextHex(int cipherTextFileLength, String[] hexForCipherTextArray,
			int key, String algoUsed) {
		// variable to store the decrypted hex values
		String finalDecryptedHexOutput = "";
		// variable to store decrypted cipher text hex integer value
		int decryptedCipherHex = 0;
		// loop to decrypt the cipher text hex and get a simple English hex string value
		for (int counterCipherHexToNormalTextHex = 0; counterCipherHexToNormalTextHex < cipherTextFileLength; counterCipherHexToNormalTextHex++) {
			// get each cipher text hex value
			String eachCipherTextHex = hexForCipherTextArray[counterCipherHexToNormalTextHex];
			// get int for each cipher text hex
			int eachCipherTextHexInteger = Hex16.convert(eachCipherTextHex);
			// decrypt each cipher text hex's integer value
			// if algorithm used is KPT, decrypt using KPT final key
			if (algoUsed.equalsIgnoreCase("kpt")) {
				decryptedCipherHex = Coder.decrypt(KPT.finalKey, eachCipherTextHexInteger);
			} else { // else use key parameter
				decryptedCipherHex = Coder.decrypt(key, eachCipherTextHexInteger);
			}
			// format the decrypted value
			String normalTextHex = String.format("0x%04x", decryptedCipherHex);
			// if algorithm used is KPT, check for length before adding hex values
			if (algoUsed.equalsIgnoreCase("kpt")) {
				// do not add a ',' to first input insertion
				if (finalDecryptedHexOutput.length() == 0) {
					// Initially add the final decrypted hex value to output string
					finalDecryptedHexOutput += normalTextHex;
				} else {
					// add the final decrypted hex value to output string
					finalDecryptedHexOutput += "\r\n" + normalTextHex;
				}
			} else { // else append the hex output
				// add the final decrypted hex value to output string
				finalDecryptedHexOutput += "\r\n" + normalTextHex;
			}
		}
		// return the hex values for decrypted text
		return finalDecryptedHexOutput;
	}

	public static String hexToEnglishLetter(String hexForEngLetter, String algoUsed) {
		// counter variable
		int counter = 0;
		// variable to store final output
		String finalOutputString = "";
		// array to store all decrypted hex values split by new line
		String[] hexForDecryptedOutput = hexForEngLetter.split("\r\n");
		// if algorithm used is KPT, counter starts from 0
		if (algoUsed.equalsIgnoreCase("kpt")) {
			counter = 0;
		} else { // else counter starts from 1
			counter = 1;
		}
		// loop to convert each hex value to a meaningful letter
		for (; counter < hexForDecryptedOutput.length; counter++) {
			// get each hex value
			String eachLetterHex = hexForDecryptedOutput[counter];
			// get integer for hex
			int convertedInt = Hex16.convert(eachLetterHex);
			// get quotient of hex's integer by 256 to get which letter is it
			int c0 = convertedInt / 256;
			// get remainder of value to get char value of integer
			int c1 = convertedInt % 256;
			// build final string
			finalOutputString += (char) c0;
			// if remainder is not zero, then add to built up string
			if (c1 != 0) {
				finalOutputString += (char) c1;
			} else { // else do nothing for now
				continue;
			}
		}
		// return final meaningful string
		return finalOutputString;
	}
}
