package br.ufsm.dsweb.dao;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufsm.dsweb.model.Model;

@Stateless
public abstract class ModelDAO<T extends Model> {
	private T mModel;
	private String mTableName;
	
	@PersistenceContext(unitName="entitymanagertwitcher")
	private EntityManager mEntityManager;

	public ModelDAO(T model, String tablename) {
		mModel = model;
		mTableName = tablename;
	}
	
	public EntityManager getEntityManager() {
		return mEntityManager;
	}

	@SuppressWarnings("unchecked")
	public T getByID(int model_id) {
		T res = null;
		try {
			res = (T) getEntityManager().find(mModel.getClass(), model_id);
		} catch(Exception e) {
		}
		return res;
	}
	public void save(T model) {
		getEntityManager().persist(model);
	}
	public void removeByID(int model_id) {
		T model = getByID(model_id);
		if(model != null) {
			getEntityManager().remove(model);
		}
	}
	@SuppressWarnings("unchecked")
	public void removeByCols(HashMap<String,String> col_vals) {
		String query = "SELECT m FROM "+getTableName()+" m WHERE ";
		for(String col : col_vals.keySet()) {
			query += "m."+col+" = :"+col;
		}
		Query q = getEntityManager().createQuery(query);
		for(String col : col_vals.keySet()) {
			q.setParameter(col, col_vals.get(col));
		}
		for(T model : (List<T>) q.getResultList()) {
			getEntityManager().remove(model);
		}
	}
	public void update(T model) {
		getEntityManager().merge(model);
	}
	@SuppressWarnings("unchecked")
	public List<T> getAllByCol(String col, String value) {
		return (List<T>) getEntityManager().createQuery("SELECT m FROM " +getTableName()+ " m WHERE m."+col+" = :"+col)
				.setParameter(col, value)
				.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return (List<T>) getEntityManager().createQuery("SELECE m FROM " +getTableName())
				.getResultList();
	}
	
	public String getTableName() {
		return mTableName;
	}
}
