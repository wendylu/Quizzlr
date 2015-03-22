/**
 * Quizzlr
 */
package quizzlr.backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a site-wide Announcement from the database.
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public class Announcement {
	private int announcementID;

	// Constructor
	/**
	 * Creates an Announcement object that references the database
	 * @param announcementID
	 */
	private Announcement(int announcementID) {
		this.announcementID = announcementID;
	}

	// Getters
	/**
	 * @return the announcement ID
	 */
	public int getAnnouncementID() {
		return announcementID;
	}

	/**
	 * @return the title of the announcement
	 */
	public String getTitle() {
		return DB.getStringProperty("announcements", "title", "announcementID", getAnnouncementID());
	}

	/**
	 * @return the content of the announcement
	 */
	public String getContent() {
		return DB.getStringProperty("announcements", "content", "announcementID", getAnnouncementID());
	}

	/**
	 * @return the user that last edited the announcement
	 */
	public User getLastEditor() {
		return User.getUserFromID(DB.getIntProperty("announcements", "lastEditorID", "announcementID", getAnnouncementID()));
	}

	/**
	 * @return the timestamp of the last edit
	 */
	public Timestamp getLastEditTime() {
		return DB.getTimestampProperty("announcements", "lastEditTime", "announcementID", getAnnouncementID());
	}

	// Make Announcement hashable
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + announcementID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Announcement)) {
			return false;
		}
		Announcement other = (Announcement) obj;
		if (announcementID != other.announcementID) {
			return false;
		}
		return true;
	}

	// Setters
	/**
	 * Sets the announcement's title
	 * @param newTitle
	 */
	public void setTitle(String newTitle) {
		DB.setStringProperty("announcements", "title", "announcementID", getAnnouncementID(), newTitle);
	}

	/**
	 * Sets the content of the announcement
	 * @param newContent
	 */
	public void setContent(String newContent) {
		DB.setStringProperty("announcements", "content", "announcementID", getAnnouncementID(), newContent);
	}

	/**
	 * Sets the last editor of the announcement
	 * @param newLastEditor
	 */
	public void setLastEditor(User newLastEditor) {
		DB.setIntProperty("announcements", "lastEditorID", "announcementID", getAnnouncementID(), newLastEditor.getUserID());
	}

	// Object convenience methods
	/**
	 * Deletes the announcement from the database
	 */
	public void delete() {
    	DB.deleteItem("announcements", "announcementID", getAnnouncementID());
    }

	// Static convenience methods
	/**
	 * Gets all announcements from the database
	 * @return
	 */
	public static List<Announcement> getAllAnnouncements() {
		List<Announcement> results = new ArrayList<Announcement>();
		Statement stmt = null;
		try {
			stmt = DB.getStatement();
			ResultSet rs = stmt.executeQuery("SELECT announcementID FROM announcements ORDER BY announcementID DESC");
			while (rs.next()) {
				results.add(getAnnouncementByID(rs.getInt("announcementID")));
			}
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

		return results;
	}

	/**
	 * Creates a new announcement in the datase
	 * @param title
	 * @param content
	 * @param author
	 * @return The announcement just created
	 */
	public static Announcement createAnnouncement(String title, String content, User author) {
		PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("INSERT INTO announcements (title,content,lastEditorID) VALUES(?,?,?)");
			stmt.setString(1, title);
			stmt.setString(2, content);
			stmt.setInt(3, author.getUserID());
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int newAnnouncementID = rs.getInt(1);
				return new Announcement(newAnnouncementID);
			}
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

		return null;
	}

	/**
	 * Gets an announcement by its ID. Returns null if no such announcement
	 * exists.
	 * @param announcementID
	 * @return
	 */
	public static Announcement getAnnouncementByID(int announcementID) {
		if (DB.doesItemExist("announcements", "announcementID", announcementID)) {
    		return new Announcement(announcementID);
    	}

		return null;
	}
}
