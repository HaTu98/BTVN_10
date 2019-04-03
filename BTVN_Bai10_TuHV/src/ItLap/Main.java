package ItLap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		Instant start = Instant.now();
		Date t = new Date();
		System.out.println(t);
		BufferedReader br = null;
		FileReader fr = null;

		String path = "C:\\Users\\Ha Tu\\Desktop\\BaiTapITLap\\Buổi 10_HuyNQ_29-03-2019\\";
		String pathEx1 = "C:\\Java\\BTVN_Bai10_TuHV1\\BTVN_Bai10_TuHV\\src\\IpList.csv";
		String pathEx2 = "C:\\Java\\BTVN_Bai10_TuHV1\\BTVN_Bai10_TuHV\\src\\Browser.csv";
		String pathEx3 = "C:\\Java\\BTVN_Bai10_TuHV1\\BTVN_Bai10_TuHV\\src\\peakTimes.csv";
		String pathEx4 = "C:\\Java\\BTVN_Bai10_TuHV1\\BTVN_Bai10_TuHV\\src\\TopAPI.csv";
		
		IpAdress ip = new IpAdress();
		Browser browser = new Browser();
		PeakTimes peakTime = new PeakTimes();
		TopAPI topAPI = new TopAPI();

		try {
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();
			
			for (File file : listOfFiles) {
			    if (file.isFile()) {
			    	String str;
					fr = new FileReader(path+file.getName());
					br = new BufferedReader(fr);
					while ((str = br.readLine()) != null) {
						ip.readData(str);
						browser.readData(str);
						peakTime.readData(str);
						topAPI.readData(str);
					}
			    }
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ip.writeIp(pathEx1);

		browser.wirteBrowser(pathEx2);
		
		peakTime.writePeakTime(pathEx3);
		
		topAPI.writeAPI(pathEx4);
				
		Date t1 = new Date();
		System.out.println(t1);
		Instant end = Instant.now();
		System.out.println(Duration.between(start, end).toMillis());
	}
}
