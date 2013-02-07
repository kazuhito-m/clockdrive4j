package tk.bnbm.clockdrive4j.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 「背景の雲」を司るクラス。
 * @author kazuhito_m
 */
public class Cloud {

    // 定数群

    /** 画面半分(比率)。 */
    private static final double HARF_SCR = 0.5D;

    /** 画面二倍(比率)。 */
    private static final double DOUBLE_SCR = 2D;

    // プロパティ群

    /** 背景サイズ。 */
    private Point2D.Double backgroundSize;

    /** 雲の座標情報群。 */
    private List<Point2D.Double> positions;

    /**
     * コンストラクタ。
     * <br>背景サイズや、雲の数など、基本的に生成したい情報を渡す。
     * @param width 背景の横長。
     * @param height 背景の縦長。
     * @param count 雲の数。
     */
    public Cloud(final double width, final double height, final int count) {
        // 資源の初期化。
        backgroundSize = new Point2D.Double(width, height);
        positions = new ArrayList<Point2D.Double>();
        // 指定数分の雲を作成。
        for (int i = 0; i < count; i++) {
            double x = getRundum(width);
            double y = height * DOUBLE_SCR / count * i;
            positions.add(new Point2D.Double(x, y));
        }
    }

    /**
     * 引数に与えた上限から、その領域内でランダムな位置を返す。
     * @param limit 上限座標。
     * @return 計算後ランダムな座標。
     */
    protected float getRundum(final double limit) {
        return (float) (Math.random() * limit * DOUBLE_SCR - limit * HARF_SCR);
    }

    /**
     * 指定した経過時間で保持している雲座標情報を移動させる。
     * @param elapsed 経過時間。
     */
    public void move(final double elapsed) {
        for (Point2D.Double p : positions) {
            // 引数指定分、雲を移動(左下方向へ)
            double newX = p.getX() - elapsed;
            double newY = p.getY() - elapsed;

            double bgSizeX = backgroundSize.getX();
            double bgSizeY = backgroundSize.getY();

            if (newX < -bgSizeX * HARF_SCR) {
                newX += bgSizeX * DOUBLE_SCR;
            }
            if (newY < -bgSizeY * HARF_SCR) {
                newY += bgSizeY * DOUBLE_SCR;
                newX = getRundum(bgSizeX);
            }
            // 新しい座標に移動(インスタンスはそのまま)
            p.setLocation(newX, newY);
        }
    }

    // Setter/Getter群

    /**
     * 雲の座標情報群を返す。
     * @return 座標情報のリスト。
     */
    public List<Point2D.Double> getPositions() {
        return this.positions;
    }
}
