package com.azhar.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class TaskManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TaskManagementServiceApplication.class);
		String classpath = System.getProperty("java.class.path").toLowerCase();
		boolean isIntelliJ = classpath.contains("idea_rt.jar");
		boolean isEclipse = classpath.contains("org.eclipse.equinox.launcher");
		boolean isVSCode = System.getenv("TERM_PROGRAM") != null
				&& System.getenv("TERM_PROGRAM").equalsIgnoreCase("vscode")
				|| classpath.contains("vscode");  // Often in paths for extensions

		// Set profile based on detection
		if (isIntelliJ || isEclipse || isVSCode) {
			app.setAdditionalProfiles("local");  // IntelliJ uses "local"
		}
		app.run(args);
	}

}
