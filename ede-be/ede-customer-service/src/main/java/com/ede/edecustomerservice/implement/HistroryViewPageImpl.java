package com.ede.edecustomerservice.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.dao.HistoryViewPageDao;
import com.ede.edecustomerservice.entity.HistoryViewPage;
import com.ede.edecustomerservice.service.History_View_Page_Service;

@Service
public class HistroryViewPageImpl implements History_View_Page_Service {

	@Autowired
	HistoryViewPageDao dao;

	@Override
	public HistoryViewPage addViewPage(HistoryViewPage historyViewPage) {
		return dao.save(historyViewPage);
	}

	@Override
	public HistoryViewPage findByIpToDate(String ip) {
		return dao.findByIpToDate(ip);
	}

	@Override
	public List<HistoryViewPage> listAll() {
		return dao.findAll();
	}

	@Override
	public List<HistoryViewPage> getHistoryViewPagesNew() {
		return dao.getHistoryViewPagesNew();
	}

}
