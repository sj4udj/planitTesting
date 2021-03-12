package Planit;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.testng.annotations.Test;

public class utilityReader {  
	Properties pro;

	public utilityReader() throws Exception {
		try {
			File file = new File("C:\\Users\\ssc\\Documents\\Srinu\\Eclipse_Project_Files\\Planit\\src\\Planit\\ObjectRepo.property");
			FileInputStream fis = new FileInputStream(file);
			pro = new Properties();  
			pro.load(fis);
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
	}

	public String chromPath() {
		return pro.getProperty("ChromeDriver");
	}

	public String urlPath() {
		return pro.getProperty("Url");
	}
	
	public String userXpath() {
		return pro.getProperty("UsernameXpath");
	}
	
	public String passXpath() {
		return pro.getProperty("PasswordXapth");
	}

	public String userName() {
		return pro.getProperty("Username");
	}

	public String password() {
		return pro.getProperty("Password");
	}
}
