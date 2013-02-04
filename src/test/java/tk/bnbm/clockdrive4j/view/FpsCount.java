package tk.bnbm.clockdrive4j.view;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class FpsCount extends Application {

	@Override
	public void start(final Stage primaryStage) {

		final Scene scene = new Scene(new HBox(), 800, 600, Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.show();

		new AnimationTimer() {
			private long second;
			private int fpsCount;

			@Override
			public void handle(long now) {
				// fps計測
				long nowSecond = now / 1000000000L;
				fpsCount++;
				if (second != nowSecond) {
					System.out.println("fps : " + fpsCount);
					second = nowSecond;
					fpsCount = 0;
				}
			}
		}.start();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
