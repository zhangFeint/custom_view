package com.library.depending.viewutils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.InputType;
import android.widget.EditText;
/**
 *EditText提供了一个android:digits配置，它表示EditText能够接受的字符集合。
 * 场景一：只允许输入数字android:digits="0123456789"
 * 场景二：只允许输入数字和英文字母 android:digits="0123456789abcdefghigjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
 * 场景三：只允许输入数字，英文字母和@.两个符号android:digits="0123456789abcdefghigjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@."
 *
 */
public class EditTextUtils {
    private static EditTextUtils editTextUtils;
    public int visible = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, invisible = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;//EditText显示（隐藏）密码

    /**
     * 单例模式
     */
    public static EditTextUtils getInstance() {
        if (editTextUtils == null) {
            editTextUtils = new EditTextUtils();
        }
        return editTextUtils;
    }

    /**
     * EditText显示（隐藏）密码   显示：true  隐藏：false
     *
     * @param b
     */
    public void showHidePassword(EditText editText, boolean b) {
        if (b) {
            editText.setInputType(visible);
        } else {
            editText.setInputType(invisible);
        }
    }
    private static final String STORAGE_NAME = "Remember";
    private static final String SAVE_USER = "user";
    private static final String SAVE_PASSWORD = "password";
    /**
     * @param activity
     * @param isSave   保存账号密码： true  不保存： false

     */
    public LogData saveAccount(Activity activity, boolean isSave, LogData logData) {
        SharedPreferences    sharedPreferences = activity.getSharedPreferences(STORAGE_NAME, activity.MODE_PRIVATE); //1、实例化首选项存储
        SharedPreferences.Editor editor = sharedPreferences.edit();  //2、使用edit()获取SharedPreferences.Editor对象，用来存储数据
        editor.putString(SAVE_USER,isSave? logData.getAccount():"");
        editor.putString(SAVE_PASSWORD, isSave? logData.getPassword():"");
        editor.commit(); //6、进行首选项提交数据
       return new LogData(sharedPreferences.getString(SAVE_USER, ""),sharedPreferences.getString(SAVE_PASSWORD, "")) ;
    }

   public class LogData{
        String account,password;

       public LogData(String account, String password) {
           this.account = account;
           this.password = password;
       }

       public String getAccount() {
           return account;
       }

       public String getPassword() {
           return password;
       }
   }

}
