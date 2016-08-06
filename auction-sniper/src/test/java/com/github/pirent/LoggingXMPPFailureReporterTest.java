package com.github.pirent;

import static org.mockito.Mockito.verify;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.pirent.xmpp.LoggingXMPPFailureReporter;

@RunWith(MockitoJUnitRunner.class)
public class LoggingXMPPFailureReporterTest {

	@Mock
	private Logger logger;
	
	private LoggingXMPPFailureReporter reporter;
	
	@Before
	public void init() {
		reporter = new LoggingXMPPFailureReporter(logger);
	}
	
	@Test
	public void writesMessageTranslationFailureToLog() {
		reporter.cannotTranslateMessage("auction id", "bad message",
				new Exception("bad"));
		
		verify(logger).severe(
				"<auction id> Could not translate message \"bad message\" "
						+ "because \"java.lang.Exception: bad\"");
	}
	
	@AfterClass
	public static void resetLogging() {
		LogManager.getLogManager().reset();
	}
}
