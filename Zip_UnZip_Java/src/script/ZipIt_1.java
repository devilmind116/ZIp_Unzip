package script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



public class ZipIt_1 {
	
	static boolean flag = true;
	
    public static void main(String[] args) throws IOException {
        String sourceFile = "A:\\ProgrammingJAR\\cloudera-quickstart-vm-5.4.2-0-virtualbox"; // source Path
        
        FileOutputStream fos = new FileOutputStream("A:\\ProgrammingJARCompressed_Logs " + today() + ".zip"); // Target Path
        
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
        
        retention(fileToZip);
        
        
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        try {
        	
        	if (fileToZip.isHidden()) {
        		
        		System.out.println(fileToZip.getName() + " can't be accessed as it is marked as hidden.");
                return;
            }
        	
            if (fileToZip.isDirectory()) {
            	
                if (fileName.endsWith("/")) {
                	
                    zipOut.putNextEntry(new ZipEntry(fileName));
                    zipOut.closeEntry();
                } 
                else {
                	
                    zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                    zipOut.closeEntry();
                }
                
                File[] children = fileToZip.listFiles();
                for (File childFile : children) {
                	
                    zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
                    
                }
                
                return;
                
            }
            
            FileInputStream fis = new FileInputStream(fileToZip);
            
            ZipEntry zipEntry = new ZipEntry(fileName);
            
            zipOut.putNextEntry(zipEntry);
            
            byte[] bytes = new byte[1024];
            
            int length;
            
            while ((length = fis.read(bytes)) >= 0) {
            	
                zipOut.write(bytes, 0, length);
                
            }
            
            alert(fileToZip.getName() + "has been successfully zipped on "+today()+ "\n" );
            fileToZip.delete();
            fis.close();
            
        }catch (Exception e) {
        	
        	System.out.println("Error occured in zipping the file "+fileToZip.getName()+ "on today \n");
        	e.printStackTrace();
        	flag = false;
        	
        }
    }
    
    private static void retention(File fileToZip) {
    	
    	File[] files = fileToZip.listFiles();
    	
    	for (File file : files) {
			
			long diff = new Date().getTime() - file.lastModified();

			if (diff > 7 * 24 * 60 * 60 * 1000) {
			    file.delete();
			}
		}
    }
    
    private static void alert(String msg) throws IOException {
    	FileWriter myWriter = new FileWriter("A:\\ProgrammingJAR\\cloudera-quickstart-vm-5.4.2-0-virtualbox\\Zipping logs.txt", true);
    	myWriter.write(msg);
        myWriter.close();
    }
    
    public static String today() {
		LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM_dd_yyyy");
        String date = dateObj.format(formatter);
        return date;
	}

}