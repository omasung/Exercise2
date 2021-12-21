package com.activedge.exercise2.dao;

import java.util.List;

import com.activedge.exercise2.model.Stock;

public interface StockDAO {

	void addStock(Stock stock);

	void addOrUpdateStock(Stock stock);

	void updateStock(Stock stock);

	Stock getStockByPk(Long stockId);

	List<Stock> getAllStock();

	List<Stock> getAllStockPaginated(int pageSize, int pageNumber);

	void deleteStock(Long stockId);

}
