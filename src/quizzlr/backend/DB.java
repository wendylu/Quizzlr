/**
 *
 */
package quizzlr.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Database abstraction that hides a lot of the ugly details, such as SQL
 * exception handling.
 * @author David Lo
 *
 */
class DB {
	/**
	 * MySQL server
	 */
	private final static String server = "mysql-user-master.stanford.edu";
	/**
	 * MySQL username
	 */
	private final static String username = "ccs108davidlo";
	/**
	 * MySQL password
	 */
	private final static String password = "maifeesh";
	/**
	 * MySQL database
	 */
	private final static String database = "c_cs108_davidlo";

	/**
	 * Connection used to make database queries
	 */
	private static Connection connection;

	/**
	 * Gets a database connection
	 * @return Connection to the database
	 */
	public static Connection getConnection() {
		if (connection == null) {
			createDBConnection();
		}
		return connection;
	}

	/**
	 * Gets a statement to run queries against the database
	 * @return Statement to run query with
	 * @throws SQLException
	 */
	public static Statement getStatement() throws SQLException {
		return getConnection().createStatement();
	}

	/**
	 * Gets a prepared statement to run queries against the database
	 * @param sql SQL statement to prepare
	 * @return Statement to run query with
	 * @throws SQLException
	 */
	public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}

	/**
	 * Callback for getProperty to actually get the values out of the ResultSet
	 * @author David Lo
	 *
	 * @param <T>
	 */
	static interface SQLResultGetter<T> {
		public T get(ResultSet rs, String propertyName) throws SQLException;
	}

	/**
	 * Gets a property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @param getter Callback that actually gets the value from the ResultSet
	 * @return
	 */
	private static <T> T getProperty(String table, String propertyName, String idFieldName, int id, SQLResultGetter<T> getter) {
		PreparedStatement stmt = null;
		try {
    		stmt = getPreparedStatement("SELECT "+propertyName+" FROM "+table+" WHERE "+idFieldName+"=?");
			stmt.setInt(1, id);
    		ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	return getter.get(rs, propertyName);
            }

            return null;
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Gets a property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id String ID of item to get
	 * @param getter Callback that actually gets the value from the ResultSet
	 * @return
	 */
	public static <T> T getProperty(String table, String propertyName, String idFieldName, String id, SQLResultGetter<T> getter) {
		PreparedStatement stmt = null;
		try {
    		stmt = getPreparedStatement("SELECT "+propertyName+" FROM "+table+" WHERE "+idFieldName+"=?");
			stmt.setString(1, id);
    		ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	return getter.get(rs, propertyName);
            }

            return null;
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Checks to see if an item with the ID exists. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static Boolean doesItemExist(String table, String idFieldName, int id) {
		Boolean val = getProperty(table, idFieldName, idFieldName, id, new SQLResultGetter<Boolean>() {
			@Override
			public Boolean get(ResultSet rs, String propertyName)
					throws SQLException {
				return true;
			}
		});

		if (val == null) {
			return false;
		}
		return val;
	}
	
	/**
	 * Checks to see if an item with the ID exists. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static Boolean doesItemExist(String table, String idFieldName, String id) {
		Boolean val = getProperty(table, idFieldName, idFieldName, id, new SQLResultGetter<Boolean>() {
			@Override
			public Boolean get(ResultSet rs, String propertyName)
					throws SQLException {
				return true;
			}
		});

		if (val == null) {
			return false;
		}
		return val;
	}

	/**
	 * Deletes an item with the specified ID. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to delete
	 * @return
	 */
	public static void deleteItem(String table, String idFieldName, int id) {
		PreparedStatement stmt = null;
		try {
    		stmt = getPreparedStatement("DELETE FROM "+table+" WHERE "+idFieldName+"=?");
			stmt.setInt(1, id);
    		stmt.executeUpdate();
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Deletes item(s) that has a field whose value matches the given match.
	 * @param table Table to query
	 * @param fieldName Column name of field to match against
	 * @param match Value to match
	 */
	public static void deleteItems(String table, String fieldName, int match) {
		deleteItem(table, fieldName, match);
	}

	/**
	 * Gets a string property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static String getStringProperty(String table, String propertyName, String idFieldName, int id) {
		return getProperty(table, propertyName, idFieldName, id, new SQLResultGetter<String>() {
			@Override
			public String get(ResultSet rs, String propertyName)
					throws SQLException {
				return rs.getString(propertyName);
			}
		});
	}

	/**
	 * Gets a time property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static Time getTimeProperty(String table, String propertyName, String idFieldName, int id) {
		return getProperty(table, propertyName, idFieldName, id, new SQLResultGetter<Time>() {
			@Override
			public Time get(ResultSet rs, String propertyName)
					throws SQLException {
				return rs.getTime(propertyName);
			}
		});
	}

	/**
	 * Gets a timestamp property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static Timestamp getTimestampProperty(String table, String propertyName, String idFieldName, int id) {
		return getProperty(table, propertyName, idFieldName, id, new SQLResultGetter<Timestamp>() {
			@Override
			public Timestamp get(ResultSet rs, String propertyName)
					throws SQLException {
				return rs.getTimestamp(propertyName);
			}
		});
	}

	/**
	 * Gets an integer property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static Integer getIntProperty(String table, String propertyName, String idFieldName, int id) {
		return getProperty(table, propertyName, idFieldName, id, new SQLResultGetter<Integer>() {
			@Override
			public Integer get(ResultSet rs, String propertyName)
					throws SQLException {
				return rs.getInt(propertyName);
			}
		});
	}

	/**
	 * Gets an integer property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id string ID of item to get
	 * @return
	 */
	public static Integer getIntProperty(String table, String propertyName, String idFieldName, String id) {
		return getProperty(table, propertyName, idFieldName, id, new SQLResultGetter<Integer>() {
			@Override
			public Integer get(ResultSet rs, String propertyName)
					throws SQLException {
				return rs.getInt(propertyName);
			}
		});
	}

	/**
	 * Gets a float property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static Float getFloatProperty(String table, String propertyName, String idFieldName, int id) {
		return getProperty(table, propertyName, idFieldName, id, new SQLResultGetter<Float>() {
			@Override
			public Float get(ResultSet rs, String propertyName)
					throws SQLException {
				return rs.getFloat(propertyName);
			}
		});
	}
	
	/**
	 * Gets a long property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static Long getLongProperty(String table, String propertyName, String idFieldName, int id) {
		return getProperty(table, propertyName, idFieldName, id, new SQLResultGetter<Long>() {
			@Override
			public Long get(ResultSet rs, String propertyName)
					throws SQLException {
				return rs.getLong(propertyName);
			}
		});
	}

	/**
	 * Gets a boolean property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static Boolean getBooleanProperty(String table, String propertyName, String idFieldName, int id) {
		return getProperty(table, propertyName, idFieldName, id, new SQLResultGetter<Boolean>() {
			@Override
			public Boolean get(ResultSet rs, String propertyName)
					throws SQLException {
				return rs.getBoolean(propertyName);
			}
		});
	}

	/**
	 * Gets a bytes property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @return
	 */
	public static byte[] getBytesProperty(String table, String propertyName, String idFieldName, int id) {
		return getProperty(table, propertyName, idFieldName, id, new SQLResultGetter<byte[]>() {
			@Override
			public byte[] get(ResultSet rs, String propertyName)
					throws SQLException {
				return rs.getBytes(propertyName);
			}
		});
	}

	/**
	 * Callback for SetProperty to actually set the values in the UPDATE
	 * statement
	 * @author David Lo
	 *
	 * @param <T>
	 */
	static interface SQLResultSetter<T> {
		public void set(PreparedStatement stmt, int parameterIndex, T newValue) throws SQLException;
	}

	/**
	 * Sets a property from the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @param setter Callback that actually sets the value in the statement
	 * @return
	 * @return
	 */
	private static <T> void setProperty(String table, String propertyName, String idFieldName, int id, T newValue, SQLResultSetter<T> setter) {
		PreparedStatement stmt = null;
		try {
			stmt = getPreparedStatement("UPDATE "+table+" SET "+propertyName+"=? WHERE "+idFieldName+"=?");
			setter.set(stmt, 1, newValue);
			stmt.setInt(2, id);
    		stmt.executeUpdate();
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Sets a string property in the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @param newValue New value to put in
	 */
	public static void setStringProperty(String table, String propertyName, String idFieldName, int id, String newValue) {
		setProperty(table, propertyName, idFieldName, id, newValue, new SQLResultSetter<String>() {
			@Override
			public void set(PreparedStatement stmt, int parameterIndex,
					String newValue) throws SQLException {
				stmt.setString(parameterIndex, newValue);
			}
		});
	}

	/**
	 * Sets a time property in the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @param newValue New value to put in
	 */
	public static void setTimeProperty(String table, String propertyName, String idFieldName, int id, Time newValue) {
		setProperty(table, propertyName, idFieldName, id, newValue, new SQLResultSetter<Time>() {
			@Override
			public void set(PreparedStatement stmt, int parameterIndex,
					Time newValue) throws SQLException {
				stmt.setTime(parameterIndex, newValue);
			}
		});
	}

	/**
	 * Sets a timestamp property in the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @param newValue New value to put in
	 */
	public static void setTimestampProperty(String table, String propertyName, String idFieldName, int id, Timestamp newValue) {
		setProperty(table, propertyName, idFieldName, id, newValue, new SQLResultSetter<Timestamp>() {
			@Override
			public void set(PreparedStatement stmt, int parameterIndex,
					Timestamp newValue) throws SQLException {
				stmt.setTimestamp(parameterIndex, newValue);
			}
		});
	}

	/**
	 * Sets an integer property in the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @param newValue New value to put in
	 */
	public static void setIntProperty(String table, String propertyName, String idFieldName, int id, Integer newValue) {
		setProperty(table, propertyName, idFieldName, id, newValue, new SQLResultSetter<Integer>() {
			@Override
			public void set(PreparedStatement stmt, int parameterIndex,
					Integer newValue) throws SQLException {
				stmt.setInt(parameterIndex, newValue);
			}
		});
	}

	/**
	 * Sets a float property in the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @param newValue New value to put in
	 */
	public static void setFloatProperty(String table, String propertyName, String idFieldName, int id, Float newValue) {
		setProperty(table, propertyName, idFieldName, id, newValue, new SQLResultSetter<Float>() {
			@Override
			public void set(PreparedStatement stmt, int parameterIndex,
					Float newValue) throws SQLException {
				stmt.setFloat(parameterIndex, newValue);
			}
		});
	}

	/**
	 * Sets a boolean property in the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @param newValue New value to put in
	 */
	public static void setBooleanProperty(String table, String propertyName, String idFieldName, int id, Boolean newValue) {
		setProperty(table, propertyName, idFieldName, id, newValue, new SQLResultSetter<Boolean>() {
			@Override
			public void set(PreparedStatement stmt, int parameterIndex,
					Boolean newValue) throws SQLException {
				stmt.setBoolean(parameterIndex, newValue);
			}
		});
	}

	/**
	 * Sets a bytes property in the database. Note that the table being
	 * referenced MUST have a primary key/unique index on the idFieldName
	 * @param table Table to query
	 * @param propertyName Column name of table to get
	 * @param idFieldName Column name of ID field in table
	 * @param id ID of item to get
	 * @param newValue New value to put in
	 */
	public static void setBytesProperty(String table, String propertyName, String idFieldName, int id, byte[] newValue) {
		setProperty(table, propertyName, idFieldName, id, newValue, new SQLResultSetter<byte[]>() {
			@Override
			public void set(PreparedStatement stmt, int parameterIndex,
					byte[] newValue) throws SQLException {
				stmt.setBytes(parameterIndex, newValue);
			}
		});
	}

	/**
	 * @author David Lo
	 *
	 * @param <T>
	 */
	static public interface ObjectIDTranslator<T> {
		public T translate(ResultSet rs) throws SQLException;
	}

	/**
	 * @param table
	 * @param matchIDName
	 * @param matchID
	 * @param callback
	 * @return
	 */
	public static <T> Set<T> getMatchedSet(String table, String matchIDName, int matchID, ObjectIDTranslator<T> callback) {
		PreparedStatement stmt = null;
		HashSet<T> result = new HashSet<T>();
		try {
    		stmt = getPreparedStatement("SELECT * FROM "+table+" WHERE "+matchIDName+"=?");
			stmt.setInt(1, matchID);
    		ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	result.add(callback.translate(rs));
            }

            return result;
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/**
	 * @param table
	 * @param matchIDName
	 * @param matchID
	 * @param filter
	 * @param callback
	 * @return
	 */
	public static <T> Set<T> getMatchedSetFiltered(String table, String matchIDName, int matchID, String filter, ObjectIDTranslator<T> callback) {
		PreparedStatement stmt = null;
		HashSet<T> result = new HashSet<T>();
		try {
    		stmt = getPreparedStatement("SELECT * FROM "+table+" WHERE "+matchIDName+"=? AND "+filter);
			stmt.setInt(1, matchID);
    		ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	result.add(callback.translate(rs));
            }

            return result;
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * @param table
	 * @param matchIDName
	 * @param matchID
	 * @param callback
	 * @return
	 */
	public static <T> List<T> getMatchedList(String table, String matchIDName, int matchID, ObjectIDTranslator<T> callback) {
		PreparedStatement stmt = null;
		ArrayList<T> result = new ArrayList<T>();
		try {
    		stmt = getPreparedStatement("SELECT * FROM "+table+" WHERE "+matchIDName+"=?");
			stmt.setInt(1, matchID);
    		ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	result.add(callback.translate(rs));
            }

            return result;
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * @param table
	 * @param matchIDName
	 * @param matchID
	 * @param sortExpr
	 * @param callback
	 * @return
	 */
	public static <T> List<T> getMatchedListSorted(String table, String matchIDName, int matchID, String sortExpr, ObjectIDTranslator<T> callback) {
		PreparedStatement stmt = null;
		ArrayList<T> result = new ArrayList<T>();
		try {
    		stmt = getPreparedStatement("SELECT * FROM "+table+" WHERE "+matchIDName+"=? ORDER BY "+sortExpr);
			stmt.setInt(1, matchID);
    		ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	result.add(callback.translate(rs));
            }

            return result;
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/**
	 * @param table
	 * @param matchIDName
	 * @param matchID
	 * @param sortExpr
	 * @param filterExpr
	 * @param callback
	 * @return
	 */
	public static <T> List<T> getMatchedListSortedFiltered(String table, String matchIDName, int matchID, String sortExpr, String filterExpr, ObjectIDTranslator<T> callback) {
		PreparedStatement stmt = null;
		ArrayList<T> result = new ArrayList<T>();
		try {
    		stmt = getPreparedStatement("SELECT * FROM "+table+" WHERE "+matchIDName+"=? AND " + filterExpr + " ORDER BY "+sortExpr);
			stmt.setInt(1, matchID);
    		ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	result.add(callback.translate(rs));
            }

            return result;
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/**
	 * @param table
	 * @param matchIDName
	 * @param matchID
	 * @param sortExpr
	 * @param callback
	 * @param limit
	 * @return
	 */
	public static <T> List<T> getMatchedListSortedLimit(String table, String matchIDName, int matchID, String sortExpr, ObjectIDTranslator<T> callback, int limit) {
		return getMatchedListSorted(table, matchIDName, matchID, sortExpr + " LIMIT " + limit, callback);
	}
	
	/**
	 * @param table
	 * @param matchIDName
	 * @param matchID
	 * @param sortExpr
	 * @param filterExpr
	 * @param callback
	 * @param limit
	 * @return
	 */
	public static <T> List<T> getMatchedListSortedFilteredLimit(String table, String matchIDName, int matchID, String sortExpr, String filterExpr, ObjectIDTranslator<T> callback, int limit) {
		return getMatchedListSortedFiltered(table, matchIDName, matchID, sortExpr + " LIMIT " + limit, filterExpr, callback);
	}
	
	/**
	 * @param table
	 * @param matchIDName
	 * @param matchID
	 * @param sortExpr
	 * @param callback
	 * @return
	 */
	public static <T> List<T> getListSorted(String table, String sortExpr, ObjectIDTranslator<T> callback) {
		PreparedStatement stmt = null;
		ArrayList<T> result = new ArrayList<T>();
		try {
    		stmt = getPreparedStatement("SELECT * FROM "+table+" ORDER BY "+sortExpr);
			ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	result.add(callback.translate(rs));
            }

            return result;
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/**
	 * @param table
	 * @param matchIDName
	 * @param matchID
	 * @param sortExpr
	 * @param filterExpr
	 * @param callback
	 * @return
	 */
	public static <T> List<T> getListSortedFiltered(String table, String sortExpr, String filterExpr, ObjectIDTranslator<T> callback) {
		PreparedStatement stmt = null;
		ArrayList<T> result = new ArrayList<T>();
		try {
    		stmt = getPreparedStatement("SELECT * FROM "+table+" WHERE " + filterExpr + " ORDER BY "+sortExpr);
			ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	result.add(callback.translate(rs));
            }

            return result;
    	} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Creates a connection to the MySQL database
	 */
	private static void createDBConnection() {
		// Code adopted from handout 22
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + server, username, password);
			Statement stmt = connection.createStatement();
			stmt.executeQuery("USE " + database);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
