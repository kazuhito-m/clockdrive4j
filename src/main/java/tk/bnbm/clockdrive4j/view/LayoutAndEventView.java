package tk.bnbm.clockdrive4j.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * 本アプリケーション、メインビューの基底抽象クラス。<br>
 *
 * 描画物のレイアウトと各種イベントのキックを責務とする。<br>
 * Modelオブジェクトとの紐づけ・描画反映はおこなわず、継承先クラスが行うものとする。
 *
 * @author kazuhito_m
 */
public abstract class LayoutAndEventView extends Application {

	// 描画オブジェクト群

	protected ImageView bgImage;

	protected ImageView fgImage;

	protected ImageView carImage;

	protected Label dispTime;

	protected AnimationTimer timer;

	/**
	 * 描画物体の初期化を行うイベント。<br>
	 *
	 * 描画物体とそのレイアウトを行うのはこの本クラスの責務だが、<br>
	 * その初期化(ImageViewなら元画像のセット）などは、一度きりのここで行う。
	 */
	public abstract void initDisplayObjects(Scene scene) throws Exception;

	/**
	 * 描画(再描画)を行うイベント。<br>
	 *
	 * 描画タイミング(描画間隔)を決めるのは、本クラスであり、 <br>
	 * F継承先クラスでは「描画する方法」のみに注力して実装することを期待している。
	 */
	public abstract void repaint();

	/**
	 * 自身Viewの表示物に対し初期化を行う。
	 *
	 * @throws Exception
	 */
	protected void initView(Stage stage) throws Exception {
		// 下地(シーンとグループ)作成。
		Group root = new Group();
		Scene scene = new Scene(root, 512, 512);
		// 描画物を作成とともにグループへ突っ込む。
		root.getChildren().add((bgImage = new ImageView()));
		root.getChildren().add((fgImage = new ImageView()));
		root.getChildren().add((carImage = new ImageView()));
		root.getChildren().add((dispTime = new Label()));

		stage.setScene(scene);
		stage.setTitle("CleckDrive");

		// TODO 描画域の角を丸める。

		// 継承先での描画物の初期化。
		initDisplayObjects(scene);
	}

	/**
	 * 自身Viewの活動を開始する。
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// 自身描画物の初期化
		this.initView(stage);
		// 表示
		stage.show();
		// 描画ループ
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				System.out.println("now : " + now);
				repaint();
			}
		};
		timer.start();
	}

}
