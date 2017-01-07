package fr.aresrpg.tofumanchou.infra.db;

import fr.aresrpg.commons.domain.database.Collection;
import fr.aresrpg.commons.domain.database.Database;

/**
 * 
 * @since
 */
public class DbAccessor<T> {

	private Database database;
	private Collection<T> collection;

	private DbAccessor() {
	}

	/**
	 * @return the collection
	 */
	public Collection<T> get() {
		return collection;
	}

	/**
	 * @return the database
	 */
	public Database getDatabase() {
		return database;
	}

	public static <T> DbAccessor<T> create(Database db, String collectionName, Class<T> type) {
		DbAccessor<T> dbAccessor = new DbAccessor<>();
		dbAccessor.database = db;
		dbAccessor.collection = db.get(collectionName, type);
		return dbAccessor;
	}
}
