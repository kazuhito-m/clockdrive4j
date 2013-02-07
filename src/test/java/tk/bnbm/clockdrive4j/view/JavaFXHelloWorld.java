package tk.bnbm.clockdrive4j.view;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import jfx.messagebox.MessageBox;

import static jfx.messagebox.MessageBox.*;

public class JavaFXHelloWorld extends Application {
	public static void main(String[] args) {
		Application.launch(JavaFXHelloWorld.class, args);
	}

	@Override
	public void start(Stage stage) {
		HBox hbox = new HBox();
		Scene scene = new Scene(hbox);
		hbox.getChildren().add(new Label("Hello, World"));

		stage.setScene(scene);
		stage.setTitle("Hello");
		stage.show();

		messageBoxTest(stage);

		stage.close();

	}

	/**
	 * エンドレスにメッセージボックス出し続けていく系の？
	 * @param stage
	 */
	private void messageBoxTest(Stage stage) {
		int[] buttunNo = new int[] { OK, CANCEL, YES, NO, ABORT, RETRY, IGNORE };
		int option = ICON_INFORMATION;
		for (int n : buttunNo)
			option += n;
		int ret = MessageBox.show(stage, "メッセージ", "上部タイトル", option);

		if (MessageBox.show(stage, "押されたボタンは " + ret + " です。続けますか？",
				"MessageBoxの結果表示", YES + NO) == YES) {
			messageBoxTest(stage);
		}
	}
}