package br.ufsm.dsweb.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufsm.dsweb.language.EnglishLanguage;
import br.ufsm.dsweb.language.LanguageInterface;

@ManagedBean(name="languageController")
@ViewScoped
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
		languages.add(new EnglishLanguage().toString());
		
		return languages;
	}
	
	public void changeLanguage() {
		if(mLanguageName.equals(new EnglishLanguage().toString())) {
			mCurrentLanguage = new EnglishLanguage();
		}
	}

	public String getLanguageName() {
		return mLanguageName;
	}

	public void setLanguageName(String mLanguageName) {
		this.mLanguageName = mLanguageName;
	}
}
