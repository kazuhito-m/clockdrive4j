package tk.bnbm.clockdrive4j.view;

import static jfx.messagebox.MessageBox.CANCEL;
import static jfx.messagebox.MessageBox.OK;

import java.util.Date;

import javafx.animation.AnimationTimer;

import jfx.messagebox.MessageBox;
import tk.bnbm.clockdrive4j.model.Road;

/**
 * DebugFormの「道の軌跡を記録する」機能を司るクラス。
 * @author kazuhito_m
 */
public class RoadDriveRecorder {

    private static final double INTERVAL_SECONDS = 5.0;

    private Road recordRoad;

    private Date recordStarted;

    private  AnimationTimer timer;

    public RoadDriveRecorder() {
        recordRoad = new Road();
    }

    public void record(MainView sut) {

        StringBuilder sb = new StringBuilder();
        sb.append("今から、マウスポインタの動きどおりに道の軌跡を記録します。");
        sb.append("\n■ OKをクリックした");
        sb.append(INTERVAL_SECONDS);
        sb.append("秒後から、軌跡の記録が開始されます。");
        sb.append("\n■ つまり、");
        sb.append(INTERVAL_SECONDS);
        sb.append("秒以内に'0時'にあたる位置にマウスポインタを置いてください。");
        sb.append("\n■ そして、");
        sb.append(INTERVAL_SECONDS);
        sb.append("秒間かけて'0時'から'1時'にあたる位置まで動かしていってください。");
        sb.append("\n■ まず'0時'から'1時'の部分を記録し、その後は");
        sb.append(INTERVAL_SECONDS);
        sb.append("秒ごとに、'1時'から'2時'の部分、'2時'から'3時'の部分、と移っていきます。");
        sb.append("\n■ '11時'から'12時'の部分を記録し終わったら、道の軌跡が出来上がります。");

        if (confirmMessage(sb.toString()) == CANCEL)
            return;

        // TODO ガイドラインとして、中心から１２等分する放射線を描く

        timer = createTimer();
        recordStarted = new Date();
        recordRoad.clearPosition();

    }

    protected int confirmMessage(String msg) {
        return MessageBox.show(null, msg, "道の軌跡を手動で記録する", OK + CANCEL);
    }

    protected AnimationTimer createTimer() {
        return new AnimationTimer() {

            @Override
            public void handle(long now) {
                // TODO 自動生成されたメソッド・スタブ

            }
        };
    }
    
    /**
     * 現在が「記録の最中」なのかを真偽値で判定する。
     * @return 記録中か否か。true:中。
     */
    public boolean isRecording() {
        return timer != null;
    }
}
