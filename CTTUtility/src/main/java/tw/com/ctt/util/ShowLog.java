package tw.com.ctt.util;

import org.apache.logging.log4j.Logger;

public class ShowLog {

	public ShowLog() {

	}

	public static void err(Logger log, Exception e) {
		log.error(e.getMessage());
		StringBuilder errSB = new StringBuilder();
		for (StackTraceElement ste : e.getStackTrace()) {
			errSB.append("\n" + ste.toString());
			ste = null;
		}
		log.error(errSB.toString() + "\n");
		errSB.setLength(0);
		errSB = null;
	}

}
