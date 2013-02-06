package tk.bnbm.clockdrive4j.view;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * デバッグ用ダイアログのコントロールクラス。
 * 
 * @author kazuhito_m
 */
public class DebugForm {

    // 定数。

    /** 時刻表示域の時刻書式 */
    private static final DateFormat FMT_DT = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");

    // 自身コントロール群

    @FXML
    protected TextField targetDate;

    @FXML
    protected Button oneDayTravel;

    @FXML
    protected Button oneHourDrive;

    @FXML
    protected Button recordRoad;

    // 自身プロパティ群

    private AnimationTimer timer;

    private String lastButtonCaption;

    /**
     * デバッグ対象の「アプリケーションメイン画面」<br>
     * ※プログラムからこのクラスのインスタンスにアクセスできないため、static変数を使用して繋ぐ。
     */
    protected static MainView sut;

    /**
     * 時刻値が変更されたら、親フォームの描画内容へ反映させる
     * 
     * @param e
     */
    @FXML
    protected void doTextChanged(Event e) {
        Date validTime;
        try {
            validTime = FMT_DT.parse(targetDate.getText());
            sut.draw(validTime);
        } catch (ParseException pe) {
            // 認識できないんだから、何もしなくて良いんじゃないか？
        }
    }

    /**
     * 「24時間ぐるっと旅をする！！」クリックイベント。
     * 
     * @param a イベントオブジェクト。
     */
    @FXML
    protected void doOneDayTraval(ActionEvent a) {
        buttonDisableControl(oneDayTravel); // ボタンの有効無効制御。
        simulate(24);
    }

    /**
     * 「1時間ドライブする！！」クリックイベント。
     * 
     * @param a イベントオブジェクト。
     */
    @FXML
    protected void doOneHourDrive(ActionEvent a) {
        buttonDisableControl(oneHourDrive); // ボタンの有効無効制御。
        simulate(1);
    }

    /**
     * 「"道"の軌跡を記録する。」クリックイベント。
     * 
     * @param a イベントオブジェクト。
     */
    @FXML
    protected void doRecordRoad(ActionEvent a) {
        buttonDisableControl(recordRoad); // ボタンの有効無効制御。
        System.out.println("doRecordRoad実行されたよ！");
    }

    /**
     * 早回しでシミュレート開始する（２４時間 or １時間）
     * 
     * @param hour 指定の時間。
     */
    protected void simulate(final int totalHours) {
        // まず、現在シミュレーション中じゃないことを確認
        if (timer == null) {
            final int intervalSeconds = (totalHours == 24 ? 90 : 15);
            // 計算用カレンダー用意。(故意に設定しなければ現在時刻)
            final Calendar cal = Calendar.getInstance();
            if (totalHours == 24) {
                cal.set(2000, 1, 1, 0, 0, 0); // 24時間回しなら、深夜0:00から。
            }
            // アニメーション開始。
            timer = new AnimationTimer() {
                private int i = 0;

                @Override
                public void handle(long l) {
                    // 描画更新
                    sut.draw(cal.getTime());
                    // 経過時間進行制御。
                    cal.add(Calendar.SECOND, intervalSeconds);
                    if ((i += intervalSeconds) > (totalHours * 60 * 60)) {
                        this.stop();
                        timer = null;
                        buttonDisableControl(null);
                        return;
                    }
                }
            };
            timer.start();
        } else {
            // タイマーがあるってことは「シミュレーション中」って事。シミュレーション中断。
            timer.stop();
            timer = null;
        }
    }

    /**
     * ボタンの有効・無効制御。<br>
     * 表示が「気持ち悪くなる」ので、最低限の「この最中は、これできない」を制御。
     * 
     * @param target
     */
    private void buttonDisableControl(Button target) {
        final String STOP_CAPT = "終了";
        Button[] buttons = new Button[] { oneDayTravel, oneHourDrive,
                recordRoad };
        // 退避しているボタンのラベルがあれば、現在は「無効」中。「復帰」に向ける。
        boolean toDesable = (lastButtonCaption == null);
        // ボタン全回し。
        for (Button b : buttons) {
            if (toDesable && b == target) {
                // 無効に向かってる、かつ生かす対象なら。
                lastButtonCaption = target.getText();
                target.setText(STOP_CAPT);
            } else if (!toDesable && STOP_CAPT.equals(b.getText())) {
                // 復帰に向かってる、かつ「退避中のボタン」なら。
                b.setText(lastButtonCaption);
                lastButtonCaption = null;
            } else {
                // それ以外のボタンは無効か復帰かどちらか。
                b.setDisable(toDesable);
            }
        }
    }

    // Setter/Getter

    /**
     * デバッグ対象を外部から与える。<br>
     * アプリケーションからは、このオブジェクトが見えないため、クラス変数を利用しセットする。
     * 
     * @param sut セットするデバッグ対象画面オブジェクト。
     */
    public static void setDebugTarget(MainView target) {
        sut = target;
    }

    /**
     * DebugFormの外側(stage)の形状を決め、初期化する。
     * 
     * @param s
     * @throws IOException
     */
    public static void initStage(Stage s) throws IOException {
        s.setTitle("DebugForm");
        s.initStyle(StageStyle.UTILITY); // 作業用ダイアログ。
        Parent root = FXMLLoader.load(DebugForm.class
                .getResource("debugForm.fxml"));
        Scene scene = new Scene(root);
        s.setScene(scene);
        s.setResizable(false);
        // 日付部分を初期化
        for (Object n : root.getChildrenUnmodifiable()) {
            if (n instanceof TextField) {
                TextField tf = (TextField) n;
                tf.setText(FMT_DT.format(new Date()));
            }
        }
    }
}
