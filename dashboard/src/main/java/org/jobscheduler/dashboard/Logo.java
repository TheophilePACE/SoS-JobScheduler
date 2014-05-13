/**
 * Copyright (C) 2014 BigLoupe http://bigloupe.github.io/SoS-JobScheduler/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.jobscheduler.dashboard;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.fusesource.jansi.AnsiConsole;
import org.springframework.core.io.ClassPathResource;

public  class Logo {

	public  void createAndDisplayLogo() throws IOException {
		int width = 100;
		int height = 140;
		
		ClassPathResource resource = new ClassPathResource("logo-dashboard-job-scheduler.txt");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(resource.getInputStream()));
		String line = null;
		StringBuilder logo = new StringBuilder();
		while((line = in.readLine()) != null) {
		    logo.append(line).append("\n");
		}
		System.out.println(logo);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("SansSerif", Font.BOLD, 12));
	 
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString("JOB SCHED", 10, 20);
		for (int y = 0; y < height; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < width; x++) {
	 
				sb.append(image.getRGB(x, y) == -16777216 ? "  " : "$");
	 
			}
	 
			if (sb.toString().trim().isEmpty()) {
				continue;
			}
	 
			System.out.println(sb);
		}
	}
	
	public static void main(String[] args) throws IOException {
		AnsiConsole.systemInstall();
		Logo logo = new Logo();
		logo.createAndDisplayLogo();
	}
}