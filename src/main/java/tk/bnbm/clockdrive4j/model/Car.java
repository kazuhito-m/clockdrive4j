package tk.bnbm.clockdrive4j.model;

import static java.lang.Math.PI;
import static java.util.Calendar.SECOND;

import java.awt.geom.Point2D;
import java.util.Calendar;
import java.util.Date;

/**
 * 「車」を司るクラス。
 * @author kazuhito_m
 */
public class Car {

    // 定数群。

    /** 計算用「全周」角度。 */
    private static final double ALL_ANGLE = 360.0D;

    /** 計算用「半周」角度。 */
    private static final double HARF_ANGLE = 180.0D;

    /** 計算用「垂直・90度」角度。 */
    private static final double RIGHT_ANGLE = 90.0D;

    /** 計測範囲(秒) */
    private static final int MEASURING_RANGE_SEC = 5;

    // プロパティ群。

    /** 与えられた時刻。 */
    private Date time;

    /** 車の位置や角度を算出するためのRoadインスタンス。 */
    private Road road;

    /**
     * コンストラクタ。 <br>
     * 引数にRoadインスタンスを受け取って、格納する。
     * 
     * @param road 自身が走ることになる"道路"オブジェクト。
     */
    public Car(final Road r) {
        this.road = r;
    }

    /**
     * 基準時刻を与える。
     * 
     * @param t 基準時刻。
     */
    public void setTime(final Date time) {
        this.time = (Date) time.clone();
    }

    /**
     * 車を描くべき位置を取得する。
     * 
     * @return 座標オブジェクト。
     */
    public Point2D.Double getPosition() {
        return road.getPosition(this.time);
    }

    /**
     * 車をの向き(描くべき角度を360度法)を取得する。 <br>
     * 前後２点から滑らかに補完する。
     * 
     * @return 角度(360度法)
     */
    public double getAngle() {

        Calendar c = Calendar.getInstance();

        // 自身が保持している「時刻」から前後５秒算出。
        c.setTime(this.time);
        c.add(SECOND, -MEASURING_RANGE_SEC);
        Date before5Sec = c.getTime();

        c.setTime(this.time);
        c.add(SECOND, +MEASURING_RANGE_SEC);
        Date after5Sec = c.getTime();

        // 座標算出。
        Point2D.Double pA = road.getPosition(before5Sec);
        Point2D.Double pB = road.getPosition(after5Sec);
        double xDiff = pA.getX() - pB.getX();
        double yDiff = pA.getY() - pB.getY();
        double rad = Math.atan2(yDiff, xDiff);
        double angle = rad * HARF_ANGLE / PI; // ラジアンを度に。
        return (angle + RIGHT_ANGLE + ALL_ANGLE) % ALL_ANGLE; // 0～360度を越えないように。
    }
}
