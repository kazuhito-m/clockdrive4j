package tk.bnbm.clockdrive4j.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AnimationTest1 extends Application {

    // 描画制御系定数
    private static final long INTERVAL_NANO_SEC = 500000000L; // 描画間隔(ナノ秒)

    // 描画オブジェクト群
    private Rectangle r = null;

    @Override
    public void start(final Stage stage) {
        // 自身Windowの性質を決定
        stage.initStyle(StageStyle.TRANSPARENT); // 透明ダイアログ(描画は内容物に任す)

        Group root = new Group();

        final Scene scene = new Scene(root, 512, 512, Color.TRANSPARENT);
        stage.setScene(scene);

        r = new Rectangle(1, 1, 250, 250);
        r.setFill(Color.BLUE);

        root.getChildren().add(r);

        stage.show();

        new AnimationTimer() {
            private long timing; // 前回同期をとったタイミング。

            @Override
            public void handle(long now) {
                // 不要なフレームは描画しない。
                if (timing == now / INTERVAL_NANO_SEC) {
                    return;
                } else {
                    timing = now / INTERVAL_NANO_SEC;
                    System.out.println("now : " + now);
                }

                if (r.getFill().equals(Color.BLUE)) {
                    r.setFill(Color.RED);
                } else {
                    r.setFill(Color.BLUE);
                }
                r.setX(r.getX() + 1);
                r.setY(r.getY() + 1);

            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
