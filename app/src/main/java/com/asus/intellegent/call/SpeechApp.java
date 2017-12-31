package com.asus.intellegent.call;

import android.content.Context;
import android.util.Log;

import com.iflytek.aimic.ErrorCode;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.util.ContactManager;
import com.iflytek.sunflower.FlowerCollector;

//智能语音初始化
public class SpeechApp {

    private Context context;
    //private ContactManager mgr;        //获取 ContactManager 实例化对象

    // 语音听写对象
    private SpeechRecognizer mIat;
    private SpeechRecoListener mRecoListener;
    //语音合成
    private SpeechSynthesizer mTts;
    private SpeechSynthesis mSynListener;
    //语义理解
    private TextUnderstander mTextUnderstander;
    private SpeechTextUnderStander searchListener;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    private SpeechDialogListener mRecognizerDialogListener;

    private static String TAG = SpeechApp.class.getSimpleName();

    public SpeechApp(Context context, SpeechRecognizer mIat, SpeechSynthesizer mTts,
                     TextUnderstander mTextUnderstander, RecognizerDialog mIatDialog) {
        this.context = context;
        this.mIat = mIat;
        this.mTts = mTts;
        this.mTextUnderstander = mTextUnderstander;
        this.mIatDialog = mIatDialog;
    }

    /**
     * 初始化讯飞语音
     */

    public void speech() {

        mRecoListener = new SpeechRecoListener(context); //听写监听器
        mSynListener = new SpeechSynthesis(context);
        searchListener = new SpeechTextUnderStander(context);
        mRecognizerDialogListener = new SpeechDialogListener(context);

        //2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        /*mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");*/

        //开始合成
        //mTts.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynListener);

        //mIat.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
        //mIat.setParameter(SpeechConstant.SUBJECT, "asr");


        setParam();
    }

    public void speaking(String s) {
        mTts.setParameter(SpeechConstant.VOICE_NAME, s);
        mTts.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynListener);
    }

    public void getIat() {
        //3.开始听写
        int ret = mIat.startListening(mRecoListener);
        Log.d("讯飞:", "startlistening" + ret);
    }

    public void getIatDialog() {
        mIatDialog.setListener(mRecognizerDialogListener);
        // 显示听写对话框
        mIatDialog.show();
    }

    public void getMgr() {
        ContactManager mgr = ContactManager.createManager(context, mContactListener);
        //异步查询联系人接口，通过 onContactQueryFinish 接口回调 mgr.asyncQueryAllContactsName();
        mgr.asyncQueryAllContactsName();
    }

    public void setTts(String str) {
        /*BaseDrawerActivity baseDrawerActivity = (BaseDrawerActivity) context;
        if (!baseDrawerActivity.isIats) {
            if (baseDrawerActivity.getRippleLayout().isRippleAnimationRunning()) {
                baseDrawerActivity.getRippleLayout().stopRippleAnimation();
            }
            baseDrawerActivity.getIcon_voice_normal().clearAnimation();
        }*/
        //开始合成
        mTts.startSpeaking(str, mSynListener);
    }

    public void setTextUnderstander(String str) {
        //开始语义理解
        mTextUnderstander.understandText(str, searchListener);
    }


    /**
     * 参数设置
     *
     * @param
     * @return
     */
    private void setParam() {
        // 设置识别领域(听写）
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        // 设置语言
        //mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        //mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = "mandarin";
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");


		/*设置合成*/
        // 设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置发音人（更多在线发音人，用户可参见 附录13.2)
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyuan");
        //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");
        //设置音调
        mTts.setParameter(SpeechConstant.PITCH, "50");

        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        //mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");

        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

    }


    int ret = 0; // 函数调用返回值

    //获取联系人监听器。
    public ContactManager.ContactListener mContactListener = new ContactManager.ContactListener() {
        @Override
        public void onContactQueryFinish(String contactInfos, boolean changeFlag) {
            //指定引擎类型
            mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
            ret = mIat.updateLexicon("contact", contactInfos, lexiconListener);
            if (ret != ErrorCode.SUCCESS) {
                Log.d("讯飞", "上传联系人失败：" + ret);
            }
        }
    };

    //上传联系人监听器。
    private LexiconListener lexiconListener = new LexiconListener() {
        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
                Log.d("讯飞", error.toString());
            } else {
                Log.d("讯飞", "上传成功！");
            }
        }
    };

    //数据收集接口
    public boolean flowerCollector() {
            /*1.功能设置接口*/
        //1、开启调试模式
        FlowerCollector.setDebugMode(true);
        //开启后可以在 logcat 中看到相应的日志，默认不开启。
        //2、设置会话时间，默认 30s（单位为毫秒）。
        FlowerCollector.setSessionContinueMillis(30000);
        //3、开启页面统计模式 系统默认通过 OnResume 和 OnPause 统计页面，可以调用
        FlowerCollector.openPageMode(true);
        //开启 页面统计模式。开启后，可以通过 onPageStart(String pageName) ,onPageEnd(String pageName) 自定 义页面的名称。
        //4、开启自动获取位置信息
        FlowerCollector.setAutoLocation(true);
        //开启后会在每次发送日志时获取设备位置信息，默认开启。
        //5、开启自动捕获异常信息
        FlowerCollector.setCaptureUncaughtException(true);
        //开启后会在自动捕获程序的异常信息，默认关闭。 ---个性化接口---
        //6、 设置用户性别
        FlowerCollector.setGender(context, null);
        //7、 设置用户年龄
        FlowerCollector.setAge(context, 0);
        //8、 设置用户 id
        FlowerCollector.setUserID(context, null);
        /*3.错误信息记录 */
        FlowerCollector.setCaptureUncaughtException(true);

        /*5.数据上报策略 */
        FlowerCollector.updateOnlineConfig(context, null);
        return true;
    }

}
