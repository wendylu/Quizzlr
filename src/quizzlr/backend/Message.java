/**
 * Quizzlr
 */
package quizzlr.backend;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Class that represents a Message from the database.
 * All manipulations performed will be immediately reflected in the database,
 * and all property gets will use data from the database.
 * @author David Lo
 *
 */
public class Message {
	private int messageID;

	/**
	 * Creates a Message object that references the database
	 * @param messageID
	 */
	private Message(int messageID) {
		this.messageID = messageID;
	}

	// Getters
	/**
	 * @return the message ID
	 */
	public int getMessageID() {
		return messageID;
	}

	/**
	 * @return the sending user
	 */
	public User getFromUser() {
		return User.getUserFromID(DB.getIntProperty("messages", "fromUserID", "messageID", getMessageID()));
	}

	/**
	 * @return the receiving user
	 */
	public User getToUser() {
		return User.getUserFromID(DB.getIntProperty("messages", "toUserID", "messageID", getMessageID()));
	}

	/**
	 * @return if the message was read
	 */
	public boolean getIsRead() {
		return DB.getBooleanProperty("messages", "isRead", "messageID", getMessageID());
	}

	/**
	 * @return the sent timestamp
	 */
	public Timestamp getSentTimestamp() {
		return DB.getTimestampProperty("messages", "sentTimestamp", "messageID", getMessageID());
	}

	/**
	 * @return the message type
	 */
	public int getMessageType() {
		return DB.getIntProperty("messages", "messageType", "messageID", getMessageID());
	}

	/**
	 * @return the message content
	 */
	public String getMessageContent() {
		String content = DB.getStringProperty("messages", "messageContent", "messageID", getMessageID());
		// If no message, return empty string
		if (content == null) {
			return "";
		}

		return content;
	}

	// Setters
	/**
	 * Sets if the message has been read
	 * @param newIsRead
	 */
	public void setIsRead(boolean newIsRead) {
		DB.setBooleanProperty("messages", "isRead", "messageID", getMessageID(), newIsRead);
	}

	// Methods that make Message hashable
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + messageID;
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
		if (!(obj instanceof Message)) {
			return false;
		}
		Message other = (Message) obj;
		if (messageID != other.messageID) {
			return false;
		}
		return true;
	}

	// Object convenience methods
	/**
	 * Deletes the message
	 */
	public void delete() {
		DB.deleteItem("messages", "messageID", getMessageID());
	}

	// Static convenience methods
	/**
	 * Gets a message from its messageID. If no such message exists, returns
	 * null
	 * @param messageID
	 * @return
	 */
	public static Message getMessageFromID(int messageID) {
		if (DB.doesItemExist("messages", "messageID", messageID)) {
    		return new Message(messageID);
    	}

		return null;
	}

	/**
	 * Sends a message from one user to another.
	 * @param from sending user
	 * @param to receiving user
	 * @param messageType
	 * @param messageContent
	 */
	public static void sendMessage(User from, User to, int messageType, String messageContent) {
		PreparedStatement stmt = null;
		try {
			stmt = DB.getPreparedStatement("INSERT INTO messages (fromUserID, toUserID, messageType, messageContent) VALUES(?,?,?,?)");
			stmt.setInt(1, from.getUserID());
			stmt.setInt(2, to.getUserID());
			stmt.setInt(3, messageType);
			stmt.setString(4, messageContent);
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
}
