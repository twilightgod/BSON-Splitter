package inlocomedia.test.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mongodb.DBCollection;
import com.mongodb.LazyDBDecoder;
import com.mongodb.LazyDBObject;

public class BSONCounter {



	public static long numberOfObjects(String bsonFilePath) throws IOException {
		long numberOfObjects = 0;
		
		InputStream inputStream = new FileInputStream(bsonFilePath); 

		LazyDBDecoder bsonDecoder = new LazyDBDecoder(); 
		DBCollection nullDBCollection = (DBCollection) null;

		@SuppressWarnings("unused")
		LazyDBObject lazyDBObject;
		
		while(inputStream.available() > 0) {
			// Creating lazy bson object and getting bytes size
			lazyDBObject = (LazyDBObject) bsonDecoder.decode(inputStream, nullDBCollection); 

			// Number of objects written
			numberOfObjects++;
		} 
		
		return numberOfObjects;
	}

}
