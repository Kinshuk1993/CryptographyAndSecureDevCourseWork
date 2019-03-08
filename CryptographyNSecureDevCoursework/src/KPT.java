import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KPT {

	// variable to store the final key obtained
	private static int finalKey = 0;

	public static void main(String[] args) throws FileNotFoundException {
		//plain text file path
		String plainTextFilePath = "src/Input_Files/pt1.txt";
		//cipher text file path
		String cypherTextFilePath = "src/Input_Files/ct1.txt";
		//call method to get decrypted text using brute force attack
		String finalPlainTextOutput = bruteForceAttack(plainTextFilePath, cypherTextFilePath);
		//print the final decrypted text
		System.out.println(finalPlainTextOutput);
	}

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
		String decryptedHex = decryptCipherHexToNormalTextHex(cipherTextFileLength, hexForCipherTextArray);

		// call method to convert each hex to readable string
		String finalOutputString = hexToEnglishLetter(decryptedHex);
		// return the decrypted sentence
		return ("Final decrypted sentence: " + finalOutputString);
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

	public static String decryptCipherHexToNormalTextHex(int cipherTextFileLength, String[] hexForCipherTextArray) {
		// variable to store the decrypted hex values
		String finalDecryptedHexOutput = "";
		// loop to decrypt the cipher text hex and get a simple English hex string value
		for (int counterCipherHexToNormalTextHex = 0; counterCipherHexToNormalTextHex < cipherTextFileLength; counterCipherHexToNormalTextHex++) {
			// get each cipher text hex value
			String eachCipherTextHex = hexForCipherTextArray[counterCipherHexToNormalTextHex];
			// get int for each cipher text hex
			int eachCipherTextHexInteger = Hex16.convert(eachCipherTextHex);
			// decrypt each cipher text hex's integer value
			int decryptedCipherHex = Coder.decrypt(KPT.finalKey, eachCipherTextHexInteger);
			// format the decrypted value
			String normalTextHex = String.format("0x%04x", decryptedCipherHex);
			// do not add a ',' to first input insertion
			if (finalDecryptedHexOutput.length() == 0) {
				// Initially add the final decrypted hex value to output string
				finalDecryptedHexOutput += normalTextHex;
			} else {
				// add the final decrypted hex value to output string
				finalDecryptedHexOutput += "\r\n" + normalTextHex;
			}
		}
		// return the hex values for decrypted text
		return finalDecryptedHexOutput;
	}
	
	public static String hexToEnglishLetter(String hexForEngLetter) {
		// variable to store final output
		String finalOutputString = "";
		// array to store all decrypted hex values split by new line
		String[] hexForDecryptedOutput = hexForEngLetter.split("\r\n");
		// loop to convert each hex value to a meaningful letter
		for (int counter = 0; counter < hexForDecryptedOutput.length; counter++) {
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
			if (c1 != 0)
				finalOutputString += (char) c1;
		}
		// return final meaningful string
		return finalOutputString;
	}
}
