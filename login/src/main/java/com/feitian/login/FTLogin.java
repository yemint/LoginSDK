package com.feitian.login;

import android.util.ArrayMap;

import java.util.Map;

/**
 * 登录sdk
 *
 * @author yemint
 */
public class FTLogin {

    private String phoneNum;
    private String passWord;
    private String TAG="FTLogin";

    /**
     * 登录验证函数
     *
     * @param phoneNum 账号
     * @param passWord 密码
     * @return
     */
    public void onLogin(String phoneNum, String passWord, CnHttpUtils.CallBacks callBacks) {
        this.phoneNum = phoneNum;
        this.passWord = passWord;


        int num = formatPhoneNum();
        int psd = formatPassword();

        if (num != 0) {
            callBacks.onFailure(new Exception(num + ""));
            CnLogUtils.e(TAG+".onLogin() 手机号格式不对或为空",num+"");
            return;
        }
        if (psd != 0) {
            callBacks.onFailure(new Exception(psd + ""));
            CnLogUtils.e(TAG+".onLogin() 密码格式不对或为空",psd+"");

            return;
        }


        getUserInfo(callBacks);
    }

    private int formatPassword() {
        if (passWord.isEmpty()) {
            return AppConstants.WE;
        }

        if (!CnFormat.isPassword(passWord)) {
            return AppConstants.WF;
        }
        return 0;
    }

    private int formatPhoneNum() {
        if (phoneNum.isEmpty()) {
            return AppConstants.NE;
        }
        if (!CnFormat.isMobile(phoneNum)) {

            return AppConstants.NF;
        }
        return 0;
    }

    /**
     * 登录数据请求
     *
     * @param callBacks
     */
    private void getUserInfo(CnHttpUtils.CallBacks callBacks) {

        Map<String, String> map = new ArrayMap<>();
        map.put("", phoneNum);
        map.put("", passWord);

        CnHttpUtils.getInstance().get(AppConstants.LOGIN_URL, map, callBacks, UserInfo.class);
    }
}
