package inlocomedia.test;

import static org.junit.Assert.assertEquals;
import inlocomedia.BSONSplitter;
import inlocomedia.test.helper.BSONCounter;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class BSONSliptterTest {
	public static String TEST_FILE_NAME = "splitted";
	public static String BSON_FILE_WITH_2MB = "backup.bson";
	
	@Rule
    public TemporaryFolder tempFolderGeneration = new TemporaryFolder();

	@Test
	public void shouldSplitBSON() throws IOException {
		// Temporary folder
		File tempFolder = tempFolderGeneration.newFolder("test");

		// Runnig split BSON
		BSONSplitter.splitBsonFile(BSON_FILE_WITH_2MB, 1, tempFolder.getAbsolutePath(), TEST_FILE_NAME);

		// BSON of 2 MB when splitted sholud generate 2(two) splitted files
		File splittedFile = new File(tempFolder.getAbsolutePath() + "/" + TEST_FILE_NAME + ".1.bson");
		File splittedFile2 = new File(tempFolder.getAbsolutePath() + "/" + TEST_FILE_NAME + ".2.bson");

		// Asserting that files was created
		assertEquals(splittedFile.exists(), true);
		assertEquals(splittedFile2.exists(), true);
	}

	@Test
	public void splittedFilesShouldHaveSameNumberOfObjects() throws IOException {
		// Temporary folder
		File tempFolder = tempFolderGeneration.newFolder("test");
		
		long totalNumberOfObjects = BSONCounter.numberOfObjects(BSON_FILE_WITH_2MB);

		BSONSplitter.splitBsonFile(BSON_FILE_WITH_2MB, 1, tempFolder.getAbsolutePath(), TEST_FILE_NAME);

		// BSON of 2 MB when splitted sholud generate 2(two) splitted files
		String splittedFile = tempFolder.getAbsolutePath() + "/"  + TEST_FILE_NAME + ".1.bson";
		String splittedFile2 = tempFolder.getAbsolutePath() + "/" + TEST_FILE_NAME + ".2.bson";

		long splittedFileNumberOfObjects = BSONCounter.numberOfObjects(splittedFile);
		long splittedFile2NumberOfObjecs = BSONCounter.numberOfObjects(splittedFile2);

		assertEquals((splittedFile2NumberOfObjecs + splittedFileNumberOfObjects), totalNumberOfObjects);
	}

	@Test
	public void splittedFileShouldHaveSameNumberOfObjectsWhenIsSmall() throws IOException {
		// Temporary folder
		File tempFolder = tempFolderGeneration.newFolder("test");
				
		long totalNumberOfObjects = BSONCounter.numberOfObjects(BSON_FILE_WITH_2MB);

		// Split size is bigger than file 10MB > 2MB
		BSONSplitter.splitBsonFile(BSON_FILE_WITH_2MB, 10, tempFolder.getAbsolutePath(), TEST_FILE_NAME);

		// Should generate 1(one) splitted file
		String splittedFile = tempFolder.getAbsolutePath() + "/" + TEST_FILE_NAME + ".1.bson";

		long splittedFileNumberOfObjects = BSONCounter.numberOfObjects(splittedFile);

		// Same number of Objects
		assertEquals(splittedFileNumberOfObjects, totalNumberOfObjects);
	}

}
