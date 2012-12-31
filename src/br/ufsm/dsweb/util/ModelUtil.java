package br.ufsm.dsweb.util;

import br.ufsm.dsweb.model.Model;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

public class ModelUtil {
	private static String USER_FILENAME = "users.csv";
	private static String TWEET_FILENAME = "tweets.csv";
	
	public static String filenameFromModel(Model submodel) {
		String filename = "";
		if(submodel instanceof User) {
			filename = USER_FILENAME;
		} else if(submodel instanceof Tweet) {
			filename = TWEET_FILENAME;
		}
		return filename;
	}
}
