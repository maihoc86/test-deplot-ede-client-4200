package com.ede.edecustomerservice.service;

import com.ede.edecustomerservice.entity.HistoryViewPage;

public interface History_View_Page_Service {

	HistoryViewPage addViewPage(HistoryViewPage historyViewPage);

	HistoryViewPage findByIpToDate(String ip);

}
