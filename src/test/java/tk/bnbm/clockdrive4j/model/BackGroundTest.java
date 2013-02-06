package tk.bnbm.clockdrive4j.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class BackGroundTest {

	private static final String IMAGES_PATH = "target/classes/images/";

	public static class 基本的なテスト {

		private BackGround sut;

		@Before
		public void setUp() {
			// 環境依存を減らすため、現在pathから類推
			sut = new BackGround(IMAGES_PATH);
		}

		@Test
		public void 時間を与えられる() {
			sut.setTime(new Date());
		}

		@Test
		public void プロパティが読める() {
			assertThat(sut.getSrcImagePath(), is(notNullValue()));
			assertThat(sut.getDestImagePath(), is(notNullValue()));
			assertThat(sut.getBlendRatio(), is(notNullValue()));
		}

		@Test
		public void プロパティが読み込み可能なファイルパスを返す() {
			String path = sut.getSrcImagePath();
			assertThat(new File(path).exists(), is(true));
			path = sut.getDestImagePath();
			assertThat(new File(path).exists(), is(true));
		}

		@Test
		public void ファイル配列は名前アスキー昇順で格納される() {
			String prevName = null;
			for (File f : sut.imageFileNames) {
				if (prevName != null) {
					// FIXME lessThan()/graterThan()が無くなってる…ので、自力コンペア。
					assertTrue(prevName.compareTo(f.getName()) < 0);
				}
				prevName = f.getName();
			}
		}
	}

	@RunWith(Parameterized.class)
	public static class 背景4枚の時テスト {

		private BackGround sut;

		private int hour;
		private int minute;
		private int second;
		private String bgA;
		private String bgB;
		private double blend;

		/**
		 * コンストラクタ。パラメータを引数とする。
		 */
		public 背景4枚の時テスト(int hour, int minute, int second, String bgA,
				String bgB, double blend) {
			this.hour = hour;
			this.minute = minute;
			this.second = second;
			this.bgA = bgA;
			this.bgB = bgB;
			this.blend = blend;
		}

		@Before
		public void setUp() {
			sut = new BackGround(IMAGES_PATH);
		}

		@Parameters
		public static Collection<Object[]> getParams() {
			Object[][] params = {
					{ 0, 0, 0, "bg01.png", "bg02.png", 0D },
					{ 5, 59, 59, "bg01.png", "bg02.png",
							1.0D - (1.0D / 60.0D / 60.0D / 6.0D) },
					{ 6, 0, 0, "bg02.png", "bg03.png", 0D },
					{ 11, 59, 59, "bg02.png", "bg03.png",
							1.0D - (1.0D / 60.0D / 60.0D / 6.0D) },
					{ 12, 0, 0, "bg03.png", "bg04.png", 0D },
					{ 23, 59, 59, "bg04.png", "bg01.png",
							1.0D - (1.0D / 60.0D / 60.0D / 6.0D) } };
			return Arrays.asList(params);
		}

		@Test
		public void 時刻指定に応じた_適切な画像ファイルパスのペアとブレンド率を返す() {
			sut.imageFileNames = new File[] { new File("bg01.png"),
					new File("bg02.png"), new File("bg03.png"),
					new File("bg04.png") };

			Calendar c = Calendar.getInstance();
			c.set(2000, 1, 1, hour, minute, second);

			sut.setTime(c.getTime());
			assertThat(sut.getSrcImagePath(), is(bgA));
			assertThat(sut.getDestImagePath(), is(bgB));
			assertThat(sut.getBlendRatio(), is(blend));
		}
	}

	@RunWith(Theories.class)
	public static class 背景6枚の時テスト {

		// 意味履き違えてTheories(総当たり)で作ってしまったが、良いサンプルなので残しておく。
		// (本来であれば、Parameterized(パラメタライズ)テストとして作るべき。)

		private BackGround sut;

		@Before
		public void setUp() {
			// 環境依存を減らすため、現在pathから類推
			sut = new BackGround(IMAGES_PATH);
		}

		@DataPoints
		public static Object[][] VALUES = {
				{ 0, 0, 0, "bg01.png", "bg02.png", 0.0D },
				{ 3, 59, 59, "bg01.png", "bg02.png",
						1.0D - (1.0D / 60.0D / 60.0D / 4.0D) },
				{ 4, 0, 0, "bg02.png", "bg03.png", 0.0D },
				{ 7, 59, 59, "bg02.png", "bg03.png",
						1.0D - (1.0D / 60.0D / 60.0D / 4.0D) },
				{ 8, 0, 0, "bg03.png", "bg04.png", 0.0D },
				{ 23, 59, 59, "bg06.png", "bg01.png",
						1.0D - (1.0D / 60.0D / 60.0D / 4.0D) } };

		@Theory
		public void 時刻指定に応じた_適切な画像ファイルパスのペアとブレンド率を返す(Object[] values) {
			sut.imageFileNames = new File[] { new File("bg01.png"),
					new File("bg02.png"), new File("bg03.png"),
					new File("bg04.png"), new File("bg05.png"),
					new File("bg06.png") };

			int hour = ((Integer) values[0]).intValue();
			int minute = ((Integer) values[1]).intValue();
			int second = ((Integer) values[2]).intValue();
			String bgA = (String) values[3];
			String bgB = (String) values[4];
			double blend = ((Double) values[5]).doubleValue();

			Calendar c = Calendar.getInstance();
			c.set(2000, 1, 1, hour, minute, second);

			sut.setTime(c.getTime());
			assertThat(sut.getSrcImagePath(), is(bgA));
			assertThat(sut.getDestImagePath(), is(bgB));
			assertThat(sut.getBlendRatio(), is(blend));
		}
	}

}
