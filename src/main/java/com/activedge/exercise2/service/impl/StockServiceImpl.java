package com.activedge.exercise2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.activedge.exercise2.dao.StockDAO;
import com.activedge.exercise2.model.Stock;
import com.activedge.exercise2.service.StockService;

@Service
@Transactional
public class StockServiceImpl implements StockService {

	@Autowired private StockDAO stockDAO;

	@Override
	public void addStock(Stock stock) {

		stockDAO.addStock(stock);
	}

	@Override
	public void addOrUpdateStock(Stock stock) {

		stockDAO.addOrUpdateStock(stock);
	}

	@Override
	public void updateStock(Stock stock) {

		stockDAO.updateStock(stock);
	}

	@Override
	public Stock getStockByPk(Long stockId) {

		return stockDAO.getStockByPk(stockId);
	}

	@Override
	public List<Stock> getAllStock() {

		return stockDAO.getAllStock();
	}
	
	@Override
	public List<Stock> getAllStockPaginated(int pageSize, int pageNumber) {

		return stockDAO.getAllStockPaginated(pageSize, pageNumber);
	}

	@Override
	public void deleteStock(Long stockId) {

		stockDAO.deleteStock(stockId);
	}
	
}
