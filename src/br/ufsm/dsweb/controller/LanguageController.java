package br.ufsm.dsweb.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ufsm.dsweb.language.EnglishLanguage;
import br.ufsm.dsweb.language.LanguageInterface;
import br.ufsm.dsweb.language.PortugueseLanguage;

@ManagedBean(name="languageController")
@SessionScoped
public class LanguageController implements Serializable {
	private LanguageInterface mCurrentLanguage;
	private String mLanguageName;

	public LanguageController() {
		mCurrentLanguage = new EnglishLanguage();
		mLanguageName = mCurrentLanguage.getLanguageName();
	}
	
	public LanguageInterface getCurrentLanguage() {
		return mCurrentLanguage;
	}

	public void setCurrentLanguage(LanguageInterface mCurrentLanguage) {
		this.mCurrentLanguage = mCurrentLanguage;
	}
	
	public ArrayList<String> getLanguages() {
		ArrayList<String> languages = new ArrayList<String>();
		languages.add(new EnglishLanguage().getLanguageName());
		languages.add(new PortugueseLanguage().getLanguageName());
		
		return languages;
	}
	
	public void changeLanguage() {
		if(mLanguageName.equals(new EnglishLanguage().getLanguageName())) {
			mCurrentLanguage = new EnglishLanguage();
		} else if(mLanguageName.equals(new PortugueseLanguage().getLanguageName())) {
			mCurrentLanguage = new PortugueseLanguage();
		}
	}

	public String getLanguageName() {
		return mLanguageName;
	}

	public void setLanguageName(String mLanguageName) {
		this.mLanguageName = mLanguageName;
	}
}
