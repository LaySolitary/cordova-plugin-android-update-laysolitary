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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goingtech.yishenqing.R;

import org.apache.cordova.LOG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoWen on 2016/1/20.
 */
public class MsgBox {
    public static final String TAG = "MsgBox";
    private Context mContext;
    private MsgHelper msgHelper;
    private AlertDialog downloadDialog;

    private Button redownloadid;
    private Button manual_installation;
    private TextView progressValues;
    private TextView appValues;
    private ImageView imageView;
    private List<Version> queue = new ArrayList<Version>(1);
    public MsgBox(Context mContext) {
        this.mContext = mContext;
        this.msgHelper = new MsgHelper(mContext.getPackageName(), mContext.getResources());
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
            String newestAppVersion = CheckAppUpdate.getNewestAppVersion();

            // 构造软件下载对话框
            AlertDialog.Builder builder = new Builder(mContext, R.style.AlertDialogCustom);
            View view = LayoutInflater.from(mContext).inflate(R.layout.appupdate_test, null);

            redownloadid = view.findViewById(R.id.dialog_button_redownload_id);
            manual_installation = view.findViewById(R.id.dialog_button_manual_installation_id);
            progressValues=view.findViewById(R.id.dialog_progress_values);
            appValues = view.findViewById(R.id.app_version_tips);
            imageView = view.findViewById(R.id.dialog_cancel_id);
            /*注入样式*/
            redownloadid.setBackgroundResource(R.drawable.button_circle_shape_disonclick);
            manual_installation.setBackgroundResource(R.drawable.button_circle_shape_onclick);
            appValues.setText(newestAppVersion);
            /*
                手动安装 downloadDialogOnClickNeu
                重新下载 downloadDialogOnClickPos
                转到后台更新 downloadDialogOnClickNeg
            */
            imageView.setOnClickListener(onClickListenerNeg);
            redownloadid.setOnClickListener(onClickListenerPos);

            builder.setView(view);
            downloadDialog= builder.create();
            Window window = downloadDialog.getWindow();
            window.setBackgroundDrawableResource(R.drawable.dialog_outer_frame);

        }

        if (showDialog && !downloadDialog.isShowing()) downloadDialog.show();

        downloadDialog.setTitle(msgHelper.getString(MsgHelper.UPDATING));
        downloadDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失


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
/*    public Dialog showErrorDialog(View.OnClickListener errorDialogOnClick) {
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
    }*/

    /**
     * 关闭弹框
     */
    public void closeDialogShow() {
        downloadDialog.dismiss();
    }

}
