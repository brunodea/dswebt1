package br.ufsm.dsweb.language;

public interface LanguageInterface {
	@Override
	public String toString();
	public String getLanguageName();
	
	public String getTimelineOf();
	public String getSelectOne();
	public String getLanguage();
	public String getEditProfile();
	public String getFullName();
	public String getUsername();
	public String getPassword();
	public String getDone();
	public String getUnfollow();
	public String getFollow();
	public String getFollowers();
	public String getFollowing();
	public String getUsers();
	public String getLogin();
	public String getSignup();
	public String getWelcome();
	public String getLogout();
	public String getTweet();
	public String getUnretweet();
	public String getRetweet();
	public String getRetweets();
	public String getCharactersRemaining();
	public String getResults();
	public String getSearch();
	
	public String getUserDoesntExist();
	public String getSuccess();
	public String getSuccessTweet();
	public String getInvalidUsername();
	public String getUsernameAlreadyTaken();
	public String getImpossibleToLogin();
	public String getUsernameOrPasswordWrong();
	public String getSuccessChanges();
}
