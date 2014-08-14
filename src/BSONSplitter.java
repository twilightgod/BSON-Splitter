import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mongodb.DBCollection;
import com.mongodb.LazyDBDecoder;
import com.mongodb.LazyDBObject;

public class BSONSplitter {
	
	public static void splitBsonFile(String bsonFilePath, int maxSplittedSizeInMB) throws IOException {
		long maxSplittedSize = maxSplittedSizeInMB * 1024 * 1024;
		long numberOfObjects = 0;
		int numberOfSplittedFiles = 1; 
		long bytesWrittenForSplittedFile = 0;
		
		InputStream inputStream = new FileInputStream(bsonFilePath); 
		OutputStream outputStream = new FileOutputStream("splitted." + numberOfSplittedFiles + ".bson");
		
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
				outputStream = new FileOutputStream("splitted." + numberOfSplittedFiles + ".bson");
				
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
	   splitBsonFile("backup.bson", 150);
	} 

}
