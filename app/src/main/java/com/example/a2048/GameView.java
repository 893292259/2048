package com.example.a2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yushiko on 2018/4/17.
 */

    public class GameView extends GridLayout{
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<>();// 链表方便增加删除

    public GameView(Context context){
        super(context);
        initialView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialView();
    }

    private void initialView(){
        setColumnCount(4);// 设置每行最多4个
        setRowCount(4);
        setBackgroundColor(0xffbbada0);
        addCard(250, 250);// 把参数传过去
        start();
    }


    public void initalgame() {
        setOnTouchListener(new View.OnTouchListener() {

            private float startX, startY, changeX, changeY;

            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();// 起始的X坐标
                        startY = event.getY();// 起始的Y坐标
                        break;
                    case MotionEvent.ACTION_UP:
                        changeX = event.getX() - startX;// 改变的X坐标＝现在的－起始的
                        changeY = event.getY() - startY;// 改变的Y坐标＝现在的－起始的

                        // 若X的绝对值>Y的绝对值，则是左右移动，否则为上下移动，左上角坐标为(0,0)
                        if (Math.abs(changeX) > Math.abs(changeY)) {
                            if (changeX < -5) {
                                Left();
                            } else if (changeX > 5) {
                                Right();
                            }
                        } else {
                            if (changeY < -5) {
                                Up();
                            } else if (changeY > 5) {
                                Down();
                            }
                        }

                        break;
                }
                return true;
            }

        });

    }
    // 添加卡片
    private void addCard(int cardWidth, int cardHeight) {

        Card c;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(0);
                // 添加视图
                addView(c, cardWidth, cardHeight);
                cardsMap[x][y] = c;

            }
        }
    }
    // 开始游戏
    private void start() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }
        // 添加随机数
        addRandomNum();
        addRandomNum();
    }
    // 添加随机数
    private void addRandomNum() {

        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() <= 0) {
                    emptyPoints.add(new Point(x, y));// 把空位给emptypoints链表
                }
            }
        }
        // 随机把emptyPoints中的一个赋值，生成2的概率为9,4为1
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
    }
    private void Left() {

        boolean flag = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                for (int x1 = x + 1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x--;
                            flag = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().add(
                                    cardsMap[x][y].getNum());
                            flag = true;
                        }
                        break;
                    }
                }
            }
        }
        if (flag) {
            addRandomNum();
            IsFinish();
        }
    }

    private void Right() {

        Boolean flag = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {

                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            x++;
                            flag = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().add(
                                    cardsMap[x][y].getNum());
                            flag = true;
                        }
                        break;
                    }
                }
            }
        }
        if (flag) {
            addRandomNum();
            IsFinish();
        }
    }

    private void Up() {
        boolean flag = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            flag = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().add(
                                    cardsMap[x][y].getNum());
                            flag = true;
                        }
                        break;
                    }
                }
            }
        }
        if (flag) {
            addRandomNum();
            IsFinish();
        }
    }

    private void Down() {
        boolean flag = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {

                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            flag = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);
                            MainActivity.getMainActivity().add(
                                    cardsMap[x][y].getNum());
                            flag = true;
                        }
                        break;
                    }
                }
            }
        }

        if (flag) {
            addRandomNum();
            IsFinish();
        }
    }
    private void IsFinish() {

        boolean finish = true;

        ALL: for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                // 如果这个位置没有值，或者两两相等，则不结束，否则游戏结束
                if (cardsMap[x][y].getNum() == 0
                        || (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y]))
                        || (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y]))
                        || (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1]))
                        || (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))) {

                    finish = false;
                    break ALL;
                }
            }
        }

        if (finish) {
            new AlertDialog.Builder(getContext())
                    .setTitle("游戏结束!")
                    .setMessage("您的得分为：" + MainActivity.score)
                    .setPositiveButton("重来",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    MainActivity.getMainActivity().clear();
                                    start();
                                }
                            })
                    .setNegativeButton("退出",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    MainActivity.getMainActivity().finish();
                                }
                            }).show();
        }

    }
}
