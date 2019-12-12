package uk.ac.shef.oak.com6510.viewModel;

import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.view.PhotoActivity;

public class PhotoViewModel {
    private Photo model;
    private PhotoActivity pActivity;

    public PhotoViewModel(PhotoActivity pActivity){
        this.pActivity = pActivity;
        model = new Photo();
    }

    public void loadPhoto(){

    }
}
