package ItLap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.Comparator;

public class IpAdress {
	private String path;
	public BufferedReader br = null;
	public FileReader fr = null;
	public Map<String, Integer> ipMap = new HashMap<String, Integer>();

	public IpAdress() {

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void getIp() {
		ipMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
				.forEach(System.out::println);
	}

	public void writeIp(String path) {
		Map<String, Integer> result = ipMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(path);
			fileWriter.append("IP,Count\n".toString());
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

	public void readData(String str) {
		String key = str.split(" ")[0];
		int value;
		if (ipMap.containsKey(key)) {
			value = ipMap.get(key);
			ipMap.remove(key);
			ipMap.put(key, value + 1);
		} else {
			ipMap.put(key, 1);
		}
	}
}
