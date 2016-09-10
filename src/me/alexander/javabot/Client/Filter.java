package me.alexander.javabot.Client;

import java.util.ArrayList;

public class Filter {

	private static ArrayList<String> words() {
		ArrayList<String> badWords = new ArrayList<>();

		badWords.add("fuck");
		badWords.add("wtf");
		badWords.add("whore");
		badWords.add("idiot");
		badWords.add("dick");

		badWords.add("pussy");
		badWords.add("douchebag");
		badWords.add("scumbag");
		badWords.add("cum");
		badWords.add("omfg");
		badWords.add("porn");
		badWords.add("fucker");

		badWords.add("cunt");
		badWords.add("balls");
		badWords.add("cumshot");

		badWords.add("fucked");
		badWords.add("escort");
		badWords.add("fucking");
		badWords.add("kys");
		badWords.add("faggot");
		badWords.add("faggots");
		badWords.add("stfu");
		badWords.add("prick");
		badWords.add("faggit");
		badWords.add("tits");
		badWords.add("tit");
		badWords.add("gtfo");
		badWords.add("retard");
		badWords.add("prostitute");

		badWords.add("ass");
		badWords.add("asslord");
		badWords.add("buttocks");

		badWords.add("nigger");
		badWords.add("nigga");
		badWords.add("niggas");

		badWords.add("dumbfuck");
		badWords.add("vagina");
		badWords.add("penis");
		badWords.add("sux");
		badWords.add("sucks");
		badWords.add("suck");

		badWords.add("skidded");

		badWords.add("niggers");
		badWords.add("nigger");
		badWords.add("nigga");

		badWords.add("heil");
		badWords.add("hitler");

		// Clients

		badWords.add("wurst");
		badWords.add("huzuni");
		badWords.add("impact");
		badWords.add("kronik");
		badWords.add("matix");
		badWords.add("nodus");

		badWords.add("wolfram");
		badWords.add("reflex");

		return badWords;
	}

	public static boolean filter(String msg) {
		if (words().contains(msg.toLowerCase())) {
			return true;
		}
		return false;
	}

}
