package com.example.carlosjoseanguiano.multimedia_bsd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;



/**
 * Created by Carlos Anguiano on 29/06/17.
 * For more info contact: c.joseanguiano@gmail.com
 */

class MediaAdapterAllAlbum extends RecyclerView.Adapter<MediaAdapterAllAlbum.MediaListHolder> {
    private static final String TAG = MediaAdapterAllAlbum.class.getSimpleName();
    private List<DataPicturesAlbum> itemList;
    private MediaAdapterAllAlbumClickListener mListener;
    private static final int MAX_WIDTH = 150;
    private static final int MAX_HEIGHT = 150;
    private int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    private boolean backPressed = false;
    private Activity context;

    MediaAdapterAllAlbum(Activity context,List<DataPicturesAlbum> mediaList, boolean backPressed) {
        Log.i(TAG, "MediaAdapterAllAlbums: ");
        this.context = context;
        this.itemList = mediaList;
        this.backPressed = backPressed;
    }

    public void subscribe(MediaAdapterAllAlbumClickListener mListener) {
        this.mListener = mListener;
    }

    void unsubscribe() {
        this.mListener = null;
    }

    @Override
    public MediaListHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_multimedia_album, viewGroup, false);
        return new MediaListHolder(v);
    }

    @Override
    public void onBindViewHolder(MediaListHolder mediaListRowHolder, final int i) {
        mediaListRowHolder.bindTo(mediaListRowHolder, i);
    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

    class MediaListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnail;
        TextView textBucket;
        TextView sizeImages;


        MediaListHolder(View view) {
            super(view);
            this.thumbnail = view.findViewById(R.id.grid_album);
            this.textBucket = view.findViewById(R.id.txtBucket);
            this.sizeImages = view.findViewById(R.id.sizeImages);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DataPicturesAlbum message = itemList.get(getAdapterPosition());
            int pos = getAdapterPosition();
            if (mListener != null) {
                mListener.itemClicked(itemList.get(pos), message.getFolder(), message.getType(), backPressed);
            }

        }

        @SuppressLint("SetTextI18n")
        public void bindTo(MediaListHolder mediaListRowHolder, int i) {
            DataPicturesAlbum message = itemList.get(i);

            mediaListRowHolder.textBucket.setText(itemList.get(i).getFolder());
//            mediaListRowHolder.sizeImages.setText(itemList.get(i).getPathSize().size() + "");


            try {
//                Uri uri = Uri.fromFile(new File("file://" + itemList.get(i).getPathSize().get(0)));
                Uri uri = Uri.fromFile(new File(itemList.get(i).getPathSize().get(0)));
                if (message.getType().equalsIgnoreCase("video")) {
                    Bitmap bmThumbnail = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(message.getPathSize().get(0),
                            MediaStore.Video.Thumbnails.FULL_SCREEN_KIND), MAX_WIDTH, MAX_HEIGHT);
                    mediaListRowHolder.thumbnail.setImageBitmap(bmThumbnail);
                } else {

                /*    Bitmap bitmapImage = BitmapFactory.decodeFile(uri.getPath());
                    float screenWidth = getScreenWidth(context);
                    float newHeight = screenWidth;

                    if (mediaListRowHolder.thumbnail.getWidth() != 0 && mediaListRowHolder.thumbnail.getHeight() != 0) {
                        newHeight = (screenWidth * mediaListRowHolder.thumbnail.getHeight()) / mediaListRowHolder.thumbnail.getWidth();
                    }
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmapImage, (int) screenWidth, (int) newHeight, true);
                    mediaListRowHolder.thumbnail.setImageBitmap(scaledBitmap);*/


                    float screenWidth = getScreenWidth(context);
                    float newHeight = screenWidth;

                    Picasso.with(context)
                            .load(uri)
//                            .transform(new BitmapTransform(screenWidth, newHeight,uri))
                            .resize(size, size)
                            .centerCrop()
//                            .placeholder(R.drawable.logo_slogan)
                            .into(mediaListRowHolder.thumbnail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        float getScreenWidth(Activity activity) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            return (float) outMetrics.widthPixels;
        }
    }
}

