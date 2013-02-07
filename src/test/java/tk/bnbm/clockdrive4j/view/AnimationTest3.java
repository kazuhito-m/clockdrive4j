package tk.bnbm.clockdrive4j.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AnimationTest3 extends Application {

    // 描画制御系定数
    private static final long INTERVAL_NANO_SEC = 100000000L; // 描画間隔(ナノ秒)

    private static final SimpleDateFormat SDF_HMS = new SimpleDateFormat(
            "hh:mm:ss");

    @Override
    public void start(final Stage stage) {
        // 自身Windowの性質を決定
        stage.initStyle(StageStyle.TRANSPARENT); // 透明ダイアログ(描画は内容物に任す)

        // TODO 角を丸くしたコンテナ

        Group root = new Group();
        final Scene scene = new Scene(root, 512, 512, Color.TRANSPARENT);
        stage.setScene(scene);

        ImageView iv = new ImageView("file:./target/classes/images/bg01.png");
        root.getChildren().add(iv);
        final ImageView iv2 = new ImageView(
                "file:./target/classes/images/bg02.png");
        // iv2.setBlendMode(BlendMode.MULTIPLY);
        iv2.setOpacity(0.5);
        root.getChildren().add(iv2);

        final ImageView carImage = new ImageView(
                "file:./target/classes/images/car.png");
        root.getChildren().add(carImage);
        carImage.setX(0);
        carImage.setY(500);

        final Label dispTime = new Label(SDF_HMS.format(new Date()));
        dispTime.setFont(new Font("Verdana", 50L));
        dispTime.setTextFill(Color.LIGHTBLUE);

        DropShadow ds = new DropShadow();
        ds.setOffsetX(3);
        ds.setOffsetY(3);
        ds.setColor(Color.BLUE);
        dispTime.setEffect(ds);

        root.getChildren().add(dispTime);
        dispTime.relocate(10, scene.getHeight()
                - (dispTime.getFont().getSize() + 10));

        // root.getChildren().add(r);

        stage.show();

        // 描画ループ(Windows系におけるタイマーオブジェクト)
        new AnimationTimer() {
            private long timing; // 前回同期をとったタイミング。
            private double append = 0.05;

            @Override
            public void handle(long now) {
                // 変化なしフレームは破棄。
                long nowTiming = now / INTERVAL_NANO_SEC;
                if (timing == nowTiming) {
                    return;
                }
                timing = nowTiming;

                double opacity = iv2.getOpacity();
                if (opacity >= 1 || opacity <= 0) {
                    append = append * -1;
                }
                iv2.setOpacity(opacity + append);

                // 10度づつ、車を回転
                carImage.setRotate(carImage.getRotate() + 10);

                // 時刻を表示
                dispTime.setText(SDF_HMS.format(new Date()));

            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
