package tk.bnbm.clockdrive4j.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * デバッグ用ダイアログのコントロールクラス。
 *
 * @author kazuhito_m
 */
public class DebugForm {

	// 自身コントロール群

	@FXML
	protected TextField targetDate;

	@FXML
	protected Button oneDayTravel;

	@FXML
	protected Button oneHourDrive;

	@FXML
	protected Button recordRoad;

	/**
	 * デバッグ対象の「アプリケーションメイン画面」<br>
	 * ※プログラムからこのクラスのインスタンスにアクセスできないため、static変数を使用して繋ぐ。
	 */
	protected static MainView debugTarget;

	@FXML
	protected void doOneDayTraval(ActionEvent a) {
		System.out.println("doOneDayTraval実行されたよ！");
	}

	@FXML
	protected void doOneHourDrive(ActionEvent a) {
		System.out.println("doOneHourDrive実行されたよ！");
	}

	@FXML
	protected void doRecordRoad(ActionEvent a) {
		System.out.println("doRecordRoad実行されたよ！");
	}

	// Setter/Getter

	/**
	 * @param debugTarget セットする debugTarget
	 */
	public static void setDebugTarget(MainView target) {
		debugTarget = target;
	}

	/**
	 * DebugFormの外側(stage)の形状を決め、初期化する。
	 *
	 * @param s
	 * @throws IOException
	 */
	public static void initStage(Stage s) throws IOException {
		s.setTitle("DebugForm");
		s.initStyle(StageStyle.UTILITY); // 作業用ダイアログ。
		Parent root = FXMLLoader.load(DebugForm.class
				.getResource("debugForm.fxml"));
		Scene scene = new Scene(root);
		s.setScene(scene);
		s.setResizable(false);
	}

}
