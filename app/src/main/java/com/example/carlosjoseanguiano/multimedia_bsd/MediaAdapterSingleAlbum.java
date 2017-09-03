package com.example.carlosjoseanguiano.multimedia_bsd;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos Anguiano on 29/06/17.
 * For more info contact: c.joseanguiano@gmail.com
 */

class MediaAdapterSingleAlbum extends RecyclerView.Adapter<MediaAdapterSingleAlbum.MediaListHolder> {
    public static final String TAG = MediaAdapterSingleAlbum.class.getSimpleName();
    private static final int MAX_WIDTH = 150;
    private static final int MAX_HEIGHT = 150;
    private int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    private SendMessageActionsListener messageActionsListener;
    private MediaSingleClicked mListener;
    private List<DataPicture> itemList;
    private ArrayList<DataPicture> selectedMessages = new ArrayList<>();
    private ArrayList<String> pathSelected = new ArrayList<>();
    private boolean activateModeDisplay = false;
    private Activity context;
    private ActionMode customMode;
    private String typeBuckets;
    private String typeAlbum;
    private MultiSelector mMultiSelector;
    private boolean onLongClicks = false;
    private int contador;

    MediaAdapterSingleAlbum(Activity context, ArrayList<String> path_On, String typeBucket,
                            List<DataPicture> listFilterBucket, String typeAlbum, MultiSelector mMultiSelector) {
        this.context = context;
        this.pathSelected = path_On;
        this.typeBuckets = typeBucket;
        this.itemList = listFilterBucket;
        this.typeAlbum = typeAlbum;
        this.mMultiSelector = mMultiSelector;
    }

    public void onDestroy() {
        removeListeners();
    }

    void setOnMessageActionsListener(SendMessageActionsListener l) {
        messageActionsListener = l;
    }

    private void removeListeners() {
        messageActionsListener = null;
    }

    public void subscribe(MediaSingleClicked mListener) {
        this.mListener = mListener;
    }

    void unsubscribe() {
        this.mListener = null;
    }

    interface SendMessageActionsListener {
        void forwardMessage(ArrayList<String> idMessages, String typeBuckets, String typeAlbum, boolean v);
    }

    @Override
    public MediaListHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.item_multimedia_grids, viewGroup, false);

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

    class MediaListHolder extends SwappingHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView thumbnail;

        MediaListHolder(View view) {
            super(view, mMultiSelector);
            mMultiSelector.setSelectable(true);
            this.thumbnail = view.findViewById(R.id.grid_image);
//            this.selector = view.findViewById(R.id.img);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            DataPicture messages = itemList.get(getAdapterPosition());
            AppCompatActivity activity = (AppCompatActivity) context;

            if (selectedMessages != null) {
                contador = selectedMessages.size();
            }
            if (mMultiSelector.isSelectable()) {
                mMultiSelector.tapSelection(this);
                if (messages.getFileType().contains("images")) {
                    if (v.isSelected()) {
                        contador = contador - 1;
                        addMessage(messages);
                        itemView.setSelected(!itemView.isSelected());
                        if (customMode == null) {
                            activity.startSupportActionMode(customModeCallback);
                            customMode.setTitle("Seleccionado " + contador);

                        } else {
                            customMode.setTitle("Seleccionado " + contador);
                        }
                    } else {
                        contador = contador + 1;
                        if (pathSelected != null && pathSelected.size() > 0) {
                            addMessage(messages);
                            if (!activateModeDisplay) {
                                if (v.isSelected()) {
                                } else {
                                    itemView.setSelected(!itemView.isSelected());
                                }
                                if (customMode == null) {
                                    activity.startSupportActionMode(customModeCallback);
                                    customMode.setTitle("Seleccionado " + contador);

                                } else {
                                    customMode.setTitle("Seleccionado " + contador);
                                }
                            } else {
                                mListener.itemClicked(messages, typeBuckets, typeAlbum, true);
                            }

                        } else {
                            if (onLongClicks) {
                                addMessage(messages);
                                if (customMode == null) {
                                    activity.startSupportActionMode(customModeCallback);
                                    customMode.setTitle("Seleccionado " + contador);

                                } else {
                                    customMode.setTitle("Seleccionado " + contador);
                                }
                            } else {
                                mListener.itemClicked(messages, typeBuckets, typeAlbum, true);
                            }
                        }
                    }
                } else {
                    mListener.itemClicked(messages, typeBuckets, typeAlbum, false);
                }

            }
        }

        @Override
        public boolean onLongClick(View v) {
            DataPicture message = itemList.get(getAdapterPosition());
            AppCompatActivity activity = (AppCompatActivity) context;
            onLongClicks = true;
            if (selectedMessages != null) {
                contador = selectedMessages.size();
            }

            if (mMultiSelector.isSelectable()) {
                mMultiSelector.tapSelection(this);
                if (message.getFileType().contains("images")) {
                    addMessage(message);
                    if (v.isSelected()) {
                        contador = contador - 1;
                        itemView.setSelected(!itemView.isSelected());
                        if (customMode == null) {
                            activity.startSupportActionMode(customModeCallback);
                            customMode.setTitle("Seleccionado " + contador);

                        } else {
                            customMode.setTitle("Seleccionado " + contador);
                        }
                    } else {
                        contador = contador + 1;
                        itemView.setSelected(!itemView.isSelected());
                        if (customMode == null) {
                            activity.startSupportActionMode(customModeCallback);
                            customMode.setTitle("Seleccionado " + contador);
                        } else {
                            customMode.setTitle("Seleccionado " + contador);
                        }
                    }
                }
            }
            return true;
        }

        public void bindTo(MediaListHolder mediaListRowHolder, int i) {
            DataPicture message = itemList.get(i);
            try {
                Uri uri = Uri.fromFile(new File(message.getFilePath()));
                if (message.getFileType().equalsIgnoreCase("video")) {
                    Bitmap bmThumbnail = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(message.getFilePath(),
                            MediaStore.Video.Thumbnails.FULL_SCREEN_KIND), MAX_WIDTH, MAX_HEIGHT);
                    mediaListRowHolder.thumbnail.setImageBitmap(bmThumbnail);
                } else {
                    if (pathSelected != null) {
                        for (String pathSelectedFile : pathSelected) {
                            if (message.getFilePath().equals(pathSelectedFile)) {
                                itemView.setSelected(!itemView.isSelected());
                                if (!selectedMessages.contains(message)) {
                                    mMultiSelector.tapSelection(this);
//                                    selectedMessages.add(message);
                                    if (pathSelected != null && pathSelected.size() > 0) {
                                        Log.i(TAG, "bindTo: ");
                                        selectedMessages.add(message);
                                    }
                                }
                            }
                        }

                    }

                    /*Bitmap bitmapImage = BitmapFactory.decodeFile(uri.getPath());
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
                            .placeholder(R.drawable.ic_3d_rotation)
                            .into(mediaListRowHolder.thumbnail);

                }
                Log.i(TAG, "bindTo: ");
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


    private ActionMode.Callback customModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuItem item;
            customMode = mode;
            item = menu.add(Menu.NONE, R.id.action_forward, Menu.NONE, R.string.OK);
            item.setTitle("OK");
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_forward:
                    if (messageActionsListener != null) {
                        ArrayList<String> messagesIds = new ArrayList<>();
                        for (DataPicture listSelected : selectedMessages) {
                            if (listSelected != null) {
                                messagesIds.add(listSelected.getFilePath());
                            }
                        }
                        if (selectedMessages.size() > 0) {
                            messageActionsListener.forwardMessage(messagesIds, typeBuckets, typeAlbum, true);
                        } else {
                            activateModeDisplay = true;
                        }
                    }
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            notifyDataSetChanged();
            selectedMessages.clear();
            mMultiSelector.clearSelections();
            onLongClicks = false;
            customMode.finish();
            if (pathSelected != null) {
                pathSelected.clear();
            }
        }
    };

    private void addMessage(DataPicture message) {
        if (message.getFileType().equalsIgnoreCase("video")) {
            Log.w("A", "if");
        }
        int i = 0;
        boolean exists = false;
        for (DataPicture dataPictures : selectedMessages) {
            if (dataPictures.getFilePath().equals(message.getFilePath())) {
                exists = true;
                break;
            }
            i = i + 1;
        }
        if (exists) {
            selectedMessages.remove(i);
        } else {
            selectedMessages.add(message);
        }
    }
}
