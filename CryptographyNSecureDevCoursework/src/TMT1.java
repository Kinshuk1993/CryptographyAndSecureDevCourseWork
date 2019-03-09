import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TMT1 {

	public static void createChainTable(int lengthOfChain, int numberOfChains, String plainTextHexFilePath,
			String chainTableFilePath) throws IOException {
		@SuppressWarnings("resource")
		String plainTextHexString = new Scanner(new File(plainTextHexFilePath)).useDelimiter("\\Z").next();
		// create block chains and write to table
		for (int counterForWritingChainBlocks = 0; counterForWritingChainBlocks < numberOfChains; counterForWritingChainBlocks++) {
			// get a random number for encryption link
			int randomLinkVal = (int) Math.floor(Math.random() * 65536);
			int linkOne = randomLinkVal;
			// create lengthOfChain minus 1 more links
			for (int counterForLinkCreation = 0; counterForLinkCreation < lengthOfChain; counterForLinkCreation++) {
				// get the encrypted value for the plain text hex
				randomLinkVal = Coder.encrypt(randomLinkVal, Hex16.convert(plainTextHexString));
			}
			// write the chains created in the chain table file
			TMT1.writeEachChainToTable(chainTableFilePath, linkOne, randomLinkVal);
		}
	}

	public static void writeEachChainToTable(String chainTableFilePath, int linkOne, int randomLinkVal)
			throws IOException {
		// create the path for chain table file
		Path chainTablePath = Paths.get(chainTableFilePath);
		// get the content to write in file as Files accepts lists or arrays only
		List<String> eachChain = Arrays.asList(linkOne + "," + randomLinkVal);
		// write each chain to chain table file with append option enabled
		Files.write(chainTablePath, eachChain, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
	}
}
