package br.unb.cic.reminders.model.db;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.framework.persistence.DBInvalidEntityException;
import br.unb.cic.framework.persistence.GenericDAO;
import br.unb.cic.reminders.model.Reminder;

public class DefaultReminderDAO extends GenericDAO<Reminder> implements ReminderDAO {
	public DefaultReminderDAO(Context c) {
		super(c);
	}

	public Long saveReminder(Reminder r) throws DBException {
		try {
			return persist(r);
		} catch (DBInvalidEntityException e) {
			throw new DBException();
		}
	}

	public List<Reminder> listReminders() throws DBException {
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(DBConstants.SELECT_REMINDERS, null);
			return remindersFromCursor(cursor);
		} catch (Exception e) {
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	public void updateReminder(Reminder reminder) throws DBException {
		try {
			persist(reminder);
		} catch (DBInvalidEntityException e) {
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	public void deleteReminder(Reminder reminder) throws DBException {
		try {
			db = dbHelper.getWritableDatabase();
			db.delete(DBConstants.REMINDER_TABLE, DBConstants.REMINDER_PK_COLUMN + "=" + reminder.getId(), null);
		} catch (SQLiteException e) {
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	public void persistReminder(Reminder reminder) throws DBException {
		try {
			persist(reminder);
		} catch (DBInvalidEntityException e) {
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	private Reminder cursorToReminder(Cursor cursor) throws DBException {
		Long pk = cursor.getLong(cursor.getColumnIndex(DBConstants.REMINDER_PK_COLUMN));
		String text = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_TEXT_COLUMN));
		String details = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_DETAILS_COLUMN));
		int done = cursor.getInt(cursor.getColumnIndex(DBConstants.REMINDER_DONE_COLUMN));
		Reminder reminder = createReminderCursor(cursor);
		reminder.setText(text);
		reminder.setDetails(details);
		reminder.setId(pk);
		reminder.setDone(done);
		return reminder;
	}

	private Reminder createReminderCursor(Cursor cursor) throws DBException {
		Reminder reminder = new Reminder();
		String date = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_DATE_COLUMN));
		String hour = cursor.getString(cursor.getColumnIndex(DBConstants.REMINDER_HOUR_COLUMN));
		reminder.setDate(date);
		reminder.setHour(hour);
		return reminder;
	}

	private List<Reminder> remindersFromCursor(Cursor cursor) throws DBException {
		List<Reminder> reminders = new ArrayList<Reminder>();
		if (cursor.moveToFirst()) {
			do {
				Reminder reminder = cursorToReminder(cursor);
				reminders.add(reminder);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return reminders;
	}
}