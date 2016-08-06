package com.github.pirent;

import static com.github.pirent.xmpp.XMPPAuctionHouse.LOG_FILE_NAME;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.logging.LogManager;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matcher;

public class AuctionLogDriver {

	private final File logFile = new File(LOG_FILE_NAME);
	
	@PostConstruct
	public void initFile() throws IOException {
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
	}
	
	public void hasEntry(Matcher<String> matcher) throws IOException {
		assertThat(FileUtils.readFileToString(logFile), matcher);
	}
	
	public void clearLog() {
		logFile.delete();
		LogManager.getLogManager().reset();
	}
}
