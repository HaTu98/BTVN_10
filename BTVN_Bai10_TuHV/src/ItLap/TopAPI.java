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

public class TopAPI {
	private String path;
	public BufferedReader br = null;
	public FileReader fr = null;
	public Map<String, Integer> apiMap = new HashMap<String, Integer>();

	public TopAPI() {
		
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void getAPI() {
		apiMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
				.forEach(System.out::println);
	}

	public void writeAPI(String path) {
		Map<String, Integer> result = apiMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		FileWriter fileWriter = null;
		try {
			int count = 1;
			fileWriter = new FileWriter(path);
			fileWriter.append("STT,API,Count\n".toString());
			for (String i : result.keySet()) {
				fileWriter.append((count + "," + i + "," + result.get(i) + "\n").toString());
				count++;
				if (count >= 6) {
					break;
				}
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
		String key;
		int value;
		String data = "";
		if(str.split(" ").length > 6) {
			data = str.split(" ")[6];
			data = data.replace("?", " ");
			data = data.split(" ")[0].substring(1);
			if (data.length() > 8) {
				if (data.substring(0, 8).equals("undefined")) {
					data = data.substring(10);
				}
			}
		}
		if (data.length() > 2) {
			if (data.substring(0, 3).equals("api")) {
				String[] arr = data.split("/");
				if (arr.length > 1) {
					arr[1] = arr[1].replace(".", "").replace(")", "");
					key = "/" + arr[0] + "/" + arr[1];
					if (apiMap.containsKey(key)) {
						value = apiMap.get(key);
						apiMap.remove(key);
						apiMap.put(key, value + 1);
					} else {
						apiMap.put(key, 1);
					}
				}
			}
		}
	}
}
