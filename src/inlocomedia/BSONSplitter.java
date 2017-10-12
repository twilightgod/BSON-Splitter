package inlocomedia;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mongodb.DBCollection;
import com.mongodb.LazyDBDecoder;
import com.mongodb.LazyDBObject;

public class BSONSplitter {
	private static final String DEFAULT_OUTPUT_PATH = "./";
	private static final String DEFAULT_OUTPUT_FILENAME = "splitted";
	private static String DEFALUT_FILE = "backup.bson";
	private static int DEFAULT_SPLITTED_BSON_SIZE_IN_MB = 250;

	private static String splittedBsonFilePath(String outputPath,
			String outputFileName, int numberOfSplittedFiles) {
		return outputPath + "/" + outputFileName + "." + numberOfSplittedFiles + ".bson";
	}
	
	public static void splitBsonFile(String bsonFilePath, int maxSplittedSizeInMB, String outputPath, String outputFileName) throws IOException {
		long maxSplittedSize = (long)maxSplittedSizeInMB * 1024 * 1024;
		long numberOfObjects = 0;
		int numberOfSplittedFiles = 1; 
		long bytesWrittenForSplittedFile = 0;

		InputStream inputStream = new FileInputStream(bsonFilePath); 
		OutputStream outputStream = new FileOutputStream(splittedBsonFilePath(outputPath, outputFileName, numberOfSplittedFiles));

		LazyDBDecoder bsonDecoder = new LazyDBDecoder(); 
		LazyDBObject lazyDBObject; 
		DBCollection nullDBCollection = (DBCollection) null;

		while(inputStream.available() > 0) {
			// Creating lazy bson object and getting bytes size
			lazyDBObject = (LazyDBObject) bsonDecoder.decode(inputStream, nullDBCollection); 
			bytesWrittenForSplittedFile += lazyDBObject.pipe(outputStream);

			// Number of objects written
			numberOfObjects++;

			if (bytesWrittenForSplittedFile > maxSplittedSize) {
				numberOfSplittedFiles++; 

				// Flush, close and creating a new file
				outputStream.flush();
				outputStream.close(); 
				outputStream = new FileOutputStream(splittedBsonFilePath(outputPath, outputFileName, numberOfSplittedFiles));

				// Reset bytes written
				bytesWrittenForSplittedFile = 0; 
			} 
		} 

		if (outputStream != null) {
			outputStream.flush();
			outputStream.close(); 
		}

		System.out.println(numberOfObjects + " objects found");
		System.out.println(numberOfSplittedFiles + " splitted files");
	}
	
	public static void main(String args[]) throws Exception {
		String filePath = DEFALUT_FILE;
		String outputPath = DEFAULT_OUTPUT_PATH;
		String outputFileName = DEFAULT_OUTPUT_FILENAME;
		
		int splittedBSONSizeInMB = DEFAULT_SPLITTED_BSON_SIZE_IN_MB;

		// Get file path and splitted sive from args
		if(args.length > 0) {
			filePath = args[0];
		} 
		
		if(args.length > 1) {
			splittedBSONSizeInMB = Integer.parseInt(args[1]);
		}
		
		if(args.length > 2) {
			outputPath = args[2];
		}
		
		if(args.length > 3) {
			outputFileName = args[3];
		}

		splitBsonFile(filePath, splittedBSONSizeInMB, outputPath, outputFileName);
	} 

}
