package com.ede.edecustomerservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ede.edecustomerservice.entity.HistoryViewPage;

public interface HistoryViewPageDao extends JpaRepository<HistoryViewPage, String> {

	@Query(value = "Select * from history_view_page where ip=:ip AND DATEADD(DAY, DATEDIFF(DAY, 0, GETDATE()), 0) - DATEADD(DAY, DATEDIFF(DAY, 0, date_view), 0) = 0", nativeQuery = true)
	HistoryViewPage findByIpToDate(String ip);

	@Query(value = "Select * from history_view_page where  DATEADD(DAY, DATEDIFF(DAY, 0, GETDATE()), 0) - DATEADD(DAY, DATEDIFF(DAY, 0, date_view), 0) < 7", nativeQuery = true)
	List<HistoryViewPage> getHistoryViewPagesNew();

}
