package com.czrbc.gank;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;


/**
 * @author Gavin
 * @date 2017/07/06.
 */

public class RxJava2Test {
    CompositeDisposable mCompositeDisposable;

    @Before
    public void init(){
        mCompositeDisposable=new CompositeDisposable();
    }
    @Test
    public void just(){
        Observable<String> observable = Observable.just("hello", "world");
        observable.map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                return s+",";
            }
        }).flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull String s) throws Exception {
                return Observable.just(s,"what","do","you","want","to","do","?\n");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.print(s+" ");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println(throwable.getMessage());
                throwable.printStackTrace();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("complete");
            }
        });
    }

    @Test
    public void jsut1(){
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
                System.out.println("subscribe");
            }

            @Override
            public void onNext(Integer value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("complete");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }
    @Test
    public void just2(){
     Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            System.out.println("emit 1");
            emitter.onNext(1);
            System.out.println("emit 2");
            emitter.onNext(2);
            System.out.println("emit 3");
            emitter.onNext(3);
            System.out.println("emit complete");
            emitter.onComplete();
            System.out.println("emit 4");
            emitter.onNext(4);
        }
    }).subscribe(new Observer<Integer>() {
         int i=0;
         @Override
         public void onSubscribe(Disposable d) {
             mCompositeDisposable.add(d);
             System.out.println("subscribe");
         }

         @Override
         public void onNext(Integer value) {
             i++;
             System.out.println(value);
             if (i==2)
             {
                 System.out.println("dispose");
                 System.out.println(mCompositeDisposable.isDisposed());
                 mCompositeDisposable.dispose();
                 System.out.println(mCompositeDisposable.isDisposed());
                 mCompositeDisposable.clear();
                 System.out.println(mCompositeDisposable.isDisposed());
             }
         }

         @Override
         public void onError(Throwable e) {
             System.out.println("error");
             System.out.println(e.getMessage());
             e.printStackTrace();
         }

         @Override
         public void onComplete() {
             System.out.println("complete");
         }
    });
    }
}