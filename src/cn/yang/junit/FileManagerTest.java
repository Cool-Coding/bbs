package cn.yang.junit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.yang.util.FileManager;

public class FileManagerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testCopy() {
		File source=new File("D:"+File.separator+"test");
		File destination=new File("J:"+File.separator+"abc\\a.txt");
		try {
			FileManager.copy(source,destination);
		} catch (IOException e) {
		}
	}

	@Test
	public void testDelete() {
		File source=new File("D:"+File.separator+"test");
		FileManager.delete(source);
	}

	@Test
	public void testMove() throws IOException {
		File source=new File("D:"+File.separator+"test");
		File destination=new File("J:"+File.separator+"abc");
		
	    FileManager.move(source, destination);
	}

}
