package at.pfadfindergallneukirchen.quest.service.impl;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;
import at.pfadfindergallneukirchen.quest.service.QuestService;

public class QuestServiceImpl implements QuestService {

	private QuestDao dao;
	
	@Override
	public QuestDao getDao() {
		return dao;
	}
	
	@Override
	public void setDao(QuestDao dao) {
		this.dao = dao;
	}
}
