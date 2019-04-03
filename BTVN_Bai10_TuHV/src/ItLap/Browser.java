package ItLap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import eu.bitwalker.useragentutils.UserAgent;

public class Browser {
	private String path;
	public BufferedReader br = null;
	public FileReader fr = null;
	public Map<String, Integer> browMap = new HashMap<String, Integer>();
	public UserAgent userAgent;
	public Browser() {
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void getBrower() {
		browMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
				.forEach(System.out::println);
	}
	
	public void wirteBrowser(String path) {
		Map<String, Integer> result = browMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(path);
			fileWriter.append("Browser,Count\n".toString());
			for (String i : result.keySet()) {
				fileWriter.append((i + "," + result.get(i) + "\n").toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}
	}
	
	public String printBrowser(String browser) {
		if(browser.contains("CHROME")) {
			browser = "CHROME";
		} else if(browser.contains("SAFARI")) {
			browser = "SAFARI";
		} else if(browser.contains("FIREFOX")) {
			browser = "FIREFOX";
		} else if(browser.contains("EDGE")) {
			browser = "EDGE";
		} else if(browser.contains("IE")) {
			browser = "IE";
		} else if(browser.contains("MOZILLA")) {
			browser = "MOZILLA";
		} else if(browser.contains("OPERA")) {
			browser = "OPERA";
		}  else if(browser.contains("BOT")) {
			browser = "BOT";
		}
		return browser;
	}
	
	public void readData(String str) {
		String[] data = str.split("\"");
		String key = data[data.length-1];
		userAgent = UserAgent.parseUserAgentString(key);
		key = printBrowser(userAgent.getBrowser().toString());
		
		int value;
		if (browMap.containsKey(key)) {
			value = browMap.get(key);
			browMap.remove(key);
			browMap.put(key, value + 1);
		} else {
			if(key != "UNKNOWN") {
				browMap.put(key, 1);
			}
		}
	}
}
