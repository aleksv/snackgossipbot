package snackgossipbot;

import java.util.Arrays;
import java.util.List;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SnackGossipBot extends TelegramLongPollingBot {

	private int cash = 0;

	@Override
	public void onUpdateReceived(Update update) {
		try {
			String text = update.getMessage().getText();
			System.out.println(text);
			if (text.startsWith("/")) {
				text = text.substring(1);
			} else {
				return;
			}
			text = text.trim().toLowerCase();
			List<String> args = Arrays.asList(text.split(" "));

			if (args.size() == 3) {
				if ("cash".equals(args.get(0))) {
					if ("add".equals(args.get(1))) {
						cash += Integer.parseInt(args.get(2));
					} else if ("remove".equals(args.get(1))) {
						cash -= Integer.parseInt(args.get(2));
					}
				}
			}

			SendMessage msg = new SendMessage(update.getMessage().getChatId() + "", "<pre>Cash: " + cash + "</pre>");
			msg.setParseMode("HTML");

			execute(msg);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotUsername() {
		return Main.BOT_USERNAME;
	}

	@Override
	public String getBotToken() {
		return Main.BOT_API_KEY;
	}

}
