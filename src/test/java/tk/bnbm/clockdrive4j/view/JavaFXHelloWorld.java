package tk.bnbm.clockdrive4j.view;

import static jfx.messagebox.MessageBox.ABORT;
import static jfx.messagebox.MessageBox.CANCEL;
import static jfx.messagebox.MessageBox.ICON_INFORMATION;
import static jfx.messagebox.MessageBox.IGNORE;
import static jfx.messagebox.MessageBox.NO;
import static jfx.messagebox.MessageBox.OK;
import static jfx.messagebox.MessageBox.RETRY;
import static jfx.messagebox.MessageBox.YES;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import jfx.messagebox.MessageBox;

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