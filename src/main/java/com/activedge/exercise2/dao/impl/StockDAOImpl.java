package com.activedge.exercise2.dao.impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.activedge.exercise2.dao.StockDAO;
import com.activedge.exercise2.model.Stock;

@Repository
public class StockDAOImpl implements StockDAO {

	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(EntityManagerFactory entityManagerFactory) {
		sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
	}

	@Override
	public void addStock(Stock stock) {
		sessionFactory.getCurrentSession().save(stock);
	}

	@Override
	public void addOrUpdateStock(Stock stock) {
		sessionFactory.getCurrentSession().saveOrUpdate(stock);
	}

	@Override
	public void updateStock(Stock stock) {
		sessionFactory.getCurrentSession().update(stock);
	}

	@Override
	public Stock getStockByPk(Long stockId) {
		return (Stock) sessionFactory.getCurrentSession().get(Stock.class, stockId);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Stock> getAllStock() {
		return sessionFactory.getCurrentSession().createQuery("FROM Stock").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Stock> getAllStockPaginated(int pageSize, int pageNumber) {
		
		return sessionFactory.getCurrentSession().createQuery("FROM Stock ORDER BY id DESC")

				.setFirstResult(pageSize * pageNumber)//page number = pageSize * pageNumber
				.setMaxResults(pageSize)//pageSize
				
				.list();
	}
	
	@Override
	public void deleteStock(Long stockId) {
		Stock stock = (Stock) sessionFactory.getCurrentSession().load(Stock.class, stockId);
		if (stock != null) {
			this.sessionFactory.getCurrentSession().delete(stock);
		}
	}	
	
}
