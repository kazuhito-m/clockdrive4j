package tk.bnbm.clockdrive4j.model

import org.junit.*
import static org.junit.Assert.*

class RoadTest {

	Road road;

	@Before
	void setup() {
		// 環境依存を減らすため、現在pathから類推
		String path = "target/test-classes/RoadData.csv"
		road = new Road(path)
	}

	@Test
	void 時間を与えられ_道路上の座標を得られる() {
		def position = road.getRoadPosition(new Date());
	}


	//	@Test
	//	public void 与えた時刻に応じて文字盤上の角度が得られる(int hour, int minute, int second, double angle) {
	//		assertEquals(angle, Road.calcPositionRatio(new Date(2000, 1, 1, hour, minute, second)));
	//	}
	//
	//
	//	@Test
	//	public void 与えた時間に応じて_道路上の座標が変化する(int hourA, int minuteA, int secondA, int hourB, int minuteB, int secondB, String expectTo) {
	//		if (expectTo.contains('異')) {
	//			assertNotEquals(
	//					road.getRoadPosition(new Date(2000, 1, 1, hourA, minuteA, secondA))
	//					, road.getRoadPosition(new Date(2000, 1, 1, hourB, minuteB, secondB)));
	//		}
	//		else {
	//			assertNotEquals(
	//					road.getRoadPosition(new Date(2000, 1, 1, hourA, minuteA, secondA))
	//					, road.getRoadPosition(new Date(2000, 1, 1, hourB, minuteB, secondB)));
	//		}
	//	}
}
