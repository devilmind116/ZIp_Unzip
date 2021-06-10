package script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipIt {
	
	static List<String> allFiles = new ArrayList<String>();
	
	static SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
	
	public static void main(String[] args) {
		
		zipping();
		
		/*
		 * if(flag) { deletion(); }
		 */
		
		/* retention(); */
		
	}

	private static void retention() {

		File dir = new File("A:/Resume"); /*ZipFile Source path*/
		
		ZipIt zipIt = new ZipIt();
		
		zipIt.listFiles(dir);
		
		
		for (String path : allFiles) {
			
			File ipfile = new File(path);
			
			long diff = new Date().getTime() - ipfile.lastModified();

			if (diff > 7 * 24 * 60 * 60 * 1000) {
			    ipfile.delete();
			}
		}
		
	}

	private static void deletion() {
		
		File dir = new File("A:/Resume"); /*Source path*/
		
		ZipIt zipIt = new ZipIt();
		
		zipIt.listFiles(dir);
		
		
		for (String path : allFiles) {
			
			File ipfile = new File(path);
			
	        if(ipfile.delete()){
	        	
	            System.out.println(ipfile.getAbsolutePath() + " File deleted from Project root directory");
	            
	        }
	        else System.out.println(ipfile.getAbsolutePath() + " doesn't exist in the project root directory");
			
		}
		
	}

	private static boolean zipping() {
		
		try{
			File dir = new File("A:/Resume"); /*Source path*/
			
			ZipIt zipIt = new ZipIt();
			
			zipIt.listFiles(dir);
			
			File zipFile = new File("A:/Resume"/* + sdf.format(new Date())*/); /*Target path with zipFile name*/
		
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
			
			byte[] buffer = new byte[1024];
			int len;
			
			for (String path : allFiles) {
				File ipfile = new File(path);
				
				
				String zippath = path.substring(dir.getAbsolutePath().length() + 1, path.length());
				
				ZipEntry zen = new ZipEntry(zippath);
				
				zos.putNextEntry(zen);
				
				FileInputStream fis = new FileInputStream(ipfile);
				
				while ((len = fis.read(buffer)) > 0) {
                  zos.write(buffer, 0, len);
                }
				
				//close all IO streams. Or else we may get corrupted zip files
				zos.closeEntry();
				fis.close();
				
				System.out.println(ipfile.getAbsolutePath()+"is zipped");
			}
			
			zos.close();
		}
		catch(Exception e) {
		
			System.out.println("Error in Zipping the file:");
			e.printStackTrace();
			return false;
			
		}
		
		return true;
	}

	private void listFiles(File dir) {
		
		File[] files = dir.listFiles();
		
		for (File file : files) {
			
				allFiles.add(file.getAbsolutePath());
				
		}
		
	}
}
