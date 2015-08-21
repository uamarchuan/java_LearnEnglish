package ua.learnenglish.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class CheckVersion {
	
	public void getVersion(){
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL("https://github.com/uamarchuan/java_LearnEnglish/blob/master/LearnEnglish/version.txt").openStream()))) {
		  String fullVersion = br.readLine();
		  String version = fullVersion.split("_")[0];
		  String revision = fullVersion.split("_")[1];
		  System.out.println("Version " + version + " revision " + revision);
		} catch (IOException e) {
			System.out.println("Біда!");
		}
	}
}

//Last available java version: 
//Version 7 Update 9
//
//Currently installed java version: 1.7.0_07