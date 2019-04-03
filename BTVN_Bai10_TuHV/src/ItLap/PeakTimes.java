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

public class PeakTimes {
	private String path;
	public BufferedReader br = null;
	public FileReader fr = null;
	public Map<String, Integer> peakMap = new HashMap<String, Integer>();

	public PeakTimes() {

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void getTime() {
		peakMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
				.forEach(System.out::println);
	}
	
	public void writePeakTime(String path) {
		Map<String, Integer> peakTime = new HashMap<String, Integer>();
		Map<String, String> data = new HashMap<String, String>();
		for(String oldKey : peakMap.keySet()) {
			String key = oldKey.substring(0,11);

			int oldValue = peakMap.get(oldKey);
			if(peakTime.containsKey(key)) {
				int value = peakTime.get(key);
				if(value < peakMap.get(oldKey)) {
					String time = oldKey.substring(12,14)+ "," + peakMap.get(oldKey);
					data.remove(key);
					data.put(key,time);
					peakTime.remove(key);
					peakTime.put(key, oldValue);
				}
			} else {
				String time = oldKey.substring(12,14)+ "," + peakMap.get(oldKey);
				data.put(key,time);
				peakTime.put(key, oldValue);
			}
		}
		
		Map<String, String> result = data.entrySet().stream()
				.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(path);
			fileWriter.append("Date,Time,Count\n".toString());
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
		if(str.split(" ").length > 3){
			String key = str.split(" ")[3];
			key = key.substring(1, 15);
			int value;
			if (peakMap.containsKey(key)) {
				value = peakMap.get(key);
				peakMap.remove(key);
				peakMap.put(key, value + 1);
			} else {
				peakMap.put(key, 1);
			}
		}
	}
}
