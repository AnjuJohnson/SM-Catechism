package com.zinedroid.syromalabar.smartcatechism.common.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;
import com.zinedroid.syromalabar.smartcatechism.common.AppConstants;
import com.zinedroid.syromalabar.smartcatechism.common.Models.Chapter;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ChapterActivity;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ChapterDiscussion;
import com.zinedroid.syromalabar.smartcatechism.common.Models.ImageGallery;
import com.zinedroid.syromalabar.smartcatechism.common.Models.MenuContent;
import com.zinedroid.syromalabar.smartcatechism.common.Utility;
import com.zinedroid.syromalabar.smartcatechism.fragments.GalleryFragment;


import java.io.IOException;
import java.util.ArrayList;

import static com.zinedroid.syromalabar.smartcatechism.common.AppConstants.mPlayer;


/**
 * Created by Cecil Paul on 10/9/17.
 */

public class ParagraphAdapter extends ArrayAdapter<MenuContent> {
    Activity context;
    ArrayList<MenuContent> mParagraphsList;
    MenuContent demo = new MenuContent();
    public ArrayList<ChapterActivity> mChapterActivityList;
    public ArrayList<ChapterDiscussion> mChapterDiscussionList;
    String fontsize;
    TextView mPlayPositionTextView, mDurationTextView, mButtonPlayBg, mButtonStop;
    SeekBar mSeekbarAudio;
    ImageView playOrPause;
    Utility.HomeTitle homeTitle;

    public ParagraphAdapter(Activity context, ArrayList<MenuContent> mParagraphsList, String fontsize, BaseFragment baseFragment) {
        super(context, R.layout.item_paragraph_flip, mParagraphsList);
        this.context = context;
        this.mParagraphsList = mParagraphsList;
        this.fontsize = fontsize;
        homeTitle = (Utility.HomeTitle) baseFragment.getActivity();
    }

    @Override
    public int getCount() {
        int activity, discussion;
        try {
            if (mChapterDiscussionList.size() > 0) discussion = 1;
            else discussion = 0;
        } catch (Exception e) {
            e.printStackTrace();
            discussion = 0;
        }
        try {
            if (mChapterActivityList.size() > 0) activity = 1;
            else activity = 0;
        } catch (Exception e) {
            e.printStackTrace();
            activity = 0;
        }
        return mParagraphsList.size() + discussion + activity + 1;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        demo = mParagraphsList.get(position-1);
        ViewHolder mViewHolder = null;
        final Chapter mChapter = AppConstants.mChapter;
//        mChapter.setChapterVideoLink("http://zinedroid.com/audio/VID-20170803-WA0021.mp4");
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_paragraph_flip, null);
            mViewHolder.coverImageView = (ImageView) convertView.findViewById(R.id.coverImageView);
            mViewHolder.mChapterNoTextView = (TextView) convertView.findViewById(R.id.mChapterNoTextView);
            mViewHolder.mChapterTitleTextView = (TextView) convertView.findViewById(R.id.mChapterTitleTextView);
            mViewHolder.mTitleTextView = (TextView) convertView.findViewById(R.id.mTitleTextView);
            mViewHolder.mChapterContextTextView = (WebView) convertView.findViewById(R.id.mChapterContextTextView);
            mViewHolder.mParagraphFrontLayout = (RelativeLayout) convertView.findViewById(R.id.mParagraphFrontLayout);
            mViewHolder.mDiscussionLayout = (LinearLayout) convertView.findViewById(R.id.mDiscussionLayout);
            mViewHolder.mExtraListView = (ListView) convertView.findViewById(R.id.mExtraListView);
            mViewHolder.mParagraphGallery = (FloatingActionButton) convertView.findViewById(R.id.mParagraphGallery);
            mViewHolder.mParagraphAudio = (FloatingActionButton) convertView.findViewById(R.id.mParagraphAudio);
            mViewHolder.mParagraphVideo = (FloatingActionButton) convertView.findViewById(R.id.mParagraphVideo);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //////////

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        if (!mChapter.getChapterAudioLink().equalsIgnoreCase("")) {
            mViewHolder.mParagraphAudio.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mParagraphAudio.setVisibility(View.INVISIBLE);
        }
        if (!mChapter.getChapterVideoLink().equalsIgnoreCase("")) {
            mViewHolder.mParagraphVideo.setVisibility(View.VISIBLE);
            mViewHolder.mParagraphVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse(mChapter.getChapterVideoLink());
                    intent.setDataAndType(data, "video/*");
                    context.startActivity(intent);
                }
            });
        } else {
            mViewHolder.mParagraphVideo.setVisibility(View.INVISIBLE);
        }
        if (mChapter.getChapterAudioLink() != "") {
            mViewHolder.mParagraphAudio.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mParagraphAudio.setVisibility(View.INVISIBLE);
        }
        mViewHolder.mParagraphAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog mAudioDialog = new Dialog(context);
                mAudioDialog.setContentView(R.layout.layout_audio);
                mAudioDialog.setCanceledOnTouchOutside(false);
                mAudioDialog.setCancelable(true);
                //   mPlayer = new MediaPlayer();
                mAudioDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        //   mPlayer.stop();
                        mPlayer.release();  //
                        mPlayer = null;
                        mAudioDialog.dismiss();
                    }
                });
                mAudioDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                        if (mPlayer != null) {
                            mPlayer.stop();
                        }

                    }
                });
                mSeekbarAudio = (SeekBar) mAudioDialog.findViewById(R.id.mSeekbarAudio);
                mSeekbarAudio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                        mPlayer.seekTo(i);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        int s = seekBar.getProgress();
                        mPlayer.seekTo(s);
                    }
                });
                mPlayPositionTextView = (TextView) mAudioDialog.findViewById(R.id.mPlayPositionTextView);
                mDurationTextView = (TextView) mAudioDialog.findViewById(R.id.mDurationTextView);
                mButtonPlayBg = (TextView) mAudioDialog.findViewById(R.id.mButtonPlayBg);
                mButtonPlayBg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);

                        mAudioDialog.dismiss();
                    }
                });
                mButtonStop = (TextView) mAudioDialog.findViewById(R.id.mButtonStop);
                mButtonStop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPlayer.release();  //
                        mPlayer = null;
                        mAudioDialog.dismiss();
                    }
                });
                playOrPause = (ImageView) mAudioDialog.findViewById(R.id.playOrPause);
                if (mPlayer != null) {
                    if (mPlayer.isPlaying()) {
                        //       Log.d("clicked","clicked");
                        mPlayer.stop();
                    }
                } else {
                    Log.d("elseclicked","clicked");
                    mPlayer = new MediaPlayer();
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    Thread t = new Thread() {

                        @Override
                        public void run() {
                            try {
                                while (!isInterrupted()) {
                                    Thread.sleep(400);
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                mPlayPositionTextView.setText(convertSecondsToHMmSs(mPlayer.getCurrentPosition()) + "");
                                                mSeekbarAudio.setProgress(mPlayer.getCurrentPosition());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            } catch (InterruptedException e) {
                            }
                        }
                    };

                    t.start();
                    playOrPause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mPlayer.isPlaying()) {
                                playOrPause.setImageResource(android.R.drawable.ic_media_play);
                                mPlayer.pause();
                            } else {
                                playOrPause.setImageResource(android.R.drawable.ic_media_pause);
                                mPlayer.start();
                            }
                        }
                    });
                    try {

//                    mPlayer.setDataSource("http://zinedroid.com/audio/a1.mp3");
                      //  mPlayer.setDataSource(myUrl.replaceAll(" ","%20"));
                        mPlayer.setDataSource(mChapter.getChapterAudioLink().replaceAll(" ","%20"));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    try {

                        mPlayer.prepare();
                        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                playOrPause.setImageResource(android.R.drawable.ic_media_pause);
                                mAudioDialog.show();
                                mPlayer.start();
                                mDurationTextView.setText(convertSecondsToHMmSs(mPlayer.getDuration()) + "");
                                mSeekbarAudio.setMax(mPlayer.getDuration());
                            }
                        });
                        //   mPlayer.prepareAsync();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "You might not set the URI correctlyyy!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "You might not set the URI correctlyy!", Toast.LENGTH_LONG).show();
                    }

                    //    mPlayer.prepareAsync();
                }
            }
        });

        if (position == 0) {
            mViewHolder.mParagraphGallery.setVisibility(View.INVISIBLE);
            mViewHolder.mDiscussionLayout.setVisibility(View.GONE);
            mViewHolder.mParagraphFrontLayout.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams mLayoutParams = mViewHolder.coverImageView.getLayoutParams();
            mLayoutParams.height = (int) (width / 1.485);
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mParams.setMargins(0, 25, 0, 25);
           /* mViewHolder.mChapterTitleTextView.setText(mChapter.getChapterTitle());
            mViewHolder.mChapterTitleTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));*/
            mViewHolder.coverImageView.setLayoutParams(mLayoutParams);
            try {
                Picasso.with(context).load(mChapter.getChapterImage()).into(mViewHolder.coverImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
           /* mViewHolder.mChapterNoTextView.setText(mChapter.getChapterNo());
            mViewHolder.mChapterNoTextView.setLayoutParams(mParams);*/
            if (fontsize.equals("LARGE")) {
                mViewHolder.mChapterTitleTextView.setTextSize(30);
                mViewHolder.mChapterTitleTextView.setText(mChapter.getChapterTitle());
                mViewHolder.mChapterTitleTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                mViewHolder.mChapterNoTextView.setTextSize(30);
                mViewHolder.mChapterNoTextView.setText(mChapter.getChapterNo());
                mViewHolder.mChapterNoTextView.setLayoutParams(mParams);

                mViewHolder.settings = mViewHolder.mChapterContextTextView.getSettings();
                mViewHolder.settings.setTextZoom(mViewHolder.settings.getTextZoom() + 30);
                mViewHolder.mChapterContextTextView.loadData(mChapter.getChapterContext(), "text/html; charset=UTF-8;", null);
            }
            else if(fontsize.equals("MEDIUM")||fontsize.equals("DEFAULT")){
                mViewHolder.mChapterTitleTextView.setTextSize(20);
                mViewHolder.mChapterTitleTextView.setText(mChapter.getChapterTitle());
                mViewHolder.mChapterTitleTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                mViewHolder.mChapterNoTextView.setTextSize(20);
                mViewHolder.mChapterNoTextView.setText(mChapter.getChapterNo());
                mViewHolder.mChapterNoTextView.setLayoutParams(mParams);
                mViewHolder.settings = mViewHolder.mChapterContextTextView.getSettings();
                mViewHolder.settings.setTextZoom(mViewHolder.settings.getTextZoom() + 10);
                mViewHolder.mChapterContextTextView.loadData(mChapter.getChapterContext(), "text/html; charset=UTF-8;", null);
            }
            else if(fontsize.equals("SMALL")){
                mViewHolder.mChapterTitleTextView.setTextSize(15);
                mViewHolder.mChapterTitleTextView.setText(mChapter.getChapterTitle());
                mViewHolder.mChapterTitleTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                mViewHolder.mChapterNoTextView.setTextSize(15);
                mViewHolder.mChapterNoTextView.setText(mChapter.getChapterNo());
                mViewHolder.mChapterNoTextView.setLayoutParams(mParams);
                mViewHolder.settings = mViewHolder.mChapterContextTextView.getSettings();
                mViewHolder.settings.setTextZoom(mViewHolder.settings.getTextZoom() -20);
                mViewHolder.mChapterContextTextView.loadData(mChapter.getChapterContext(), "text/html; charset=UTF-8;", null);
            }


        } else if (position > 0 && position < mParagraphsList.size() + 1) {
            mViewHolder.mDiscussionLayout.setVisibility(View.GONE);
            mViewHolder.mParagraphFrontLayout.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams mLayoutParams = mViewHolder.coverImageView.getLayoutParams();
            mLayoutParams.height = 0;
            mViewHolder.mChapterTitleTextView.setLayoutParams(mLayoutParams);
            mViewHolder.coverImageView.setLayoutParams(mLayoutParams);
            mViewHolder.mChapterNoTextView.setLayoutParams(mLayoutParams);
           /* if(mParagraphsList.get()){

            }
            */

            try {
                ArrayList<ImageGallery> mGalleryList = (ArrayList<ImageGallery>) ImageGallery.findWithQuery(ImageGallery.class, "Select * from image_Gallery where chapter_Id = " + mParagraphsList.get(position - 1).getChapterId() + " and content_Id = " + mParagraphsList.get(position - 1).getContentId());

                if (mGalleryList.size() > 0) {
                    mViewHolder.mParagraphGallery.setVisibility(View.VISIBLE);
                } else {
                    mViewHolder.mParagraphGallery.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mViewHolder.mParagraphGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  AppConstants.mMenuContentGallery=new MenuContent();
                    //AppConstants.mMenuContentGallery=demo;
                    ArrayList<ImageGallery> mGalleryList = (ArrayList<ImageGallery>) ImageGallery.findWithQuery(ImageGallery.class, "Select * from image_Gallery where chapter_Id = " + mParagraphsList.get(position - 1).getChapterId() + " and content_Id = " + mParagraphsList.get(position - 1).getContentId());
                    MenuContent mMenuContent = mParagraphsList.get(position - 1);
                    mMenuContent.setImageGallery(mGalleryList);
                    AppConstants.mMenuContentGallery = mMenuContent;
                    galleryfragment();
                }
            });

            if (fontsize.equals("LARGE")) {
                mViewHolder.settings = mViewHolder.mChapterContextTextView.getSettings();
                mViewHolder.settings.setTextZoom(mViewHolder.settings.getTextZoom() + 30);
                mViewHolder.mChapterContextTextView.loadData(getItem(position - 1).getContent(),
                        "text/html; charset=UTF-8;", null);
            } else if (fontsize.equals("MEDIUM")||fontsize.equals("DEFAULT")) {
                mViewHolder.settings = mViewHolder.mChapterContextTextView.getSettings();
                mViewHolder.settings.setTextZoom(mViewHolder.settings.getTextZoom() + 10);
                mViewHolder.mChapterContextTextView.loadData(getItem(position - 1).getContent(), "text/html; charset=UTF-8;", null);
            }
            else if (fontsize.equals("SMALL")) {
                mViewHolder.settings = mViewHolder.mChapterContextTextView.getSettings();
                mViewHolder.settings.setTextZoom(mViewHolder.settings.getTextZoom() -10);
                mViewHolder.mChapterContextTextView.loadData(getItem(position - 1).getContent(), "text/html; charset=UTF-8;", null);
            }

        } else if (position == mParagraphsList.size() + 1) {
            mViewHolder.mParagraphGallery.setVisibility(View.INVISIBLE);
            mViewHolder.mParagraphAudio.setVisibility(View.GONE);
            mViewHolder.mParagraphVideo.setVisibility(View.GONE);
            mViewHolder.mDiscussionLayout.setVisibility(View.VISIBLE);
            mViewHolder.mParagraphFrontLayout.setVisibility(View.GONE);
            mViewHolder.mTitleTextView.setText("Discussions");
            DiscussionAdapter mDiscussionAdapter = new DiscussionAdapter(context, mChapterDiscussionList);
            mViewHolder.mExtraListView.setAdapter(mDiscussionAdapter);
        } else if (position == mParagraphsList.size() + 2) {
            mViewHolder.mParagraphGallery.setVisibility(View.INVISIBLE);
            mViewHolder.mParagraphAudio.setVisibility(View.GONE);
            mViewHolder.mParagraphVideo.setVisibility(View.GONE);
            mViewHolder.mDiscussionLayout.setVisibility(View.VISIBLE);
            mViewHolder.mParagraphFrontLayout.setVisibility(View.GONE);
            mViewHolder.mTitleTextView.setText("Activity");
            ChapterActivityAdapter mChapterActivityAdapter = new ChapterActivityAdapter(context, mChapterActivityList);
            mViewHolder.mExtraListView.setAdapter(mChapterActivityAdapter);
        }
        return convertView;
    }

    public class ViewHolder {
        ListView mExtraListView;
        RelativeLayout mParagraphFrontLayout;
        LinearLayout mDiscussionLayout;
        TextView mChapterNoTextView, mChapterTitleTextView, mTitleTextView;
        WebView mChapterContextTextView;
        ImageView coverImageView;
        FloatingActionButton mParagraphVideo, mParagraphAudio, mParagraphGallery;
        WebSettings settings;
    }

    public static String convertSecondsToHMmSs(long seconds) {
        long ms = seconds / 1000;
        long s = ms % 60;
        long m = (ms / 60) % 60;
        long h = (ms / (60 * 60)) % 24;
        return String.format("%d:%02d:%02d", h, m, s);
    }

    public void galleryfragment() {
      /*  Intent i = new Intent(context, GalleryActivity.class);
        context.startActivity(i);
*/

        homeTitle.ChangeFragment(new GalleryFragment(), true, true);
        homeTitle.onTitleChange("Gallery Images");

    }


}
//22490739