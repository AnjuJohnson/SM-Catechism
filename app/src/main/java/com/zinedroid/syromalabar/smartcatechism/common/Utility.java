package com.zinedroid.syromalabar.smartcatechism.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.zinedroid.syromalabar.smartcatechism.Base.BaseFragment;
import com.zinedroid.syromalabar.smartcatechism.R;

/**
 * Created by Cecil Paul on 31/8/17.
 */

public class Utility {
    public interface HomeTitle {
        public void onTitleChange(String Title);

        public void ChangeFragment(BaseFragment fragment, boolean replace, boolean addBackstack);
    }
    public static Dialog showProgressBar(Context mContext) {
        Dialog progress = new Dialog(mContext);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.progress);
        progress.setCancelable(true);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return progress;
    }

    public interface menuIconChange{
        public void iconchange(BaseFragment fragment);
    }

}
