package br.ufsm.dsweb.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.ufsm.dsweb.model.Model;

public abstract class ModelDAO<T extends Model> {
	private Class<T> mClass;
	private String mTableName;
	
	private static EntityManagerFactory mEMF = null;

	private EntityManager mEntityManager = null;
	
	public ModelDAO(Class<T> classT, String tablename) {
		mClass = classT;
		mTableName = tablename;
	}
	
	public EntityManager getEntityManager() {
		if(mEMF == null) {
			mEMF = Persistence.createEntityManagerFactory("twitcherPersistence");
		}
		if(mEntityManager == null || !mEntityManager.isOpen()) {
			mEntityManager = mEMF.createEntityManager();
		}
		return mEntityManager;
	}

	public T getByID(int model_id) {
		T res = null;
		try {
			res = getEntityManager().find(mClass, model_id);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
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
		} finally {
			getEntityManager().close();
		}
		return res;
	}
	public T getFirstByCol(String col, int value) {
		return getFirstByCol(col, value+"");
	}
	@SuppressWarnings("unchecked")
	public T getFirstByCols(HashMap<String, String> col_vals) {
		T res = null;
		try {
			res = (T)createQuery(col_vals).getSingleResult();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
		return res;
	}
	public void save(T model) {
		try {
			getEntityManager().getTransaction().begin();
			getEntityManager().persist(model);
			getEntityManager().getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
	}
	public void removeByID(int model_id) {
		T model = getByID(model_id);
		if(model != null) {
			try {
				getEntityManager().getTransaction().begin();
				getEntityManager().remove(model);
				getEntityManager().getTransaction().commit();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void removeByCols(HashMap<String,String> col_vals) {
		try {
			getEntityManager().getTransaction().begin();
			for(T model : (List<T>) createQuery(col_vals).getResultList()) {
				getEntityManager().remove(model);
			}
			getEntityManager().getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
		}
	}
	public void update(T model) {
		try {
			getEntityManager().getTransaction().begin();
			getEntityManager().merge(model);
			getEntityManager().getTransaction().commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
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
		} finally {
			getEntityManager().close();
		}
		return res;
	}
	public List<T> getAllByCol(String col, int value) {
		return getAllByCol(col, value+"");
	}
	@SuppressWarnings("unchecked")
	public List<T> getAllByCols(HashMap<String, String> col_vals) {
		List<T> res = new ArrayList<T>();
		try {
			res = (List<T>) createQuery(col_vals).getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getEntityManager().close();
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
		} finally {
			getEntityManager().close();
		}
		return res;
	}
	private Query createQuery(HashMap<String, String> col_vals) {
		String query = "SELECT m FROM "+getTableName()+" m WHERE ";
		for(String col : col_vals.keySet()) {
			query += "m."+col+" = :"+col;
		}
		Query q = getEntityManager().createQuery(query);
		for(String col : col_vals.keySet()) {
			q.setParameter(col, col_vals.get(col));
		}
		
		return q;
	}
	
	public String getTableName() {
		return mTableName;
	}
}
