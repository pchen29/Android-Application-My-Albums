/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com6510.presenter;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.shef.oak.com6510.view.ViewInterface;
import uk.ac.shef.oak.com6510.model.Model;

public class Presenter implements PresenterInterface {

    ViewInterface userinterface;
    Model mModel;

    /**
     * the presenter does not know anything about the actual UI passed as parameter as it comes as an instance of the UI interface
     * @param application
     */
    public Presenter(Context context, AppCompatActivity application) {
        userinterface= (ViewInterface) application;
        mModel= new Model(context, this);
    }

    /**
     * this is the presenter's interface method that enables the UI to call the presenter
     * it sends the data to the model
     * @param title
     */
    @Override
    public void insertTitle(String title) {
        // send it to the model
        mModel.insertTitle(title);
    }


    /**
     * it receives confirmation of correct insertion of title. It sends them back to the UI
     * @param title
     */
    public void titleInserted(String title){
        // send it back to the UI
        userinterface.titleInsertedFeedback(title);
    }

    /**
     * it receives confirmation of correct insertion of title. It sends them back to the UI
     * @param title
     * @param s
     */
    public void errorInsertingTitle(String title, String errorString){
        // send it back to the UI
        userinterface.titleError(title, errorString);
    }


    public ViewInterface getUserinterface() {
        return userinterface;
    }


}
