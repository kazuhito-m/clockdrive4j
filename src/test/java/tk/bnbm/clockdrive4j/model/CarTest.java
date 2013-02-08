package tk.bnbm.clockdrive4j.model;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class CarTest {

    public static class ファイルから読み出した固定値でのテスト {

        private Road road;
        private Car sut;

        @Before
        public void setUp() throws Exception {
            // 環境依存を減らすため、現在pathから類推
            road = new Road("target/classes/datas/roadData.csv");
            sut = new Car(road);
        }

        @Test
        public void 時間を与えられる() {
            sut.setTime(new Date());
        }

        @Test
        public void プロパティが読める() {

            sut.setTime(new Date());

            Point2D.Double position = sut.getPosition();
            double angle = sut.getAngle();

            assertThat(position, is(notNullValue()));
            assertThat(angle, is(not(0D)));

        }
    }

    @RunWith(Parameterized.class)
    public static class ファイル読出固定値を使ってのパラメタライズド {

        private int hourA;
        private int minuteA;
        private int secondA;
        private int hourB;
        private int minuteB;
        private int secondB;
        private String expectTo;

        // テスト対象
        private Road road;
        private Car sut;

        public ファイル読出固定値を使ってのパラメタライズド(int hourA, int minuteA, int secondA,
                int hourB, int minuteB, int secondB, String expectTo) {
            this.hourA = hourA;
            this.minuteA = minuteA;
            this.secondA = secondA;
            this.hourB = hourB;
            this.minuteB = minuteB;
            this.secondB = secondB;
            this.expectTo = expectTo;
        }

        @Before
        public void setUp() throws Exception {
            // 環境依存を減らすため、現在pathから類推
            road = new Road("target/classes/datas/roadData.csv");
            sut = new Car(road);
        }

        @Parameters
        public static Collection<Object[]> getParam() {
            Object[][] params = { { 0, 0, 0, 0, 0, 30, "異なるはず" },
                    { 0, 1, 0, 0, 2, 0, "異なるはず" },
                    { 1, 59, 0, 2, 0, 0, "異なるはず" },
                    { 11, 59, 0, 12, 0, 0, "異なるはず" },
                    { 0, 0, 0, 12, 0, 0, "同じはず" },
                    { 6, 0, 0, 18, 0, 0, "同じはず" } };
            return Arrays.asList(params);
        }

        @Test
        public void 与えた時間に応じて_角度が変化する() {
            Calendar c = Calendar.getInstance();

            // Act(実行)
            c.set(2001, 1, 1, hourA, minuteA, secondA);
            sut.setTime(c.getTime());
            Double angleA = sut.getAngle();

            c.set(2001, 1, 1, hourB, minuteB, secondB);
            sut.setTime(c.getTime());
            Double angleB = sut.getAngle();

            // Assert(検証)
            if (expectTo.contains("異")) {
                assertThat(angleA, is(not(angleB)));
            } else {
                assertThat(angleA, is(angleB));
            }
        }
    }

    @RunWith(Parameterized.class)
    public static class 円軌道に固定しての角度テスト {

        // テスト対象。
        private Car sut;

        // パラメタライズドで受け取る値群。
        private int hour;
        private double angle;

        public 円軌道に固定しての角度テスト(int hour, double angle) {
            this.hour = hour;
            this.angle = angle;
        }

        @Before
        public void setUp() throws Exception {
            // 計算により決まった道のりを蓄えるRoadオブジェクトを作成。
            Road road = new Road(null) {
                static final int ONE_CYCLE = 360;
                static final int HERF_CYCLE = ONE_CYCLE / 2;

                @Override
                protected void loadCsvFromFile(final String roadCsvFilePath)
                        throws NumberFormatException, IOException {
                    // 90度(真上)から始まって、右回りで1周する「座標の数列」を作成。
                    int base = ONE_CYCLE + 90;
                    for (int i = 0; i < ONE_CYCLE; i++) {
                        double angle = (double) (base - i) % ONE_CYCLE;
                        double rad = angle / HERF_CYCLE * PI;
                        double x = 100D * cos(rad); // 半径は100
                        double y = -100D * sin(rad); // JavaFXの座標系は上が負数なので
                        this.roadPositions.add(new Point2D.Double(x, y));
                    }
                }
            };
            sut = new Car(road);
        }

        /**
         * パラメータを返す。{時間,度}の組み合わせ。ある程度の抜き打ち。
         * @return パラメータ群。
         */
        @Parameters
        public static Collection<Object[]> getParams() {
            Object[][] params = { { 0, 270D }, { 3, 0D }, { 6, 90D },
                    { 9, 180D }, { 12, 270D }, { 18, 90D }, { 24, 270D },
                    { 1, 300D }, { 2, 330D }, { 19, 120D } };
            return Arrays.asList(params);
        }

        @Test
        public void 円軌道を描く際の時間別角度テスト() {
            // n時以外は固定した日付時刻を作成。車オブジェクトに設定。
            Calendar cal = Calendar.getInstance(); // 計算用カレンダー。
            cal.set(2013, 2, 8, this.hour, 0, 0);
            sut.setTime(cal.getTime());

            // Act - 実行。角度を取得。
            double result = sut.getAngle();
            // 小数点第3位四捨五入。(doubleの計算誤差考慮)
            result = BigDecimal.valueOf(result).setScale(2, ROUND_HALF_UP)
                    .doubleValue();
            // / Assert 検証。
            assertThat(result, is(this.angle));
        }
    }
}