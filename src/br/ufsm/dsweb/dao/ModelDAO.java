package br.ufsm.dsweb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.ufsm.dsweb.model.Model;

@Stateless
public abstract class ModelDAO<T extends Model> {
	private Class<T> mClass;
	private String mTableName;
	
	@PersistenceContext(unitName="entitymanagertwitcher")
	private EntityManager mEntityManager;

	public ModelDAO(Class<T> classT, String tablename) {
		mClass = classT;
		mTableName = tablename;
	}
	
	public EntityManager getEntityManager() {
		return mEntityManager;
	}

	public T getByID(int model_id) {
		T res = null;
		try {
			res = getEntityManager().find(mClass, model_id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	@SuppressWarnings("unchecked")
	public T getFirstByCol(String col, String value) {
		T res = null;
		try {
		 res = (T) getEntityManager().createQuery("SELECT m FROM " +getTableName()+" m WHERE m."+col+" = :"+col)
				 .setParameter(col, value)
				 .getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	public void save(T model) {
		try {
			getEntityManager().persist(model);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void removeByID(int model_id) {
		T model = getByID(model_id);
		if(model != null) {
			try {
				getEntityManager().remove(model);
			} catch(Exception e) {
				e.printStackTrace();
			}
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
		try {
			for(T model : (List<T>) q.getResultList()) {
				getEntityManager().remove(model);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void update(T model) {
		try {
			getEntityManager().merge(model);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public List<T> getAllByCol(String col, String value) {
		List<T> res = new ArrayList<T>();
		try {
		res = (List<T>) getEntityManager().createQuery("SELECT m FROM " +getTableName()+ " m WHERE m."+col+" = :"+col)
				.setParameter(col, value)
				.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		List<T> res = new ArrayList<T>();
		try {
			res = (List<T>) getEntityManager().createQuery("SELECE m FROM " +getTableName())
					.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public String getTableName() {
		return mTableName;
	}
}
