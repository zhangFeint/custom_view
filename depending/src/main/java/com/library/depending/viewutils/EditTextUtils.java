package com.library.depending.viewutils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * EditText提供了一个android:digits配置，它表示EditText能够接受的字符集合。
 * 场景一：只允许输入数字android:digits="0123456789"
 * 场景二：只允许输入数字和英文字母 android:digits="0123456789abcdefghigjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
 * 场景三：只允许输入数字，英文字母和@.两个符号android:digits="0123456789abcdefghigjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@."
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
        SharedPreferences sharedPreferences = activity.getSharedPreferences(STORAGE_NAME, activity.MODE_PRIVATE); //1、实例化首选项存储
        SharedPreferences.Editor editor = sharedPreferences.edit();  //2、使用edit()获取SharedPreferences.Editor对象，用来存储数据
        editor.putString(SAVE_USER, isSave ? logData.getAccount() : "");
        editor.putString(SAVE_PASSWORD, isSave ? logData.getPassword() : "");
        editor.commit(); //6、进行首选项提交数据
        return new LogData(sharedPreferences.getString(SAVE_USER, ""), sharedPreferences.getString(SAVE_PASSWORD, ""));
    }

    public class LogData {
        String account, password;

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

    /**
     * 手机号 用空格断开
     *
     * @param editText
     */
    public  void setPhoneFormat(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    editText.setText(sb.toString());
                    editText.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 银行卡号 用空格断开
     *
     * @param editText
     */
    public  void setBankCardFormat(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 4 && i != 9 && i != 14 && i != 19 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 5 || sb.length() == 10 || sb.length() == 15 || sb.length() == 20) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    editText.setText(sb.toString());
                    editText.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public void showInput(Activity mActivity,final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    public void hideInput(Activity mActivity) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE);
        View v = mActivity.getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
    public void deg(Activity mActivity) {
        mActivity. getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// 设置默认键盘

    }
}
