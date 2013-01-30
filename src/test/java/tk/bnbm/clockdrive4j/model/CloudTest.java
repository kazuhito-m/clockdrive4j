package tk.bnbm.clockdrive4j.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import org.junit.Before;
import org.junit.Test;

/**
 * Cloudのテストクラス。
 *
 * @author kazuhito_m
 */
public class CloudTest {

	private Cloud sut;

	@Before
	public void setUp() {
		sut = new Cloud(512, 512, 10);
	}

	@Test
	public void 雲はすべてバラバラの位置() {
		Point2D.Double prev = null;
		for (Point2D.Double now : sut.getPositions()) {
			if (prev != null) {
				assertThat(now, is(not(prev)));
			}
			prev = now;
		}
	}

	@Test
	public void 徐々に移動していく() {
		// ※カバレッジ対策も含む。
		Point2D.Double prev = (Double) sut.getPositions().get(0).clone();
		sut.move(1);
		Point2D.Double now = sut.getPositions().get(0);
		assertThat(now, is(not(prev)));
	}

	@Test
	public void 雲が座標中の下限を下回る場合() {
		// Arrange 準備
		sut.getPositions().clear(); // 座標データを全て削除
		Point2D.Double p = new Point2D.Double(-512D, -512D); // 新しい座標データを作成。
		sut.getPositions().add(p); // 一つだけ足す

		// Act 実行
		sut.move(+1D);

		// Assert 検証
		assertThat(p.getX(), is(not(0D)));	// ランダム要素を含むため、0でなければ
		assertTrue(p.getY() >= 0D);
	}
}
