package com.example.carlosjoseanguiano.multimedia_bsd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


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

    MediaAdapterAllAlbum(Activity context, List<DataPicturesAlbum> mediaList, boolean backPressed) {
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
        ImageView picture;


        MediaListHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

            this.picture = view.findViewById(R.id.album_preview);
//            this.thumbnail = view.findViewById(R.id.grid_album);
//            this.textBucket = view.findViewById(R.id.txtBucket);
//            this.sizeImages = view.findViewById(R.id.sizeImages);
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

//            mediaListRowHolder.textBucket.setText(itemList.get(i).getFolder());

            try {
//                Uri uri = Uri.fromFile(new File("file://" + itemList.get(i).getPathSize().get(0)));
                Uri uri = Uri.fromFile(new File(itemList.get(i).getPathSize().get(0)));
                if (message.getType().equalsIgnoreCase("video")) {
                    Bitmap bmThumbnail = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(message.getPathSize().get(0),
                            MediaStore.Video.Thumbnails.FULL_SCREEN_KIND), MAX_WIDTH, MAX_HEIGHT);
                    mediaListRowHolder.thumbnail.setImageBitmap(bmThumbnail);
                } else {

                    /*Picasso.with(context)
                            .load(uri)
                            .resize(size, size)
                            .centerCrop()
                            .into(mediaListRowHolder.thumbnail);*/


                 /* String a = "file://" + itemList.get(i).getPathSize().get(0);
                    RequestOptions options = new RequestOptions()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                    Glide.with(context)
                            .load(a)
                            .apply(options)
                            .into(mediaListRowHolder.thumbnail);*/


                    String a = "file://" + itemList.get(i).getPathSize().get(0);

                    RequestOptions options = new RequestOptions()
//                            .signature(f.getSignature())
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .centerCrop()
                            .error(R.drawable.ic_3d_rotation)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

                    Glide.with(context)
                            .load("file:///storage/emulated/0/Pictures/facebook/FB_IMG_15041262415725784.jpg")
                            .apply(options)
                            .into(mediaListRowHolder.picture);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

