package tk.bnbm.clockdrive4j.view;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * DebugFormのテストクラス。<br>
 *
 * 「UIのカバレッジも向上できないか？」という観点から、実装してみたもの。<br>
 * お試しなのであまり意味は無い。
 *
 * @author kazuhito_m
 *
 */
public class DebugFormTest {

	DebugForm sut; // テスト対象オブジェクト

	@Before
	public void setUp() throws Exception {
	}


	@Ignore
	@Test
	public final void startによりステージオブジェクトがセットされる() throws Exception {
		// FIXME Mockでのテスト技法を期待。(現在勉強不足で使えず…）
		// Arrange - 準備
		Stage stage = new Stage();	//
		// Act - 実行
//		sut.start(stage);
		// Assert - 検証
		//		assertThat(sut.self, is(stage));
	}

}
