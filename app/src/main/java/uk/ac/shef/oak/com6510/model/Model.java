/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com6510.model;

import android.content.Context;
import android.os.AsyncTask;

import uk.ac.shef.oak.com6510.presenter.Presenter;

public class Model {
    private final PhotoDAO mPhotoDao;
    Presenter presenter;

    public Model(Context context, Presenter presenter) {
        this.presenter= presenter;

        PhotoDatabase db = PhotoDatabase.getDatabase(context);
        mPhotoDao = db.photoDao();
    }

    /**
     *  it generates a random integer (0 and 1) and returns either an error or a correct message
     * @param title
     */
    public void insertTitle(String title) {
        if (!title.isEmpty()) {
            // data insertion cannot be done on the UI thread. Use an ASync process!!
            new InsertIntoDbAsync(mPhotoDao, new Photo(title),presenter).execute();
        } else presenter.errorInsertingTitle(title, "Tile should not be empty");
    }

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class InsertIntoDbAsync extends AsyncTask<Void, Void, Void> {
        private final PhotoDAO mPhotoDao;
        private final Photo mPhotoData;
        private final Presenter mPresenter;

        InsertIntoDbAsync(PhotoDAO dao, Photo photoData, Presenter presenter) {
            mPhotoDao = dao;
            mPhotoData= photoData;
            mPresenter= presenter;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mPhotoDao.insert(mPhotoData);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mPresenter.titleInserted(mPhotoData.getTitle());
        }
    }

}
