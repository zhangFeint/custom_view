package com.library.depending.viewutils;

import android.text.InputType;
import android.widget.EditText;

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
}
