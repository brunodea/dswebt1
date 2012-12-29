package br.ufsm.dsweb.db;

import br.ufsm.dsweb.model.Model;
import br.ufsm.dsweb.model.Model.ModelType;
import br.ufsm.dsweb.model.Tweet;
import br.ufsm.dsweb.model.User;

public class DBActions {
	private static String USER_FILENAME = "users.csv";
	private static String TWEET_FILENAME = "tweets.csv";
	
	private static int getLastID(String filename) {
		int last_id = -1;
		
		String last_row = DBCore.getLastRow(filename);
		if(!last_row.equals("")) {
			String[] vals = last_row.split(",");
			last_id = Integer.parseInt(vals[0]);
		}
		
		return last_id;
	}
	
	private static String filenameForModelType(ModelType modeltype) {
		String filename = "";
		switch(modeltype) {
		case USER:
			filename = USER_FILENAME;
			break;
		case TWEET:
			filename = TWEET_FILENAME;
			break;
		default:
			break;
		}
		
		return filename;
	}
	
	private static Model modelFromModelType(ModelType modeltype) {
		Model model = null;
		switch(modeltype) {
		case USER:
			model = new User();
			break;
		case TWEET:
			model = new Tweet();
			break;
		default:
			break;
		}
		
		return model;
	}
	
	private static Model getModelByID(ModelType modeltype, int model_id) {
		Model model = modelFromModelType(modeltype);
		String csv = DBCore.getRowByCol(filenameForModelType(modeltype), 0, String.valueOf(model_id));
		if(!csv.equals("")) {
			model.fromCSV(csv);
		}
		return model;
	}
	
	public static void saveModel(Model model) {
		String filename = filenameForModelType(model.getType());
		model.setID(getLastID(filename)+1);
		DBCore.appendToFile(filename, model.toCSV());
	}
	
	public static User getUserByID(int user_id) {
		return (User) getModelByID(ModelType.USER, user_id);
	}
	public static Tweet getTweetByID(int tweet_id) {
		return (Tweet) getModelByID(ModelType.TWEET, tweet_id);
	}
	
	public static boolean usernameExists(String username) {
		String r = DBCore.getRowByCol(filenameForModelType(ModelType.USER), 2, username); 
		return r != null && !r.equals("");
	}
}
