package com.asus.intellegent.ui.activity;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.library.widget.TitleBar;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.sunflower.FlowerCollector;

import com.asus.intellegent.R;
import com.asus.intellegent.call.SpeechApp;
import com.asus.intellegent.open.ContactInfo;
import com.asus.intellegent.ui.adapter.FeedAdapter;
import com.asus.intellegent.voice.ClickFunction;
import com.asus.intellegent.voice.MessageHandler;
import com.asus.intellegent.voice.StaticDate;

import com.asus.intellegent.ui.adapter.MTAdapter;
import com.asus.intellegent.ui.permission.PermissionsChecker;
import com.asus.intellegent.understandjson.Session;
import com.asus.intellegent.voice.Comments;

import com.library.widget.RippleLayout;

/**
 * Created by ASUS on 2017/8/11.
 */

public class BaseDrawerActivity extends BaseActivity {


    public ImageView icon_voice_normal;
    public ImageView icon_translation;
    public ImageView icon_help;

    public LinearLayout outView;
    public LinearLayout contentRoot;
    public LinearLayout linearLayout;
    public LinearLayout layout;
    public LinearLayout content;

    public ListView listView;

    public RecyclerView rvComments;
    protected RecyclerView rvFeed;
    protected RecyclerView rView;
    protected FeedAdapter feedAdapter;
    protected MTAdapter mtAdapter;
    public Comments comments;

    private boolean isExit;
    //private SharedPreferences sp;
    public ClickFunction click;
    public List<ContactInfo> contactLists;
    private SoundPool soundPool;    //声明一个声音管理
    private int soundStart;    //创建某个声音对应的音频ID
    private int soundStop;
    private int soundProcessing;
    public Animation animation;
    public RippleLayout rippleLayout;
    public MessageHandler mHandler;
    protected final List<FeedAdapter.FeedItem> feedItems = new ArrayList<>();
    //protected final List<MTAdapter.FeedItem> mtItems = new ArrayList<>();

    public static SpeechApp app;
    // 语音听写对象
    protected SpeechRecognizer mIat;
    //语音合成
    protected SpeechSynthesizer mTts;
    //语义理解
    protected TextUnderstander mTextUnderstander;
    // 语音听写UI
    protected RecognizerDialog mIatDialog;

    private static final int REQUEST_CODE = 0; // 请求码

    public PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final String[] PERMISSIONS = setmPermissionsChecker();

    private static final String TAG = BaseActivity.class.getCanonicalName();
    public boolean ischeckNetworkState;

    @Override
    protected void onStart() {
        super.onStart();
        mPermissionsChecker = new PermissionsChecker(this);
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            //finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ischeckNetworkState = !checkNetworkState();
        getSetting();
        // 缺少权限时, 进入权限配置页面
        /*if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }*/
        boolean b = app.flowerCollector();
        if (b) {
            Log.e(TAG, "数据收集接口已开启页面统计模式");
        } else {
            Log.e(TAG, "数据收集接口已未启页面统计模式");
        }
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(this);
        FlowerCollector.onPageStart(TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(this);
        //session.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIat != null) {
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
        if (mTts != null) {
            mTts.destroy();
            mTts.stopSpeaking();
        }
        if (mTextUnderstander != null) {
            mTextUnderstander.cancel();
            mTextUnderstander.destroy();
        }
        if (mIatDialog != null) {
            mIatDialog.dismiss();
            mIatDialog.cancel();
        }
        //session.commit();
        //session.clear();
        //finish();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        super.initState(-1);
        speech();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        getSetting();
        super.removeAllActions();
        super.addAction(new TitleBar.ImageAction(R.drawable.set_up) {
            @Override
            public void performAction(View view) {
                BaseDrawerActivity.this.startIntent();
            }
        });
    }

    public SpannableString setSpan(String tmp) {
        SpannableString setSpan = new SpannableString(tmp);
        //设置斜体
        setSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 0, tmp.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return setSpan;
    }

    public void welcome() {
        ischeckNetworkState = !checkNetworkState();
        if (ischeckNetworkState) {
            updateView("请连接网络！", FeedAdapter.FeedItem.TYPE_RECEIVE);

        } else {
            Log.e("11111111", "1111111" + isWelcome);
            if (isWelcome) {
                String s = getRandomWelcomeTips();
                updateView(s, FeedAdapter.FeedItem.TYPE_RECEIVE);
                //app.setTts(s);
            }
            /**
             * 法语
             * result("D'où venez-vous？ De Hong-kong？",StaticDate.FALSE);
             * app.setmTts("D'où venez-vous？ De Hong-kong？");
             */
        }
    }

    /**
     * 初始化view。
     */
    public void initView() {
        //initState();
        overridePendingTransition(0, 0);
        contactLists = getContactLists(this);

        //加载动画
        animation = AnimationUtils.loadAnimation(this, R.anim.zoom);

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundStart = soundPool.load(this, R.raw.start, 1);
        soundStop = soundPool.load(this, R.raw.stop, 1);
        soundProcessing = soundPool.load(this, R.raw.processing, 1);

        rView = findViewById(R.id.rView);
        content = findViewById(R.id.content);

        rippleLayout = findViewById(R.id.ripple_layout);
        contentRoot = findViewById(R.id.contentRoot);
        outView = findViewById(R.id.outView);

        icon_voice_normal = findViewById(R.id.icon_voice_normal);
        icon_translation = findViewById(R.id.icon_translation);
        icon_help = findViewById(R.id.icon_help);

        rvComments = findViewById(R.id.rvComments);
        rvFeed = findViewById(R.id.rvFeed);
        listView = findViewById(R.id.listView);
        linearLayout = findViewById(R.id.linearLayout);
        layout = findViewById(R.id.layout);

        comments = new Comments(this, contactLists);
        click = new ClickFunction(this, app);
        icon_voice_normal.setOnClickListener(click);
        icon_help.setOnClickListener(click);
        icon_translation.setOnClickListener(click);
        rippleLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
        click.isLayout(StaticDate.FALSE);
    }

   /* public void setupFeed() {
        feedAdapter = new FeedAdapter(feedItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(linearLayoutManager);

        rvFeed.setAdapter(feedAdapter);
    }*/

    /**
     * 所需的全部权限
     */
    private static String[] setmPermissionsChecker() {

        String[] PERMISSIONS = new String[]{
                /*Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION*/

                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MODIFY_AUDIO_SETTINGS

        };
        return PERMISSIONS;
    }

    public void speech() {

        mHandler = new MessageHandler(this);

        //创建 SpeechSynthesizer 对象, 第二个参数：本地合成时传 InitListener可为null
        mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
        mTts = SpeechSynthesizer.createSynthesizer(this, mInitListener);
        //创建文本语义理解对象
        mTextUnderstander = TextUnderstander.createTextUnderstander(this, mInitListener);
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, mInitListener);

        app = new SpeechApp(this, mIat, mTts, mTextUnderstander, mIatDialog);

        app.speech();
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) super.getSystemService(CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        Log.e(TAG, "检测网络是否连接 " + flag);
        return flag;
    }

    public boolean isWelcome = false;
    public boolean isIats = false;
    private boolean is_announcements = true;

    public void isIat() {
        getSoundPool().play(getSoundstart(),
                0.7f, 0.7f, 0, 0, 1);
        if (isIats) {
            app.getIatDialog();
        } else {
            icon_voice_normal.startAnimation(animation);
            rippleLayout.startRippleAnimation();
            app.getIat();
        }
        Log.e(TAG, "111111111111" + StaticDate.isMessage);
    }

    //用户第一次进入，随机获取欢迎语
    public String getRandomWelcomeTips() {
        String[] welcome = getResources().getStringArray(R.array.welcome_tips);
        int index = (int) (Math.random() * welcome.length - 1);
        return welcome[index];
    }

    public void updateView(String result, int type) {
        click.isLayout(StaticDate.TRUE);
        feedItems.add(new FeedAdapter.FeedItem(result, type));
        feedAdapter.notifyItemInserted(feedItems.size() - 1);
        rvFeed.scrollToPosition(feedItems.size() - 1);
        //设置recycler的Item分割线
        //listview转gridview没有列的分割
        //rvFeed.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvFeed.setItemAnimator(new DefaultItemAnimator());
        //rvFeed.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));

        if (is_announcements && type == FeedAdapter.FeedItem.TYPE_RECEIVE && !ischeckNetworkState) {
            if (result.indexOf("URL") != -1) {
                result = result.substring(0, result.indexOf("URL"));
            }
            Log.e(TAG, "" + result);

            //语音合成
            app.setTts(result);
        }

        rippleLayout.stopRippleAnimation();
        icon_voice_normal.clearAnimation();
    }

    /**
     * 初始化监听器。
     */
    public InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Log.e("初始化失败，错误码：", "" + code);
            }
        }
    };

    public boolean get() {
        /*sp = getSharedPreferences("isFirst", MODE_PRIVATE);    //读写
        boolean isFirst = session.getBoolean("isFirst", true);
        //getBoolean(String key, boolean defValue) 偏好如果没有就取defValue的值。
        if (isFirst == true) {    //首次启动
            Log.e(isFirst + "", "11111111");
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();    //提交
        }*/
        boolean isFirst = session.getBoolean("isFirst", true);
        if (isFirst == true) {    //首次启动
            session.putBoolean("isFirst", false);
            session.commit();    //提交
        }
        return isFirst;
    }

    @Override
    protected boolean getSetting() {
        isIats = session.getBoolean(StaticDate.DICTATION_DIALOG_BOX_SETTING, isIats);
        is_announcements = session.getBoolean(StaticDate.ANNOUNCEMENTS_SETTING, is_announcements);
        Log.e("333333333", "1111" + is_announcements);
        String voice_name = session.getString(StaticDate.VOICE_NAME, "xiaoyuan");
        Log.e("11111111", "1" + voice_name);
        mTts.setParameter(SpeechConstant.VOICE_NAME, voice_name);
        Log.e("333333333", "1111" + isWelcome);
        isWelcome = session.getBoolean(StaticDate.WELCOME_SETTING, isWelcome);
        Log.e("333333333", "1111" + isWelcome);
        return true;
    }

    public void startIntent() {
        Bundle bundle = new Bundle();
        //bundle.putParcelable("key", app);
        Intent intent = new Intent(this, SettingActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        //设置切换动画
        super.overridePendingTransition(R.anim.in, R.anim.out);
    }


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "在按一次退出程序", Toast.LENGTH_LONG).show();
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            AppManager.getAppManager().getActivity(this);
            //第一个参数为进入的目标activity动画效果，第二个参数为退出的activity动画
            super.overridePendingTransition(R.anim.fade, R.anim.hold);
            /*super.finish();
            //第一个参数为进入的目标activity动画效果，第二个参数为退出的activity动画
            super.overridePendingTransition(R.anim.hold, R.anim.fade);
            System.exit(0);*/
        }
    }


    /**
     * 获取通信录中所有的联系人
     */
    public List<ContactInfo> getContactLists(Context context) {
        List<ContactInfo> lists = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        //moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactInfo contactInfo = new ContactInfo(name, number);
            lists.add(contactInfo);
            Log.e("通讯录的姓名:" + name, "通讯录的号码:" + number);
        }
        /*for (int i = 0; i < 10; i++) {
            //读取通讯录的姓名
            String name = "白" + i;
            String number = "180 0271 291" + i;
            ContactInfo contactInfo = new ContactInfo(name, number);
            lists.add(contactInfo);
            Log.e("通讯录的姓名:" + name, "通讯录的号码:" + number);
        }*/
        return lists;
    }


    public int getAppBarHeight() {
        return dip2px(56) + getStatusBarHeight();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = 0;
        }

        return result;
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public SoundPool getSoundPool() {
        return soundPool;
    }

    public int getSoundstart() {
        return soundStart;
    }

    public int getSoundstop() {
        return soundStop;
    }

    public int getSoundProcessing() {
        return soundProcessing;
    }

    public SpeechApp getApp() {
        return app;
    }

}
