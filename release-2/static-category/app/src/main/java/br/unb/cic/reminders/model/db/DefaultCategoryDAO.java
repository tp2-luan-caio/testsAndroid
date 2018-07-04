package br.unb.cic.reminders.model.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import br.unb.cic.framework.persistence.DBException;
import br.unb.cic.framework.persistence.DBInvalidEntityException;
import br.unb.cic.framework.persistence.GenericDAO;
import br.unb.cic.reminders.model.Category;

public class DefaultCategoryDAO extends GenericDAO<Category> implements CategoryDAO {

	public DefaultCategoryDAO(Context c) {
		super(c);
	}

	/**
	 * @see CategoryDAO#listCategories()
	 */
	public List<Category> listCategories() throws DBException {
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(DBConstants.SELECT_CATEGORIES, null);

			List<Category> categories = new ArrayList<Category>();

			if (cursor.moveToFirst()) {
				do {
					Category category = cursorToCategory(cursor);
					categories.add(category);
				} while (cursor.moveToNext());
			}
			cursor.close();
			return categories;
		} catch (SQLiteException e) {
			Log.e(DefaultCategoryDAO.class.getCanonicalName(), e.getLocalizedMessage());
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	/**
	 * @see CategoryDAO#findCategory(String)
	 */
	public Category findCategory(String name) throws DBException {
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(DBConstants.SELECT_CATEGORY_BY_NAME, new String[] { name });

			return returnUniqueCategory(cursor);
		} catch (SQLiteException e) {
			Log.e(DefaultCategoryDAO.class.getCanonicalName(), e.getLocalizedMessage());
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	/**
	 * @see CategoryDAO#findCategoryById(Long)
	 */
	public Category findCategoryById(Long id) throws DBException {
		try {
			db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(DBConstants.SELECT_CATEGORY_BY_ID, new String[] { id.toString() });

			return returnUniqueCategory(cursor);
		} catch (SQLiteException e) {
			Log.e(DefaultCategoryDAO.class.getCanonicalName(), e.getLocalizedMessage());
			throw new DBException();
		} finally {
			db.close();
			dbHelper.close();
		}
	}

	/*
	 * Given a cursor, returns the first category.
	 */
	private Category returnUniqueCategory(Cursor cursor) {
		List<Category> categories = new ArrayList<Category>();

		if (cursor.moveToFirst()) {
			Category category = cursorToCategory(cursor);
			categories.add(category);
			cursor.close();
			return categories.get(0);
		}
		return null;
	}

	/*
	 * Creates a category from a given cursor.
	 */
	private Category cursorToCategory(Cursor cursor) {
		Long pk = cursor.getLong(cursor.getColumnIndex(DBConstants.CATEGORY_PK_COLUMN));
		String name = cursor.getString(cursor.getColumnIndex(DBConstants.CATEGORY_NAME_COLUMN));

		Category category = new Category();
		category.setName(name);
		category.setId(pk);

		return category;
	}

}
