package tk.bnbm.clockdrive4j.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AnimationTest2 extends Application {

	// 描画制御系定数
	private static final long INTERVAL_NANO_SEC = 100000000L; // 描画間隔(ナノ秒)

	@Override
	public void start(final Stage stage) {
		// 自身Windowの性質を決定
		stage.initStyle(StageStyle.TRANSPARENT); // 透明ダイアログ(描画は内容物に任す)
		// stage.initStyle(StageStyle.UNDECORATED); // ウィンドウバーがない奴

		Group root = new Group();

		ImageView iv = new ImageView("file:./target/classes/images/bg01.png");
		root.getChildren().add(iv);
		final ImageView iv2 = new ImageView(
				"file:./target/classes/images/bg02.png");
		// iv2.setBlendMode(BlendMode.MULTIPLY);
		iv2.setOpacity(0.5);
		root.getChildren().add(iv2);

		final Scene scene = new Scene(root, 512, 512, Color.TRANSPARENT);
		stage.setScene(scene);

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

			}
		}.start();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
