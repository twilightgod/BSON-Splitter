package inlocomedia.test;

import static org.junit.Assert.*;
import inlocomedia.BSONSplitter;
import inlocomedia.test.helper.BSONCounter;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class BSONSliptterTest {
	public static String TEST_FILE_PATH = "bin/inlocomedia/test/";
	public static String TEST_FILE_NAME = "splitted";
	public static String BSON_FILE_WITH_2MB = "backup.bson";
	
	@Test
	public void shouldSplitBSON() throws IOException {
		BSONSplitter.splitBsonFile(BSON_FILE_WITH_2MB, 1, TEST_FILE_PATH, TEST_FILE_NAME);
		
		// BSON of 2 MB when splitted sholud generate 2(two) splitted files
		File splittedFile = new File(TEST_FILE_PATH + TEST_FILE_NAME + ".1.bson");
		File splittedFile2 = new File(TEST_FILE_PATH + TEST_FILE_NAME + ".2.bson");
		
		assertEquals(splittedFile.exists(), true);
		assertEquals(splittedFile2.exists(), true);
	}
	
	@Test
	public void splittedFilesShouldHaveSameNumberOfObjects() throws IOException {
		long totalNumberOfObjects = BSONCounter.numberOfObjects(BSON_FILE_WITH_2MB);
		
		BSONSplitter.splitBsonFile(BSON_FILE_WITH_2MB, 1, TEST_FILE_PATH, TEST_FILE_NAME);
		
		// BSON of 2 MB when splitted sholud generate 2(two) splitted files
		String splittedFile = TEST_FILE_PATH + TEST_FILE_NAME + ".1.bson";
		String splittedFile2 = TEST_FILE_PATH + TEST_FILE_NAME + ".2.bson";
		
		long splittedFileNumberOfObjects = BSONCounter.numberOfObjects(splittedFile);
		long splittedFile2NumberOfObjecs = BSONCounter.numberOfObjects(splittedFile2);
		
		assertEquals((splittedFile2NumberOfObjecs + splittedFileNumberOfObjects), totalNumberOfObjects);
	}
	
	@Test
	public void splittedFileShouldHaveSameNumberOfObjectsWhenIsSmall() throws IOException {
		long totalNumberOfObjects = BSONCounter.numberOfObjects(BSON_FILE_WITH_2MB);
		
		// Split size is bigger than file 10MB > 2MB
		BSONSplitter.splitBsonFile(BSON_FILE_WITH_2MB, 10, TEST_FILE_PATH, TEST_FILE_NAME);
		
		// Should generate 1(one) splitted file
		String splittedFile = TEST_FILE_PATH + TEST_FILE_NAME + ".1.bson";
		
		long splittedFileNumberOfObjects = BSONCounter.numberOfObjects(splittedFile);
		
		// Same number of Objects
		assertEquals(splittedFileNumberOfObjects, totalNumberOfObjects);
	}
	
}
