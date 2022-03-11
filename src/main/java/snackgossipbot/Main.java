package snackgossipbot;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

	private final static Logger LOG = LogManager.getLogger(Main.class);
	public static boolean IS_DEV = false;
	public static String BOT_USERNAME;
	public static String BOT_API_KEY;

	public static void main(String[] args) throws ParseException {
		parseArgs(args);
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(new SnackGossipBot());
		} catch (TelegramApiException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public static void parseArgs(String[] args) throws ParseException {
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		options.addOption(Option.builder("n").hasArg()
				.required(true)
				.desc("The username of the bot")
				.build());

		options.addOption(Option.builder("a").hasArg()
				.required(true)
				.desc("The api key of the bot")
				.build());

		options.addOption(Option.builder("d")
				.required(false)
				.desc("is dev mode")
				.build());

		CommandLine commandLine = parser.parse(options, args);
		BOT_USERNAME = commandLine.getOptionValue("n");
		BOT_API_KEY = commandLine.getOptionValue("a");
		if (commandLine.hasOption("d")) {
			IS_DEV = true;
		}

	}
}