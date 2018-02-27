package FrameViews;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Utils {
	
	private Utils() {}
	
	public static final  String OUTPUT_DIR_CONF_PREFIX = "OUTPUT_DIR=";
	public static final  String CONF_DIR_PATH = "C:\\UnzipS\\conf";
	public static final  String CONF_FILE = "conf.txt";
	public static final  String newline = "\n";
	private static final int BUFFER_SIZE = 4096;
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy-HH.mm.ss");
	
	/**
	 * 
	 * @param zipFilePath
	 * @param outputDir
	 * @throws IOException 
	 */
	public static void unzipFile(String zipFilePath, String outputDir) throws IOException{
		
		BufferedOutputStream out;
		InputStream in;
		ZipEntry entry;
		ZipFile zipFile;
		Enumeration entries;
		zipFile = new ZipFile(zipFilePath);
		ZipFile tmp = zipFile;
		entries = zipFile.entries();
		byte[] buffer = new byte[1024];
		if (zipFile.size() != 0) {

			// Create parent directory if not exists
			File directory = new File(String.valueOf(outputDir));
			if (!directory.exists()) {
				directory.mkdir();
			}

			// creo le directory
			while (entries.hasMoreElements()) {
				entry = (ZipEntry) entries.nextElement();
				if (entry.isDirectory()) {
					(new File(outputDir + File.separator + entry.getName())).mkdir();
				}
			}
			// estraggo i file
			entries = tmp.entries();
			while (entries.hasMoreElements()) {
				entry = (ZipEntry) entries.nextElement();
				if (!entry.isDirectory()) {
					in = zipFile.getInputStream(entry);
					out = new BufferedOutputStream(new FileOutputStream(outputDir + File.separator + entry.getName()));
					int len;
					while ((len = in.read(buffer)) >= 0) {
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
				}
			}
			zipFile.close();
		}
	}
	
	/**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
	
	/**
	 * 
	 * @param confFile
	 * @return
	 * @throws IOException
	 */
	public static String readOutputDirFromConfFile() throws IOException{
		String path = "";
		BufferedReader br = new BufferedReader(new FileReader(CONF_DIR_PATH+File.separator+CONF_FILE));
		try {
		    String line = br.readLine();

		    while (line != null) {
		        if(line.startsWith(OUTPUT_DIR_CONF_PREFIX)){
		        	return line.substring(line.indexOf("=")+1, line.length());
		        } 	
		    }
		} finally {
		    br.close();
		}
		return path;
	}
	
	public static void updateConfFile(String inputString) throws IOException{
		BufferedWriter br = new BufferedWriter(new FileWriter(CONF_DIR_PATH+File.separator+CONF_FILE));
		try {
		    br.write(inputString);
		} finally {
		    br.close();
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getConfFileBody() throws IOException {
		File file = new File(Utils.CONF_DIR_PATH + File.separator + Utils.CONF_FILE);

		if (file.exists()) {
			StringBuffer sb = new StringBuffer();
			BufferedReader br = new BufferedReader(new FileReader(CONF_DIR_PATH + File.separator + CONF_FILE));
			try {
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					line = br.readLine();
				}
			} finally {
				br.close();
			}
			return sb.toString();
		}
		return "";
	}

	/**
	 * 
	 * @param className
	 * @param path
	 * @return
	 */
	public static ImageIcon createImageIcon(Class className, String path) {
        java.net.URL imgURL = className.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

	public static String getTimestamp() {
		return sdf.format(new Date());
	}
	

  

}
