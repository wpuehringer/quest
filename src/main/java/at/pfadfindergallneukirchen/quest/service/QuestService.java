package at.pfadfindergallneukirchen.quest.service;

import at.pfadfindergallneukirchen.quest.dao.QuestDao;

public interface QuestService {

	public void setDao(QuestDao dao);
	public QuestDao getDao();
}
