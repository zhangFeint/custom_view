package com.library.depending.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.library.depending.viewutils.ButtonUtils;

import java.util.Timer;
import java.util.TimerTask;

public class ExitProgram {
    private static ExitProgram exitProgram;

    /**
     * 单例模式
     */
    public static ExitProgram getInstance() {
        if (exitProgram == null) {
            exitProgram = new ExitProgram();
        }
        return exitProgram;
    }



}
