package com.rana.juyelrana.jsonimageandtextparsing;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by JuyelRana on 5/26/2016.
 */
public class ProductsAdapter extends ArrayAdapter<Products>{

    private List<Products> productsList;
    private int resourse;

    private LayoutInflater inflater;

    public ProductsAdapter(Context context, int resource, List<Products> objects) {
        super(context, resource, objects);
        productsList = objects;
        this.resourse = resource;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){

            convertView = inflater.inflate(resourse,null);

        }

        //Initialize all components of row layout

        ImageView productImage;
        TextView productTitle;
        TextView productSubTitle;
        TextView productPrice;
        TextView productDescription;
        final ProgressBar progressBar;

        productImage = (ImageView)convertView.findViewById(R.id.row_image);
        productTitle = (TextView)convertView.findViewById(R.id.row_txtitle);
        productSubTitle = (TextView)convertView.findViewById(R.id.row_txsubtitle);
        productPrice = (TextView)convertView.findViewById(R.id.row_txtprice);
        productDescription = (TextView)convertView.findViewById(R.id.row_description);
        progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

        //For load image before adding progressbar
        //ImageLoader.getInstance().displayImage(productsList.get(position).getImage(),productImage);

        //Adding prograessbar
        //For load image
        ImageLoader.getInstance().displayImage(productsList.get(position).getImage(), productImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(view.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(view.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(view.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(view.GONE);
            }
        });
        //Just for load image
        productTitle.setText(productsList.get(position).getTitle());
        productSubTitle.setText(productsList.get(position).getSubtitle());
        productPrice.setText(productsList.get(position).getPrice());
        productDescription.setText(productsList.get(position).getDescription());


        return convertView;
    }




}
