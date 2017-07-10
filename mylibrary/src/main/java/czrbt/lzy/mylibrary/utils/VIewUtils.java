package czrbt.lzy.mylibrary.utils;
// @author: lzy  time: 2016/11/08.


import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

/**
 * 使用时要在对应的地方写上destroy
 */
public class ViewUtils {

    private static Toast toast;
    private static Snackbar snackbar;
    private static ProgressDialog progress;
    private static MediaRecorder recorder;
    private static MediaPlayer mPlayer;
    public static void showToast( Context mContext,String msg){
        if (toast==null)
            toast= Toast.makeText(mContext,msg,Toast.LENGTH_SHORT);
        else
            toast.setText(msg);
        toast.show();
    }
    public static void showSnack( View view,String msg,String action , View.OnClickListener listener){
        if (snackbar==null)
            snackbar= Snackbar.make(view,msg,Snackbar.LENGTH_SHORT);
        else
            snackbar.setText(msg);
        if (action!=null&&!action.isEmpty()&&listener!=null)
            snackbar.setAction(action,listener);
        snackbar.show();
    }
    public static void showPro( Context mContext,String title,String msg){
        if (progress==null) {
            progress = new ProgressDialog(mContext);
            progress.setCancelable(false);
        }
        progress.setMessage(msg);
        progress.setTitle(title);
        if (!progress.isShowing())
            progress.show();
    }
    public static void dismissPro(){
        if (progress!=null&&progress.isShowing())
            progress.dismiss();
    }
    public static void setPro( Context mContext,int num,int total){
        if (progress==null) {
            progress = new ProgressDialog(mContext);
            progress.setCancelable(false);
        }
        progress.setMax(total);
        progress.setProgress(num);
        if (!progress.isShowing())
            progress.show();
    }
    /**http://www.cnblogs.com/mythou/
     * 定义音频编码
     * class MediaRecorder.AudioEncoder
     * 定义声音资源
     * class MediaRecorder.AudioSource
     * 回调接口，当录音出现错误的时候调用
     * interface  MediaRecorder.OnErrorListener
     * 回调接口，当录音出现错误的时候调用
     * interface MediaRecorder.OnInfoListener
     * 定义输出格式
     * class MediaRecorder.OutputFormat
     * 定义视频编码
     * class MediaRecorder.VideoEncoder
     * 定义视频source
     * class MediaRecorder.VideoSource
     * 获取音频信号源的最高值。
     * final static int getAudioSourceMax()
     * 最后调用这个方法采样的时候返回最大振幅的绝对值
     * int getMaxAmplitude()
     * 准备recorder 开始捕获和编码数据
     * void prepare()
     * 发布与此MediaRecorder对象关联的资源
     * void release()
     * 重新启动mediarecorder到空闲状态
     * void reset()
     * 设置录制的音频通道数。
     * void setAudioChannels(int numChannels)
     * 设置audio的编码格式
     * void setAudioEncoder(int audio_encoder)
     * 设置录制的音频编码比特率
     * void setAudioEncodingBitRate(int bitRate)
     * 设置录制的音频采样率。
     * void setAudioSamplingRate(int samplingRate)
     * 设置用于录制的音源。
     * void setAudioSource(int audio_source)
     * 辅助时间的推移视频文件的路径传递。
     * void setAuxiliaryOutputFile(String path)
     * 在文件描述符传递的辅助时间的推移视频
     * void setAuxiliaryOutputFile(FileDescriptor fd)
     * 设置一个recording的摄像头
     * void setCamera(Camera c)
     * 设置视频帧的捕获率
     * void setCaptureRate(double fps)
     * 设置记录会话的最大持续时间（毫秒）
     * void setMaxDuration(int max_duration_ms)
     * 设置记录会话的最大大小（以字节为单位）
     * void setMaxFileSize(long max_filesize_bytes)
     * 注册一个回调被调用发生错误时，同时录制
     * void setOnErrorListener(MediaRecorder.OnErrorListener l)
     * 注册要同时记录一个信息事件发生时调用的回调。
     * void setOnInfoListener(MediaRecorder.OnInfoListener listener)
     * 设置输出的视频播放的方向提示
     * void setOrientationHint(int degrees)
     * 传递要写入的文件的文件描述符
     * void setOutputFile(FileDescriptor fd)
     * 设置输出文件的路径
     * void setOutputFile(String path)
     * 设置在录制过程中产生的输出文件的格式
     * void setOutputFormat(int output_format)
     * 表面设置显示记录媒体（视频）的预览
     * void setPreviewDisplay(Surface sv)
     * 从一个记录CamcorderProfile对象的使用设置
     * void setProfile(CamcorderProfile profile)
     * 设置视频编码器，用于录制
     * void setVideoEncoder(int video_encoder)
     * 设置录制的视频编码比特率
     * void setVideoEncodingBitRate(int bitRate)
     * 设置要捕获的视频帧速率
     * void setVideoFrameRate(int rate)
     * 设置要捕获的视频的宽度和高度
     * void setVideoSize(int width, int height)
     * 开始捕捉和编码数据到setOutputFile（指定的文件）
     * void setVideoSource(int video_source)
     * 开始录音
     * void start()
     * 停止recording
     * void stop()
     */
    public static void StartSaveMedia(FileDescriptor PATH_NAME) throws IOException {
        if (recorder==null)
            recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(PATH_NAME);
        recorder.prepare();
        recorder.start();   // 开始录音
    }
    public static void stopMedia() {
        recorder.stop();
        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        recorder.release();
    }
    public static void StartMediaPlay(File mSampleFile) throws IOException {
        if (mPlayer==null)
            mPlayer = new MediaPlayer();
        mPlayer.setDataSource(mSampleFile.getAbsolutePath());
        mPlayer.setOnCompletionListener(mediaPlayer -> {
        });
        mPlayer.setOnErrorListener((mediaPlayer, i, i1) -> false);
        mPlayer.prepare();
        mPlayer.start();
    }
    public static void stopMediaPlay() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    public static void destroy(){
        toast=null;
        snackbar=null;
        progress=null;
        recorder=null;
        mPlayer=null;
    }

}
