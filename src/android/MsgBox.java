package com.vaenow.appupdate.android;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goingtech.yishenqing.R;

import org.apache.cordova.LOG;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuoWen on 2016/1/20.
 */
public class MsgBox {
    public static final String TAG = "MsgBox";
    private Context mContext;
    private MsgHelper msgHelper;

    private Dialog noticeDialog;
    private AlertDialog downloadDialog;
    private ProgressBar downloadDialogProgress;
    private Dialog errorDialog;

    private Button redownloadid;
    private Button manual_installation;
    private TextView progressValues;

    public MsgBox(Context mContext) {
        this.mContext = mContext;
        this.msgHelper = new MsgHelper(mContext.getPackageName(), mContext.getResources());
    }

    /**
     * 显示软件更新对话框
     *
     * @param onClickListener
     */
    public Dialog showNoticeDialog(View.OnClickListener onClickListener) {
        if (noticeDialog == null) {
            LOG.d(TAG, "showNoticeDialog");
            // 构造对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(msgHelper.getString(MsgHelper.UPDATE_TITLE));
            builder.setMessage(msgHelper.getString(MsgHelper.UPDATE_MESSAGE));
            // 更新
//            builder.setPositiveButton(msgHelper.getString(MsgHelper.UPDATE_UPDATE_BTN), onClickListener);
            noticeDialog = builder.create();
        }

        if (!noticeDialog.isShowing()) noticeDialog.show();

        noticeDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        return noticeDialog;
    }


    /**
     * 显示软件下载对话框
     */
    @SuppressLint("ResourceType")
    public Map<String, Object> showDownloadDialog(View.OnClickListener onClickListenerNeg,
                                                  View.OnClickListener onClickListenerPos,
                                                  View.OnClickListener onClickListenerNeu,
                                                  boolean showDialog) {
        if (downloadDialog == null) {
            LOG.d(TAG, "showDownloadDialog");

            // 构造软件下载对话框
            AlertDialog.Builder builder = new Builder(mContext, R.style.AlertDialogCustom);
            View view = LayoutInflater.from(mContext).inflate(R.layout.appupdate_test, null);
            redownloadid = view.findViewById(R.id.dialog_button_redownload_id);
            manual_installation = view.findViewById(R.id.dialog_button_manual_installation_id);
            progressValues=view.findViewById(R.id.dialog_progress_values);
            /*注入样式*/
            redownloadid.setBackgroundResource(R.drawable.button_circle_shape_disonclick);
            manual_installation.setBackgroundResource(R.drawable.button_circle_shape_onclick);
//            redownloadid.setOnClickListener();

//            redownloadid.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    System.out.println("1111111111111111");
//                }
//            });

            manual_installation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("2222222222222222");
                }
            });
            builder.setView(view);
            downloadDialog= builder.create();
            Window window = downloadDialog.getWindow();
            window.setBackgroundDrawableResource(R.drawable.dialog_outer_frame);
//            builder.setTitle(msgHelper.getString(MsgHelper.UPDATING));
//            // 给下载对话框增加进度条
//            final LayoutInflater inflater = LayoutInflater.from(mContext);
//            View v = inflater.inflate(msgHelper.getLayout(MsgHelper.APPUPDATE_PROGRESS), null);
//
//            /* 更新进度条 */
//            downloadDialogProgress = (ProgressBar) v.findViewById(msgHelper.getId(MsgHelper.UPDATE_PROGRESS));
//            builder.setView(v);
//            // 取消更新
//            //builder.setNegativeButton(msgHelper.getString("update_cancel"), onClickListener);
//            //转到后台更新
//            builder.setNegativeButton(R.id.dialog_button_redownload_id, onClickListenerNeg);
//            builder.setNeutralButton(msgHelper.getString(MsgHelper.DOWNLOAD_COMPLETE_NEU_BTN), onClickListenerNeu);
//            builder.setPositiveButton(msgHelper.getString(MsgHelper.DOWNLOAD_COMPLETE_POS_BTN), onClickListenerPos);
//            downloadDialog = builder.create();
        }

        if (showDialog && !downloadDialog.isShowing()) downloadDialog.show();

        downloadDialog.setTitle(msgHelper.getString(MsgHelper.UPDATING));
        downloadDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        if (downloadDialog.isShowing()) {
//            downloadDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.VISIBLE); //Update in background
//            downloadDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setVisibility(View.GONE); //Install Manually
//            downloadDialog.getButton(DialogInterface.BUTTON_POSITIVE).setVisibility(View.GONE); //Download Again
        }

        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("dialog", downloadDialog);
        ret.put("progress", progressValues);
        return ret;
    }

    /**
     * 错误提示窗口
     *
     * @param errorDialogOnClick
     */
    public Dialog showErrorDialog(View.OnClickListener errorDialogOnClick) {
        if (this.errorDialog == null) {
            LOG.d(TAG, "initErrorDialog");
            // 构造对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(msgHelper.getString(MsgHelper.UPDATE_ERROR_TITLE));
            builder.setMessage(msgHelper.getString(MsgHelper.UPDATE_ERROR_MESSAGE));
            // 更新
//            builder.setPositiveButton(msgHelper.getString(MsgHelper.UPDATE_ERROR_YES_BTN), errorDialogOnClick);
            errorDialog = builder.create();
        }

        if (!errorDialog.isShowing()) errorDialog.show();

        return errorDialog;
    }


}
