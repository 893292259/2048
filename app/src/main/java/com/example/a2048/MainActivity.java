package com.example.a2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    public static int score;
    private static MainActivity mainactivity = null;
    private TextView tv_bestScore;
    private  GameView gameView;
    private int current_score = 0;
    private int currentBestScore;

    public MainActivity() {
        mainactivity = this;
    }

    public static MainActivity getMainActivity() {
        return mainactivity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inital();
        gameView = findViewById(R.id.game);
        gameView.initalgame();
    }

    public void inital() {
        text = (TextView) findViewById(R.id.text);
        tv_bestScore = (TextView) findViewById(R.id.tv_bestScore);

        BestScode bs = new BestScode(this);
        currentBestScore = bs.getBestScode();
        tv_bestScore.setText("" + currentBestScore);

        // WindowManager wm = (WindowManager) this
        // .getSystemService(Context.WINDOW_SERVICE);

        // int width = wm.getDefaultDisplay().getWidth();
        // int height = wm.getDefaultDisplay().getHeight();
        // iv=(ImageView) findViewById(R.id.image);
        // 生成图片
        // iv.setImageResource(R.drawable.myname);
        // iv.setScaleType(ImageView.ScaleType.FIT_XY);
        // iv.setAdjustViewBounds(true);
        // iv.setMaxHeight(height);// 屏幕高度
        // iv.setMaxWidth(width);// 屏幕宽度
    }

    // 清空分数
    public void clear() {
        score = 0;
        show();
    }

    // 显示分数
    public void show() {
        text.setText("" + score);

    }

    // 添加分数
    public void add(int s) {
        score += s;
        show();
        if (score > currentBestScore) {
            currentBestScore = score;
            BestScode bs = new BestScode(this);
            bs.setBestScode(currentBestScore);
            tv_bestScore.setText("" + currentBestScore);
        }

    }

    /**
     * 菜单、返回键响应
     */
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
