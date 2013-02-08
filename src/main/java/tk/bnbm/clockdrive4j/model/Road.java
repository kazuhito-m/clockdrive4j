package tk.bnbm.clockdrive4j.model;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * "道路"を司るクラス。
 * @author kazuhito_m
 */
public class Road {

    // 定数群。

    /** 半日の「時間」。 */
    private static final double HOUR_OF_HERFDAY = 12D;

    /** 半日の「分間」。 */
    private static final double MIN_OF_HERFDAY = 60D * 12D;

    /** 半日の「秒間」。 */
    private static final double SEC_OF_HERFDAY = 60D * 60D * 12D;

    /** 100パーセント */
    private static final double ALL = 1D;

    // プロパティ群。

    /** 道路上の位置の一覧。 */
    protected List<Point2D.Double> roadPositions;

    /**
     * コンストラクタ。
     * @param roadCsvFilePath 読み込むファイルのパス。
     * @throws IOException
     * @throws NumberFormatException
     */
    public Road(final String roadCsvFilePath) throws NumberFormatException,
            IOException {
        roadPositions = new ArrayList<Point2D.Double>();
        loadCsvFromFile(roadCsvFilePath);
    }

    /**
     * カンマ区切りかタブ区切りの道路上座標ＣＳＶファイルを読み込み、格納しておく。
     * @param roadCsvFilePath 読み込むCSVファイルのパス。
     * @throws IOException
     * @throws NumberFormatException
     */
    protected void loadCsvFromFile(final String roadCsvFilePath)
            throws NumberFormatException, IOException {

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {

            fis = new FileInputStream(roadCsvFilePath);
            isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);

            String line = null;
            while ((line = br.readLine()) != null) {
                // 1行をデータの要素に分割
                StringTokenizer st = new StringTokenizer(line, ",\t");
                if (st.hasMoreTokens()) {
                    String x = st.nextToken();
                    if (st.hasMoreTokens()) {
                        String y = st.nextToken();
                        Point2D.Double p = new Point2D.Double(
                                Double.parseDouble(x), Double.parseDouble(y));
                        roadPositions.add(p);
                    }
                }
            }

        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * 時刻に応じた角度を算出するための係数を、0～1.0の範囲で得る。
     * @param time 角度と対応する時刻。
     * @return 指定した時刻に居るべき角度。
     */
    public static double calcPositionRatio(final Date time) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        double hour = (double) cal.get(HOUR_OF_DAY);
        double min = (double) cal.get(MINUTE);
        double sec = (double) cal.get(SECOND);

        double ratio = (hour % HOUR_OF_HERFDAY / HOUR_OF_HERFDAY)
                + (min / MIN_OF_HERFDAY) + (sec / SEC_OF_HERFDAY);
        return ratio;
    }

    /**
     * 与えた時間に応じた、道路上の位置を得る。<br>
     * 近傍２点から、微妙な位置を滑らかに補完する。
     * @param time 指定時刻(時分秒を対象)。
     * @return 点オブジェクト。
     */
    public Point2D.Double getPosition(final Date time) {
        // 「指定時刻 / 一日」の比率計算。
        double ratioOfDay = calcPositionRatio(time);
        // 「道」データ中で「何番目相等か」を計算。
        double baseIndex = ratioOfDay * roadPositions.size();
        int idxA = (int) baseIndex % roadPositions.size();
        // 次番を計算。
        int idxB = (idxA + 1) % roadPositions.size();
        // 切り捨てた「当点と次点のどの辺か」を比率で記録。
        double distRatio = baseIndex - (int) baseIndex;

        // 「道」データから座標取り出し
        Point2D.Double from = roadPositions.get(idxA);
        Point2D.Double to = roadPositions.get(idxB);
        // 2点を補完した座標を計算
        double x = from.getX() * (ALL - distRatio) + to.getX() * distRatio;
        double y = from.getY() * (ALL - distRatio) + to.getY() * distRatio;

        return new Point2D.Double(x, y);
    }
}
