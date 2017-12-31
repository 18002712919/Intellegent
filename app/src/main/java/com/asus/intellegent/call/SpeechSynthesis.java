package com.asus.intellegent.call;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SynthesizerListener;

import com.asus.intellegent.ui.activity.BaseDrawerActivity;

//合成监听器
public class SpeechSynthesis implements SynthesizerListener {

	private BaseDrawerActivity baseDrawerActivity;

	public SpeechSynthesis(Context context) {
		this.baseDrawerActivity = (BaseDrawerActivity) context;
	}

	//会话结束回调接口，没有错误时，error为null
	public void onCompleted(SpeechError error) {
		baseDrawerActivity.getSoundPool().play(baseDrawerActivity.getSoundstop(),
				0.7f, 0.7f, 0, 0, 1);
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		baseDrawerActivity.mHandler.isHandler();*/
		if (baseDrawerActivity.mHandler.handler != null) {
			baseDrawerActivity.mHandler.handler.sendEmptyMessageDelayed(0, 100);
		}
	}
	//缓冲进度回调
	//percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在 文本中结束位置，info为附加信息。
	public void onBufferProgress(int percent, int beginPos, int endPos, String info) {

	}
	//开始播放
	public void onSpeakBegin() {

	}
	//暂停播放
	public void onSpeakPaused() {
	}
	//播放进度回调  //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文 本中结束位置.
	public void onSpeakProgress(int percent, int beginPos, int endPos) {

	}
	//恢复播放回调接口
	public void onSpeakResumed() {

	}
	//会话事件回调接口
	public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

	}

}
