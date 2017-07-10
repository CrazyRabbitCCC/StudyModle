package czrbt.lzy.record.person;
// @author: lzy  time: 2016/10/28.


import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import czrbt.lzy.record.view.viewHelper.LoginHelper;

public class LoginPerson extends BasePerson<LoginHelper> {


    public LoginPerson(Context context, LoginHelper view) {
        super(context, view);
    }

    @Override
    public void onCreate() {

    }
    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }


    public void login(){
        showProgress("正在登录，请稍候。。。");
//        ApiService.User user=new ApiService.User(account, password);
        Observable.timer(3000 , TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(aLong -> Observable.just(LogIn()))
                .subscribe(flag ->{
                    dismissProgress();
                    if (flag) viewHelper.afterLoading();
                    else showToast("账号密码错误");
                }, e-> {
                    Logger.e("xxx", e.toString());
                    dismissProgress();
                    showToast("网络连接超时");
                });

    }

    private  boolean LogIn() {
        return true;
    }

    public boolean checkEmail() {
        return !(viewHelper.getAccount().contains("@")||viewHelper.getAccount().contains("!")||viewHelper.getAccount().contains("."));
    }

    public boolean checkPassword() {
        return viewHelper.getPassword().length() > 4;
    }
}