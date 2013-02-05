package tk.bnbm.clockdrive4j.view;

import java.awt.geom.Point2D;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import tk.bnbm.clockdrive4j.model.BackGround;
import tk.bnbm.clockdrive4j.model.Car;
import tk.bnbm.clockdrive4j.model.Cloud;
import tk.bnbm.clockdrive4j.model.Road;

public class MainView extends LayoutAndEventView {

	// 定数群。

	/** 左下時刻表示域の時刻書式 */
	private static final DateFormat FMT_TIME = new SimpleDateFormat("hh:mm:ss");

	// Modelオブジェクト群。

	protected BackGround bg;
	protected Road road;
	protected Car car;
	protected Cloud cloud;

	// 広域変数(プロパティ)
	private Date current;

	@Override
	public void initDisplayObjects(Scene scene) throws Exception {
		// Modelオブジェクト群初期化。
		bg = new BackGround("./target/classes/images/");
		road = new Road("./target/classes/datas/RoadData.csv");
		car = new Car(road);
		cloud = new Cloud(scene.getWidth(), scene.getHeight(), 15);
		// 固定な描画オブジェクトは設定しておく。
		// TODO イメージをリソースから読み込む。
		carImage.setImage(new Image("file:./target/classes/images/car.png"));
		// 初回の描画
		draw(new Date());

	}

	/**
	 * 描画(再描画)イベント。
	 */
	@Override
	public void repaint() {
		cloud.move(1);
		draw(new Date());
	}

	/**
	 * 指定された時刻の時計イメージをすべて描く。
	 *
	 * @param date 表示を行う時刻。
	 */
	private void draw(Date time) {
		// 現在時刻を記憶。
		current = time;
		// Modelの現在内容を描画更新。
		drawBackGround(current);
		drawCar(current);
		drawDigitalTime(current);
		// drawClouds();
	}

	/**
	 * 指定された時刻に応じた背景を描く。
	 *
	 * @param time 表示を行う時刻。
	 */
	private void drawBackGround(Date time) {
		// 時刻セット
		bg.setTime(time);
		// その時刻から割り出される背景を割り出す。
		Image srcImage = new Image("file:" + bg.getSrcImagePath());
		Image destImage = new Image("file:" + bg.getDestImagePath());
		// ブレンド元の背景画像を描く（不透明ベタ塗り）
		bgImage.setImage(srcImage);
		// ブレンド先の背景画像を描く（半透明）
		fgImage.setImage(destImage);
		fgImage.setOpacity(bg.getBlendRatio());
	}

	/**
	 * 指定された時刻に応じて、適切な位置と角度で車を描く。
	 *
	 * @param time 表示を行う時刻。
	 */
	private void drawCar(Date time) {
		// 時刻セット。
		car.setTime(time);
		// 自身のイメージの大きさを考慮し「中心にくる」考慮を入れた座標計算。
		Point2D.Double p = car.getPosition();
		double x = p.getX() - (carImage.getLayoutBounds().getWidth() / 3);
		double y = p.getY() - (carImage.getLayoutBounds().getHeight() / 3);
		// 描画更新
		carImage.setX(x);
		carImage.setY(y);
		carImage.setRotate(car.getAngle());
	}

	/**
	 * 指定された時刻に応じて、デジタル時刻を描く。
	 *
	 * @param time 表示を行う時刻。
	 */
	private void drawDigitalTime(Date time) {
		dispTime.setText(FMT_TIME.format(time));
	}

	/**
	 * アプリケーションのエントリポイント。
	 *
	 * @param args コマンドライン引数。
	 */
	public static void main(String[] args) {
		Application.launch(MainView.class, args);
	}

}
